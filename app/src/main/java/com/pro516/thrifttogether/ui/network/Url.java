package com.pro516.thrifttogether.ui.network;

import android.app.Application;

/**
 * Created by hncboy on 2019-05-07.
 */
public class Url extends Application {

    public static final int ERROR = -666;
    public static final int LOAD_ALL = 11;
    public static final int UPDATE_AFTER_EXCHANGE = 12;

    public static int userID = 1;
    public static int cityID = 1;

    public static final int LOAD_HISTORY = 15;
    public static final int LOAD_HOT = 16;

    public static String IP = "http://hncboy.natapp1.cc/thrifttogether";

    public static String RECOMMEND = IP + "/shop/city/1/recommend"; // 今日推荐

    public static String SEARCH = IP + "/search/"; // 搜索
    public static String SEARCH_HISTORY = IP + "/search/city/" + cityID + "/user/" + userID; // 历史纪录搜索
    public static String SEARCH_HOT = IP + "/search/city/" + cityID ;// 历史纪录搜索

    public static String SHOP_FOODS = IP + "/shop/city/" + cityID + "/category/1"; // 美食


    public static String COUPON = IP + "/coupon"; // 优惠券
    public static String COUPON_USER = IP + "/coupon/user/" + userID; // 用户优惠券

    public static String COLLECTION = IP + "/collect/user/"; //

    public static String ORDER_GET = IP + "/order/user/";// 获取订单
    public static String ORDER_DELETE_OR_UPDATE = IP + "/order/";// 订单
    public static String ORDER_DETAILS = IP + "/order/";// 订单详细信息

    public static String REVIEW = IP + "/review";

    public static String USER_RECENTLY_BROWSE = IP + "/user/" + userID + "/recentlybrowse";//最近浏览
    public static String USER_INFO = IP + "/user/" + userID;//最近浏览
    public static String USER_FEEDBACK = IP + "/user/" + userID + "/feedback/";

    public static String UPLOAD_PIC = IP +"/user/uploadPic"; // 上传图片
}
