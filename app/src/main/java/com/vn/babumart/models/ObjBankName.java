package com.vn.babumart.models;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 06-September-2019
 * Time: 14:51
 * Version: 1.0
 */
public class ObjBankName {
    String sNameBank;
    String sCodeBank;
    String id;

    public ObjBankName(String sNameBank, String sCodeBank, String id) {
        this.sNameBank = sNameBank;
        this.sCodeBank = sCodeBank;
        this.id = id;
    }

    public String getsNameBank() {
        return sNameBank;
    }

    public void setsNameBank(String sNameBank) {
        this.sNameBank = sNameBank;
    }

    public String getsCodeBank() {
        return sCodeBank;
    }

    public void setsCodeBank(String sCodeBank) {
        this.sCodeBank = sCodeBank;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
