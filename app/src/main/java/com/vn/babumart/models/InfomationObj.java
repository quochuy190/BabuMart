package com.vn.babumart.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 18-July-2019
 * Time: 10:02
 * Version: 1.0
 */
public class InfomationObj implements Serializable {
    @SerializedName("COMMENTS")
    String COMMENTS;
    @SerializedName("TYPES")
    String TYPES;
    @SerializedName("ID")
    String ID;
    @SerializedName("TITLE")
    String TITLE;
    @SerializedName("CONTENT")
    String CONTENT;
    @SerializedName("CREATE_DATE")
    String CREATE_DATE;
    @SerializedName("DESCRIPTION")
    String DESCRIPTION;
    @SerializedName("IS_ACTIVE")
    String IS_ACTIVE;
    @SerializedName("IMAGE_COVER")
    String IMAGE_COVER;

    public String getCOMMENTS() {
        return COMMENTS;
    }

    public void setCOMMENTS(String COMMENTS) {
        this.COMMENTS = COMMENTS;
    }

    public String getTYPES() {
        return TYPES;
    }

    public void setTYPES(String TYPES) {
        this.TYPES = TYPES;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }

    public String getCREATE_DATE() {
        return CREATE_DATE;
    }

    public void setCREATE_DATE(String CREATE_DATE) {
        this.CREATE_DATE = CREATE_DATE;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getIS_ACTIVE() {
        return IS_ACTIVE;
    }

    public void setIS_ACTIVE(String IS_ACTIVE) {
        this.IS_ACTIVE = IS_ACTIVE;
    }

    public String getIMAGE_COVER() {
        return IMAGE_COVER;
    }

    public void setIMAGE_COVER(String IMAGE_COVER) {
        this.IMAGE_COVER = IMAGE_COVER;
    }
}
