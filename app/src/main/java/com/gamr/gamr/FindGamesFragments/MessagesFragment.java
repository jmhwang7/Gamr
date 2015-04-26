package com.gamr.gamr.FindGamesFragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gamr.gamr.ConversationActivity;
import com.gamr.gamr.R;
import com.gamr.gamr.Server.Message;
import com.gamr.gamr.Server.Server;
import com.gamr.gamr.Server.User;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment that shows the current user's messages
 */
public class MessagesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private List<Message> mMessages;
    private View mRootView;
    private MessagesAdapter mAdapter;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MessagesFragment newInstance(int sectionNumber) {
        MessagesFragment fragment = new MessagesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MessagesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.messages_fragment, container, false);

        // First we find the list view
        final ListView messagesListView = (ListView) mRootView.findViewById(R.id.messagesListView);

        updateConversations();

        /*
        // From there we will populate our list with our current messages
        if (mMessages == null) {
            mMessages = getCurrentMessages();
        }

        if (mAdapter == null) {
            // We then create the adapter based on that list and attach it to the view
            final MessagesAdapter adapter = new MessagesAdapter(mRootView.getContext(), mMessages);
            messagesListView.setAdapter(adapter);
        } */

        // We also need to attach the on click listener
        messagesListView.setOnItemClickListener(new MessagesListener());

        SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.refreshlayout);
        swipeLayout.setOnRefreshListener(this);

        return mRootView;
    }

    /**
     * Updates the conversations for the user
     */
    public void updateConversations() {
        GetConversationsTask task = new GetConversationsTask(getActivity());
        task.execute();
    }

    /**
     * Retrieves the current messages.
     */
    public List<Message> getCurrentMessages() {
        // TODO We need to integrate this with the server
        User.sUser.getMostRecentMessagesList();
        List<Message> tempList = new ArrayList<Message>();
        tempList.add(new Message("Test", "d49f9b92-b927-11e4-847c-8bb5e9000002", 10L, "d49f9b92-b927-11e4-847c-8bb5e9000003"));
        return tempList;
    }

    @Override
    public void onRefresh() {
        updateConversations();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((SwipeRefreshLayout) mRootView.findViewById(R.id.refreshlayout)).setRefreshing(false);
            }
        }, 500);
    }


    /**
     * A listener meant to track when a user clicks on a message in the view.
     */
    public class MessagesListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Update that we have seen the message
            mMessages.get(position).setMessageViewed();

            // Redraw the entirety of the stuff
            ((ListView) mRootView.findViewById(R.id.messagesListView)).invalidateViews();

            Intent intent = new Intent(getActivity(), ConversationActivity.class);
            intent.putExtra(ConversationActivity.SENDER_KEY,
                    mMessages.get(position).getFromId());

            startActivity(intent);
        }
    }

    /**
     * An adapter that creates custom views based on messages.
     */
    public class MessagesAdapter extends ArrayAdapter<Message> {
        private final Context mContext;

        public MessagesAdapter(Context context, List<Message> messages) {
            super(context, R.layout.message_adapter, messages);
            mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.message_adapter, parent, false);

            // Once we have the row view, we can populate the various fields depending on the message
            TextView previewText = (TextView) rowView.findViewById(R.id.messagePreviewText);
            TextView senderText = (TextView) rowView.findViewById(R.id.messageSenderText);
            TextView timeReceivedText = (TextView) rowView.findViewById(R.id.messageTimeText);

            Message message = this.getItem(position);
            previewText.setText(message.getMessagePreview());
            senderText.setText(message.getFromUsername());
            timeReceivedText.setText(message.getTimeReceived());

            // Sets the color if it hasn't been viewed yet
            if (!message.wasMessageViewed()) {
                rowView.setBackgroundResource(R.color.NewMessageBackground);
            } else {
                rowView.setBackgroundResource(R.color.TransparentColor);
            }

            return rowView;
        }
    }

    private class GetConversationsTask extends AsyncTask<Void, Void, List<Message>> {
        private Context mContext;

        public GetConversationsTask(Context context) {
            mContext = context;
        }

        @Override
        protected List<Message> doInBackground(Void ... params) {
            return Server.getConversation(User.sUser.getAccountID(), null);
        }

        @Override
        protected void onPostExecute(List<Message> messages) {
            if (mContext != null) {
                mMessages = messages;
                Log.d("Testing", "Size : " + messages.size());
                mAdapter = new MessagesAdapter(this.mContext, messages);
                final ListView messagesListView = (ListView) mRootView.findViewById(R.id.messagesListView);
                messagesListView.setAdapter(mAdapter);
            }
        }
    }
}
