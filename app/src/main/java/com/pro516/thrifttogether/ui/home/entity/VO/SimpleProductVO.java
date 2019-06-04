package com.pro516.thrifttogether.ui.home.entity.VO;

public class SimpleProductVO {
    //@ApiModelProperty(value = "商品id")
    private Integer productId;

    //@ApiModelProperty(value = "商品封面")
    private String productCoverUrl;

    //@ApiModelProperty(value = "商品名称")
    private String productName;

    //@ApiModelProperty(value = "商品使用时间")
    private String productUseTime;

    //@ApiModelProperty(value = "商品价格")
    private Double productPrice;

    //@ApiModelProperty(value = "商品原价")
    private Double productOriginalPrice;

    //@ApiModelProperty(value = "商品折扣")
    private Double productDiscount;

    //@ApiModelProperty(value = "商品销量")
    private Integer productSales;

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

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductCoverUrl() {
        return productCoverUrl;
    }

    public void setProductCoverUrl(String productCoverUrl) {
        this.productCoverUrl = productCoverUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductUseTime() {
        return productUseTime;
    }

    public void setProductUseTime(String productUseTime) {
        this.productUseTime = productUseTime;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }
}
