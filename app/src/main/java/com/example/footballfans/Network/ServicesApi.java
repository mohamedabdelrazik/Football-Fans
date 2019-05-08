package com.example.footballfans.Network;

import com.example.footballfans.Models.ModelAllLeagues;
import com.example.footballfans.Models.ModelEvents;
import com.example.footballfans.Models.ModelPlayerDetails;
import com.example.footballfans.Models.ModelPlayers;
import com.example.footballfans.Models.ModelTable;
import com.example.footballfans.Models.ModelTeams;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServicesApi {

    @GET("all_leagues.php")
    Call<ModelAllLeagues> getAllLeagues();

    @GET("lookup_all_teams.php")
    Call<ModelTeams> getTeamsOfLeagues(@Query("id") String id);

    @GET("lookupteam.php")
    Call<ModelTeams> getTeamDetails(@Query("id") String id);

    @GET("lookup_all_players.php")
    Call<ModelPlayers> getTeamPlayerss(@Query("id") String id);

    @GET("lookupplayer.php")
    Call<ModelPlayerDetails> getPlayerDetails(@Query("id") String id);

    @GET("eventsnext.php")
    Call<ModelEvents> getNextMatches(@Query("id") String id);

    @GET("lookuptable.php")
    Call<ModelTable> getTable(@Query("l") String id);

    @GET("searchplayers.php")
    Call<ModelPlayers> searchForPlayer(@Query("p") String playerName);

    @GET("searchteams.php")
    Call<ModelTeams> searchForTeam(@Query("t") String teamName);

    @GET("eventsday.php")
    Call<ModelEvents> getTodayMatches(@Query("d") String date,
                                      @Query("s") String score);




}
