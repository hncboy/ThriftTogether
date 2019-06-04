package com.pro516.thrifttogether.ui.home.entity.VO;

import java.sql.Date;
import java.util.List;

public class SimpleReviewVO {

    //@ApiModelProperty(value = "评价id")
    private Integer reviewId;

    //@ApiModelProperty(value = "用户名")
    private String username;

    //@ApiModelProperty(value = "用户头像")
    private String avatorUrl;

    //@ApiModelProperty(value = "评价内容")
    private String reviewContent;

    //@ApiModelProperty(value = "评论图片")
    private List<String> reviewPicUrlList;

    //@ApiModelProperty(value = "评价分数")
    private Double reviewScore;

    //@JsonFormat(pattern = "yyyy-MM-dd")
    //@ApiModelProperty(value = "评价时间")
    private Date reviewTime;
    private String productName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatorUrl() {
        return avatorUrl;
    }

    public void setAvatorUrl(String avatorUrl) {
        this.avatorUrl = avatorUrl;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public List<String> getReviewPicUrlList() {
        return reviewPicUrlList;
    }

    public void setReviewPicUrlList(List<String> reviewPicUrlList) {
        this.reviewPicUrlList = reviewPicUrlList;
    }

    public Double getReviewScore() {
        return reviewScore;
    }

    public void setReviewScore(Double reviewScore) {
        this.reviewScore = reviewScore;
    }

    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }
}

