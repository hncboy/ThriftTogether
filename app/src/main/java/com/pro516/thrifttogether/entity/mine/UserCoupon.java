package com.pro516.thrifttogether.entity.mine;

public class UserCoupon {
//    @ApiModelProperty(value = "用户优惠卷id")
    private Integer userCouponId;

//    @ApiModelProperty(value = "优惠卷图片url")
    private String couponImageUrl;

//    @ApiModelProperty(value = "优惠卷折扣价格")
    private Integer couponDiscountedPrice;

//    @JsonFormat(pattern = "yyyy-MM-dd")
//    @ApiModelProperty(value = "优惠卷过期时间")
    private String expiredDate;

//    @ApiModelProperty(value = "优惠卷状态 1已使用 2未使用 3已过期")
    private Integer userCouponStatus;

    private String couponName;

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Integer getUserCouponId() {
        return userCouponId;
    }

    public void setUserCouponId(Integer userCouponId) {
        this.userCouponId = userCouponId;
    }

    public String getCouponImageUrl() {
        return couponImageUrl;
    }

    public void setCouponImageUrl(String couponImageUrl) {
        this.couponImageUrl = couponImageUrl;
    }

    public Integer getCouponDiscountedPrice() {
        return couponDiscountedPrice;
    }

    public void setCouponDiscountedPrice(Integer couponDiscountedPrice) {
        this.couponDiscountedPrice = couponDiscountedPrice;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Integer getUserCouponStatus() {
        return userCouponStatus;
    }

    public void setUserCouponStatus(Integer userCouponStatus) {
        this.userCouponStatus = userCouponStatus;
    }
}
