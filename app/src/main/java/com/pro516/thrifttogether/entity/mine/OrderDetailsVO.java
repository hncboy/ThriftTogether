package com.pro516.thrifttogether.entity.mine;

import java.io.Serializable;

public class OrderDetailsVO implements Serializable {
//    @ApiModelProperty(value = "订单号")
    private String orderNo;

//    @ApiModelProperty(value = "购买商品名称")
    private String productName;

//    @ApiModelProperty(value = "购买商品封面")
    private String productCoverUrl;

//    @ApiModelProperty(value = "购买商品单价")
    private Double productBuyPrice;

//    @ApiModelProperty(value = "购买数量")
    private Integer productCount;

//    @ApiModelProperty(value = "是否使用优惠券 0不使用 1使用")
    private Integer isUseCoupon;

//    @ApiModelProperty(value = "优惠券折扣价格")
    private Integer discountedPrice;

//    @ApiModelProperty(value = "订单总价")
    private Double productAmountTotal;

//    @ApiModelProperty(value = "联系电话")
    private String contactPhone;

//    @ApiModelProperty(value = "付款方式 1支付宝 2微信")
    private Integer paymentMethod;

//    @ApiModelProperty(value = "订单状态 1待付款 2待使用 3待评价 4表示已完成 5退款售后")
    private Integer orderStatus;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
//    @ApiModelProperty(value = "订单创建时间")
    private String createTime;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
//    @ApiModelProperty(value = "订单付款时间")
    private String payTime;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
//    @ApiModelProperty(value = "订单使用时间")
    private String useTime;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
//    @ApiModelProperty(value = "订单评价时间")
    private String reviewTime;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
//    @ApiModelProperty(value = "订单退款售后时间")
    private String afterSaleTime;

    private String shopName;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCoverUrl() {
        return productCoverUrl;
    }

    public void setProductCoverUrl(String productCoverUrl) {
        this.productCoverUrl = productCoverUrl;
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

    public Integer getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(Integer discountedPrice) {
        this.discountedPrice = discountedPrice;
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

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public String getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(String reviewTime) {
        this.reviewTime = reviewTime;
    }

    public String getAfterSaleTime() {
        return afterSaleTime;
    }

    public void setAfterSaleTime(String afterSaleTime) {
        this.afterSaleTime = afterSaleTime;
    }
}
