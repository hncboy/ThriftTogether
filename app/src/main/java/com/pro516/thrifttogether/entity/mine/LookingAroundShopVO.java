package com.pro516.thrifttogether.entity.mine;

import java.util.List;

public class LookingAroundShopVO {
//    @ApiModelProperty(value = "店铺id")
    private Integer shopId;

//    @ApiModelProperty(value = "店铺名称")
    private String shopName;

//    @ApiModelProperty(value = "店铺封面")
    private String shopCoverUrl;

//    @ApiModelProperty(value = "店铺评分")
    private Double averageScore;

//    @ApiModelProperty(value = "人均价格")
    private Integer averagePrice;

//    @ApiModelProperty(value = "店铺区域")
    private String shopArea;

//    @ApiModelProperty(value = "店铺位置")
    private String shopAddress;

//    @ApiModelProperty(value = "城市id")
    private Integer cityId;

//    @ApiModelProperty(value = "店铺团购名称列表")
    private List<String> productNameList;

//    @ApiModelProperty(value = "用户是否收藏 0不收藏 1收藏")
    private Integer isCollected;

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

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public List<String> getProductNameList() {
        return productNameList;
    }

    public void setProductNameList(List<String> productNameList) {
        this.productNameList = productNameList;
    }

    public Integer getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(Integer isCollected) {
        this.isCollected = isCollected;
    }
}
