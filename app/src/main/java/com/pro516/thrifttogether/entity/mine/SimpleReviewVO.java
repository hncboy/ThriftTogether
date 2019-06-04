package com.pro516.thrifttogether.entity.mine;

import java.io.Serializable;
import java.util.List;

public class SimpleReviewVO implements Serializable {
//    @ApiModelProperty(value = "用户id")
    private Integer userId;

//    @ApiModelProperty(value = "评价内容")
    private String reviewContent;

//    @ApiModelProperty(value = "评论图片")
    private List<String> reviewPicUrlList;

//    @ApiModelProperty(value = "评价订单id")
    private String orderNo;

//    @ApiModelProperty(value = "评价分数")
    private Integer reviewScore;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getReviewScore() {
        return reviewScore;
    }

    public void setReviewScore(Integer reviewScore) {
        this.reviewScore = reviewScore;
    }
}
