package com.gamr.gamr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Space;
import android.widget.TextView;

import com.gamr.gamr.AsyncTasks.SendMessageTask;
import com.gamr.gamr.Server.ConversationList;
import com.gamr.gamr.Server.Message;
import com.gamr.gamr.Server.User;

import java.util.List;

/**
 * An activity that represents a conversation between two people that allows for the sending and
 * viewing of messages.
 */
public class ConversationActivity extends ActionBarActivity {
    public static final String SENDER_KEY = "MESSAGE_SENDER";
    public static final String OTHER_USERNAME_KEY = "OTHER USERNAME";

    public static final String LOG_TAG = ConversationActivity.class.getSimpleName();

    private String mOtherUser;
    private List<Message> mMessageList;
    private String mRealName;
    public ConversationAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        // First we find the list view
        final ListView messagesListView = (ListView) findViewById(R.id.conversationListView);

        // From there we will populate our list with our current messages
        if (mMessageList == null) {
            mOtherUser = getIntent().getExtras().getString(SENDER_KEY);
            mRealName = getIntent().getExtras().getString(OTHER_USERNAME_KEY);
            mMessageList = getConversation(mOtherUser);
        }

        if (mAdapter == null) {
            // We then create the adapter based on that list and attach it to the view
            mAdapter = new ConversationAdapter(this, mMessageList);
            messagesListView.setAdapter(mAdapter);
        }
    }

    /**
     * Gets the current conversation for the two users
     */
    private List<Message> getConversation(String user) {
        ConversationList conversationList = User.sUser.getConversation(user);

        conversationList.updateConversation(this, user);

        return conversationList.getMessageList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_conversation, menu);

        MenuItem menuItem = menu.findItem(R.id.action_profile);

        menuItem.setTitle("View " + mRealName + "'s profile");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;

            case R.id.action_refresh_message:
                getConversation(mOtherUser);
                return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_profile) {
            Intent intent = new Intent(ConversationActivity.this, ProfileActivity.class);
            intent.putExtra(ProfileActivity.PROFILE_NAME_KEY, mOtherUser);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Handles send message event from user.
     * @param v
     */
    public void sendMessage(View v) {
        TextView textView = (TextView) findViewById(R.id.messageTextBox);

        if (textView.getText().equals("")) {
            return;
        }

        new SendMessageTask().execute(mOtherUser, textView.getText().toString());

        textView.setText("");

        mMessageList = getConversation(mOtherUser);

        findViewById(R.id.messageLayout).invalidate();
    }

    /**
     * An adapter to change the display of messages based on who sent them
     */
    public class ConversationAdapter extends ArrayAdapter<Message> {
        private final Context mContext;

        public ConversationAdapter(Context context, List<Message> messages) {
            super(context, R.layout.message_adapter, messages);
            mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.conversation_adapter, parent, false);

            TextView messageUsername = (TextView) rowView.findViewById(R.id.messageUsername);
            TextView messageText = (TextView) rowView.findViewById(R.id.messageContent);

            Message message = getItem(getCount() - 1 - position);
            //Message message = mMessageList.get(mMessageList.size() - 1 - position);
            String messageString;

            LinearLayout.LayoutParams spaceParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);

            spaceParams.weight = 0.2f;

            // This sets it to be right justified
            if (message.getFromId().equals(User.sUser.getAccountID())) {
                ((ImageView) (rowView.findViewById(R.id.leftProfileView))).setVisibility(View.INVISIBLE);
                messageText.setGravity(Gravity.RIGHT);
                ((Space) rowView.findViewById(R.id.leftConversationSpace)).setLayoutParams(spaceParams);
            } else {
                ((ImageView) (rowView.findViewById(R.id.rightProfileView))).setVisibility(View.INVISIBLE);
                ((Space) rowView.findViewById(R.id.rightConversationSpace)).setLayoutParams(spaceParams);
            }

            messageUsername.setText(message.getFromUsername());
            messageText.setText(message.getText());
            return rowView;
        }
    }
}

