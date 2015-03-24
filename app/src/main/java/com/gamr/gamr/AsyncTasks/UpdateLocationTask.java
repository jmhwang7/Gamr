package com.gamr.gamr.AsyncTasks;

import android.os.AsyncTask;

import com.gamr.gamr.Server.Server;
import com.gamr.gamr.Server.User;

/**
 * Created by Jennifer on 3/6/15.
 */
public class UpdateLocationTask extends AsyncTask<Double, Void, Void> {
    private final String LOG_TAG = UpdateLocationTask.class.getSimpleName();

    protected Void doInBackground(Double... location) {
        Server.updateLocation(User.sUser.getAndroidID(), location[0], location[1]);
        return null;
    }

}
