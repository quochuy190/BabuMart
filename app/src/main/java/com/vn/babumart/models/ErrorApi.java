package com.vn.babumart.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ErrorApi {
    @SerializedName("ERROR")
    String sERROR;
    @SerializedName("MESSAGE")
    String sMESSAGE;
    @SerializedName("MESSGE")
    String MESSGE;
    @SerializedName("RESULT")
    String sRESULT;
    @SerializedName("VALUE")
    String VALUE;
    @SerializedName("DISCOUNT_UP")
    String DISCOUNT_UP;
    @SerializedName("DISCOUNT_DOWN")
    String DISCOUNT_DOWN;
    @SerializedName("URL")
    String URL;
    String sNameImage;

    public ErrorApi() {
    }

    public ErrorApi(String sERROR, String sMESSAGE) {
        this.sERROR = sERROR;
        this.sMESSAGE = sMESSAGE;
    }

    private static ErrorApi getObject(JSONObject jsonObject) {
        return new Gson().fromJson(jsonObject.toString(), ErrorApi.class);
    }

    public static ArrayList<ErrorApi> getList(String jsonArray) throws JSONException {
        ArrayList<ErrorApi> arrayList = new ArrayList<>();
        Type type = new TypeToken<List<ErrorApi>>() {
        }.getType();
        Gson gson = new Gson();
        arrayList = gson.fromJson(jsonArray, type);
        return arrayList;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getsNameImage() {
        return sNameImage;
    }

    public void setsNameImage(String sNameImage) {
        this.sNameImage = sNameImage;
    }

    public String getVALUE() {
        return VALUE;
    }

    public void setVALUE(String VALUE) {
        this.VALUE = VALUE;
    }

    public String getDISCOUNT_UP() {
        return DISCOUNT_UP;
    }

    public void setDISCOUNT_UP(String DISCOUNT_UP) {
        this.DISCOUNT_UP = DISCOUNT_UP;
    }

    public String getDISCOUNT_DOWN() {
        return DISCOUNT_DOWN;
    }

    public void setDISCOUNT_DOWN(String DISCOUNT_DOWN) {
        this.DISCOUNT_DOWN = DISCOUNT_DOWN;
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
