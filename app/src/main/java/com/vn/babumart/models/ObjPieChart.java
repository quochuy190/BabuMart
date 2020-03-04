package com.vn.babumart.models;

import com.anychart.chart.common.dataentry.DataEntry;

import java.io.Serializable;
import java.util.List;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 12-June-2019
 * Time: 09:28
 * Version: 1.0
 */
public class ObjPieChart implements Serializable {
    List<DataEntry> data;
    String title;

    public ObjPieChart(List<DataEntry> data, String title) {
        this.data = data;
        this.title = title;
    }

    public List<DataEntry> getData() {
        return data;
    }

    public void setData(List<DataEntry> data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
