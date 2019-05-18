package com.pro516.thrifttogether.ui.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by hncboy on 2019-05-07.
 */
public class HttpUtils {

    private static final OkHttpClient mOkHttpClient = new OkHttpClient();
    private static final MediaType TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public static Response doDelete(String url) throws IOException {
        Request request = new Request.Builder().delete().url(url).build();
        return mOkHttpClient.newCall(request).execute();
    }

    public static Response doGet(String url) throws IOException {
        Request request = new Request.Builder().get().url(url).build();
        return mOkHttpClient.newCall(request).execute();
    }

    public static Response doPost(String url, Object obj) throws IOException {
        String json = gson.toJson(obj);
        RequestBody body = RequestBody.create(TYPE, json);
        Request request = new Request.Builder().post(body).url(url).build();
        return mOkHttpClient.newCall(request).execute();
    }

    public static Response doPut(String url, Object obj) throws IOException {
        String json = gson.toJson(obj);
        RequestBody body = RequestBody.create(TYPE, json);
        Request request = new Request.Builder().put(body).url(url).build();
        return mOkHttpClient.newCall(request).execute();
    }

    public static String getStringFromServer(String url) throws IOException {
        String responseStr = null;
        Response response = doGet(url);
        if (response.isSuccessful()) {
            responseStr = response.body().string();
        }
        return responseStr;
    }
}
