package com.gamr.gamr.Server;

import android.content.Context;
import android.os.AsyncTask;

import com.gamr.gamr.ConversationActivity;

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
        updateConversation(null);
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
    public void updateConversation(Context context) {
        // TODO Ping the server for information on this
        new UpdateTask(context).execute();
    }

    private void getDemoConversation() {
        mMostRecentMessage = new Message("This is simply a test message", "SenderTest", 1425261066,
                mOtherUserID);
        mMessageList = new ArrayList<Message>();
        mMessageList.add(mMostRecentMessage);
    }

    private class UpdateTask extends AsyncTask<Void, Void, List<Message>> {
        private Context mContext;

        public UpdateTask(Context context) {
            mContext = context;
        }

        @Override
        protected List<Message> doInBackground(Void ... params) {
            return Server.getConversation("d49f9b92-b927-11e4-847c-8bb5e9000002", "d49f9b92-b927-11e4-847c-8bb5e9000003");
        }

        @Override
        protected void onPostExecute(List<Message> messages) {
            mMessageList = messages;
            if (mContext != null) {
                ConversationActivity.ConversationAdapter adapter = ((ConversationActivity) mContext).mAdapter;
                adapter.clear();
                adapter.addAll(messages);
            }
        }
    }
}
