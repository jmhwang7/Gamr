package com.gamr.gamr.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;

import com.gamr.gamr.AsyncTasks.UpdateGCMDeviceIdTask;
import com.gamr.gamr.FindGamesActivity;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

/**
 * Created by Jennifer on 4/3/15.
 */
public class GCMUtils {
    private static final String SENDER_ID = "707442672120";
    private static final String LOG_TAG = GCMUtils.class.getSimpleName();
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";

    /**
     * Gets the current registration ID for application on GCM service.
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing registration ID
     */
    public static String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationID = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationID.isEmpty()) {
            Log.i(LOG_TAG, "Registration not found.");
            return "";
        }

        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(LOG_TAG, "App version changed.");
            return "";
        }
        return registrationID;
    }

    private static SharedPreferences getGCMPreferences(Context context) {
        return context.getSharedPreferences(((Activity) context).getClass().getSimpleName(),
                Context.MODE_PRIVATE);
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        }
        catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public static void registerInBackground(Context context) {
        new AsyncTask<Context, Void, Void>() {
            @Override
            protected Void doInBackground(Context... context) {
                Context ctx = context[0];
                String msg = "";
                GoogleCloudMessaging gcm = ((FindGamesActivity) ctx).gcm;
                try {
                    String regid = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;

                    sendRegistrationIdToBackend(regid);
                    storeRegistrationId(ctx, regid);
                }
                catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }

                Log.d(LOG_TAG, msg);
                return null;
            }

        }.execute(context);
    }

    private static void sendRegistrationIdToBackend(String regid) {
        new UpdateGCMDeviceIdTask().execute(regid);
    }

    private static void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(LOG_TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }
}
