package com.pro516.thrifttogether.ui.discover.bean;

public class SurroundingShopsBean {

    private String titleName;
    private String category;
    private String address;
    private String price;
    private String image;
    private String distance;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public SurroundingShopsBean(String titleName, String category, String address, String price, String image, String distance) {
        this.titleName = titleName;
        this.category = category;
        this.address = address;
        this.price = price;
        this.image = image;
        this.distance = distance;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
