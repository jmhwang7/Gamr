package com.gamr.gamr.Utils;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.gamr.gamr.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Handles any messages from GCM
 * Created by Jennifer on 4/24/15.
 */
public class GCMIntentService extends IntentService{
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuilder;
    private static final String LOG_TAG = GCMIntentService.class.getSimpleName();

    /**
     * Public constructor for GCMIntentService
     */
    public GCMIntentService () {
        super("GCMIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                Log.i(LOG_TAG, "Send error: " + extras.toString());
            }
            else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                Log.i(LOG_TAG, "Deleted messages on server: " + extras.toString());
            }
            else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                String type = extras.getString("message", "");
                switch(type) {
                    case "match":
                        handleMatch(extras);
                        break;
                    case "message":
                        break;
                }
                Log.i(LOG_TAG, extras.toString());
            }
        }

        GCMBroadcastReceiver.completeWakefulIntent(intent);
    }

    /**
     * Private method to handle a "match" message from GCM
     * @param extras
     */
    private void handleMatch(Bundle extras) {
        String other_user = extras.getString("other_user", "");
        sendNotification("You have matched with " + other_user);
    }

    /**
     * Launches a notification to the user with the given message
     * @param message
     */
    private void sendNotification(String message) {
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent intent = PendingIntent.getBroadcast(this, 0, new Intent("Gamr.DoNothing"), 0);

        mBuilder = new NotificationCompat.Builder(this)
                .setFullScreenIntent(intent, true)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Gamr")
                .setContentText(message)
                .setAutoCancel(true);

        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

}
