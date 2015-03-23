package com.gamr.gamr.ServerRepresentations;

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
    private String message_id;
    private String text;
    private String from_id;
    private long date;
    private String to_id;

    private String mMessagePreview;
    private boolean mMessageViewed;

    public Message() {
    }

    public Message(String content, String sender, long timeReceived, String otherUserId) {
        text = content;
        from_id = sender;
        date = timeReceived;
        mMessagePreview = text.length() > MESSAGE_PREVIEW_LENGTH ?
                text.substring(0, MESSAGE_PREVIEW_LENGTH) : text;
        mMessageViewed = false;
        to_id = otherUserId;
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
                + "\nMessage: " + text + "\n";
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFrom_id() {
        return from_id;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getTo_id() {
        return to_id;
    }

    public void setTo_id(String to_id) {
        this.to_id = to_id;
    }

    public boolean wasMessageViewed() {
        return mMessageViewed;
    }

    public void setMessageViewed() {
        mMessageViewed = true;
    }

    public String getMessagePreview() {
        return mMessagePreview;
    }
}
