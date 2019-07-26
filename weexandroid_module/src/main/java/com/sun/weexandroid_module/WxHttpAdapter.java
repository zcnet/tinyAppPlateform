package com.sun.weexandroid_module;

import com.taobao.weex.adapter.IWXHttpAdapter;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.common.WXRequest;
import com.taobao.weex.common.WXResponse;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * Created by sun on 2018/8/7
 * <p>
 * 网络请求适配器
 */
public class WxHttpAdapter extends WXModule implements IWXHttpAdapter {

    @SuppressWarnings("unused")
    private static final String TAG = "sunHY_HttpAdapter";

    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");

    private OkHttpClient mHttpClient;

    private static final int TIMEOUT_CONNECT = 5;
    private static final int TIMEOUT_WRITE = 10;
    private static final int TIMEOUT_READ = 10;

    public WxHttpAdapter() {
        init();
    }

    private void init() {

        mHttpClient = new OkHttpClient
                .Builder()
                .connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .build();

    }

    /**
     * HTTP加载主流程 此方法JS会调用
     *
     * @param wxRequest 从Js传来的Request参数
     * @param listener  回调接口
     */
    @JSMethod(uiThread = false)
    @Override
    public void sendRequest(final WXRequest wxRequest, final OnHttpListener listener) {

        final Request request;
        Request.Builder okHttpBuilder = new Request.Builder();

        //通过判断请求方式加载不同的RequestBody
        if (wxRequest.method.equals("GET")) {
            okHttpBuilder.url(wxRequest.url);
        } else if (wxRequest.method.contains("HEAD")) {
            okHttpBuilder.url(wxRequest.url);
            okHttpBuilder.method(wxRequest.method, null);
        } else {
            okHttpBuilder
                    .url(wxRequest.url)
                    .method(wxRequest.method, new RequestBody() {
                                @Override
                                public MediaType contentType() {
                                    return MEDIA_TYPE_MARKDOWN;
                                }

                                @Override
                                public void writeTo(BufferedSink sink) throws IOException {
                                    sink.writeUtf8("Numbers\n");
                                    sink.writeUtf8("-------\n");
                                    for (int i = 2; i <= 997; i++) {
                                        sink.writeUtf8(String.format(" * %s = %s\n", i, factor(i)));
                                    }
                                }

                                private String factor(int n) {
                                    for (int i = 2; i < n; i++) {
                                        int x = n / i;
                                        if (x * i == n) return factor(x) + " × " + i;
                                    }
                                    return Integer.toString(n);
                                }
                            }
                    );
        }

        //拼接Header
        Set<String> keys = wxRequest.paramMap.keySet();
        for (String key : keys) {
            okHttpBuilder.addHeader(key, wxRequest.paramMap.get(key));
        }

        request = okHttpBuilder.build();

        mHttpClient.newCall(request).enqueue(new Callback() {

            /**
             * 网络请求失败回调
             */
            @Override
            public void onFailure(Call call, IOException e) {
                WXResponse wxResponse = new WXResponse();
                wxResponse.errorCode = "-1";
                wxResponse.errorMsg = e.getMessage();
                wxResponse.statusCode = "-1";
                listener.onHttpFinish(wxResponse);
            }

            /**
             * 网络请求成功回调
             */
            @Override
            public void onResponse(Call call, Response response) {
                WXResponse wxResponse = new WXResponse();
                if (response == null || response.body() == null) {
                    Loger.showE(TAG, "response null ");
                    return;
                }
                try {
                    wxResponse.originalData = response.body().string().getBytes();
                } catch (IOException e) {
                    Loger.showE(TAG, "response error ->", e);
                }
                wxResponse.errorCode = String.valueOf(response.code());
                wxResponse.errorMsg = response.message();
                wxResponse.statusCode = String.valueOf(response.code());
                listener.onHttpFinish(wxResponse);
            }
        });
    }


    @SuppressWarnings("unused")
    @JSMethod(uiThread = false)
    public int downLoadFile(String url) {
        return 1;
    }


}
