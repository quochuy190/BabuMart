package com.vn.babumart.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 16-August-2019
 * Time: 17:09
 * Version: 1.0
 */
public class ObjConfigCommis {
    @SerializedName("ID")
    String ID;
    @SerializedName("VALUE")
    String VALUE;
    @SerializedName("DISCOUNT_UP")
    String DISCOUNT_UP;
    @SerializedName("DISCOUNT_DOWN")
    String DISCOUNT_DOWN;
    @SerializedName("TYPES")
    String TYPES;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getVALUE() {
        return VALUE;
    }

    public void setVALUE(String VALUE) {
        this.VALUE = VALUE;
    }

    public String getDISCOUNT_UP() {
        return DISCOUNT_UP;
    }

    public void setDISCOUNT_UP(String DISCOUNT_UP) {
        this.DISCOUNT_UP = DISCOUNT_UP;
    }

    public String getDISCOUNT_DOWN() {
        return DISCOUNT_DOWN;
    }

    public void setDISCOUNT_DOWN(String DISCOUNT_DOWN) {
        this.DISCOUNT_DOWN = DISCOUNT_DOWN;
    }

    public String getTYPES() {
        return TYPES;
    }

    public void setTYPES(String TYPES) {
        this.TYPES = TYPES;
    }
}
