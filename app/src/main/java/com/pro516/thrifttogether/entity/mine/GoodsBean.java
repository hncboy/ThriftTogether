package com.pro516.thrifttogether.entity.mine;

import java.io.Serializable;

public class GoodsBean implements Serializable {
    private String img;
    private String shopName;
    private String address;
    private String goodsName;
    private Double price;

    public GoodsBean(String img, String shopName, String address, String goodsName, Double price) {
        this.img = img;
        this.shopName = shopName;
        this.address = address;
        this.goodsName = goodsName;
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
