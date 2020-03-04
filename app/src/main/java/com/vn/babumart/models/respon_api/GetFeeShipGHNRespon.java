package com.vn.babumart.models.respon_api;

import com.google.gson.annotations.SerializedName;
import com.vn.babumart.models.FeeShipGHNObj;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 13-January-2020
 * Time: 11:01
 * Version: 1.0
 */
public class GetFeeShipGHNRespon {
    String code;
    String msg;
    FeeShipGHNObj data;
    @SerializedName("ERROR")
    String sERROR;
    @SerializedName("MESSAGE")
    String sMESSAGE;
    @SerializedName("MESSGE")
    String MESSGE;
    @SerializedName("RESULT")
    String sRESULT;

    public GetFeeShipGHNRespon(String sERROR, String sRESULT) {
        this.sERROR = sERROR;
        this.sRESULT = sRESULT;
    }

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

    public String getMESSGE() {
        return MESSGE;
    }

    public void setMESSGE(String MESSGE) {
        this.MESSGE = MESSGE;
    }

    public String getsRESULT() {
        return sRESULT;
    }

    public void setsRESULT(String sRESULT) {
        this.sRESULT = sRESULT;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public FeeShipGHNObj getData() {
        return data;
    }

    public void setData(FeeShipGHNObj data) {
        this.data = data;
    }
}
