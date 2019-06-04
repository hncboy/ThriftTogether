package com.pro516.thrifttogether.ui.mine.reservation.vo;

import java.util.Date;

//@ApiModel(value = "SimpleReservationVO 显示在我的预约里的简单预约信息")
public class SimpleReservationVO {

    //@ApiModelProperty(value = "预约id")
    private Integer reservationId;

    //@ApiModelProperty(value = "预约店铺封面")
    private String shopCoverUrl;

    //@ApiModelProperty(value = "预约店铺名称")
    private String shopName;

    //@ApiModelProperty(value = "预约店铺区域")
    private String shopArea;

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    //@ApiModelProperty(value = "预约时间")
    private String reserveTime;

    //@ApiModelProperty(value = "预定人数")
    private Integer reservationPeopleNum;

    //@ApiModelProperty(value = "预约状态  0预约中 1已取消 2已过期")
    private Integer reservationStatus;

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public String getShopCoverUrl() {
        return shopCoverUrl;
    }

    public void setShopCoverUrl(String shopCoverUrl) {
        this.shopCoverUrl = shopCoverUrl;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopArea() {
        return shopArea;
    }

    public void setShopArea(String shopArea) {
        this.shopArea = shopArea;
    }

    public String getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(String reserveTime) {
        this.reserveTime = reserveTime;
    }

    public Integer getReservationPeopleNum() {
        return reservationPeopleNum;
    }

    public void setReservationPeopleNum(Integer reservationPeopleNum) {
        this.reservationPeopleNum = reservationPeopleNum;
    }

    public Integer getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(Integer reservationStatus) {
        this.reservationStatus = reservationStatus;
    }
}

