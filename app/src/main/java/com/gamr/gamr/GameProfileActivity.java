package com.gamr.gamr;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.gamr.gamr.GameProfileFragments.GamesListFragment;
import com.gamr.gamr.GameProfileFragments.LeagueProfileFragment;

/**
 * Activity that let's the user set various criteria for finding a game for League of Legends
 */
public class GameProfileActivity extends FragmentActivity
        implements GamesListFragment.OnGameSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_profile);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new GamesListFragment())
                    .addToBackStack("Showing GamesList")
                    .commit();
        }
    }

    @Override
    public void onGameSelected() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new LeagueProfileFragment())
                .addToBackStack("Showing LoL Profile form.")
                .commit();
    }

}
