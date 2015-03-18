package com.gamr.gamr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;

import com.gamr.gamr.ServerRepresentations.User;


public class SplashScreenActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sets the activity to fullscreen with no settings bar
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

        createUser();

        setContentView(R.layout.activity_splash_screen);
    }

    /**
     * Creates the user for the entire application.
     */
    private void createUser() {
        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        User.instantiateUser(manager.getDeviceId() );
    }

    /**
     * Used to handle the various button events for starting a search
     * @param v Button clicked by user
     */
    public void buttonListener(View v) {

        switch (v.getId()) {
            case R.id.lol_button:
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
