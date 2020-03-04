package com.vn.babumart.models;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 04-June-2019
 * Time: 11:38
 * Version: 1.0
 */
public class CustomEvent {
    String sOption;
    String sDes;
    String sType;

    public CustomEvent(String sOption) {
        this.sOption = sOption;
    }

    public String getsOption() {
        return sOption;
    }

    public void setsOption(String sOption) {
        this.sOption = sOption;
    }

    public String getsDes() {
        return sDes;
    }

    public void setsDes(String sDes) {
        this.sDes = sDes;
    }

    public String getsType() {
        return sType;
    }

    public void setsType(String sType) {
        this.sType = sType;
    }
}
