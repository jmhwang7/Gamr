package com.gamr.gamr.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * Created by Jennifer on 3/29/15.
 */
public class GooglePlayUtils {
    public static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;
    public static final int REQUEST_CODE_PICK_ACCOUNT = 1002;

    public static boolean checkPlayServices(Context context) {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (status != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
                showErrorDialog(context, status);
            } else {
                Toast.makeText(context, "This device is not supported.",
                        Toast.LENGTH_LONG).show();
                ((Activity) context).finish();
            }
            return false;
        }
        return true;
    }

    private static void showErrorDialog(Context context, int code) {
        GooglePlayServicesUtil.getErrorDialog(code, (Activity) context, REQUEST_CODE_RECOVER_PLAY_SERVICES)
                .show();
    }

    public static void showAccountPicker(Context context) {
        Intent pickAccountIntent = AccountPicker.newChooseAccountIntent(null, null,
                new String[]{ GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE },
                true, null, null, null, null);
        ((Activity) context).startActivityForResult(pickAccountIntent, REQUEST_CODE_PICK_ACCOUNT);
    }
}
