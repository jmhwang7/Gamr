package com.gamr.gamr;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gamr.gamr.Server.LeagueProfile;
import com.gamr.gamr.Server.Profile;
import com.gamr.gamr.Server.Server;
import com.gamr.gamr.Server.User;

import java.util.ArrayList;
import java.util.List;


public class ProfileActivity extends ActionBarActivity implements View.OnClickListener {
    public static final String PROFILE_NAME_KEY = "PROFILE KEY";
    private boolean mIsUser;
    private boolean[] mButtonsClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mIsUser = User.sUser.getProfileName().equals(getIntent().getExtras().getString(PROFILE_NAME_KEY));

        mButtonsClicked = new boolean[5];

        setInitialStates();
    }

    /**
     * Sets up the various fields and buttons to represent the profile
     */
    private void setInitialStates() {
        if (mIsUser) {
            // We need the profile to be editable
            addListeners();

            List<String> roles = User.sUser.getLeagueProfile().getRoles();

            for (String s : roles) {
                Log.d("Testing", s);
                switch (s) {
                    case LeagueProfile.ADCARRY:
                        mButtonsClicked[4] = true;
                        break;

                    case LeagueProfile.SUPPORT:
                        mButtonsClicked[1] = true;
                        break;

                    case LeagueProfile.JUNGLER:
                        mButtonsClicked[3] = true;
                        break;

                    case LeagueProfile.MID:
                        mButtonsClicked[2] = true;
                        break;

                    case LeagueProfile.TOP:
                        mButtonsClicked[0] = true;
                        break;
                }
            }

            ((TextView) findViewById(R.id.tagNameText)).setText(User.sUser.getProfileName());

            updateButtons();
        } else {
            ((EditText) findViewById(R.id.tagNameText)).setFocusable(false);
            ((EditText) findViewById(R.id.tagNameText)).setFocusableInTouchMode(false);
            ((EditText) findViewById(R.id.tagNameText)).setCursorVisible(false);
            ((EditText) findViewById(R.id.tagNameText)).setClickable(false);
        }
    }

    /**
     * Add listeners to the various buttons
     */
    private void addListeners() {
        ((ImageButton) findViewById(R.id.tankImageButton)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.supportImageButton)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.mageImageButton)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.adcarryImageButton)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.junglerImageButton)).setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        if (mIsUser) {
            // We need to update the profile on the server
            List<String> roles = new ArrayList<String>();

            if (mButtonsClicked[0]) {
                roles.add(LeagueProfile.TOP);
            }

            if (mButtonsClicked[1]) {
                roles.add(LeagueProfile.SUPPORT);
            }

            if (mButtonsClicked[2]) {
                roles.add(LeagueProfile.MID);
            }

            if (mButtonsClicked[3]) {
                roles.add(LeagueProfile.JUNGLER);
            }

            if (mButtonsClicked[4]) {
                roles.add(LeagueProfile.ADCARRY);
            }

            UpdateRoleTask updateRoleTask = new UpdateRoleTask();
            updateRoleTask.execute(roles);

            User.sUser.getLeagueProfile().setRoles(roles);

            String tagName = ((EditText) findViewById(R.id.tagNameText)).getText().toString();

            UpdateTagNameTask updateTagNameTask = new UpdateTagNameTask();
            updateTagNameTask.execute(tagName);

            User.sUser.setProfileName(tagName);
        }

        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tankImageButton:
                mButtonsClicked[0] = !mButtonsClicked[0];
                break;

            case R.id.supportImageButton:
                mButtonsClicked[1] = !mButtonsClicked[1];
                break;

            case R.id.mageImageButton:
                mButtonsClicked[2] = !mButtonsClicked[2];
                break;

            case R.id.junglerImageButton:
                mButtonsClicked[3] = !mButtonsClicked[3];
                break;

            case R.id.adcarryImageButton:
                mButtonsClicked[4] = !mButtonsClicked[4];
                break;
        }
        updateButtons();
    }

    private void updateButtons() {
        findViewById(R.id.tankImageButton).setAlpha(mButtonsClicked[0]?1.0f:0.3f);
        findViewById(R.id.supportImageButton).setAlpha(mButtonsClicked[1]?1.0f:0.3f);
        findViewById(R.id.mageImageButton).setAlpha(mButtonsClicked[2]?1.0f:0.3f);
        findViewById(R.id.junglerImageButton).setAlpha(mButtonsClicked[3]?1.0f:0.3f);
        findViewById(R.id.adcarryImageButton).setAlpha(mButtonsClicked[4]?1.0f:0.3f);
    }

    private class UpdateRoleTask extends AsyncTask<List<String>, Void, Void> {

        @Override
        protected Void doInBackground(List<String>... params) {
            Server.updateLeagueRoles(User.sUser.getAccountID(), params[0]);
            return null;
        }
    }

    private class UpdateTagNameTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            Server.updateUsername(User.sUser.getAccountID(), params[0]);
            return null;
        }
    }

    private class GetProfileTask extends AsyncTask<String, Void, Profile> {

        @Override
        protected Profile doInBackground(String... params) {
            return Server.getProfile(params[0]);
        }

        @Override
        protected void onPostExecute(Profile p) {
            ((EditText) findViewById(R.id.tagNameText)).setText(p.getUsername());
        }
    }
}
