package com.gamr.gamr.Server;

import android.content.Context;

import com.gamr.gamr.Utils.AccountUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This represents the user on the server
 */
public class User {
    public static User sUser;

    private String mAccountID;
    private String mProfileName;
    private Map<String, ConversationList> mConversationMap;
    private List<String> mGames;

    private User(Context context) {
        if ((mAccountID = AccountUtils.getAccountID(context)) != null) {
            retrieveProfile(context);
        }
    }

    /**
     * This creates the static user variable that can be used by the rest of the program
     */
    public static void instantiateUser(Context context) { sUser = new User(context); }


    /**
     * Sets the profile name for the user
     */
    public void setProfileName(String profileName) { mProfileName = profileName; }

    /**
     * Gets the profile name for the user
     */
    public String getProfileName() {
        return mProfileName;
    }

    /**
     * Gets user's id.
     */
    public String getAccountID() { return mAccountID; }

    /**
     * Sets user's id.
     */
    public void setAccountID(String accountID) { mAccountID = accountID; }

    /**
     * Returns whether user exists on server
     */
    public boolean isUserGenerated() { return mAccountID != null; }

    /**
     * Gets a map of all the conversations between this user and other users. The keys are the user
     * ID's and the values are the actual conversations
     */
    public Map<String, ConversationList> getConversationMap() {
        return mConversationMap;
    }

    /**
     * Retrieves a list of all of the most recent messages for each conversation
     */
    public List<Message> getMostRecentMessagesList() {
        List<Message> mostRecentMessagesList = new ArrayList<Message>();

        for (ConversationList list : mConversationMap.values()) {
            mostRecentMessagesList.add(list.getMostRecentMessage());
        }

        return mostRecentMessagesList;
    }

    /**
     * Returns the conversation for the given user.
     */
    public ConversationList getConversation(String user) {
        return new ConversationList(user);
    }

    /**
     * Generates a profile for the user on the server
     */
    public void generateProfile() {
        // TODO Implement this method
    }

    /**
     * This method pings the server to retrieve the information for the given android ID
     */
    private void retrieveProfile(Context context) {
        mAccountID = AccountUtils.getAccountID(context);
        mProfileName = AccountUtils.getProfileName(context);
        // TODO Implement the server profile retrieval

        // TODO remove this method call
        generateTestProfile();
    }

    private void generateTestProfile() {
        // TODO Remove this method
        mConversationMap = new HashMap<String, ConversationList>();

        ConversationList testList = new ConversationList("d49f9b92-b927-11e4-847c-8bb5e9000003");

        mConversationMap.put(testList.getOtherUserID(), testList);

        mProfileName = "d49f9b92-b927-11e4-847c-8bb5e9000002";
        mAccountID = "d49f9b92-b927-11e4-847c-8bb5e9000002";
        mGames = new ArrayList<String>();

    }

    public List<String> getGames() {
        return mGames;
    }

    /**
     * Checks to see if the user is already on the server or not
     */
    private boolean checkIfUserExists() {
        // TODO Implement the server check

        // TODO Remove this, it always returns true
        return true;
    }
}