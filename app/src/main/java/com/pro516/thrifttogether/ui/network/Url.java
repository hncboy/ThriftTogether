package com.pro516.thrifttogether.ui.network;

/**
 * Created by hncboy on 2019-05-07.
 */
public class Url {

    private static String IP = "http://192.168.43.251:8080/thrifttogether";

    public static String RECOMMEND = IP + "/shop?categoryId=1&cityId=1&subdivisionId=1"; // GET 获取所有产品
    public static String COUPON= IP + "/coupon"; // GET 获取所有产品
    public static String USER_REGISTER = IP + "/user/register"; // POST 用户注册
    public static String USER_LOGIN = IP + "/user/login"; // POST 用户登录
    public static String LIST_SHOPPINGCART = IP + "/shoppingcart/user"; // GET 获取所有购物车商品
}
