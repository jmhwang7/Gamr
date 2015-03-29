package com.gamr.gamr.Server;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Thomas on 3/29/2015.
 */
public class LeagueProfile {
    @SerializedName("in_game_name")
    private String summonerName;

    @SerializedName("1")
    private String rank;

    @SerializedName("2")
    private String roles;

    @SerializedName("3")
    private String gameModes;

    public LeagueProfile(){}

    @Override
    public String toString(){
        return "Summoner Name: " + summonerName + "\n"
                + "Rank: " + rank + "\n"
                + "Roles: " + roles + "\n"
                + "Modes: " + gameModes;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getGameModes() {
        return gameModes;
    }

    public void setGameModes(String gameModes) {
        this.gameModes = gameModes;
    }
}
