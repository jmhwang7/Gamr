package com.gamr.gamr.Utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by Jennifer on 4/24/15.
 */
public class GCMBroadcastReceiver extends WakefulBroadcastReceiver{
    private static final String LOG_TAG = GCMBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(LOG_TAG, "received broadcast message.");
        ComponentName comp = new ComponentName(context.getPackageName(),
                GCMIntentService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
}
