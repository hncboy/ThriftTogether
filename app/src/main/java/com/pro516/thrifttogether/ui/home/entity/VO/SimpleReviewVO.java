package com.pro516.thrifttogether.ui.home.entity.VO;

import java.sql.Date;
import java.util.List;

public class SimpleReviewVO {

    private Integer reviewId;
    private String username;
    private String avatorUrl;
    private String reviewContent;
    private List<String> reviewPicUrlList;
    private Double reviewScore;
    private Date reviewTime;

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

