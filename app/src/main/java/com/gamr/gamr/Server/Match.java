package com.gamr.gamr.Server;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Thomas on 3/21/2015.
 */
public class Match {
    @SerializedName("id")
    private String matchId;

    @SerializedName("lon")
    private double longitude;
    @SerializedName("lat")
    private double latitude;

    private double distance;

    public Match() {
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Match's ID " + matchId + "\n" + "Distance from user: " + distance;
    }
}
