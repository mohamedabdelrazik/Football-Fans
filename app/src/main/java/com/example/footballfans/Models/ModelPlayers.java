package com.example.footballfans.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelPlayers {
    @SerializedName("player")
    @Expose
    private List<ModelPlayers2> playersList = null;

    public List<ModelPlayers2> getPlayersList() {
        return playersList;
    }

    public void setPlayersList(List<ModelPlayers2> playersList) {
        this.playersList = playersList;
    }
}
