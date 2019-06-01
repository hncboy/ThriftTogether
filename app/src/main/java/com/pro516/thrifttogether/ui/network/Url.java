package com.pro516.thrifttogether.ui.network;

/**
 * Created by hncboy on 2019-05-07.
 */
public class Url {

    public static final int ERROR = -666;
    public static final int LOAD_ALL = 11;

    public static int userID = 1;
    public static String IP = "http://hncboy.natapp1.cc/thrifttogether";

    public static String RECOMMEND = IP + "/shop/recommend"; // 今日推荐

    public static String COUPON = IP + "/coupon"; // 优惠券
    public static String COUPON_USER = IP + "/coupon/user/" + userID; // 用户优惠券

    public static String COLLECTION = IP + "/collect/user/"; //

    public static String ORDER_GET = IP + "/order/user/";// 获取订单
    public static String ORDER_DELETE_OR_UPDATE = IP + "/order/";// 订单

    public static String REVIEW = IP + "/review";

    public static String USER_RECENTLY_BROWSE = IP + "/user/" + userID + "/recentlybrowse";//最近浏览
    public static String USER_FEEDBACK = IP + "/user/" + userID + "/feedback/";
}
