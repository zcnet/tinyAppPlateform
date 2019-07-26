package com.aoshuotec.voiceassistant.baidusdkmodule;

import android.os.Bundle;
import android.text.TextUtils;

import com.airiche.volume.AiVolume;
import com.aoshuotec.voiceassistant.JniGateway;
import com.aoshuotec.voiceassistant.constants.BaiDConstant;
import com.aoshuotec.voiceassistant.listener.IDataCallBack;
import com.aoshuotec.voiceassistant.utils.Logg;
import com.google.protobuf.nano.MessageNano;
import com.z.tinyapp.utils.logs.sLog;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import okhttp3.Call;
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
import speech.StreamApi.APIRequest;
import speech.StreamApi.APIResponse;
import speech.StreamApi.ApiData;
import speech.StreamApi.ApiLast;
import speech.StreamApi.ApiParam;


public class AsrDemo {

    private String asrId = "00000000-54b3-e7c7-0000-000046bf9999";
    private ApiParam apiParam = new ApiParam();
    // replace it with your own audio stream/buffer

    private boolean isClose = false;
    private boolean isOutClose = false;

    private IDataCallBack mCallBack;
    private AiVolume mAiVolume;

    private Call mUploadCall;
    private Call mDownCall;

    AsrDemo(IDataCallBack callBack, AiVolume aiVolume) {
        mCallBack = callBack;
        mAiVolume = aiVolume;
    }

    void setApiParam(ApiParam apiParam) {
        this.apiParam = apiParam;
    }

    void setAsrId(String asrId) {
        this.asrId = asrId;
    }

    String getAsrId() {
        return asrId;
    }

    String getUploadUrl() {
        return "http://audiotest.baidu.com/open_asr_test/up?id=" + getAsrId();
    }

    String getDownloadUrl() {
        return "http://audiotest.baidu.com/open_asr_test/down?id=" + getAsrId();
    }

    /**
     * 请求参数请务必根据实际情况填写
     * <p>
     * Build the HEAD segment of the Okhttp RequestBody
     *
     * @return StreamApi.APIRequest
     */
    private APIRequest buildApiRequestParam() {
        APIRequest apiRequest = new APIRequest();
        apiRequest.apiReqType = StreamApi.API_REQ_TYPE_PARAM;

        ApiParam param = new ApiParam();
        // Activate to get cuid. Check activation procedure for more.激活后获取 cuid/uuid. 每个用户的cuid需要保证唯一
        param.cuid = "your_cuid";
        // Assinged by Baidu，百度分配，测试使用 com.baidu.open_asr_test
        param.chunkKey = "com.baidu.open_asr_test";
        // Format for audio，音频格式, 支持pcm、wav、opus，建议采用opus
        param.format = TextUtils.isEmpty(apiParam.format) ? "pcm" : apiParam.format;
        // Sample rate, 采样率, 只支持16000
        param.sampleRate = 16000;
        // Task Id. 由百度分配，用于标识第三方服务类型, 联调环境使用"-12345"
        param.taskId = (long) -12345;

        param.earlyReturn = true;

        // appid, appkey not used for now.暂时不填
//		param.apikey = "";
//		param.appid = "";
        // Params for semantic meaning. See doc for more about parameters for sematnics
        // 如果语义解析，必须添加此参数。否则，得不到语义数据
        if (!TextUtils.isEmpty(apiParam.pam)) {
            param.pam = apiParam.pam;
        }
        apiRequest.param = param;
//        logger.info(apiRequest.toString());

        return apiRequest;
    }

    /**
     * Build the data segment of the Okhttp RequestBody
     *
     * @param data byte[]
     * @return StreamApi.APIRequest
     */
    private APIRequest buildApiRequestData(byte[] data) {
        APIRequest apiRequest = new APIRequest();
        apiRequest.apiReqType = StreamApi.API_REQ_TYPE_DATA;
        apiRequest.data = new ApiData();
        apiRequest.data.len = data.length;
        apiRequest.data.postData = data;

        return apiRequest;
    }

    /**
     * Build the last segment of the Okhttp RequestBody
     *
     * @return StreamApi.APIRequest
     */
    private APIRequest buildApiRequestLast() {
        APIRequest apiRequest = new APIRequest();
        apiRequest.apiReqType = StreamApi.API_REQ_TYPE_LAST;
        apiRequest.last = new ApiLast();

        return apiRequest;
    }

