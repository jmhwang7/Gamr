package com.gamr.gamr;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Activity that let's the user set various criteria for finding a game for League of Legends
 */
public class LeagueProfileActivity extends Activity {
    private static final String DEFAULT_TAG_NAME_STRING = "Choose a tag name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sets the activity to fullscreen with no settings bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_league_profile);
    }
}
