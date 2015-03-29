package com.gamr.gamr.Server;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.util.Date;

/**
 * Used to represent a singular message from another user.
 */
public class Message {
    private static final String LOG_TAG = Message.class.getSimpleName();

    public static final int MESSAGE_PREVIEW_LENGTH = 20;
    public static final String RECEIVER_USER_NAME = "Antonio";

    //JSON fields
    @SerializedName("message_id")
    private String messageId;

    @SerializedName("text")
    private String text;

    @SerializedName("to_id")
    private String toId;

    @SerializedName("to_username")
    private String toUsername;

    @SerializedName("from_id")
    private String fromId;

    @SerializedName("from_username")
    private String fromUsername;

    @SerializedName("date")
    private long date;

    private String mMessagePreview;
    private boolean mMessageViewed;

    public Message() {
    }

    public Message(String content, String sender, long timeReceived, String otherUserId) {
        text = content;
        fromId = sender;
        date = timeReceived;
        mMessagePreview = text.length() > MESSAGE_PREVIEW_LENGTH ?
                text.substring(0, MESSAGE_PREVIEW_LENGTH) : text;
        mMessageViewed = false;
        toId = otherUserId;
    }


    public String getTimeReceived() {
        DateFormat df = DateFormat.getTimeInstance(DateFormat.SHORT);

        // backend stores time in seconds from epoch
        // Java Date constructor takes milliseconds from epoch
        return df.format(new Date(date * 1000));

    }

    @Override
    public String toString() {
        return "Sender: " + fromUsername + "\nReceiver: " + toUsername + "\nTime Received: " + getTimeReceived()
                + "\nMessage: " + text + "\n";
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    public boolean wasMessageViewed() {
        return mMessageViewed;
    }

    public void setMessageViewed() {
        mMessageViewed = true;
    }

    public String getMessagePreview() {
        mMessagePreview = text.length() > MESSAGE_PREVIEW_LENGTH ?
                text.substring(0, MESSAGE_PREVIEW_LENGTH) : text;
        return mMessagePreview;
    }
}
