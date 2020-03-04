package com.vn.babumart.models.respon_api;

import com.google.gson.annotations.SerializedName;
import com.vn.babumart.models.ObjNotify;


import java.util.List;

public class ResponGetnotify {
    @SerializedName("ERROR")
    private String sERROR;
    @SerializedName("MESSAGE")
    private String sMESSAGE;
    @SerializedName("RESULT")
    private String sRESULT;
    @SerializedName("SUM_NOT_READ")
    private String SUM_NOT_READ;
    @SerializedName("INFO")
    private List<ObjNotify> mList;

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

    public String getSUM_NOT_READ() {
        return SUM_NOT_READ;
    }

    public void setSUM_NOT_READ(String SUM_NOT_READ) {
        this.SUM_NOT_READ = SUM_NOT_READ;
    }

    public List<ObjNotify> getmList() {
        return mList;
    }

    public void setmList(List<ObjNotify> mList) {
        this.mList = mList;
    }
}
