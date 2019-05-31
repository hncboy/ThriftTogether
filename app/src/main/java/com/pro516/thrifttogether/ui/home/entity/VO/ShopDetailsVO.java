package com.pro516.thrifttogether.ui.home.entity.VO;

import java.util.List;

public class ShopDetailsVO {

    private Integer shopId;
    private String shopCoverUrl;
    private String shopName;
    private Double shopAverageScore;
    private Integer shopAveragePrice;
    private String shopAddress;
    private List<SimpleProductVO> simpleProductList;
    private Integer isCollected;

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getShopCoverUrl() {
        return shopCoverUrl;
    }

    public void setShopCoverUrl(String shopCoverUrl) {
        this.shopCoverUrl = shopCoverUrl;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Double getShopAverageScore() {
        return shopAverageScore;
    }

    public void setShopAverageScore(Double shopAverageScore) {
        this.shopAverageScore = shopAverageScore;
    }

    public Integer getShopAveragePrice() {
        return shopAveragePrice;
    }

    public void setShopAveragePrice(Integer shopAveragePrice) {
        this.shopAveragePrice = shopAveragePrice;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public List<SimpleProductVO> getSimpleProductList() {
        return simpleProductList;
    }

    public void setSimpleProductList(List<SimpleProductVO> simpleProductList) {
        this.simpleProductList = simpleProductList;
    }

    public Integer getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(Integer isCollected) {
        this.isCollected = isCollected;
    }
}
