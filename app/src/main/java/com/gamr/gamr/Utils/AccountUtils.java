package com.gamr.gamr.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.gamr.gamr.Server.User;

/**
 * Utility methods for retrieving account information either from the server or local storage
 * Created by Jennifer on 3/29/15.
 */
public class AccountUtils {
    private static final String ACCOUNT_ID_KEY = "account_id";
    private static final String INITIAL_USER_KEY = "initial_user";
    private static final String PROFILE_NAME_KEY = "profile_name";

    /**
     * Retrieves the account id of the user from local storage
     * @param context
     * @return
     */
    public static String getAccountID(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(ACCOUNT_ID_KEY, null);
    }

    /**
     * Sets the account id of the user in local storage
     * @param context
     * @param accountID
     */
    public static void setAccountID(Context context, String accountID) {
        User.sUser.setAccountID(accountID);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString(ACCOUNT_ID_KEY, accountID).commit();
    }

    /**
     * Sets the profile name of the user in local storage
     * @param context
     * @param profileName
     */
    public static void setProfileName(Context context, String profileName) {
        User.sUser.setProfileName(profileName);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString(PROFILE_NAME_KEY, profileName).commit();
    }

    /**
     * Retrieves the profile name of the user from local storage
     * @param context
     * @return
     */
    public static String getProfileName(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PROFILE_NAME_KEY, null);
    }

    /**
     * Retrieves whether the user is an initial user.
     * @param context
     * @return
     */
    public static boolean isInitialUser(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(INITIAL_USER_KEY, true);
    }
}
