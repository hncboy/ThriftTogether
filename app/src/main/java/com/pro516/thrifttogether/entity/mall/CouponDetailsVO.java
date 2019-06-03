package com.pro516.thrifttogether.entity.mall;

import java.io.Serializable;

public class CouponDetailsVO implements Serializable {
//    @ApiModelProperty(value = "优惠卷id")
    private Integer couponId;

//    @ApiModelProperty(value = "优惠卷所需积分")
    private Integer couponIntegral;

//    @ApiModelProperty(value = "优惠卷详情图片url")
    private String couponDetailsImageUrl;

//    @ApiModelProperty(value = "优惠卷信息")
    private String couponInfo;

//    @ApiModelProperty(value = "优惠卷兑换信息")
    private String couponExchangeInfo;

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Integer getCouponIntegral() {
        return couponIntegral;
    }

    public void setCouponIntegral(Integer couponIntegral) {
        this.couponIntegral = couponIntegral;
    }

    public String getCouponDetailsImageUrl() {
        return couponDetailsImageUrl;
    }

    public void setCouponDetailsImageUrl(String couponDetailsImageUrl) {
        this.couponDetailsImageUrl = couponDetailsImageUrl;
    }

    public String getCouponInfo() {
        return couponInfo;
    }

    public void setCouponInfo(String couponInfo) {
        this.couponInfo = couponInfo;
    }

    public String getCouponExchangeInfo() {
        return couponExchangeInfo;
    }

    public void setCouponExchangeInfo(String couponExchangeInfo) {
        this.couponExchangeInfo = couponExchangeInfo;
    }
}
