package com.z.tinyapp.network.okhttp;

import android.os.Environment;
import android.os.Handler;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadLargeFileListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.z.tinyapp.network.DownLoadCallBack;
import com.z.tinyapp.network.NetCode;
import com.z.tinyapp.network.UpLoadCallBack;
import com.z.tinyapp.utils.common.JsonUtil;
import com.z.tinyapp.utils.logs.sLog;
import com.z.tinyapp.utils.thred.ThreadPoolUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by zhengfei on 2018/8/13.
 * 封装OkHttp3的工具类 用单例设计模式
 */
public class OkHttp3Utils {
    private static final long DEFAULT_TIMEOUT = 20;
    private static OkHttp3Utils okHttp3Utils = null;
    private static  OkHttpClient okHttpClient = null;

    public OkHttp3Utils() {
        File sdcache = new File(Environment.getExternalStorageDirectory(), "weexcache");
        int cacheSize = 10 * 1024 * 1024;
        //OkHttp3拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
            }
        });
        //Okhttp3的拦截器日志分类 4种
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient = new OkHttpClient.Builder().connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                //添加OkHttp3的拦截器
                .addInterceptor(httpLoggingInterceptor)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize))
                .build();
    }

    public static OkHttp3Utils getInstance() {
        if (okHttp3Utils == null) {
            //加同步安全
            synchronized (OkHttp3Utils.class) {
                if (okHttp3Utils == null) {
                    okHttp3Utils = new OkHttp3Utils();
                }
            }
        }
        return okHttp3Utils;
    }




    private static Handler mHandler = null;

    public synchronized static Handler getHandler() {
        if (mHandler == null) {
            try {
                mHandler = new Handler();
            }catch (Exception e){

            }
        }
        return mHandler;
    }

    /**
     * get请求
     * 参数1 url
     * 参数2 回调Callback
     */
    public static void doGet(String url, final GsonObjectCallback callback) {
        try{
            //创建Request
            Request request = new Request.Builder().url(url).build();
            //得到Call对象
            final Call call = okHttpClient.newCall(request);
            //执行异步请求
            call.enqueue(callback);
        }catch (Exception e){
            callback.onFailed("404","链接有误");
        }

    }

    /**
     * post请求
     * 参数1 url
     * 参数2 回调Callback
     */
    public static void doPost(String url, Map<String, String> params, GsonObjectCallback callback) {

        //3.x版本post请求换成FormBody 封装键值对参数
        FormBody.Builder builder = new FormBody.Builder();
        //遍历集合
        for (String key : params.keySet()) {
            builder.add(key, params.get(key));
        }
        //创建Request
        Request request = new Request.Builder().url(url).post(builder.build()).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }


    /**
     * post请求上传文件
     * 参数1 url
     * 参数2 回调Callback
     */
    public static void uploadFile(String url, File file,  Map<String, Object> params, GsonObjectCallback callback) {
//        创建RequestBody 封装file参数
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        //创建RequestBody 设置类型等
        MultipartBody.Builder multipartBody= new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file", file.getName(), fileBody);
        if(params!=null){
            String jsonParams= JsonUtil.mapToJson(params);
            RequestBody requestBodyJson = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
            multipartBody.addPart(requestBodyJson);
        }
        RequestBody requestBody = multipartBody.build();
        //创建Request
        Request request = new Request.Builder().url(url).post(requestBody).build();
        //得到Call
        Call call = okHttpClient.newCall(request);
        //执行请求
        call.enqueue(callback);

//        RequestBody requestBody = new MultipartBody.Builder().addFormDataPart("file", file.getName(),
//                RequestBody.create(MediaType.parse("multipart/form-data"), file)) .build();
//        Request request = new Request.Builder().url(url) .post(requestBody) .build();
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(callback);

    }





    /**
     * Post请求发送JSON数据
     * 参数一：请求Url
     * 参数二：请求的JSON
     * 参数三：请求回调
     */
    public static void doPostJson(String url, Map<String, Object> params, GsonObjectCallback callback) {
        String jsonParams= JsonUtil.mapToJson(params);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public static void doPostJsonObject(String url, String params, GsonObjectCallback callback) {

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public static void doPostJsonString(String url, String params, GsonObjectCallback callback) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    /**
     * 下载文件 以流的形式写入到指定文件
     * 参数一：请求Url
     * 参数二：保存文件的路径名
     * 参数三：DownLoadCallBack
     */
    public static void download(final String url, final String saveDir, final DownLoadCallBack callBack) {

        callBack.onStart();
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                if(mHandler!=null){
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onError(NetCode.DATASEMPTY, e.toString());
                        }
                    });
                }

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    sLog.i("zf","savePath:"+saveDir);
                    //文件
                    File file = new File(saveDir);
                    if (file.exists()) {
                        file.delete();
                        file = new File(saveDir);
                    }
                    File fileParent = file.getParentFile();
                    if (!fileParent.exists()) {
                        fileParent.mkdirs();
                    }
                    boolean isCreat=false;
                    if (!file.exists()) {
                        isCreat = file.createNewFile();
                    }
                    if(!isCreat){
                        if(mHandler!=null){
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callBack.onError(NetCode.FILECREATEERROR, "文件创建失败");
                                }
                            });
                        }
                        return;
                    }
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        final int progress = (int) (sum * 1.0f / total * 100);
                        if(mHandler!=null){
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callBack.onLoadProgress(progress);
                                }
                            });
                        }
                    }
                    fos.flush();
                    if(mHandler!=null){
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onSuccess();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if(mHandler!=null){
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onError(NetCode.DATASEMPTY, "下载错误");
                            }
                        });
                    }
                } finally {
                    if (is != null) is.close();
                    if (fos != null) fos.close();
                }
            }
        });
    }

    /**
     * 下载文件 以流的形式写入到指定文件
     * 参数一：请求Url
     * 参数二：保存文件的路径名
     * 参数三：DownLoadCallBack
     */
    public static void downloadwithParam(final String url,String params, final String saveDir, final DownLoadCallBack callBack) {
        callBack.onStart();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                if(mHandler!=null){
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onError(NetCode.DATASEMPTY, e.toString());
                        }
                    });
                }

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    sLog.i("zf","savePath:"+saveDir);
                    //文件
                    File file = new File(saveDir);
                    if (file.exists()) {
                        file.delete();
                        file = new File(saveDir);
                    }
                    File fileParent = file.getParentFile();
                    if (!fileParent.exists()) {
                        fileParent.mkdirs();
                    }
                    boolean isCreat=false;
                    if (!file.exists()) {
                        isCreat = file.createNewFile();
                    }
                    if(!isCreat){
                        if(mHandler!=null){
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callBack.onError(NetCode.FILECREATEERROR, "文件创建失败");
                                }
                            });
                        }
                        return;
                    }
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        final int progress = (int) (sum * 1.0f / total * 100);
                        if(mHandler!=null){
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callBack.onLoadProgress(progress);
                                }
                            });
                        }
                    }
                    fos.flush();
                    if(mHandler!=null){
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onSuccess();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if(mHandler!=null){
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onError(NetCode.DATASEMPTY, "下载错误");
                            }
                        });
                    }
                } finally {
                    if (is != null) is.close();
                    if (fos != null) fos.close();
                }
            }
        });
    }

    public static void downLoadForLoader(final String url, final String saveDir, final DownLoadCallBack callBack) {
        FileDownloader.getImpl().create(url).setPath(saveDir).setListener(new FileDownloadLargeFileListener() {
            @Override
            protected void pending(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                callBack.onStart();
            }

            @Override
            protected void progress(BaseDownloadTask task, long soFarBytes, long totalBytes) {

            }

            @Override
            protected void paused(BaseDownloadTask task, long soFarBytes, long totalBytes) {

            }

            @Override
            protected void completed(BaseDownloadTask task) {
                callBack.onSuccess();
            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                callBack.onError(NetCode.DATASEMPTY, "下载错误");
            }

            @Override
            protected void warn(BaseDownloadTask task) {
                callBack.onWarn();
            }
        }).start();

    }

    public static void upFile(final String path, final File file, final UpLoadCallBack callBack){
        ThreadPoolUtil.execute(new Runnable() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onStart();
                    }
                });
                try {
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    // 设置为POST情
                    conn.setRequestMethod("POST");
                    // 发送POST请求必须设置如下两行
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setUseCaches(false);

                    // 换行符
                    final String newLine = "\r\n";
                    final String boundaryPrefix = "--";
                    // 数据分割线 任意定义
                    String BOUNDARY = "--uj_o0TLm-vs0RBIsC8p39r9XI3WsbKCRl";

                    // 设置请求头参数
                    conn.setRequestProperty("connection", "Keep-Alive");
                    conn.setRequestProperty("Charsert", "UTF-8");
                    conn.setRequestProperty("Content-Type", "application/json; boundary=" + BOUNDARY);

                    OutputStream out = new DataOutputStream(conn.getOutputStream());


                    // 上传文件
                    StringBuilder sb = new StringBuilder();
                    sb.append(boundaryPrefix);
                    sb.append(BOUNDARY);
                    sb.append(newLine);
                    // 文件参数,photo参数名可以随意修改
                    sb.append("Content-Disposition: form-data;name=\"myfiles\";filename=\"" + file.getName()
                            + "\"" + newLine);
                    sb.append("Content-Type:application/octet-stream");
                    // 参数头设置完以后需要两个换行，然后才是参数内容
                    sb.append(newLine);
                    sb.append(newLine);

                    // 将参数头的数据写入到输出流中
                    out.write(sb.toString().getBytes());

                    // 数据输入流,用于读取文件数据
                    DataInputStream in = new DataInputStream(new FileInputStream(
                            file));
                    byte[] bufferOut = new byte[1024];
                    int bytes = 0;
                    // 每次读1KB数据,并且将文件数据写入到输出流中
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                    // 最后添加换行
                    out.write(newLine.getBytes());
                    in.close();

                    // 定义最后数据分隔线，即--加上BOUNDARY再加上--。
                    byte[] end_data = (newLine + boundaryPrefix + BOUNDARY + boundaryPrefix + newLine)
                            .getBytes();
                    // 写上结尾标识
                    out.write(end_data);
                    out.flush();
                    out.close();

                    System.out.println("status code: "+conn.getResponseCode());

                    /**
                     * 打印返回值
                     */
                    if(conn.getResponseCode() == 200){
                        InputStream inputStream = conn.getInputStream();
                        StringBuffer buffer = new StringBuffer();
                        byte[] b = new byte[1024];
                        try {
                            for (int n; (n = inputStream.read(b)) != -1;) {
                                buffer.append(new String(b, 0, n));
                            }
                        }
                        catch (IOException e) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callBack.onError("406","请求错误");
                                }
                            });
                            e.printStackTrace();
                        }
                        final String result = buffer.toString();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onSuccess(result);
                            }
                        });

                    }else{
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onError("405","请求错误");
                            }
                        });
                    }
                    conn.disconnect();
                }catch (Exception e){
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onError("404","请求错误");
                        }
                    });
                }
            }
        });
    }
}
