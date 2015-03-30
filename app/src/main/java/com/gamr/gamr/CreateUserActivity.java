package com.gamr.gamr;

import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gamr.gamr.Server.User;
import com.gamr.gamr.Utils.AccountUtils;
import com.gamr.gamr.Utils.GooglePlayUtils;


public class CreateUserActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        ((Button) findViewById(R.id.findMatchesButton)).setOnClickListener(this);
        if (GooglePlayUtils.checkPlayServices(this)) {
            GooglePlayUtils.showAccountPicker(this);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_user, menu);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.findMatchesButton:
                String tagName = ((EditText)findViewById(R.id.choose_tag_name)).getText().toString();

                if (tagName.trim().equals("")) {
                    Toast.makeText(this, "Please put a tag name", Toast.LENGTH_LONG).show();
                    break;
                }

                AccountUtils.setProfileName(this, tagName);
                User.sUser.generateProfile();
                Intent intent = new Intent(CreateUserActivity.this, FindGamesActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case GooglePlayUtils.REQUEST_CODE_RECOVER_PLAY_SERVICES:
                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Google Play Services must be installed.",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            case GooglePlayUtils.REQUEST_CODE_PICK_ACCOUNT:
                if (resultCode == RESULT_OK) {
                    String accountID = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    AccountUtils.setAccountID(this, accountID);
                }
                else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "This application requires a Google account.",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
