package com.gamr.gamr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Activity that let's the user set various criteria for finding a game for League of Legends
 */
public class LeagueProfileActivity extends Activity {
    private static final String DEFAULT_TAG_NAME_STRING = "Choose a tag name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sets the activity to fullscreen with no settings bar
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

        setContentView(R.layout.activity_league_profile);
    }

    /**
     * Handles when a button is clicked in the activity
     */
    public void buttonClicked(View v) {
        switch (v.getId()) {
            case R.id.findMatchesButton:
                // If the user wants to start a league search
                Intent intent = new Intent(this, FindGamesActivity.class);
                startActivity(intent);
                finish();
                break;

            default:

            break;
        }
    }
}
