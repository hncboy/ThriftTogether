package com.pro516.thrifttogether.ui.buy.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class DetailAndNoteEntity {
    public static final int TEXT = 1;
    public static final int CONTENT = 2;
    private String categoryTitle = "aaa";
    private List<CategoryDetailEntity> data;

    public DetailAndNoteEntity(String categoryTitle, List<CategoryDetailEntity> data) {
        this.categoryTitle = categoryTitle;
        this.data = data;
    }

    public static class CategoryDetailEntity{
        private String name = "bbbb";
        private int num = 2;

        public CategoryDetailEntity(String name, int num) {
            this.name = name;
            this.num = num;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public List<CategoryDetailEntity> getData() {
        return data;
    }

    public void setData(List<CategoryDetailEntity> data) {
        this.data = data;
    }
}
