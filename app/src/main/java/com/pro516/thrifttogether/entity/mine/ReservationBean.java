package com.pro516.thrifttogether.entity.mine;

import java.io.Serializable;
import java.util.Date;

public class ReservationBean implements Serializable {
    private String img;
    private String shopName;
    private String address;
    private Date time;
    private int count;

    public ReservationBean(String img, String shopName, String address, Date time, int count) {
        this.img = img;
        this.shopName = shopName;
        this.address = address;
        this.time = time;
        this.count = count;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
