package com.vn.babumart.models.respon_api;

import com.google.gson.annotations.SerializedName;
import com.vn.babumart.models.InfomationObj;


import java.util.List;
/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 18-July-2019
 * Time: 10:01
 * Version: 1.0
 */
public class ResponseInfomation {
    @SerializedName("ERROR")
    private String ERROR;
    @SerializedName("MESSAGE")
    private String MESSAGE;
    @SerializedName("RESULT")
    private String RESULT;
    @SerializedName("INFO")
    private List<InfomationObj> mList;

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

    public List<InfomationObj> getmList() {
        return mList;
    }

    public void setmList(List<InfomationObj> mList) {
        this.mList = mList;
    }
}
