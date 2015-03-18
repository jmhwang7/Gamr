package com.gamr.gamr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;

import com.gamr.gamr.ServerRepresentations.User;


public class SplashScreenActivity extends Activity {
    private static final int SPLASH_TIME = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createUser();

        setContentView(R.layout.activity_splash_screen);

        // This handler will start the activity after the specified time
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, FindGamesActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME);
    }

    /**
     * Creates the user for the entire application.
     */
    private void createUser() {
        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        User.instantiateUser(manager.getDeviceId() );
    }
}
