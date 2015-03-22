package com.gamr.gamr.ServerRepresentations;

import java.text.DateFormat;
import java.util.Date;

/**
 * Used to represent a singular message from another user.
 */
public class Message {
    private static final String LOG_TAG = Message.class.getSimpleName();

    public static final int MESSAGE_PREVIEW_LENGTH = 20;
    public static final String RECEIVER_USER_NAME = "Antonio";

    private String text;
    private String from_id;
    private long date;
    private String mMessagePreview;
    private String to_id;
    private boolean mMessageViewed;

    public Message(String content, String sender, long timeReceived, String otherUserId) {
        text = content;
        from_id = sender;
        date = timeReceived;
        mMessagePreview = text.length() > MESSAGE_PREVIEW_LENGTH ?
                text.substring(0, MESSAGE_PREVIEW_LENGTH) : text;
        mMessageViewed = false;
        to_id = otherUserId;
    }

    public String getMessageContent() {
        return text;
    }

    public String getTimeReceived() {
        DateFormat df = DateFormat.getTimeInstance(DateFormat.SHORT);

        // backend stores time in seconds from epoch
        // Java Date constructor takes milliseconds from epoch
        return df.format(new Date(date * 1000));

    }

    @Override
    public String toString() {
        return "Sender: " + from_id + "\nReceiver: " + to_id + "\nTime Received: " + getTimeReceived()
                + "\nMessage: " + getMessageContent() + "\n";
    }

    public String getMessageSender() {
        return from_id;
    }

    public String getMessagePreview() {
        return mMessagePreview;
    }

    public String getOtherUserID() {
        return to_id;
    }

    public boolean wasMessageViewed() {
        return mMessageViewed;
    }

    public void setMessageViewed() {
        mMessageViewed = true;
    }
}
