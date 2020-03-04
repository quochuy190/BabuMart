package com.vn.babumart.models.respon_api;
import com.google.gson.annotations.SerializedName;
import com.vn.babumart.models.ObjCTV;

import java.util.List;

public class ResponGetLisCTV {
    @SerializedName("ERROR")
    private String sERROR;
    @SerializedName("MESSAGE")
    private String sMESSAGE;
    @SerializedName("RESULT")
    private String sRESULT;
    @SerializedName("INFO")
    private List<ObjCTV> lisDistrict;

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

    public List<ObjCTV> getLisDistrict() {
        return lisDistrict;
    }

    public void setLisDistrict(List<ObjCTV> lisDistrict) {
        this.lisDistrict = lisDistrict;
    }
}
