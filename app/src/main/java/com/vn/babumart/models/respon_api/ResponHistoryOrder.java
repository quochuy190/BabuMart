package com.vn.babumart.models.respon_api;

import com.google.gson.annotations.SerializedName;
import com.vn.babumart.models.ObjOrder;

import java.util.List;

public class ResponHistoryOrder {
    @SerializedName("ERROR")
    private String sERROR;
    @SerializedName("MESSAGE")
    private String sMESSAGE;
    @SerializedName("RESULT")
    private String sRESULT;
    @SerializedName("TOTAL_ORDER")
    private String TOTAL_ORDER;
    @SerializedName("INFO")
    private List<ObjOrder> mList;

    public String getsERROR() {
        return sERROR;
    }

    public void setsERROR(String sERROR) {
        this.sERROR = sERROR;
    }

    public String getsMESSAGE() {
        return sMESSAGE;
    }

    public void setsMESSAGE(String sMESSAGE) {
        this.sMESSAGE = sMESSAGE;
    }

    public String getsRESULT() {
        return sRESULT;
    }

    public void setsRESULT(String sRESULT) {
        this.sRESULT = sRESULT;
    }

    public List<ObjOrder> getmList() {
        return mList;
    }

    public void setmList(List<ObjOrder> mList) {
        this.mList = mList;
    }

    public String getTOTAL_ORDER() {
        return TOTAL_ORDER;
    }

    public void setTOTAL_ORDER(String TOTAL_ORDER) {
        this.TOTAL_ORDER = TOTAL_ORDER;
    }
}
