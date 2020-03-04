package com.vn.babumart.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 18-May-2019
 * Time: 21:10
 * Version: 1.0
 */
public class ObjCommissions implements Serializable {
    @SerializedName("ERROR")
    String sERROR;
    @SerializedName("MESSAGE")
    String sMESSAGE;
    @SerializedName("MESSGE")
    String MESSGE;
    @SerializedName("RESULT")
    String sRESULT;
    @SerializedName("FULL_NAME")
    String NAMECTV;
    @SerializedName("USERNAME")
    String MA_CTV;
    @SerializedName("BALANCE")
    String TOTAL_HH;
    @SerializedName("UPDATE_TIME")
    String UPDATE_TIME;
    @SerializedName("LINE_NUMBER")
    String LINE_NUMBER;
    @SerializedName("USER_CODE")
    String USER_CODE;
    @SerializedName("ID_USER")
    String ID_USER;
    @SerializedName("ID")
    String ID;
    @SerializedName("TRANSACTION_TYPE")
    String TRANSACTION_TYPE;
    @SerializedName("TRANSACTION_NAME")
    String TRANSACTION_NAME;
    @SerializedName("MOBILE")
    String MOBILE;
    @SerializedName("EMAIL")
    String EMAIL;
    @SerializedName("AMOUNT")
    private String AMOUNT;
    @SerializedName("COMMENTS")
    private String COMMENTS;
    @SerializedName("IS_PROCESS")
    private String IS_PROCESS;
    @SerializedName("STATUS")
    private String STATUS;

    public ObjCommissions(String NAMECTV, String MA_CTV, String TOTAL_HH) {
        this.NAMECTV = NAMECTV;
        this.MA_CTV = MA_CTV;
        this.TOTAL_HH = TOTAL_HH;
    }

    public String getIS_PROCESS() {
        return IS_PROCESS;
    }

    public void setIS_PROCESS(String IS_PROCESS) {
        this.IS_PROCESS = IS_PROCESS;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getCOMMENTS() {
        return COMMENTS;
    }

    public void setCOMMENTS(String COMMENTS) {
        this.COMMENTS = COMMENTS;
    }

    public String getTRANSACTION_TYPE() {
        return TRANSACTION_TYPE;
    }

    public void setTRANSACTION_TYPE(String TRANSACTION_TYPE) {
        this.TRANSACTION_TYPE = TRANSACTION_TYPE;
    }

    public String getTRANSACTION_NAME() {
        return TRANSACTION_NAME;
    }

    public void setTRANSACTION_NAME(String TRANSACTION_NAME) {
        this.TRANSACTION_NAME = TRANSACTION_NAME;
    }


    public String getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(String AMOUNT) {
        this.AMOUNT = AMOUNT;
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

    public String getUPDATE_TIME() {
        return UPDATE_TIME;
    }

    public void setUPDATE_TIME(String UPDATE_TIME) {
        this.UPDATE_TIME = UPDATE_TIME;
    }

    public String getLINE_NUMBER() {
        return LINE_NUMBER;
    }

    public void setLINE_NUMBER(String LINE_NUMBER) {
        this.LINE_NUMBER = LINE_NUMBER;
    }

    public String getUSER_CODE() {
        return USER_CODE;
    }

    public void setUSER_CODE(String USER_CODE) {
        this.USER_CODE = USER_CODE;
    }

    public String getID_USER() {
        return ID_USER;
    }

    public void setID_USER(String ID_USER) {
        this.ID_USER = ID_USER;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public ObjCommissions(String NAMECTV) {
        this.NAMECTV = NAMECTV;
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

    public String getNAMECTV() {
        return NAMECTV;
    }

    public void setNAMECTV(String NAMECTV) {
        this.NAMECTV = NAMECTV;
    }

    public String getMA_CTV() {
        return MA_CTV;
    }

    public void setMA_CTV(String MA_CTV) {
        this.MA_CTV = MA_CTV;
    }

    public String getTOTAL_HH() {
        return TOTAL_HH;
    }

    public void setTOTAL_HH(String TOTAL_HH) {
        this.TOTAL_HH = TOTAL_HH;
    }
}
