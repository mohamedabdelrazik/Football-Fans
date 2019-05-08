package com.example.footballfans.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelTeams {
    @SerializedName("teams")
    @Expose
    private List<ModelTeams2> leagues = null;

    public List<ModelTeams2> getTeams() {
        return leagues;
    }

    public void setTeams(List<ModelTeams2> leagues) {
        this.leagues = leagues;
    }
}
