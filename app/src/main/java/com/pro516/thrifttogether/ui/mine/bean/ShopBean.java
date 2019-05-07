package com.pro516.thrifttogether.ui.mine.bean;

public class ShopBean {
    private String shopName;
    private String address;
    private Double point;
    private Double avePrice;
    private String img;

    public ShopBean(String shopName, String address, Double point, Double avePrice, String img) {
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

    public Double getAvePrice() {
        return avePrice;
    }

    public void setAvePrice(Double avePrice) {
        this.avePrice = avePrice;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
