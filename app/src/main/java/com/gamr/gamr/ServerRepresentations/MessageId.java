package com.gamr.gamr.ServerRepresentations;

import com.google.gson.annotations.SerializedName;

/**
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
