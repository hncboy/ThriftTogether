package com.pro516.thrifttogether.ui.buy.entity;

import java.util.List;

public class SingleProductEntity  {
    private int code = 200;
    private String message = "";
    private List<DetailAndNoteEntity> data;

    public SingleProductEntity(int code, String message, List<DetailAndNoteEntity> data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DetailAndNoteEntity> getData() {
        return data;
    }

    public void setData(List<DetailAndNoteEntity> data) {
        this.data = data;
    }

}
