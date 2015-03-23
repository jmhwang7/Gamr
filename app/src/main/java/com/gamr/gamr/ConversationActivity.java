package com.gamr.gamr;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
    private List<Message> mMessageList;
    public ConversationAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);


        // First we find the list view
        final ListView messagesListView = (ListView) findViewById(R.id.conversationListView);

        // From there we will populate our list with our current messages
        if (mMessageList == null) {
            // TODO We should obviously change how the two activities communicate with each other
            // I am thinking we could have some global object that will track the messaging
            mMessageList = getConversation(getIntent().getExtras().getString(SENDER_KEY));
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
        // TODO This needs to be changed to correctly get a conversation
        ConversationList conversationList = User.sUser.getConversation(user);
        conversationList.updateConversation(this);
        return conversationList.getMessageList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_conversation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(View v) {
        // TODO Handle the sending of the message here
        ((TextView) findViewById(R.id.messageTextBox)).setText("");

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

            TextView messageText = (TextView) rowView.findViewById(R.id.messageContent);

            Message message = mMessageList.get(position);
            String messageString;

            // This sets it to be right justified
            if (message.getFromId().equals(Message.RECEIVER_USER_NAME)) {
                messageText.setGravity(Gravity.END);
                messageString = message.getText() + " : " + message.getFromId();
            } else {
                messageString = message.getFromId() + " : " + message.getText();
            }

            messageText.setText(messageString);

            return rowView;
        }
    }
}
