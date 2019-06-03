package com.pro516.thrifttogether.entity.mine;

import java.io.Serializable;

public class User implements Serializable {
//    @ApiModelProperty(value = "用户id")
    private Integer userId;

//    @ApiModelProperty(value = "用户名")
    private String username;

//    @ApiModelProperty(value = "头像url")
    private String avatorUrl;

//    @ApiModelProperty(value = "电话")
    private String phone;

//    @ApiModelProperty(value = "积分")
    private Integer integral;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }
}
