package com.vn.babumart.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 02-May-2019
 * Time: 10:41
 * Version: 1.0
 */
public class ObjLogin {
    @SerializedName("ERROR")
    private String sERROR;
    @SerializedName("MESSAGE")
    private String sMESSAGE;
    @SerializedName("RESULT")
    private String sRESULT;
    @SerializedName("ID")
    private String ID;
    @SerializedName("FULL_NAME")
    private String FULL_NAME;
    @SerializedName("GROUPS")
    private String GROUPS;
    @SerializedName("DOB")
    private String DOB;
    @SerializedName("GENDER")
    private String GENDER;
    @SerializedName("USERNAME")
    private String USERNAME;
    @SerializedName("PASSWORD")
    private String PASSWORD;
    @SerializedName("EMAIL")
    private String EMAIL;
    @SerializedName("CITY")
    private String CITY;
    @SerializedName("DISTRICT")
    private String DISTRICT;
    @SerializedName("ADDRESS")
    private String ADDRESS;
    @SerializedName("STATUS")
    private String STATUS;
    @SerializedName("CREATED_DATE")
    private String CREATED_DATE;
    @SerializedName("FACEBOOK")
    private String FACEBOOK;
    @SerializedName("ZALO")
    private String ZALO;
    @SerializedName("VIBER")
    private String VIBER;
    @SerializedName("STK")
    private String STK;
    @SerializedName("TENTK")
    private String TENTK;
    @SerializedName("TEN_NH")
    private String TEN_NH;
    @SerializedName("USER_CODE")
    private String USER_CODE;
    @SerializedName("GROUP_DES")
    private String GROUP_DES;
    @SerializedName("APP_VERSION")
    private String APP_VERSION;
    @SerializedName("MODEL_NAME")
    private String MODEL_NAME;
    @SerializedName("TOKEN_KEY")
    private String TOKEN_KEY;
    @SerializedName("DEVICE_TYPE")
    private String DEVICE_TYPE;
    @SerializedName("OS_VERSION")
    private String OS_VERSION;
    @SerializedName("UUID")
    private String UUID;
    @SerializedName("STATE_LOGIN")
    private String STATE_LOGIN;
    @SerializedName("TOKEN")
    private String TOKEN;
    @SerializedName("WARD")
    private String WARD;
    @SerializedName("WARD_ID")
    private String WARD_ID;

    private static ObjLogin getObject(JSONObject jsonObject) {
        return new Gson().fromJson(jsonObject.toString(), ObjLogin.class);
    }

    public static ArrayList<ObjLogin> getList(String jsonArray) throws JSONException {
        ArrayList<ObjLogin> arrayList = new ArrayList<>();
        Type type = new TypeToken<List<ObjLogin>>() {
        }.getType();

        Gson gson = new Gson();
        arrayList = gson.fromJson(jsonArray, type);

        return arrayList;
    }

    public String getWARD() {
        return WARD;
    }

    public void setWARD(String WARD) {
        this.WARD = WARD;
    }

    public String getWARD_ID() {
        return WARD_ID;
    }

    public void setWARD_ID(String WARD_ID) {
        this.WARD_ID = WARD_ID;
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

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFULL_NAME() {
        return FULL_NAME;
    }

    public void setFULL_NAME(String FULL_NAME) {
        this.FULL_NAME = FULL_NAME;
    }

    public String getGROUPS() {
        return GROUPS;
    }

    public void setGROUPS(String GROUPS) {
        this.GROUPS = GROUPS;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGENDER() {
        return GENDER;
    }

    public void setGENDER(String GENDER) {
        this.GENDER = GENDER;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getDISTRICT() {
        return DISTRICT;
    }

    public void setDISTRICT(String DISTRICT) {
        this.DISTRICT = DISTRICT;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getCREATED_DATE() {
        return CREATED_DATE;
    }

    public void setCREATED_DATE(String CREATED_DATE) {
        this.CREATED_DATE = CREATED_DATE;
    }

    public String getFACEBOOK() {
        return FACEBOOK;
    }

    public void setFACEBOOK(String FACEBOOK) {
        this.FACEBOOK = FACEBOOK;
    }

    public String getZALO() {
        return ZALO;
    }

    public void setZALO(String ZALO) {
        this.ZALO = ZALO;
    }

    public String getVIBER() {
        return VIBER;
    }

    public void setVIBER(String VIBER) {
        this.VIBER = VIBER;
    }

    public String getSTK() {
        return STK;
    }

    public void setSTK(String STK) {
        this.STK = STK;
    }

    public String getTENTK() {
        return TENTK;
    }

    public void setTENTK(String TENTK) {
        this.TENTK = TENTK;
    }

    public String getTEN_NH() {
        return TEN_NH;
    }

    public void setTEN_NH(String TEN_NH) {
        this.TEN_NH = TEN_NH;
    }

    public String getUSER_CODE() {
        return USER_CODE;
    }

    public void setUSER_CODE(String USER_CODE) {
        this.USER_CODE = USER_CODE;
    }

    public String getGROUP_DES() {
        return GROUP_DES;
    }

    public void setGROUP_DES(String GROUP_DES) {
        this.GROUP_DES = GROUP_DES;
    }

    public String getAPP_VERSION() {
        return APP_VERSION;
    }

    public void setAPP_VERSION(String APP_VERSION) {
        this.APP_VERSION = APP_VERSION;
    }

    public String getMODEL_NAME() {
        return MODEL_NAME;
    }

    public void setMODEL_NAME(String MODEL_NAME) {
        this.MODEL_NAME = MODEL_NAME;
    }

    public String getTOKEN_KEY() {
        return TOKEN_KEY;
    }

    public void setTOKEN_KEY(String TOKEN_KEY) {
        this.TOKEN_KEY = TOKEN_KEY;
    }

    public String getDEVICE_TYPE() {
        return DEVICE_TYPE;
    }

    public void setDEVICE_TYPE(String DEVICE_TYPE) {
        this.DEVICE_TYPE = DEVICE_TYPE;
    }

    public String getOS_VERSION() {
        return OS_VERSION;
    }

    public void setOS_VERSION(String OS_VERSION) {
        this.OS_VERSION = OS_VERSION;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getSTATE_LOGIN() {
        return STATE_LOGIN;
    }

    public void setSTATE_LOGIN(String STATE_LOGIN) {
        this.STATE_LOGIN = STATE_LOGIN;
    }

    public String getTOKEN() {
        return TOKEN;
    }

    public void setTOKEN(String TOKEN) {
        this.TOKEN = TOKEN;
    }
}
