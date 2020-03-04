package com.vn.babumart.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 24-June-2019
 * Time: 16:39
 * Version: 1.0
 */
public class ObjNotify implements Serializable {
    @SerializedName("CONTENT")
    private String CONTENT;
    @SerializedName("ID")
    private String ID;
    @SerializedName("SENT_TIME")
    private String SENT_TIME;
    @SerializedName("IS_READ")
    private String IS_READ;

    public ObjNotify(String CONTENT, String ID, String SENT_TIME, String IS_READ) {
        this.CONTENT = CONTENT;
        this.ID = ID;
        this.SENT_TIME = SENT_TIME;
        this.IS_READ = IS_READ;
    }

    public String getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSENT_TIME() {
        return SENT_TIME;
    }

    public void setSENT_TIME(String SENT_TIME) {
        this.SENT_TIME = SENT_TIME;
    }

    public String getIS_READ() {
        return IS_READ;
    }

    public void setIS_READ(String IS_READ) {
        this.IS_READ = IS_READ;
    }
}
