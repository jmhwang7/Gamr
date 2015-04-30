package com.gamr.gamr.ProfileHandlers;

import java.util.HashMap;
import java.util.Map;

/**
 * Specific implementation of the MatchHandler for League of Legends.
 */
public class LeagueMatchHandler implements MatchHandler {
    public static String SUMMONER_NAME_KEY = "Summoner Name";
    public static String ROLE_KEY = "Role";
    public static String RANK_KEY = "Rank";
    public static String ICON_KEY = "Icon";

    private Map<String, String>[] mMatchMapArray;
    private int mCurrentMatch;

    /**
     * Public constructor for League Match Handler
     */
    public LeagueMatchHandler() {
        mMatchMapArray = new HashMap[3];

        mMatchMapArray[0] = new HashMap<String, String>();
        mMatchMapArray[0].put(SUMMONER_NAME_KEY, "TheOddOne");
        mMatchMapArray[0].put(ROLE_KEY, "Jungler");
        mMatchMapArray[0].put(RANK_KEY, "Challenger");
        mMatchMapArray[0].put(ICON_KEY, "1");

        mMatchMapArray[1] = new HashMap<String, String>();
        mMatchMapArray[1].put(SUMMONER_NAME_KEY, "TheBadOne");
        mMatchMapArray[1].put(ROLE_KEY, "AD Carry");
        mMatchMapArray[1].put(RANK_KEY, "Bronze V");
        mMatchMapArray[1].put(ICON_KEY, "3");

        mMatchMapArray[2] = new HashMap<String, String>();
        mMatchMapArray[2].put(SUMMONER_NAME_KEY, "TheEhOne");
        mMatchMapArray[2].put(ROLE_KEY, "Mid");
        mMatchMapArray[2].put(RANK_KEY, "Gold II");
        mMatchMapArray[2].put(ICON_KEY, "2");

        mCurrentMatch = 0;
    }

    @Override
    public Map<String, String> getNextMatch() {
        if (mCurrentMatch >= mMatchMapArray.length) {
            return null;
        }
        return mMatchMapArray[mCurrentMatch];
    }

    @Override
    public void update() {
        // TODO
    }

    @Override
    public void matchProfile() {
        mCurrentMatch++;
    }

    @Override
    public void passProfile() {
        mCurrentMatch++;
    }
}
