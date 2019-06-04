package com.pro516.thrifttogether.ui.home.entity;

public class StoreProduct {
    private String image;
    private String name;
    private String time;
    private String price;

    public StoreProduct(String image, String name, String time, String price) {
        this.image = image;
        this.name = name;
        this.time = time;
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
