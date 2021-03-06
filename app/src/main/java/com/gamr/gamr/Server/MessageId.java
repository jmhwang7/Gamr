package com.gamr.gamr.Server;

import com.google.gson.annotations.SerializedName;

/**
 * Serialization class for the message id of a message retrieved from the server
 * Created by Thomas on 3/22/2015.
 */
public class MessageId {
    @SerializedName("message_id")
    private int messageId;

    /**
     * Necessary class to deserialize the message id JSON returned from send_message
     */
    public MessageId() {
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
}
