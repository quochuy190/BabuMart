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
public class ObjCTV implements Serializable {
    @SerializedName("ERROR")
    String sERROR;
    @SerializedName("MESSAGE")
    String sMESSAGE;
    @SerializedName("MESSGE")
    String MESSGE;
    @SerializedName("RESULT")
    String sRESULT;
    @SerializedName("ID")
    String ID;
    @SerializedName("USERNAME")
    String USERNAME;
    @SerializedName("PASSWORD")
    String PASSWORD;
    @SerializedName("FULL_NAME")
    String FULL_NAME;
    @SerializedName("GROUPS")
    String GROUPS;
    @SerializedName("GROUP_DES")
    String GROUP_DES;
    @SerializedName("DOB")
    String DOB;
    @SerializedName("GENDER")
    String GENDER;
    @SerializedName("GENDERNAME")
    String GENDERNAME;
    @SerializedName("EMAIL")
    String EMAIL;
    @SerializedName("CITY")
    String CITY;
    @SerializedName("DISTRICT")
    String DISTRICT;
    @SerializedName("ADDRESS")
    String ADDRESS;
    @SerializedName("STATUS")
    String STATUS;
    @SerializedName("CREATED_DATE")
    String CREATED_DATE;
    @SerializedName("MODIFIED_DATE")
    String MODIFIED_DATE;
    @SerializedName("CREATED_BY")
    String CREATED_BY;
    @SerializedName("FACEBOOK")
    String FACEBOOK;
    @SerializedName("ZALO")
    String ZALO;
    @SerializedName("VIBER")
    String VIBER;
    @SerializedName("STK")
    String STK;
    @SerializedName("TENTK")
    String TENTK;
    @SerializedName("TEN_NH")
    String TEN_NH;
    @SerializedName("CITY_ID")
    String CITY_ID;
    @SerializedName("DISTRICTTT_ID")
    String DISTRICT_ID;
    @SerializedName("USER_CODE")
    String USER_CODE;
    @SerializedName("BALANCE")
    String BALANCE;
    @SerializedName("AVATAR")
    String AVATAR;
    @SerializedName("LINE_NUMBER")
    String LINE_NUMBER;
    @SerializedName("COMISSION")
    String COMISSION;
    @SerializedName("IMG2")
    String IMG2;
    @SerializedName("IMG1")
    String IMG1;
    @SerializedName("SO_CMT")
    String SO_CMT;
    @SerializedName("COMMISSION_CHILD")
    String COMMISSION_CHILD;
    @SerializedName("WARD")
    String WARD;
    @SerializedName("WARD_ID")
    String WARD_ID;

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

    public String getCOMMISSION_CHILD() {
        return COMMISSION_CHILD;
    }

    public void setCOMMISSION_CHILD(String COMMISSION_CHILD) {
        this.COMMISSION_CHILD = COMMISSION_CHILD;
    }

    public String getCOMISSION() {
        return COMISSION;
    }

    public void setCOMISSION(String COMISSION) {
        this.COMISSION = COMISSION;
    }

    public String getIMG2() {
        return IMG2;
    }

    public void setIMG2(String IMG2) {
        this.IMG2 = IMG2;
    }

    public String getIMG1() {
        return IMG1;
    }

    public void setIMG1(String IMG1) {
        this.IMG1 = IMG1;
    }

    public String getSO_CMT() {
        return SO_CMT;
    }

    public void setSO_CMT(String SO_CMT) {
        this.SO_CMT = SO_CMT;
    }

    public String getCITY_ID() {
        return CITY_ID;
    }

    public void setCITY_ID(String CITY_ID) {
        this.CITY_ID = CITY_ID;
    }

    public String getDISTRICT_ID() {
        return DISTRICT_ID;
    }

    public void setDISTRICT_ID(String DISTRICT_ID) {
        this.DISTRICT_ID = DISTRICT_ID;
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

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

    public String getGROUP_DES() {
        return GROUP_DES;
    }

    public void setGROUP_DES(String GROUP_DES) {
        this.GROUP_DES = GROUP_DES;
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

    public String getGENDERNAME() {
        return GENDERNAME;
    }

    public void setGENDERNAME(String GENDERNAME) {
        this.GENDERNAME = GENDERNAME;
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

    public String getMODIFIED_DATE() {
        return MODIFIED_DATE;
    }

    public void setMODIFIED_DATE(String MODIFIED_DATE) {
        this.MODIFIED_DATE = MODIFIED_DATE;
    }

    public String getCREATED_BY() {
        return CREATED_BY;
    }

    public void setCREATED_BY(String CREATED_BY) {
        this.CREATED_BY = CREATED_BY;
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

    public String getBALANCE() {
        return BALANCE;
    }

    public void setBALANCE(String BALANCE) {
        this.BALANCE = BALANCE;
    }

    public String getAVATAR() {
        return AVATAR;
    }

    public void setAVATAR(String AVATAR) {
        this.AVATAR = AVATAR;
    }

    public String getLINE_NUMBER() {
        return LINE_NUMBER;
    }

    public void setLINE_NUMBER(String LINE_NUMBER) {
        this.LINE_NUMBER = LINE_NUMBER;
    }
}
