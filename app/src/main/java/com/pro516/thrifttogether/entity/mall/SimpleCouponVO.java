package com.pro516.thrifttogether.entity.mall;

import java.io.Serializable;

public class SimpleCouponVO implements Serializable {
//    @ApiModelProperty(value = "优惠券id")
    private Integer couponId;

//    @ApiModelProperty(value = "优惠券名称")
    private String couponName;

//    @ApiModelProperty(value = "优惠券所需积分")
    private Integer couponIntegral;

//    @ApiModelProperty(value = "优惠券图片url")
    private String couponImageUrl;

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Integer getCouponIntegral() {
        return couponIntegral;
    }

    public void setCouponIntegral(Integer couponIntegral) {
        this.couponIntegral = couponIntegral;
    }

    public String getCouponImageUrl() {
        return couponImageUrl;
    }

    public void setCouponImageUrl(String couponImageUrl) {
        this.couponImageUrl = couponImageUrl;
    }
}
