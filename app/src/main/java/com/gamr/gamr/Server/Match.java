package com.gamr.gamr.Server;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Thomas on 3/21/2015.
 */
public class Match {
    @SerializedName("id")
    private String matchId;

    @SerializedName("username")
    private String username;

    @SerializedName("role")
    private String role;

    @SerializedName("rank")
    private String rank;

    @SerializedName("distance")
    private double distance;

    @SerializedName("gamemode")
    private String gameMode;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
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
        return "Match's ID " + matchId + "\n" + "Distance from user: " + distance + "\n" + "Rank: " + rank
                + "\n" + "Role: " + role;
    }
}
