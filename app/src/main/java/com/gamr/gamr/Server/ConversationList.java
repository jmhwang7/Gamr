package com.gamr.gamr.Server;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a conversation between two users. It contains the most recent message as well as all
 * previous messages
 */
public class ConversationList {
    private Message mMostRecentMessage;
    private List<Message> mMessageList;
    private String mOtherUserID;

    public ConversationList(String otherUserID) {
        mOtherUserID = otherUserID;
        mMessageList = new ArrayList<Message>();
        mMostRecentMessage = null;
        updateConversation();
    }

    /**
     * Gets the most recent message in the conversation between the two users
     */
    public Message getMostRecentMessage() {
        return mMostRecentMessage;
    }

    /**
     * Gets the entire list of messages between the two users
     */
    public List<Message> getMessageList() {
        return mMessageList;
    }

    /**
     * Gets the Android device ID of the user that the current user has a conversation with
     */
    public String getOtherUserID() {
        return mOtherUserID;
    }

    /**
     * This method will update the conversation by getting the conversation's most recent changes
     * from the server.
     */
    public void updateConversation() {
        // TODO Ping the server for information on this

        // TODO Take this out
        getDemoConversation();
    }

    private void getDemoConversation() {
        mMostRecentMessage = new Message("This is simply a test message", "SenderTest", 1425261066,
                mOtherUserID);
        mMessageList = new ArrayList<Message>();
        mMessageList.add(mMostRecentMessage);
    }
}
