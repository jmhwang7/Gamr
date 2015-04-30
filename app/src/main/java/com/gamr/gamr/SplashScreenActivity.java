package com.gamr.gamr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.gamr.gamr.Server.User;

/**
 * Activity that handles the splash screen of the app.
 */
public class SplashScreenActivity extends Activity {
    private static final int SPLASH_TIME = 1500;
    private static final String LOG_TAG = SplashScreenActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final boolean isUserGenerated = createUser();

        setContentView(R.layout.activity_splash_screen);

        // This handler will start the activity after the specified time
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (!isUserGenerated) {
                    intent = new Intent(SplashScreenActivity.this, FindGamesActivity.class);
                    //intent = new Intent(SplashScreenActivity.this, ProfileActivity.class);
                }
                else {
                    intent = new Intent(SplashScreenActivity.this, CreateUserActivity.class);
                    //intent = new Intent(SplashScreenActivity.this, ProfileActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME);
    }

    /**
     * Creates the user for the entire application and returns whether user has been generated.
     */
    private boolean createUser() {
        User.instantiateUser(this);
        return User.sUser.isUserGenerated();
    }
}