    /**
     * Write request chunk to the Okhttp RequestBody
     *
     * @param sink       BufferedSink
     * @param apiRequest StreamApi.APIRequest
     */
    private void writeToSink(BufferedSink sink, APIRequest apiRequest) {
        byte[] pb_data = null;
        try {
            pb_data = MessageNano.toByteArray(apiRequest);
        } catch (Exception e) {
            sLog.i(TAG, "writeToSink: 1");
        }
        if (pb_data != null) {
            try {
                //测试验证LV格式中，L使用的是小端模式
                sink.writeIntLe(pb_data.length);
                sink.write(pb_data);
                sink.flush();

            } catch (Exception e) {
                Logg.e(TAG, "writeToSink: 2", e);
                exit();
                Bundle bundle = new Bundle();
                bundle.putInt(BaiDConstant.RESULT_CODE, BaiDConstant.DOWNLOAD_NETWORK_READ_FAIL);
                if (mCallBack != null) {
                    mCallBack.getBaiDDataBack(bundle);
                }
            }
        }
    }

    /**
     * @param sink BufferedSink
     */
    private void addVoiceData(BufferedSink sink) {
        byte[] arr = JniGateway.ReadStream(3200);
        int percent = mAiVolume.calcVolume(arr, arr.length);

        if (percent <= 10) {
            percent = 10;
        } else if (percent > 70) {
            percent = 100;
        } else {
            percent += 30;
        }

        Bundle bundle = new Bundle();
        bundle.putInt(BaiDConstant.RESULT_CODE, BaiDConstant.SPEECH_RECOGNIZED_SUCCESS);
        bundle.putInt(BaiDConstant.VOLUME_NUMBER, percent);
        if (mCallBack != null) {
            mCallBack.getBaiDDataBack(bundle);
        }
        writeToSink(sink, buildApiRequestData(arr));
    }

    void upload() throws Exception {

        isClose = false;
        isOutClose = false;

        RequestBody requestBody = new RequestBody() {
            @Override
            public MediaType contentType() {
                return null;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {

                writeToSink(sink, buildApiRequestParam());
                while (!isClose) {
                    addVoiceData(sink);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        sLog.e(TAG, "writeTo: --->", e);
                    }
                }
                sLog.i(TAG, "writeTo: no close");
                writeToSink(sink, buildApiRequestLast());
            }
        };

        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Transfer-Encoding", "chunked");

        mUploadCall = Http2Utils.doPost(getUploadUrl(), headerMap, requestBody, null);
        if (mUploadCall == null) {
            Bundle bundle = new Bundle();
            bundle.putInt(BaiDConstant.RESULT_CODE, BaiDConstant.ASR_ENGINE_BUSY);
            if (mCallBack != null) {
                mCallBack.getBaiDDataBack(bundle);
            }
        }
    }

    private String finalStr = null;

