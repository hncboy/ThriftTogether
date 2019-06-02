package com.pro516.thrifttogether.entity.mine;

import java.io.Serializable;

public class OrderBean implements Serializable {
    //@ApiModelProperty(value = "订单号")
    private Long orderNo;

    //@ApiModelProperty(value = "店铺类型")
    private Integer shopCategoryId;

    //@ApiModelProperty(value = "商品名称")
    private String productName;

    //@ApiModelProperty(value = "店铺id")
    private Integer shopId;

    //@ApiModelProperty(value = "商品封面")
    private String productCoverUrl;

    //@ApiModelProperty(value = "商品总价")
    private Double productAmountTotal;

    //@ApiModelProperty(value = "订单状态 1待付款 2待使用 3待评价 4表示已完成 5退款售后")
    private Integer orderStatus;

    //@ApiModelProperty(value = "订单创建时间")
    private String createTime;

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getShopCategoryId() {
        return shopCategoryId;
    }

    public void setShopCategoryId(Integer shopCategoryId) {
        this.shopCategoryId = shopCategoryId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getProductCoverUrl() {
        return productCoverUrl;
    }

    public void setProductCoverUrl(String productCoverUrl) {
        this.productCoverUrl = productCoverUrl;
    }

    public Double getProductAmountTotal() {
        return productAmountTotal;
    }

    public void setProductAmountTotal(Double productAmountTotal) {
        this.productAmountTotal = productAmountTotal;
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
}
