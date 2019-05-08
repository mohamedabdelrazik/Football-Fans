package com.example.footballfans.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelTable {

    @SerializedName("table")
    @Expose
    private List<ModelTable2> tableList = null;

    public List<ModelTable2> getTableList() {
        return tableList;
    }

    public void setTableList(List<ModelTable2> tableList) {
        this.tableList = tableList;
    }
}
