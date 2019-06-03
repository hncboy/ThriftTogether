package com.pro516.thrifttogether.ui.buy.entity.VO;

import java.io.Serializable;
import java.util.List;

//@ApiModel(value = "ProductDetailsVO 团购详情信息")
public class ProductDetailsVO implements Serializable {

    //@ApiModelProperty(value = "商品id")
    private Integer productId;

    //@ApiModelProperty(value = "商品名称")
    private String productName;

    //@ApiModelProperty(value = "店铺名称")
    private String shopName;

    private String productCoverUrl;

    //@ApiModelProperty(value = "商品标签")
    private List<String> productTags;

    //@ApiModelProperty(value = "套餐详情")
    private List<String> productContent;

    //@ApiModelProperty(value = "温馨提醒")
    private List<String> productReminder;

    //@ApiModelProperty(value = "团购价格")
    private Double productPrice;

    //@ApiModelProperty(value = "商品原价")
    private Double productOriginalPrice;

    //@ApiModelProperty(value = "商品折扣")
    private Double productDiscount;

    //@ApiModelProperty(value = "商品销量")
    private Integer productSales;

    //@ApiModelProperty(value = "用户是否收藏 0不收藏 1收藏")
    private Integer isCollected;

    public Integer getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(Integer isCollected) {
        this.isCollected = isCollected;
    }

    public String getProductCoverUrl() {
        return productCoverUrl;
    }

    public void setProductCoverUrl(String productCoverUrl) {
        this.productCoverUrl = productCoverUrl;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<String> getProductTags() {
        return productTags;
    }

    public void setProductTags(List<String> productTags) {
        this.productTags = productTags;
    }

    public List<String> getProductContent() {
        return productContent;
    }

    public void setProductContent(List<String> productContent) {
        this.productContent = productContent;
    }

    public List<String> getProductReminder() {
        return productReminder;
    }

    public void setProductReminder(List<String> productReminder) {
        this.productReminder = productReminder;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Double getProductOriginalPrice() {
        return productOriginalPrice;
    }

    public void setProductOriginalPrice(Double productOriginalPrice) {
        this.productOriginalPrice = productOriginalPrice;
    }

    public Double getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(Double productDiscount) {
        this.productDiscount = productDiscount;
    }

    public Integer getProductSales() {
        return productSales;
    }

    public void setProductSales(Integer productSales) {
        this.productSales = productSales;
    }
}

