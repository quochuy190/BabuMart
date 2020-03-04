package com.vn.babumart.models;

import java.io.Serializable;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 16-September-2019
 * Time: 11:02
 * Version: 1.0
 */
public class Gender implements Serializable {
    String sName;
    String sId;

    public Gender(String sName, String sId) {
        this.sName = sName;
        this.sId = sId;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }
}
