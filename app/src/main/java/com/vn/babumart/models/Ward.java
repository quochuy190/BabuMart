package com.vn.babumart.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Ward {
    @SerializedName("ERROR")
    String sERROR;
    @SerializedName("MESSAGE")
    String sMESSAGE;
    @SerializedName("MESSGE")
    String MESSGE;
    @SerializedName("RESULT")
    String sRESULT;
    @SerializedName("XAID")
    String XAID;
    @SerializedName("NAME")
    String NAME;
    @SerializedName("TYPE")
    String TYPE;
    @SerializedName("MAQH")
    String MAQH;
    @SerializedName("GHN_XAID")
    String GHN_XAID;
    @SerializedName("GHN_HUYENID")
    String GHN_HUYENID;


    public Ward() {
    }

    private static Ward getObject(JSONObject jsonObject) {
        return new Gson().fromJson(jsonObject.toString(), Ward.class);
    }

    public static ArrayList<Ward> getList(String jsonArray) throws JSONException {
        ArrayList<Ward> arrayList = new ArrayList<>();
        Type type = new TypeToken<List<Ward>>() {
        }.getType();
        Gson gson = new Gson();
        arrayList = gson.fromJson(jsonArray, type);
        return arrayList;
    }

    public String getXAID() {
        return XAID;
    }

    public void setXAID(String XAID) {
        this.XAID = XAID;
    }

    public String getGHN_XAID() {
        return GHN_XAID;
    }

    public void setGHN_XAID(String GHN_XAID) {
        this.GHN_XAID = GHN_XAID;
    }

    public String getGHN_HUYENID() {
        return GHN_HUYENID;
    }

    public void setGHN_HUYENID(String GHN_HUYENID) {
        this.GHN_HUYENID = GHN_HUYENID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }


    public String getMESSGE() {
        return MESSGE;
    }

    public void setMESSGE(String MESSGE) {
        this.MESSGE = MESSGE;
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

    public String getsRESULT() {
        return sRESULT;
    }

    public void setsRESULT(String sRESULT) {
        this.sRESULT = sRESULT;
    }

    public String getMAQH() {
        return MAQH;
    }

    public void setMAQH(String MAQH) {
        this.MAQH = MAQH;
    }
}
