package com.example.eamon.hihealth.util;

import android.os.Handler;
import android.os.Looper;

import com.example.eamon.hihealth.inter.HttpCallbackListener;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 封装工具类
 * 将OKHttp3进行封装，用于对数据的传输 JSON, 表单，图片等数据的提交和获取
 * 作者：Created by eamon
 * 时间：  on 2018/4/21.
 */

public class HttpManager {



    public static void sendHttpRequest(final String address, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;

                try{
                    URL url = new URL( address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    if (listener != null) {
                        listener.onFinish(response.toString());
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }
    /**
     * 静态实例
     */
    private static HttpManager httpManager;

    /**
     * okhttpclient 实例
     */
    private OkHttpClient client;

    /**
     * 由于请求数据一般都是子线程中请求，在这里我们使用了handler
     */
    private Handler handler;

    /**
     * 构造方法
     */
    private HttpManager () {

        client = new OkHttpClient();

        /**
         * 在这里设置链接超时 读取超时 写入超时
         */
        client.newBuilder().connectTimeout(10, TimeUnit.SECONDS);
        client.newBuilder().readTimeout(10, TimeUnit.SECONDS);
        client.newBuilder().writeTimeout(10,TimeUnit.SECONDS);

        /**
         * 初始化handler
         */
        handler = new Handler(Looper.getMainLooper());
    }

    /**
     * 单例模式 获取OkHttpManager实例
     */
    public static HttpManager getInstance () {
        if (httpManager == null) {
            httpManager = new HttpManager();
        }
        return httpManager;
    }


    /**
     *    同步方式请求数据
     */

    /**
     *  对外提供的get方法，同步的方式
     * @param url  传入的地址
     * @return
     */
    public static Response getSync(String url) {

        //通过获取到的实例来调用内部方法
        return httpManager.inner_getSync(url);
    }

    /**
     *
     * GET 方式请求的内部逻辑处理方式，同步的方式
     * @param url
     * @return
     */
    private Response inner_getSync(String url) {
        Request request = new Request
                .Builder()
                .url(url)
                .build();

        Response response = null;

        try {
            // 同步请求返回response对象
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  response;
    }

    /**
     *  对外提供的同步获取String的方法
     * @param url
     * @return
     */
    public static String getSyncString(String url) {
        return httpManager.inner_getSynString(url);
    }

    /**
     * 同步获取字符串
     * @param url
     * @return
     */
    private String inner_getSynString(String url) {
        String result = null;
        try {
            /**
             * 把取得的结果转为字符串，这里最好用String()
             */
            result = inner_getSync(url).body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 异步方式
     */
    public static void getAsync(String url, DataCallBack callback) {
        getInstance().inner_getAsync(url, callback);
    }

    /**
     * 内部逻辑请求的方法
     * @param url
     * @param callBack
     */
    private void inner_getAsync(String url, final DataCallBack callBack) {
        final Request request = new Request
                .Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverDataFailure(request, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = null;
                try {
                    result = response
                            .body()
                            .string();
                } catch (IOException e) {
                    deliverDataFailure(request, e, callBack);
                }
                deliverDataSuccess(result, callBack);
            }
        });
    }

    /**
     * 分发失败的时候调用
     * @param request
     * @param e
     * @param callBack
     */
    private void deliverDataFailure(final Request request, final IOException e, final DataCallBack callBack) {

        /**
         * 这里做异步处理
         */

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.requestFailure(request, e);
                }
            }
        });
    }

    private void deliverDataSuccess(final String result, final DataCallBack callBack) {

        /**
         * 在这里使用异步线程处理
         */

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    try {
                        callBack.requestSuccess(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public interface DataCallBack {
        void requestFailure(Request request,IOException e);

        void requestSuccess(String result) throws Exception;
    }

    /**
     * 提交表单
     */

    public static void postAsync(String url, Map<String, String> params, DataCallBack callBack) {
        getInstance().inner_postAsync(url, params, callBack);
    }

    private void inner_postAsync(String url, Map<String, String> params, final DataCallBack callBack) {
        RequestBody requestBody = null;
        if (params == null) {
            params = new HashMap<>();
        }

        FormBody.Builder builder = new FormBody.Builder();

        /**
         * 对添加的参数进行遍历，map遍历有四种方式
         */

        for (Map.Entry<String, String> map : params.entrySet()) {
            String key = map.getKey().toString();
            String value = null;

            /**
             * 判断值是否为空
             */

            if (map.getValue() == null) {
                value = "";
            } else {
                value = map.getValue();
            }

            /**
             * 把key和value 添加到formbody中
             */
            builder.add(key, value);
        }

        requestBody = builder.build();

        // 结果返回
        final Request request = new Request
                .Builder()
                .url(url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverDataFailure(request, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response
                        .body()
                        .string();
                deliverDataSuccess(result, callBack);
            }
        });
    }

    /**
     * 对外部使用的 提交json方式 POST
     * @param url
     * @param params
     * @param callBack
     */
    public static void postJSONAsync(String url,Object params, DataCallBack callBack) {
        getInstance().inner_postJSONAsync(url, params, callBack);
    }

    /**
     *  提交json 方式
     * @param url
     * @param params
     * @param callBack
     */
    private void inner_postJSONAsync(String url, Object params, final DataCallBack callBack) {
        RequestBody requestBody = null;
        if (params == null) {
            params = new Object();
        }

        Gson gson = new Gson();

        // 使用Gson将对象转换为json字符串
        String json = gson.toJson(params);

        // MediaType 设置content-Type 标头中包含的媒体类型值
        requestBody = FormBody
                .create(MediaType.parse("application/json;" +
                        "charset=utf-8"), json);

        // 结果返回
        final Request request = new Request
                .Builder()
                .url(url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverDataFailure(request, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response
                        .body()
                        .string();
                deliverDataSuccess(result, callBack);
            }
        });
    }

}

