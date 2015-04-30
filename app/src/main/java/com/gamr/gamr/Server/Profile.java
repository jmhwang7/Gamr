package com.gamr.gamr.Server;

import com.google.gson.annotations.SerializedName;

/**
 * Serialization class for a user profile retrieved from the server
 * Created by Thomas on 3/29/2015.
 */
public class Profile {
    @SerializedName("username")
    private String username;

    @SerializedName("1")
    private LeagueProfile leagueProfile;

    public Profile(){}

    @Override
    public String toString(){
        return "Username: " + username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LeagueProfile getLeagueProfile() {
        return leagueProfile;
    }

    public void setLeagueProfile(LeagueProfile leagueProfile) {
        this.leagueProfile = leagueProfile;
    }
}
