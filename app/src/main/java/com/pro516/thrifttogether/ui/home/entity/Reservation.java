package com.pro516.thrifttogether.ui.home.entity;

import java.io.Serializable;
import java.util.Date;

//@ApiModel(value = "Reservation 预约")
public class Reservation implements Serializable {

    //@ApiModelProperty(value = "用户id")
    private Integer userId;

    //@ApiModelProperty(value = "商家id")
    private Integer shopId;

    //@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    //@ApiModelProperty(value = "预定时间")
    private Date reserveTime;

    //@ApiModelProperty(value = "预定人数")
    private Integer reservationPeopleNum;

    //@ApiModelProperty(value = "预约人姓名")
    private String reservationPeopleName;

    //@ApiModelProperty(value = "预约人电话")
    private String reservationPeoplePhone;

    //@ApiModelProperty(value = "预约留言")
    private String reservationRemarks;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Date getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(Date reserveTime) {
        this.reserveTime = reserveTime;
    }

    public Integer getReservationPeopleNum() {
        return reservationPeopleNum;
    }

    public void setReservationPeopleNum(Integer reservationPeopleNum) {
        this.reservationPeopleNum = reservationPeopleNum;
    }

    public String getReservationPeopleName() {
        return reservationPeopleName;
    }

    public void setReservationPeopleName(String reservationPeopleName) {
        this.reservationPeopleName = reservationPeopleName;
    }

    public String getReservationPeoplePhone() {
        return reservationPeoplePhone;
    }

    public void setReservationPeoplePhone(String reservationPeoplePhone) {
        this.reservationPeoplePhone = reservationPeoplePhone;
    }

    public String getReservationRemarks() {
        return reservationRemarks;
    }

    public void setReservationRemarks(String reservationRemarks) {
        this.reservationRemarks = reservationRemarks;
    }
}

