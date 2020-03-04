package com.vn.babumart.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 18-July-2019
 * Time: 16:50
 * Version: 1.0
 */
public class PropetiObj {
    @SerializedName("NAME")
    String NAME;
    @SerializedName("TYPE_ID")
    String TYPE_ID;
    @SerializedName("INFO")
    List<PropetiDetail> INFO;

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getTYPE_ID() {
        return TYPE_ID;
    }

    public void setTYPE_ID(String TYPE_ID) {
        this.TYPE_ID = TYPE_ID;
    }

    public List<PropetiDetail> getINFO() {
        return INFO;
    }

    public void setINFO(List<PropetiDetail> INFO) {
        this.INFO = INFO;
    }

    public class PropetiDetail {
        @SerializedName("SUB_ID")
        String SUB_ID;
        @SerializedName("SUB_PROPERTIES")
        String SUB_PROPERTIES;
        String NAME_PARENT;

        public String getSUB_ID() {
            return SUB_ID;
        }

        public void setSUB_ID(String SUB_ID) {
            this.SUB_ID = SUB_ID;
        }

        public String getSUB_PROPERTIES() {
            return SUB_PROPERTIES;
        }

        public void setSUB_PROPERTIES(String SUB_PROPERTIES) {
            this.SUB_PROPERTIES = SUB_PROPERTIES;
        }

        public String getNAME_PARENT() {
            return NAME_PARENT;
        }

        public void setNAME_PARENT(String NAME_PARENT) {
            this.NAME_PARENT = NAME_PARENT;
        }
    }
}
