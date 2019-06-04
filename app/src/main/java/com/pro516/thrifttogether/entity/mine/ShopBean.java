package com.pro516.thrifttogether.entity.mine;

import java.io.Serializable;

public class ShopBean implements Serializable {

    //@ApiModelProperty(value = "店铺id")
    private Integer shopId;

    //@ApiModelProperty(value = "店铺名称")
    private String shopName;

    //@ApiModelProperty(value = "店铺封面")
    private String shopCoverUrl;

    //@ApiModelProperty(value = "店铺评分")
    private Double averageScore;

    //@ApiModelProperty(value = "人均价格")
    private Integer averagePrice;

    //@ApiModelProperty(value = "店铺区域")
    private String shopArea;

    public ShopBean(){}

    public ShopBean(Integer shopId, String shopName, String shopCoverUrl, Double averageScore, Integer averagePrice, String shopArea) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.shopCoverUrl = shopCoverUrl;
        this.averageScore = averageScore;
        this.averagePrice = averagePrice;
        this.shopArea = shopArea;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopCoverUrl() {
        return shopCoverUrl;
    }

    public void setShopCoverUrl(String shopCoverUrl) {
        this.shopCoverUrl = shopCoverUrl;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

    public Integer getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Integer averagePrice) {
        this.averagePrice = averagePrice;
    }

    public String getShopArea() {
        return shopArea;
    }

    public void setShopArea(String shopArea) {
        this.shopArea = shopArea;
    }
}
