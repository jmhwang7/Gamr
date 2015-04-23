package com.gamr.gamr.AsyncTasks;

import android.os.AsyncTask;

import com.gamr.gamr.Server.Server;
import com.gamr.gamr.Server.User;

/**
 * Created by Jennifer on 4/22/15.
 */
public class UpdateGCMDeviceIdTask extends AsyncTask<String, Void, Void> {
    private final String LOG_TAG = UpdateGCMDeviceIdTask.class.getSimpleName();

    protected Void doInBackground(String... deviceId) {
        Server.updateGCMDeviceId(User.sUser.getAccountID(), deviceId[0]);
        return null;
    }
}
