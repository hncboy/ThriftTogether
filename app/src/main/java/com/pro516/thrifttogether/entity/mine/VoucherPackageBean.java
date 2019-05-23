package com.pro516.thrifttogether.entity.mine;

import java.util.Date;

public class VoucherPackageBean {
    private String image;
    private String name;
    private Date time;
    private int price;

    public VoucherPackageBean(String n,Date t,int p) {
        this.name=n;
        this.time=t;
        this.price=p;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
