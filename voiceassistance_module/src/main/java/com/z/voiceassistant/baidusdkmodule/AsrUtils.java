package com.z.voiceassistant.baidusdkmodule;

import com.z.voiceassistant.JniGateway;
import com.z.tinyapp.utils.common.TextUtil;
import com.z.tinyapp.utils.logs.sLog;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;
import speech.StreamApi;

/**
 * Created by ASUS User on 2018/5/26.
 */
public class AsrUtils {
    private final static String TAG = "sunAsrUtils";
    private static List<StreamApi.APIRequest> sApiRequestQueue = new LinkedList<StreamApi.APIRequest>();
    private static final String s_chunkKey = "com.baidu.open_asr_test";//保持和Cpp的Demo一致
    private static final String s_asr_id = "00000000-54b3-e7c7-0000-000046bf9999";
    private static final long ASR_TASK_ID = -12345;//保持和Cpp的Demo一致

    private static boolean isStop;

    private static void addApiRequest_param(){
        StreamApi.APIRequest apiRequest = new StreamApi.APIRequest();
        apiRequest.apiReqType = StreamApi.API_REQ_TYPE_PARAM;
        apiRequest.param = new StreamApi.ApiParam();
        apiRequest.param.cuid = "your_cuid";
//        apiRequest.param.appid= "";//保持和Cpp的Demo一致，不填
//        apiRequest.param.apikey= "";//保持和Cpp的Demo一致，不填
        apiRequest.param.chunkKey = s_chunkKey;
        apiRequest.param.format = "pcm";
        apiRequest.param.sampleRate = 16000;
        apiRequest.param.taskId = ASR_TASK_ID;
        sApiRequestQueue.add(0, apiRequest);
    }

    private static void addApiRequest_data(byte[] data){
        StreamApi.APIRequest apiRequest = new StreamApi.APIRequest();
        apiRequest.apiReqType = StreamApi.API_REQ_TYPE_DATA;
        apiRequest.data = new StreamApi.ApiData();
        apiRequest.data.len = data.length;
        apiRequest.data.postData = data;
        sApiRequestQueue.add(apiRequest);
    }

    private static void addApiRequest_cancel(){
        StreamApi.APIRequest apiRequest = new StreamApi.APIRequest();
        apiRequest.apiReqType = StreamApi.API_REQ_TYPE_CANCEL;
        apiRequest.cancel = new StreamApi.ApiCancel();
        sApiRequestQueue.add(0, apiRequest);
    }

    private static void addApiRequest_last(){
        StreamApi.APIRequest apiRequest = new StreamApi.APIRequest();
        apiRequest.apiReqType = StreamApi.API_REQ_TYPE_LAST;
        apiRequest.last = new StreamApi.ApiLast();
        sApiRequestQueue.add(apiRequest);
    }

    private static void buildTestQueues(){
        addApiRequest_param();
        readVoiceData();
        addApiRequest_last();
    }

    private static void readVoiceData(){
//        InputStream in = null;
        JniGateway.JniStartStreaming();
        try {
//            in = new FileInputStream("/sdcard/txz/1.pcm");
            byte[] buffer = new byte[1024];
//            while(true){
//                int read = in.read(buffer, 0, buffer.length);
//                if (read <= 0){
//                    break;
//                }
//                if (read % 2 != 0){
//                    sLog.w(TAG, "read % 2 != 0");
//                }
//                byte[] data = new byte[read];
                byte[] data = JniGateway.ReadStream(1024);

//                System.arraycopy(buffer,0, data,0,read);
                System.arraycopy(buffer,0, data,0,data.length);
                addApiRequest_data(data);
//            }
        }catch (Exception e){
            sLog.e(TAG, "readVoiceData : " + e.toString());
        }

//        if (in != null){
//            try {
//                in.close();
//            }catch (Exception e){
//
//            }
//        }
    }

