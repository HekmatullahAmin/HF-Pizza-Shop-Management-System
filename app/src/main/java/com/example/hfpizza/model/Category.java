package com.example.hfpizza.model;

import java.io.Serializable;
import java.sql.Blob;

public class Category implements Serializable {
    private int categoryId;
    private String categoryName;
    private byte[] categoryImage;

    public Category() {
    }

    public Category(int categoryId, String categoryName, byte[] categoryImage) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public byte[] getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(byte[] categoryImage) {
        this.categoryImage = categoryImage;
    }
}
