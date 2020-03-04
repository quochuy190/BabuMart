package com.vn.babumart.models;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 17-May-2019
 * Time: 16:38
 * Version: 1.0
 */
public class ObjLeftMenu {
    private String sName;
    private int iImage;
    private int id;

    public ObjLeftMenu(String sName, int iImage, int id) {
        this.sName = sName;
        this.iImage = iImage;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public int getiImage() {
        return iImage;
    }

    public void setiImage(int iImage) {
        this.iImage = iImage;
    }
}
