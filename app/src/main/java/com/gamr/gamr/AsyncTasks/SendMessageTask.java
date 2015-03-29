package com.gamr.gamr.AsyncTasks;

import android.os.AsyncTask;

import com.gamr.gamr.Server.Server;
import com.gamr.gamr.Server.User;

/**
 * Async task used to send messages to a user
 */
public class SendMessageTask extends AsyncTask<String, Void, Void> {
    private final String LOG_TAG = SendMessageTask.class.getSimpleName();

    @Override
    protected Void doInBackground(String... params) {
        Server.sendMessage(User.sUser.getAccountID(), params[0], params[1]);
        return null;
    }
}
