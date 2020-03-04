package com.vn.babumart.models;

import com.google.gson.annotations.SerializedName;
/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 30-May-2019
 * Time: 16:07
 * Version: 1.0
 */
public class ReportProduct {
    @SerializedName("ERROR")
    String sERROR;
    @SerializedName("MESSAGE")
    String sMESSAGE;
    @SerializedName("MESSGE")
    String MESSGE;
    @SerializedName("RESULT")
    String sRESULT;
    @SerializedName("ID_PRODUCT_CATEGORY")
    String ID_PRODUCT_CATEGORY;
    @SerializedName("NAME")
    String NAME;
    @SerializedName("TOTAL_ORDER")
    String TOTAL_ORDER;
    @SerializedName("TOTAL_QUANTITY")
    String TOTAL_QUANTITY;
    @SerializedName("TOTAL_REVENUE")
    String TOTAL_REVENUE;
    @SerializedName("CODE_PRODUCT")
    String CODE_PRODUCT;
    @SerializedName("PRODUCT_NAME")
    String PRODUCT_NAME;
    String sTypeReport;

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

    public String getID_PRODUCT_CATEGORY() {
        return ID_PRODUCT_CATEGORY;
    }

    public void setID_PRODUCT_CATEGORY(String ID_PRODUCT_CATEGORY) {
        this.ID_PRODUCT_CATEGORY = ID_PRODUCT_CATEGORY;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getTOTAL_ORDER() {
        return TOTAL_ORDER;
    }

    public void setTOTAL_ORDER(String TOTAL_ORDER) {
        this.TOTAL_ORDER = TOTAL_ORDER;
    }

    public String getTOTAL_QUANTITY() {
        return TOTAL_QUANTITY;
    }

    public void setTOTAL_QUANTITY(String TOTAL_QUANTITY) {
        this.TOTAL_QUANTITY = TOTAL_QUANTITY;
    }

    public String getTOTAL_REVENUE() {
        return TOTAL_REVENUE;
    }

    public void setTOTAL_REVENUE(String TOTAL_REVENUE) {
        this.TOTAL_REVENUE = TOTAL_REVENUE;
    }

    public String getCODE_PRODUCT() {
        return CODE_PRODUCT;
    }

    public void setCODE_PRODUCT(String CODE_PRODUCT) {
        this.CODE_PRODUCT = CODE_PRODUCT;
    }

    public String getPRODUCT_NAME() {
        return PRODUCT_NAME;
    }

    public void setPRODUCT_NAME(String PRODUCT_NAME) {
        this.PRODUCT_NAME = PRODUCT_NAME;
    }

    public String getsTypeReport() {
        return sTypeReport;
    }

    public void setsTypeReport(String sTypeReport) {
        this.sTypeReport = sTypeReport;
    }
}
