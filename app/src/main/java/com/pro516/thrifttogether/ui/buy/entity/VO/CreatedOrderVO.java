package com.pro516.thrifttogether.ui.buy.entity.VO;

//@ApiModel(value = "CreatedOrderVO 创建的订单")
public class CreatedOrderVO {
    //@ApiModelProperty(value = "用户id")
    private Integer userId;

    //@ApiModelProperty(value = "购买商品id")
    private Integer productId;

    //@ApiModelProperty(value = "购买商品单价")
    private Double productBuyPrice;

    //@ApiModelProperty(value = "购买数量")
    private Integer productCount;

    //@ApiModelProperty(value = "是否使用优惠券 0不使用 1使用")
    private Integer isUseCoupon;

    //@ApiModelProperty(value = "使用的用户优惠券id")
    private Integer userCouponId;

    //@ApiModelProperty(value = "订单总价")
    private Double productAmountTotal;

    //@ApiModelProperty(value = "联系电话")
    private String contactPhone;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Double getProductBuyPrice() {
        return productBuyPrice;
    }

    public void setProductBuyPrice(Double productBuyPrice) {
        this.productBuyPrice = productBuyPrice;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public Integer getIsUseCoupon() {
        return isUseCoupon;
    }

    public void setIsUseCoupon(Integer isUseCoupon) {
        this.isUseCoupon = isUseCoupon;
    }

    public Integer getUserCouponId() {
        return userCouponId;
    }

    public void setUserCouponId(Integer userCouponId) {
        this.userCouponId = userCouponId;
    }

    public Double getProductAmountTotal() {
        return productAmountTotal;
    }

    public void setProductAmountTotal(Double productAmountTotal) {
        this.productAmountTotal = productAmountTotal;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
}

