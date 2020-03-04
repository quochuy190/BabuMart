package com.vn.babumart.models.respon_api;

import com.google.gson.annotations.SerializedName;
import com.vn.babumart.models.ObjConfigCommis;


import java.util.List;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 16-August-2019
 * Time: 17:10
 * Version: 1.0
 */
public class ResponseGetConfig {
    @SerializedName("ERROR")
    private String ERROR;
    @SerializedName("MESSAGE")
    private String MESSAGE;
    @SerializedName("RESULT")
    private String RESULT;
    @SerializedName("INFO")
    private List<ObjConfigCommis> mList;

    public String getERROR() {
        return ERROR;
    }

    public void setERROR(String ERROR) {
        this.ERROR = ERROR;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    public List<ObjConfigCommis> getmList() {
        return mList;
    }

    public void setmList(List<ObjConfigCommis> mList) {
        this.mList = mList;
    }
}
