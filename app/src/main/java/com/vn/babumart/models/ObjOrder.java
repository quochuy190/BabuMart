package com.vn.babumart.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 09-May-2019
 * Time: 16:05
 * Version: 1.0
 */
public class ObjOrder implements Serializable {
    @SerializedName("MOBILE")
    private String MOBILE;
    @SerializedName("ERROR")
    private String ERROR;
    @SerializedName("MESSAGE")
    private String MESSAGE;
    @SerializedName("RESULT")
    private String RESULT;
    @SerializedName("ID")
    private String ID;
    @SerializedName("CODE_ORDER")
    private String CODE_ORDER;
    @SerializedName("CREATE_BY")
    private String CREATE_BY;
    @SerializedName("FULL_NAME_CTV")
    private String FULL_NAME_CTV;
    @SerializedName("USER_CODE")
    private String USER_CODE;
    @SerializedName("CREATE_DATE")
    private String CREATE_DATE;
    @SerializedName("STATUS")
    private String STATUS;
    @SerializedName("STATUS_NAME")
    private String STATUS_NAME;
    @SerializedName("MOBILE_RECCEIVER")
    private String MOBILE_RECCEIVER;
    @SerializedName("MOBILE_RECEIVER")
    private String MOBILE_RECEIVER;
    @SerializedName("ID_CITY")
    private String ID_CITY;
    @SerializedName("ID_DISTRICT")
    private String ID_DISTRICT;
    @SerializedName("DISCOUNT")
    private String DISCOUNT;
    @SerializedName("DISTCOUNT")
    private String DISTCOUNT;

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

    public String getMOBILE_RECEIVER() {
        return MOBILE_RECEIVER;
    }

    public void setMOBILE_RECEIVER(String MOBILE_RECEIVER) {
        this.MOBILE_RECEIVER = MOBILE_RECEIVER;
    }

    @SerializedName("FULLNAME_RECEIVER")
    private String FULLNAME_RECEIVER;
    @SerializedName("TOTAL_MONEY")
    private String TOTAL_MONEY;
    @SerializedName("STATUS_EDIT")
    private String STATUS_EDIT;
    @SerializedName("LINE_NUMBER")
    private String LINE_NUMBER;
    @SerializedName("EMAIL")
    private String EMAIL;
    @SerializedName("ADDRESS_RECEIVER")
    private String ADDRESS_RECEIVER;
    @SerializedName("TIME_RECEIVER")
    private String TIME_RECEIVER;
    @SerializedName("NOTE")
    private String NOTE;
    @SerializedName("EXTRA_SHIP")
    private String EXTRA_SHIP;
    @SerializedName("ID_CODE_ORDER")
    private String ID_CODE_ORDER;

    public String getID_CITY() {
        return ID_CITY;
    }

    public void setID_CITY(String ID_CITY) {
        this.ID_CITY = ID_CITY;
    }

    public String getID_DISTRICT() {
        return ID_DISTRICT;
    }

    public void setID_DISTRICT(String ID_DISTRICT) {
        this.ID_DISTRICT = ID_DISTRICT;
    }

    public String getERROR() {
        return ERROR;
    }

    public void setERROR(String ERROR) {
        this.ERROR = ERROR;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getADDRESS_RECEIVER() {
        return ADDRESS_RECEIVER;
    }

    public void setADDRESS_RECEIVER(String ADDRESS_RECEIVER) {
        this.ADDRESS_RECEIVER = ADDRESS_RECEIVER;
    }

    public String getTIME_RECEIVER() {
        return TIME_RECEIVER;
    }

    public void setTIME_RECEIVER(String TIME_RECEIVER) {
        this.TIME_RECEIVER = TIME_RECEIVER;
    }

    public String getNOTE() {
        return NOTE;
    }

    public void setNOTE(String NOTE) {
        this.NOTE = NOTE;
    }

    public String getEXTRA_SHIP() {
        return EXTRA_SHIP;
    }

    public void setEXTRA_SHIP(String EXTRA_SHIP) {
        this.EXTRA_SHIP = EXTRA_SHIP;
    }

    public String getID_CODE_ORDER() {
        return ID_CODE_ORDER;
    }

    public void setID_CODE_ORDER(String ID_CODE_ORDER) {
        this.ID_CODE_ORDER = ID_CODE_ORDER;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCODE_ORDER() {
        return CODE_ORDER;
    }

    public void setCODE_ORDER(String CODE_ORDER) {
        this.CODE_ORDER = CODE_ORDER;
    }

    public String getCREATE_BY() {
        return CREATE_BY;
    }

    public void setCREATE_BY(String CREATE_BY) {
        this.CREATE_BY = CREATE_BY;
    }

    public String getFULL_NAME_CTV() {
        return FULL_NAME_CTV;
    }

    public void setFULL_NAME_CTV(String FULL_NAME_CTV) {
        this.FULL_NAME_CTV = FULL_NAME_CTV;
    }

    public String getUSER_CODE() {
        return USER_CODE;
    }

    public void setUSER_CODE(String USER_CODE) {
        this.USER_CODE = USER_CODE;
    }

    public String getCREATE_DATE() {
        return CREATE_DATE;
    }

    public void setCREATE_DATE(String CREATE_DATE) {
        this.CREATE_DATE = CREATE_DATE;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getSTATUS_NAME() {
        return STATUS_NAME;
    }

    public void setSTATUS_NAME(String STATUS_NAME) {
        this.STATUS_NAME = STATUS_NAME;
    }

    public String getMOBILE_RECCEIVER() {
        return MOBILE_RECCEIVER;
    }

    public void setMOBILE_RECCEIVER(String MOBILE_RECCEIVER) {
        this.MOBILE_RECCEIVER = MOBILE_RECCEIVER;
    }

    public String getFULLNAME_RECEIVER() {
        return FULLNAME_RECEIVER;
    }

    public void setFULLNAME_RECEIVER(String FULLNAME_RECEIVER) {
        this.FULLNAME_RECEIVER = FULLNAME_RECEIVER;
    }

    public String getTOTAL_MONEY() {
        return TOTAL_MONEY;
    }

    public void setTOTAL_MONEY(String TOTAL_MONEY) {
        this.TOTAL_MONEY = TOTAL_MONEY;
    }

    public String getSTATUS_EDIT() {
        return STATUS_EDIT;
    }

    public void setSTATUS_EDIT(String STATUS_EDIT) {
        this.STATUS_EDIT = STATUS_EDIT;
    }

    public String getLINE_NUMBER() {
        return LINE_NUMBER;
    }

    public void setLINE_NUMBER(String LINE_NUMBER) {
        this.LINE_NUMBER = LINE_NUMBER;
    }

    public String getDISCOUNT() {
        return DISCOUNT;
    }

    public void setDISCOUNT(String DISCOUNT) {
        this.DISCOUNT = DISCOUNT;
    }

    public String getDISTCOUNT() {
        return DISTCOUNT;
    }

    public void setDISTCOUNT(String DISTCOUNT) {
        this.DISTCOUNT = DISTCOUNT;
    }
}
