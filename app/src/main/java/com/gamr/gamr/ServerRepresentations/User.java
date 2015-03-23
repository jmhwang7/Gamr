package com.gamr.gamr.ServerRepresentations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This represents the user on the server
 */
public class User {
    public static User sUser;

    private String mAndroidID;
    private String mProfileName;
    private Map<String, ConversationList> mConversationMap;

    private User(String androidID) {
        mAndroidID = androidID;

        // We need to check if the user exists on our server already. If they do, we should pull the
        // data from the server, otherwise we need to create a new profile for them on the server
        // and retrieve their "generated" profile
        if (checkIfUserExists()) {
            retrieveProfile();
            (new HTTPServerCommunication()).getMessages();
        } else {
            generateProfile();
        }
    }

    /**
     * This creates the static user variable that can be used by the rest of the program
     */
    public static void instantiateUser(String androidID) {
        sUser = new User(androidID);
    }

    /**
     * Gets the profile name for the user
     */
    public String getProfileName() {
        return mProfileName;
    }

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
        return new ConversationList("test");
    }

    /**
     * Generates a profile for the user on the server
     */
    private void generateProfile() {
        // TODO Implement this method
    }

    /**
     * This method pings the server to retrieve the information for the given android ID
     */
    private void retrieveProfile() {
        // TODO Implement the server profile retrieval

        // TODO remove this method call
        generateTestProfile();
    }

    private void generateTestProfile() {
        // TODO Remove this method
        mConversationMap = new HashMap<String, ConversationList>();

        ConversationList testList = new ConversationList("1010");

        mConversationMap.put(testList.getOtherUserID(), testList);

        mProfileName = "Test Profile Name";
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