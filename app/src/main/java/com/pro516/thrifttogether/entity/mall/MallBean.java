package com.pro516.thrifttogether.entity.mall;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MallBean implements Serializable {
    @SerializedName("couponName")
    private String name;
    @SerializedName("couponImageUrl")
    private String img;
    @SerializedName("couponIntegral")
    private Integer requiredIntegral;
    @SerializedName("couponTotal")
    private Integer surplusQuantity;
    private Integer discountedPrices;
    private Integer exchangeNumber;
//    private String couponStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getRequiredIntegral() {
        return requiredIntegral;
    }

    public void setRequiredIntegral(Integer requiredIntegral) {
        this.requiredIntegral = requiredIntegral;
    }

    public Integer getSurplusQuantity() {
        return surplusQuantity;
    }

    public void setSurplusQuantity(Integer surplusQuantity) {
        this.surplusQuantity = surplusQuantity;
    }

    public Integer getDiscountedPrices() {
        return discountedPrices;
    }

    public void setDiscountedPrices(Integer discountedPrices) {
        this.discountedPrices = discountedPrices;
    }

    public Integer getExchangeNumber() {
        return exchangeNumber;
    }

    public void setExchangeNumber(Integer exchangeNumber) {
        this.exchangeNumber = exchangeNumber;
    }

//    public String getCouponStatus() {
//        return couponStatus;
//    }
//
//    public void setCouponStatus(String couponStatus) {
//        this.couponStatus = couponStatus;
//    }
}
