package com.gamr.gamr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class SplashScreenActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sets the activity to fullscreen with no settings bar
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

        setContentView(R.layout.activity_splash_screen);
    }

    /**
     * Used to handle the various button events for starting a search
     * @param v Button clicked by user
     */
    public void buttonListener(View v) {

        switch (v.getId()) {
            case R.id.lol_button:
                // If the user wants to start a league search
                Intent intent = new Intent(this, GameProfileActivity.class);
                startActivity(intent);
                finish();
            break;


            default:

            break;
        }
    }
}
