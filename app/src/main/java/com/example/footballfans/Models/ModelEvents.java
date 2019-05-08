package com.example.footballfans.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelEvents {
    @SerializedName("events")
    @Expose
    private List<ModelEvents2> eventsList = null;

    public List<ModelEvents2> getEventsList() {
        return eventsList;
    }

    public void setEventsList(List<ModelEvents2> eventsList) {
        this.eventsList = eventsList;
    }
}
