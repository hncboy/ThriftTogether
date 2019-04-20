package com.pro516.thrifttogether.ui.mine.bean;

import java.util.Date;

public class OrderBean {
    private String titleImage;
    private String titleName;
    private String state;
    private String contentImage;
    private Date time;
    private Double price;
    private String btnName;


    public OrderBean(String titleImage, String titleName, String state, String contentImage, Date time, Double price, String btnName) {
        this.titleImage = titleImage;
        this.titleName = titleName;
        this.state = state;
        this.contentImage = contentImage;
        this.time = time;
        this.price = price;
        this.btnName = btnName;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getContentImage() {
        return contentImage;
    }

    public void setContentImage(String contentImage) {
        this.contentImage = contentImage;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBtnName() {
        return btnName;
    }

    public void setBtnName(String btnName) {
        this.btnName = btnName;
    }
}
