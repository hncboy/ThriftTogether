package com.pro516.thrifttogether.ui.home.entity.VO;

public class SimpleProductVO {
    private Integer productId;
    private String productCoverUrl;
    private String productName;
    private String productUseTime;
    private Double productPrice;

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
