package com.pro516.thrifttogether.entity.mine;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ShopBean implements Serializable {

    private String shopName;
    @SerializedName("shopAddress")
    private String address;
    @SerializedName("averageScore")
    private Double point;
    @SerializedName("averagePrice")
    private Integer avePrice;
    @SerializedName("coverUrl")
    private String img;

    public ShopBean(String shopName, String address, Double point, Integer avePrice, String img) {
        this.shopName = shopName;
        this.address = address;
        this.point = point;
        this.avePrice = avePrice;
        this.img = img;
    }

    @Override
    public String toString() {
        return "ShopBean{" +
                "shopName='" + shopName + '\'' +
                ", address='" + address + '\'' +
                ", point=" + point +
                ", avePrice=" + avePrice +
                ", img='" + img + '\'' +
                '}';
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

    public Double getPoint() {
        return point;
    }

    public void setPoint(Double point) {
        this.point = point;
    }

    public Integer getAvePrice() {
        return avePrice;
    }

    public void setAvePrice(Integer avePrice) {
        this.avePrice = avePrice;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
