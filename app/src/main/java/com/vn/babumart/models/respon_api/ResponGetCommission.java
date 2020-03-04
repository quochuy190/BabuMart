package com.vn.babumart.models.respon_api;

import com.google.gson.annotations.SerializedName;
import com.vn.babumart.models.ObjCommissions;

import java.util.List;

public class ResponGetCommission {
    @SerializedName("ERROR")
    private String sERROR;
    @SerializedName("MESSAGE")
    private String sMESSAGE;
    @SerializedName("RESULT")
    private String sRESULT;
    @SerializedName("INFO")
    private List<ObjCommissions> mList;
    @SerializedName("TONGHH")
    private String TONGHH;
    @SerializedName("TONGRUT")
    private String TONGRUT;


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

    public List<ObjCommissions> getmList() {
        return mList;
    }

    public void setmList(List<ObjCommissions> mList) {
        this.mList = mList;
    }

    public String getTONGHH() {
        return TONGHH;
    }

    public void setTONGHH(String TONGHH) {
        this.TONGHH = TONGHH;
    }

    public String getTONGRUT() {
        return TONGRUT;
    }

    public void setTONGRUT(String TONGRUT) {
        this.TONGRUT = TONGRUT;
    }
}