    public static void build_up_stream(){
        buildTestQueues();

        String url = "http://audiotest.baidu.com/open_asr_test/up?id=" + s_asr_id;
        Map<String, String> header_map = new HashMap<String, String>();
        header_map.put("Transfer-Encoding", "chunked");

        RequestBody requestBody = new RequestBody() {
            @Override
            public MediaType contentType() {
                return null;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sLog.d(TAG, "writeTo enter");
                //send param request

                while (!sApiRequestQueue.isEmpty()) {
                    StreamApi.APIRequest apiRequest = sApiRequestQueue.remove(0);
                    byte[] pb_data = null;
                    try {
                        pb_data = JniGateway.ReadStream(1024);
                    } catch (Exception e) {
                        sLog.e(TAG, "pb : " + e.toString() + ":" + apiRequest.apiReqType);
                        continue;
                    }
                    if (pb_data != null) {
                        try {
                            sLog.d(TAG, "writeTo:" + pb_data.length);
                            sink.writeIntLe(pb_data.length);//测试验证LV格式中，L使用的是小端模式
                            sink.write(pb_data);
                            sink.flush();
                        }catch (Exception e){
                            sLog.e(TAG, e.toString() + ":" + apiRequest.apiReqType);
                        }

                    }
                    try {
                        Thread.sleep(100);
                    }catch (Exception e){

                    }
                }

            }
        };

        BaiDuFunc.do_post(url, header_map, requestBody, null);

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        build_down_stream();
    }

    public static void build_down_stream(){
        String url = "http://audiotest.baidu.com/open_asr_test/down?id=" + s_asr_id;
        Map<String, String> header_map = new HashMap<String, String>();
        header_map.put("Content-Type", "application/octet-stream");
        header_map.put("Accept-Encoding", "gzip,deflate");
        FormBody.Builder fromBody_builder = new FormBody.Builder();
        fromBody_builder.add("test_id", "");
        BaiDuFunc.do_post(url, header_map, fromBody_builder.build(), new IResponeDataCallBack() {
            @Override
            public int read(Buffer sink, long byteCount) {
                try {
                    while (sink.size() > 0) {
                        long len = sink.readIntLe();//测试验证LV格式中，L使用的是小端模式
                        byte[] data = sink.readByteArray(len);
                        StreamApi.APIResponse apiResponse = StreamApi.APIResponse.parseFrom(data);
                        sLog.e(TAG, "resp_body_read : \n" + apiResponse.toString());
                        if(!TextUtil.isEmpty(apiResponse.toString())){
                            isStop=true;
                        }

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }

    public  interface  IResponeDataCallBack{
        public int read(Buffer sink, long byteCount);
    }

    public  static class  UserResponseBody extends ResponseBody{
        private ResponseBody mResponseBody = null;
        private BufferedSource mBufferedSource = null;
        private IResponeDataCallBack mResponeBodyDataCallBack = null;

        public  UserResponseBody(ResponseBody body, IResponeDataCallBack callBack){
            mResponseBody = body;
            mResponeBodyDataCallBack = callBack;
        }

        @Override
        public long contentLength() {
            return mResponseBody.contentLength();
        }

        @Override
        public MediaType contentType() {
            return mResponseBody.contentType();
        }

        @Override
        public BufferedSource source() {
            if (mBufferedSource == null){
                mBufferedSource = Okio.buffer(source(mResponseBody.source()));
            }
            return mBufferedSource;
        }

        private Source source(Source source){
            return new ForwardingSource(source) {
                @Override
                public long read(Buffer sink, long byteCount) throws IOException {
                    long bytesRead = super.read(sink, byteCount);
                    if (mResponeBodyDataCallBack != null){
                        mResponeBodyDataCallBack.read(sink, byteCount);
                    }
                    return bytesRead;
                }
            };
        }
    }

    public static byte[] genSendData(byte[] input, boolean big){
        if (input == null || input.length == 0){
            return null;
        }
        byte[] data = new byte[4 + input.length];
        for (int i = 0; i < 4; ++i){
            if (big){
                data[3- i] = (byte)((input.length >> (8*i)) & 0x00ff);
            }else{
                data[i] = (byte)((input.length >> (8*i)) & 0x00ff);
            }
        }
        System.arraycopy(input, 0, data, 4, input.length);

        return data;
    }

    public static byte[] genReceivedData(byte[] input, boolean big){
        if (input == null || input.length == 0){
            return null;
        }
        byte[] data = new byte[input.length - 4];
        System.arraycopy(input, 4, data, 0, input.length - 4);

        return data;
    }

}
