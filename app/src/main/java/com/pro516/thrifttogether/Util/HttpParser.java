//package com.pro516.thrifttogether.Util;
//
//import com.google.android.gms.common.util.HttpUtils;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import java.util.Map;
//
//public class HttpParser {
//    public static Map<String, String> parseMapGet(String URL, String parm) {
//        String response = HttpUtils.doGet(URL, parm);
//        Gson gson = new Gson();
//        return gson.fromJson(response, new TypeToken<Map<String, String>>() {
//        }.getType());
//    }
//
//    public static Map<String, String> parseMapPost(String URL, String parm) {
//        String response = HttpUtils.doGet(URL, parm);
//        Gson gson = new Gson();
//        return gson.fromJson(response, new TypeToken<Map<String, String>>() {
//        }.getType());
//    }
//}
