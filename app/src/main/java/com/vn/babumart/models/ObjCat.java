package com.vn.babumart.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 21-May-2019
 * Time: 10:23
 * Version: 1.0
 */
public class ObjCat {
    @SerializedName("IDD")
    String IDD;
    @SerializedName("NAME")
    String NAME;
    @SerializedName("ID_PARENT")
    String ID_PARENT;
    @SerializedName("SUB_NAME")
    String SUB_NAME;
    @SerializedName("SUB_ID_PARENT")
    String SUB_ID_PARENT;

    public String getIDD() {
        return IDD;
    }

    public void setIDD(String IDD) {
        this.IDD = IDD;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getID_PARENT() {
        return ID_PARENT;
    }

    public void setID_PARENT(String ID_PARENT) {
        this.ID_PARENT = ID_PARENT;
    }

    public String getSUB_NAME() {
        return SUB_NAME;
    }

    public void setSUB_NAME(String SUB_NAME) {
        this.SUB_NAME = SUB_NAME;
    }

    public String getSUB_ID_PARENT() {
        return SUB_ID_PARENT;
    }

    public void setSUB_ID_PARENT(String SUB_ID_PARENT) {
        this.SUB_ID_PARENT = SUB_ID_PARENT;
    }
}
