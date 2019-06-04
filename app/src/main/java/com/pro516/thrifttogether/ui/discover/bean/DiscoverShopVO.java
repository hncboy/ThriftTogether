package com.pro516.thrifttogether.ui.discover.bean;

//@ApiModel(value = "DiscoverShopVO 显示发现页的店铺信息")
public class DiscoverShopVO {

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

    //@ApiModelProperty(value = "店铺位置")
    private String shopAddress;

    //@ApiModelProperty(value = "经度")
    private Double longitude;

    //@ApiModelProperty(value = "纬度")
    private Double latitude;

    //@ApiModelProperty(value = "距离 km")
    private Double distance;

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

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}

