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
    private List<String> roles;

    @SerializedName("2")
    private List<String> rank;

    @SerializedName("3")
    private List<String> gameModes;

    @SerializedName("4")
    private List<String> server;

    public LeagueProfile() {
    }

    @Override
    public String toString() {
        return "Summoner Name: " + getSummonerName() + "\n"
                + "Rank: " + getRank() + "\n"
                + "Roles: " + getRoles() + "\n"
                + "Modes: " + getGameModes() + "\n"
                + "Server: " + getServer();
    }

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    public String getRank() {
        return rank.get(0);
    }

    public void setRank(List<String> rank) {
        this.rank = rank;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getGameModes() {
        return gameModes;
    }

    public void setGameModes(List<String> gameModes) {
        this.gameModes = gameModes;
    }

    public String getServer() {
        return server.get(0);
    }

    public void setServer(List<String> server) {
        this.server = server;
    }
}
