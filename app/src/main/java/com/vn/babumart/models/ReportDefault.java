package com.vn.babumart.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 07-June-2019
 * Time: 10:42
 * Version: 1.0
 */
public class ReportDefault implements Serializable {
    @SerializedName("DAY")
    private String DAY;
    @SerializedName("TOTAL_ORDER")
    private String TOTAL_ORDER;
    @SerializedName("TOTAL_MONEY")
    private String TOTAL_MONEY;
    @SerializedName("TOTAL_COMMISSION")
    private String TOTAL_COMMISSION;
    @SerializedName("TOTAL_TT")
    private String TOTAL_TT;
    @SerializedName("YEAR")
    private String YEAR;
    @SerializedName("MONTH")
    private String MONTH;
    private String REPORT_TYPE;



    public ReportDefault() {
    }

    public String getDAY() {
        return DAY;
    }

    public void setDAY(String DAY) {
        this.DAY = DAY;
    }

    public String getTOTAL_ORDER() {
        return TOTAL_ORDER;
    }

    public void setTOTAL_ORDER(String TOTAL_ORDER) {
        this.TOTAL_ORDER = TOTAL_ORDER;
    }

    public String getTOTAL_MONEY() {
        return TOTAL_MONEY;
    }

    public void setTOTAL_MONEY(String TOTAL_MONEY) {
        this.TOTAL_MONEY = TOTAL_MONEY;
    }

    public String getTOTAL_COMMISSION() {
        return TOTAL_COMMISSION;
    }

    public void setTOTAL_COMMISSION(String TOTAL_COMMISSION) {
        this.TOTAL_COMMISSION = TOTAL_COMMISSION;
    }

    public String getTOTAL_TT() {
        return TOTAL_TT;
    }

    public void setTOTAL_TT(String TOTAL_TT) {
        this.TOTAL_TT = TOTAL_TT;
    }

    public String getYEAR() {
        return YEAR;
    }

    public void setYEAR(String YEAR) {
        this.YEAR = YEAR;
    }

    public String getMONTH() {
        return MONTH;
    }

    public void setMONTH(String MONTH) {
        this.MONTH = MONTH;
    }

    public String getREPORT_TYPE() {
        return REPORT_TYPE;
    }

    public void setREPORT_TYPE(String REPORT_TYPE) {
        this.REPORT_TYPE = REPORT_TYPE;
    }
}
