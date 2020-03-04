package com.vn.babumart.models;

import java.util.List;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 06-September-2019
 * Time: 14:56
 * Version: 1.0
 */
public class ListBankName {
    List<ObjBankName> mList;

    public ListBankName(List<ObjBankName> mList) {
        this.mList = mList;
    }

    public List<ObjBankName> getmList() {
        return mList;
    }

    public void setmList(List<ObjBankName> mList) {
        this.mList = mList;
    }
}
