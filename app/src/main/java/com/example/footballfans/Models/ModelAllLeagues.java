package com.example.footballfans.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelAllLeagues {

    @SerializedName("leagues")
    @Expose
    private List<ModelAllLeagues2> leagues = null;

    public List<ModelAllLeagues2> getLeagues() {
        return leagues;
    }

    public void setLeagues(List<ModelAllLeagues2> leagues) {
        this.leagues = leagues;
    }

}
