package com.vn.babumart.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class City {
    @SerializedName("ERROR")
    String sERROR;
    @SerializedName("MESSAGE")
    String sMESSAGE;
    @SerializedName("MESSGE")
    String MESSGE;
    @SerializedName("RESULT")
    String sRESULT;
    @SerializedName("MATP")
    String MATP;
    @SerializedName("NAME")
    String NAME;
    @SerializedName("TYPE")
    String TYPE;
    @SerializedName("OD")
    String OD;

    public City(String MATP, String NAME) {
        this.MATP = MATP;
        this.NAME = NAME;
    }

    public City() {
    }

    private static City getObject(JSONObject jsonObject) {
        return new Gson().fromJson(jsonObject.toString(), City.class);
    }

    public static ArrayList<City> getList(String jsonArray) throws JSONException {
        ArrayList<City> arrayList = new ArrayList<>();
        Type type = new TypeToken<List<City>>() {
        }.getType();
        Gson gson = new Gson();
        arrayList = gson.fromJson(jsonArray, type);
        return arrayList;
    }

    public String getMATP() {
        return MATP;
    }

    public void setMATP(String MATP) {
        this.MATP = MATP;
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

    public String getOD() {
        return OD;
    }

    public void setOD(String OD) {
        this.OD = OD;
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
}
