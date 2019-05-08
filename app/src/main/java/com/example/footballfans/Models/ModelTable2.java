package com.example.footballfans.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelTable2 {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("teamid")
    @Expose
    private String teamid;
    @SerializedName("played")
    @Expose
    private String played;
    @SerializedName("goalsfor")
    @Expose
    private String goalsfor;
    @SerializedName("goalsagainst")
    @Expose
    private String goalsagainst;
    @SerializedName("goalsdifference")
    @Expose
    private String goalsdifference;
    @SerializedName("win")
    @Expose
    private String win;
    @SerializedName("draw")
    @Expose
    private String draw;
    @SerializedName("loss")
    @Expose
    private String loss;
    @SerializedName("total")
    @Expose
    private String total;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamid() {
        return teamid;
    }

    public void setTeamid(String teamid) {
        this.teamid = teamid;
    }

    public String getPlayed() {
        return played;
    }

    public void setPlayed(String played) {
        this.played = played;
    }

    public String getGoalsfor() {
        return goalsfor;
    }

    public void setGoalsfor(String goalsfor) {
        this.goalsfor = goalsfor;
    }

    public String getGoalsagainst() {
        return goalsagainst;
    }

    public void setGoalsagainst(String goalsagainst) {
        this.goalsagainst = goalsagainst;
    }

    public String getGoalsdifference() {
        return goalsdifference;
    }

    public void setGoalsdifference(String goalsdifference) {
        this.goalsdifference = goalsdifference;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }

    public String getDraw() {
        return draw;
    }

    public void setDraw(String draw) {
        this.draw = draw;
    }

    public String getLoss() {
        return loss;
    }

    public void setLoss(String loss) {
        this.loss = loss;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
