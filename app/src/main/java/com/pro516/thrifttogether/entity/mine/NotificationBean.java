package com.pro516.thrifttogether.entity.mine;

import java.util.Date;

public class NotificationBean {
    private String img;
    private String title;
    private String content;
    private Date time;
    private String btnName;

    public NotificationBean(String img, String title, String content, Date time, String btnName) {
        this.img = img;
        this.title = title;
        this.content = content;
        this.time = time;
        this.btnName = btnName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getBtnName() {
        return btnName;
    }

    public void setBtnName(String btnName) {
        this.btnName = btnName;
    }
}
