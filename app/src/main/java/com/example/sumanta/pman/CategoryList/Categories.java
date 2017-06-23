package com.example.sumanta.pman.CategoryList;

/**
 * Created by Sumanta on 16-06-2017.
 */

public class Categories {

    private String cName;
    private Integer img;

    public Categories(String cName, Integer img) {
        this.cName = cName;
        this.img = img;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }
}
