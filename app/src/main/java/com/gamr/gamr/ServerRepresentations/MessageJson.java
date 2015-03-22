package com.gamr.gamr.ServerRepresentations;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Thomas on 3/21/2015.
 */
public class MessageJson {
    @SerializedName("message_id")
    private String messageId;

    @SerializedName("from_id")
    private String fromId;

    @SerializedName("to_id")
    private String toId;

    private String date;
    private String text;

    public MessageJson(){}

    @Override
    public String toString(){
        return "From: " + fromId + "\n" + "To: " + toId + "\n" + text;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
