package com.gamr.gamr.ProfileHandlers;

import java.util.Map;

/**
 * This interface is meant to provide possible matches for the user with the a "profile" for each
 * game that is dependent on the game
 */
public interface MatchHandler {
    /**
     * This method is meant to get the profile for the next available match.
     * @return a map containing key value pairs for various pieces of key data, null if there are
     * no matches left
     */
    public Map<String, String> getNextMatch();

    /**
     * Used to get an updated list of matches from the server.
     */
    public void update();

    /**
     * This method is called when the use wants to match with this profile. It should handle the
     * response to the server,
     */
    public void matchProfile();

    /**
     * This method is called when the user wants to pass on a profile. It should deal with the
     * response to the server.
     */
    public void passProfile();
}
