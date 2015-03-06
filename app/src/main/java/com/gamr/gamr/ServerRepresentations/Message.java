package com.gamr.gamr.ServerRepresentations;

import java.util.ArrayList;

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
    private boolean mMessageViewed;

    public Message(String content, String sender, String timeReceived) {
        mMessageContent = content;
        mMessageSender = sender;
        mTimeReceived = timeReceived;
        mMessagePreview = mMessageContent.length() > MESSAGE_PREVIEW_LENGTH ?
                mMessageContent.substring(0, MESSAGE_PREVIEW_LENGTH) : mMessageContent;
        mMessageViewed = false;
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

    public boolean wasMessageViewed() {
        return mMessageViewed;
    }

    public void setMessageViewed() {
        mMessageViewed = true;
    }

    /**
     * This is used for testing purposes. This will create a test case of messages.
     */
    public static ArrayList<Message> getSampleMessageArrayList() {
        Message message1 = new Message("This is a sample message that is going to be over 20 chars just to test stuff yay!!!!", "Antonio", "Mon 12:00");
        Message message2 = new Message("Short message", "Jennifer", "Tues 5:00");
        Message message3 = new Message("I LOVE MESSAGES", "Jacob", "Thurs 1:00");
        Message message4 = new Message("YAY STUFF", "Thomas", "Sat 3:00");

        ArrayList<Message> list = new ArrayList<>();
        list.add(message1);
        list.add(message1);
        list.add(message1);
        list.add(message1);
        list.add(message1);
        list.add(message1);
        list.add(message1);
        list.add(message1);
        list.add(message1);
        list.add(message1);
        list.add(message2);
        list.add(message3);
        list.add(message4);

        return list;
    }

    /**
     * Used to get a sample conversation between two people for testing purposes
     */
    public static ArrayList<Message> getSampleConversation(String user) {
        ArrayList<Message> list = new ArrayList<Message>();

        list.add(new Message("Hello there!", user, "Mon 12:00"));
        list.add(new Message("Hi!!! Long time no talk!", "Antonio", "Mon 12:05"));
        list.add(new Message("I know how long has it been???", user, "Mon 12:10"));
        list.add(new Message("I don't know, a month maybe!", "Antonio", "Mon 12:00"));

        return list;
    }
}
