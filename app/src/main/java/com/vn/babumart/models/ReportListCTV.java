package com.vn.babumart.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 30-May-2019
 * Time: 16:07
 * Version: 1.0
 */
public class ReportListCTV implements Serializable {
    @SerializedName("FULL_NAME")
    String FULL_NAME;
    @SerializedName("CREATE_BY")
    String CREATE_BY;
    @SerializedName("USER_CODE")
    String USER_CODE;
    @SerializedName("SUM_ORDER")
    String SUM_ORDER;
    @SerializedName("SUM_MONEY")
    String SUM_MONEY;
    @SerializedName("SUM_COMMISSION")
    String SUM_COMMISSION;
    @SerializedName("CREATE_DATE")
    String CREATE_DATE;
    @SerializedName("FN_TIME")
    String FN_TIME;
    @SerializedName("CODE_ORDER")
    String CODE_ORDER;
    String sYear;
    String sMonth;

    public String getsYear() {
        return sYear;
    }

    public void setsYear(String sYear) {
        this.sYear = sYear;
    }

    public String getsMonth() {
        return sMonth;
    }

    public void setsMonth(String sMonth) {
        this.sMonth = sMonth;
    }

    public String getFULL_NAME() {
        return FULL_NAME;
    }

    public void setFULL_NAME(String FULL_NAME) {
        this.FULL_NAME = FULL_NAME;
    }

    public String getCREATE_BY() {
        return CREATE_BY;
    }

    public void setCREATE_BY(String CREATE_BY) {
        this.CREATE_BY = CREATE_BY;
    }

    public String getUSER_CODE() {
        return USER_CODE;
    }

    public void setUSER_CODE(String USER_CODE) {
        this.USER_CODE = USER_CODE;
    }

    public String getSUM_ORDER() {
        return SUM_ORDER;
    }

    public void setSUM_ORDER(String SUM_ORDER) {
        this.SUM_ORDER = SUM_ORDER;
    }

    public String getSUM_MONEY() {
        return SUM_MONEY;
    }

    public void setSUM_MONEY(String SUM_MONEY) {
        this.SUM_MONEY = SUM_MONEY;
    }

    public String getSUM_COMMISSION() {
        return SUM_COMMISSION;
    }

    public void setSUM_COMMISSION(String SUM_COMMISSION) {
        this.SUM_COMMISSION = SUM_COMMISSION;
    }

    public String getCREATE_DATE() {
        return CREATE_DATE;
    }

    public void setCREATE_DATE(String CREATE_DATE) {
        this.CREATE_DATE = CREATE_DATE;
    }

    public String getFN_TIME() {
        return FN_TIME;
    }

    public void setFN_TIME(String FN_TIME) {
        this.FN_TIME = FN_TIME;
    }

    public String getCODE_ORDER() {
        return CODE_ORDER;
    }

    public void setCODE_ORDER(String CODE_ORDER) {
        this.CODE_ORDER = CODE_ORDER;
    }
}
