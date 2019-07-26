package com.aoshuotec.voiceassistant.baidusdkmodule;

import com.z.tinyapp.utils.logs.sLog;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by ASUS User on 2018/5/26.
 */
public class Http2Utils {
    private final static String TAG = "sunHttpUtils";

    public static Call doPost(String url, Map<String, String> header_map, RequestBody requestBody, final AsrDemo.IResponeDataCallBack respDataCallBack) throws IOException {
        sLog.d(TAG, "url:" + url);
        OkHttpClient.Builder client_builder = new OkHttpClient.Builder();
        if (respDataCallBack != null) {
            client_builder.addNetworkInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response response = chain.proceed(chain.request());
                    return response.newBuilder().body(new AsrDemo.UserResponseBody(response.body(), respDataCallBack)).build();
                }
            });
        }
        client_builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC));
        List<Protocol> protocolList = new LinkedList<Protocol>();
        protocolList.add(Protocol.HTTP_2);
        protocolList.add(Protocol.HTTP_1_1);
        client_builder.protocols(protocolList);

        OkHttpClient okHttpClient = client_builder.build();

        Request.Builder request_builder = new Request.Builder();
        request_builder.url(url);
        if (requestBody != null) {
            request_builder.post(requestBody);
        }

        Headers.Builder header_buidler = new Headers.Builder();
        Set<String> headers_set = header_map.keySet();
        for (String key : headers_set) {
            header_buidler.add(key, header_map.get(key));
        }
        Headers headers = header_buidler.build();

        request_builder.headers(headers);
        Request request = request_builder.build();
        Call call;
        call = okHttpClient.newCall(request);
        Response response = call.execute();
        String body_string = response.body().string();
        sLog.e(TAG, url + ", resp_body:" + body_string);
        return call;
    }
}
