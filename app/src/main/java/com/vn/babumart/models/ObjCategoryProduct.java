package com.vn.babumart.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ObjCategoryProduct implements Serializable, Comparable<ObjCategoryProduct> {
    @SerializedName("ERROR")
    private String sERROR;
    @SerializedName("MESSAGE")
    private String sMESSAGE;
    @SerializedName("RESULT")
    private String sRESULT;
    @SerializedName("NAME")
    private String sName;
    @SerializedName("INFO")
    private List<ObjCategoryProduct> mList;
    @SerializedName("ID")
    String IDD;
    @SerializedName("ID_PARENT")
    String ID_PARENT;
    @SerializedName("SUB_ID")
    String SUB_ID;
    @SerializedName("SUB_NAME")
    String SUB_NAME;
    @SerializedName("SUB_ID_PARENT")
    String SUB_ID_PARENT;

    int iPrioritize;
    boolean isHideSub;

    public ObjCategoryProduct(String sName, List<ObjCategoryProduct> mList) {
        this.sName = sName;
        this.mList = mList;
    }

    public ObjCategoryProduct(String IDD) {
        this.IDD = IDD;
    }

    public ObjCategoryProduct(String sName, String IDD) {
        this.sName = sName;
        this.IDD = IDD;
    }

    public String getSUB_ID() {
        return SUB_ID;
    }

    public void setSUB_ID(String SUB_ID) {
        this.SUB_ID = SUB_ID;
    }

    public String getsERROR() {
        return sERROR;
    }

    public boolean isHideSub() {
        return isHideSub;
    }

    public void setHideSub(boolean hideSub) {
        isHideSub = hideSub;
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


    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public List<ObjCategoryProduct> getmList() {
        return mList;
    }

    public void setmList(List<ObjCategoryProduct> mList) {
        this.mList = mList;
    }

/*  public ObjMenuSkill(String sName, List<ObjLessonSkill> lisLessonSkill) {
        this.sName = sName;
        this.lisLessonSkill = lisLessonSkill;
    }*/

    public int compareTo(ObjCategoryProduct employee) {
        if (iPrioritize == employee.iPrioritize)
            return 0;
        else if (iPrioritize > employee.iPrioritize)
            return 1;
        else
            return -1;
    }

    public int getiPrioritize() {
        return iPrioritize;
    }

    public void setiPrioritize(int iPrioritize) {
        this.iPrioritize = iPrioritize;
    }

    public ObjCategoryProduct(String sName, List<ObjCategoryProduct> lisLessonSkill, int iPrioritize) {
        this.sName = sName;
        this.mList = lisLessonSkill;
        this.iPrioritize = iPrioritize;
    }

    public String getIDD() {
        return IDD;
    }

    public void setIDD(String IDD) {
        this.IDD = IDD;
    }


    public String getID_PARENT() {
        return ID_PARENT;
    }

    public void setID_PARENT(String ID_PARENT) {
        this.ID_PARENT = ID_PARENT;
    }

    public String getSUB_NAME() {
        return SUB_NAME;
    }

    public void setSUB_NAME(String SUB_NAME) {
        this.SUB_NAME = SUB_NAME;
    }

    public String getSUB_ID_PARENT() {
        return SUB_ID_PARENT;
    }

    public void setSUB_ID_PARENT(String SUB_ID_PARENT) {
        this.SUB_ID_PARENT = SUB_ID_PARENT;
    }
}
