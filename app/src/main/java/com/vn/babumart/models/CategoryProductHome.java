package com.vn.babumart.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CategoryProductHome implements Serializable, Comparable<CategoryProductHome> {
    @SerializedName("ERROR")
    private String sERROR;
    @SerializedName("MESSAGE")
    private String sMESSAGE;
    @SerializedName("RESULT")
    private String sRESULT;
    @SerializedName("ID")
    private String ID;
    @SerializedName("SUB_ID")
    private String SUB_ID;
    @SerializedName("SUB_NAME")
    private String SUB_NAME;
    @SerializedName("PARENT_NAME")
    private String sName;
    @SerializedName("INFO")
    private List<Products> mList;
    int iPrioritize;
    boolean isHideSub;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public CategoryProductHome(String sName, List<Products> mList) {
        this.sName = sName;
        this.mList = mList;
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

    public boolean isHideSub() {
        return isHideSub;
    }

    public void setHideSub(boolean hideSub) {
        isHideSub = hideSub;
    }

    public String getSUB_ID() {
        return SUB_ID;
    }

    public void setSUB_ID(String SUB_ID) {
        this.SUB_ID = SUB_ID;
    }

    public String getSUB_NAME() {
        return SUB_NAME;
    }

    public void setSUB_NAME(String SUB_NAME) {
        this.SUB_NAME = SUB_NAME;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public List<Products> getmList() {
        return mList;
    }

    public void setmList(List<Products> mList) {
        this.mList = mList;
    }
    /*  public ObjMenuSkill(String sName, List<ObjLessonSkill> lisLessonSkill) {
        this.sName = sName;
        this.lisLessonSkill = lisLessonSkill;
    }*/

    public int compareTo(CategoryProductHome employee) {
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

    public CategoryProductHome(String sName, List<Products> lisLessonSkill, int iPrioritize) {
        this.sName = sName;
        this.mList = lisLessonSkill;
        this.iPrioritize = iPrioritize;
    }
}
