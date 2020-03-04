package com.vn.babumart.models.respon_api;

import com.google.gson.annotations.SerializedName;
import com.vn.babumart.models.Districts;

import java.util.List;

public class ResponGetDistrict {
    @SerializedName("ERROR")
    private String sERROR;
    @SerializedName("MESSAGE")
    private String sMESSAGE;
    @SerializedName("RESULT")
    private String sRESULT;
    @SerializedName("INFO")
    private List<Districts> lisDistrict;

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

    public List<Districts> getLisDistrict() {
        return lisDistrict;
    }

    public void setLisDistrict(List<Districts> lisDistrict) {
        this.lisDistrict = lisDistrict;
    }
}