    public void download() {
        isClose = false;
        isOutClose = false;

        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type", "application/octet-stream");
        headerMap.put("Accept-Encoding", "gzip,deflate");

        FormBody.Builder fromBody_builder = new FormBody.Builder();
        fromBody_builder.add("test_id", "");

        try {
            mDownCall = Http2Utils.doPost(getDownloadUrl(), headerMap, fromBody_builder.build(), new IResponeDataCallBack() {
                @Override
                public int read(Buffer sink, long byteCount) {

                    if (isOutClose) {
                        sLog.i(TAG, "read: type == 5  -id-->  " + Thread.currentThread().getId());
                        isClose = true;
                        isOutClose = true;
                        return 0;
                    }

                    try {
                        long len;
                        byte[] data;
                        APIResponse response;
                        String resultSpeechToText = null;

                        while (sink.size() > 0) {
                            // 测试验证LV格式中，L使用的是小端模式
                            len = sink.readIntLe();
                            data = sink.readByteArray(len);
                            response = APIResponse.parseFrom(data);
                            sLog.e(TAG, "resp_body_read: {}" + convertUnicode(response.toString()));

                            // Result for speech to text, middle result & final result
                            // 拿到语音结果后，进行后续业务逻辑处理
                            if (null != response.result) {
                                if (response.result.word.length != 0) {
                                    resultSpeechToText = response.result.word[0];
                                } else {
                                    sLog.e(TAG, "read: response.result.word size 0");
                                }
                                if (StreamApi.API_RESP_TYPE_MIDDLE == response.type) {
                                    sLog.e(TAG, "read: middle--->" + resultSpeechToText);
                                    Bundle bundle = new Bundle();
                                    bundle.putInt(BaiDConstant.RESULT_CODE, BaiDConstant.SPEECH_RECOGNIZED_SUCCESS);
                                    bundle.putString(BaiDConstant.ASR_RESULT_MIDDLE, resultSpeechToText);
                                    if (mCallBack != null) {
                                        mCallBack.getBaiDDataBack(bundle);
                                    }
                                } else if (StreamApi.API_RESP_TYPE_RES == response.type) {
                                    // 语音最后结果
                                    finalStr = resultSpeechToText;
                                }
                            }

                            // Final result for semantic meaning
                            // 语义最后结果
                            if (response.type == 5) {
                                Bundle bundle = new Bundle();

                                if (response.errNo != 0) {
                                    if (response.errNo == -3005) {
                                        bundle.putInt(BaiDConstant.RESULT_CODE, BaiDConstant.VAD_DETECT_NO_SPEECH);
                                    } else {
                                        bundle.putInt(BaiDConstant.RESULT_CODE, BaiDConstant.ASR_ENGINE_BUSY);
                                    }
                                } else {
                                    bundle.putInt(BaiDConstant.RESULT_CODE, BaiDConstant.SPEECH_RECOGNIZED_SUCCESS);
                                }
                                bundle.putString(BaiDConstant.ASR_RESULT_FINAL, finalStr);
                                if (mCallBack != null) {
                                    mCallBack.getBaiDDataBack(bundle);
                                }

                                sLog.i(TAG, "read: type == 5 prepare exit");
                                isClose = true;
                                isOutClose = true;

                                exit();

                                JniGateway.JniStopStreaming();

                                return 0;
                            }

                        }
                    } catch (Exception e) {
                        Logg.e(TAG, "download error -> ", e);
                    }
                    return 0;
                }
            });
        } catch (IOException e) {
            Logg.e(TAG, "asrdemo error -> ", e);
            Bundle bundle = new Bundle();
            bundle.putInt(BaiDConstant.RESULT_CODE, BaiDConstant.ASR_ENGINE_BUSY);
            if (mCallBack != null) {
                mCallBack.getBaiDDataBack(bundle);
            }
            exit();
            return;
        }
        if (mDownCall == null) {
            Bundle bundle = new Bundle();
            bundle.putInt(BaiDConstant.RESULT_CODE, BaiDConstant.ASR_ENGINE_BUSY);
            if (mCallBack != null) {
                mCallBack.getBaiDDataBack(bundle);
            }
        }
    }

    void exit() {
        Logg.i(TAG, "exit: ");
        isOutClose = true;
        isClose = true;

        mCallBack = null;

        if (mDownCall != null && !mDownCall.isCanceled()) {
            Logg.e(TAG, "exit -> downCall cancel");
            mDownCall.cancel();
        }
        if (mUploadCall != null && !mUploadCall.isCanceled()) {
            Logg.e(TAG, "exit -> uploadCall cancel");
            mUploadCall.cancel();
        }
    }

    private static final String TAG = "sunAsrDemo";

    public interface IResponeDataCallBack {
        public int read(Buffer sink, long byteCount);
    }

    public static class UserResponseBody extends ResponseBody {
        private ResponseBody mResponseBody = null;
        private BufferedSource mBufferedSource = null;
        private IResponeDataCallBack mResponeBodyDataCallBack = null;

        public UserResponseBody(ResponseBody body, IResponeDataCallBack callBack) {
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
            if (mBufferedSource == null) {
                mBufferedSource = Okio.buffer(source(mResponseBody.source()));
            }
            return mBufferedSource;
        }

        private Source source(Source source) {
            return new ForwardingSource(source) {
                @Override
                public long read(Buffer sink, long byteCount) throws IOException {
                    long bytesRead = super.read(sink, byteCount);
                    if (mResponeBodyDataCallBack != null) {
                        mResponeBodyDataCallBack.read(sink, byteCount);
                    }
                    return bytesRead;
                }
            };
        }
    }

    static String convertUnicode(String ori) {
        char aChar;
        int len = ori.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = ori.charAt(x++);
            if (aChar == '\\') {
                aChar = ori.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = ori.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);

        }
        return outBuffer.toString();
    }

    /**
     * Make asrId, longer than 36 chars.
     * This MUST be unique for each request
     *
     * @return String
     */
    public static String buildAsrId(String prefix) {

        String uuid = UUID.randomUUID().toString();
//		prefix = DigestUtils.md5DigestAsHex(prefix.getBytes());
        String asrId = String.format("%s-%s", prefix, uuid);
        sLog.e(TAG, "buildAsrId: uuid----->" + uuid);
        sLog.e(TAG, "buildAsrId: asrId----->" + asrId);
        return asrId;
    }
}

