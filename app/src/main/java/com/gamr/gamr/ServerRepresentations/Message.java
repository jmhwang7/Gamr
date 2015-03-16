package com.gamr.gamr.ServerRepresentations;

/**
 * Used to represent a singular message from another user.
 */
public class Message {
    public static final int MESSAGE_PREVIEW_LENGTH = 20;
    public static final String RECEIVER_USER_NAME = "Antonio";

    private String mMessageContent;
    private String mMessageSender;
    private String mTimeReceived;
    private String mMessagePreview;
    private String mOtherUserId;
    private boolean mMessageViewed;

    public Message(String content, String sender, String timeReceived, String otherUserId) {
        mMessageContent = content;
        mMessageSender = sender;
        mTimeReceived = timeReceived;
        mMessagePreview = mMessageContent.length() > MESSAGE_PREVIEW_LENGTH ?
                mMessageContent.substring(0, MESSAGE_PREVIEW_LENGTH) : mMessageContent;
        mMessageViewed = false;
        mOtherUserId = otherUserId;
    }

    public String getMessageContent() {
        return mMessageContent;
    }

    public String getTimeReceived() {
        return mTimeReceived;
    }

    public String getMessageSender() {
        return mMessageSender;
    }

    public String getMessagePreview() {
        return mMessagePreview;
    }

    public String getOtherUserID() {
        return mOtherUserId;
    }

    public boolean wasMessageViewed() {
        return mMessageViewed;
    }

    public void setMessageViewed() {
        mMessageViewed = true;
    }
}
