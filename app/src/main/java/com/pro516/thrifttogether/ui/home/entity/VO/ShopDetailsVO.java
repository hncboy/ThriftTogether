package com.pro516.thrifttogether.ui.home.entity.VO;

import java.util.List;

public class ShopDetailsVO {

    //@ApiModelProperty(value = "店铺id")
    private Integer shopId;

    //@ApiModelProperty(value = "店铺封面")
    private String shopCoverUrl;

    //@ApiModelProperty(value = "店铺名")
    private String shopName;

    //@ApiModelProperty(value = "店铺评分")
    private Double shopAverageScore;

    //@ApiModelProperty(value = "人均价格")
    private Integer shopAveragePrice;

    //@ApiModelProperty(value = "店铺位置")
    private String shopAddress;

    //@ApiModelProperty(value = "店铺团购列表信息")
    private List<SimpleProductVO> simpleProductList;

    //@ApiModelProperty(value = "用户是否收藏 0不收藏 1收藏")
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
