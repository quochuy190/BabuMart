package com.vn.babumart.models.respon_api;

import com.google.gson.annotations.SerializedName;
import com.vn.babumart.models.ReportListCTV;

import java.util.List;

public class ResponGetReportListCTV {
    @SerializedName("ERROR")
    private String sERROR;
    @SerializedName("MESSAGE")
    private String sMESSAGE;
    @SerializedName("RESULT")
    private String sRESULT;
    @SerializedName("MOBILE")
    private String MOBILE;
    @SerializedName("EMAIL")
    private String EMAIL;
    @SerializedName("INFO")
    private List<ReportListCTV> mList;

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

    public List<ReportListCTV> getmList() {
        return mList;
    }

    public void setmList(List<ReportListCTV> mList) {
        this.mList = mList;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }
}
