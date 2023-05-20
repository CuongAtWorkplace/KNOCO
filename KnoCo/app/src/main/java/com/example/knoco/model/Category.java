package com.example.knoco.model;

public class Category {

     int image ;
     String CateName ;

    public Category(int image, String cateName) {
        this.image = image;
        this.CateName = cateName;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getCateName() {
        return CateName;
    }

    public void setCateName(String cateName) {
        CateName = cateName;
    }
}
