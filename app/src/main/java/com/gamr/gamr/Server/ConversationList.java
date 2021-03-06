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

    /**
     * Public constructor for ConversationList
     * @param otherUserID id of the other user the user is communicating with
     */
    public ConversationList(String otherUserID) {
        mOtherUserID = otherUserID;
        mMessageList = new ArrayList<Message>();
        mMostRecentMessage = null;
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
    public void updateConversation(Context context, String otherUser) {
        UpdateTask task = new UpdateTask(context);
        task.execute(otherUser);
    }

    /**
     * Retrieves the entire conversation from the server.
     */
    private class UpdateTask extends AsyncTask<String, Void, List<Message>> {
        private Context mContext;

        public UpdateTask(Context context) {
            mContext = context;
        }

        @Override
        protected List<Message> doInBackground(String ... params) {
            return Server.getConversation(User.sUser.getAccountID(), params[0]);
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
