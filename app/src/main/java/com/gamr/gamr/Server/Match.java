package com.gamr.gamr.Server;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Serialization class for a match retrieved from the server
 * Created by Thomas on 3/21/2015.
 */
public class Match {
    @SerializedName("id")
    private String matchId;

    @SerializedName("username")
    private String username;

    @SerializedName("role")
    private List<String> role;

    @SerializedName("rank")
    private List<String> rank;

    @SerializedName("gamemode")
    private List<String> gameMode;

    @SerializedName("distance")
    private double distance;


    public Match() {
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    public String getRank() {
        return rank.get(0);
    }

    public void setRank(List<String> rank) {
        this.rank = rank;
    }

    public List<String> getGameMode() {
        return gameMode;
    }

    public void setGameMode(List<String> gameMode) {
        this.gameMode = gameMode;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Match's ID " + matchId + "\n" + "Distance from user: " + distance + "\n" + "Rank: " + rank.get(0)
                + "\n" + "Role: " + role;
    }
}
