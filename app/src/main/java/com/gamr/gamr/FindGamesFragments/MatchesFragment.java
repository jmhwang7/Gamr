package com.gamr.gamr.FindGamesFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gamr.gamr.ProfileHandlers.LeagueMatchHandler;
import com.gamr.gamr.ProfileHandlers.MatchHandler;
import com.gamr.gamr.R;
import com.gamr.gamr.Server.User;

import java.util.Map;

/**
 * Fragment that represents the viewing of matches in the area
 */
public class MatchesFragment extends Fragment implements View.OnClickListener{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private MatchHandler mMatchHandler;
    private View mRootView;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MatchesFragment newInstance(int sectionNumber) {
        MatchesFragment fragment = new MatchesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MatchesFragment() {
        mMatchHandler = new LeagueMatchHandler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.matches_fragment, container, false);

        ((Button) mRootView.findViewById(R.id.pass_button)).setOnClickListener(this);
        ((Button) mRootView.findViewById(R.id.match_button)).setOnClickListener(this);
        ((Button) mRootView.findViewById(R.id.findMatchesButton)).setOnClickListener(this);
        ((Button) mRootView.findViewById(R.id.new_search_button)).setOnClickListener(this);
        ((Button) mRootView.findViewById(R.id.createProfileButton)).setOnClickListener(this);

        if (!User.sUser.getGames().contains("League")) {
            mRootView.findViewById(R.id.matchesFormLayout).setVisibility(View.INVISIBLE);
            mRootView.findViewById(R.id.createProfileLayout).setVisibility(View.VISIBLE);
        }

        updateTextFields();
        return mRootView;
    }

    /**
     * Used to update the text fields for the given profile.
     */
    private void updateTextFields() {
        Map<String, String> profileMap = mMatchHandler.getNextMatch();
        if (profileMap != null) {
            ((TextView) mRootView.findViewById(R.id.matchScreenSummonerName)).setText(
                    profileMap.get(LeagueMatchHandler.SUMMONER_NAME_KEY));
            ((TextView) mRootView.findViewById(R.id.role)).setText(
                    profileMap.get(LeagueMatchHandler.ROLE_KEY));
            ((TextView) mRootView.findViewById(R.id.ranking)).setText(
                    profileMap.get(LeagueMatchHandler.RANK_KEY));

            setImageView(profileMap);
        } else {
            ((TextView) mRootView.findViewById(R.id.matchScreenSummonerName)).setText("No summoner");
            ((TextView) mRootView.findViewById(R.id.role)).setText("N/A");
            ((TextView) mRootView.findViewById(R.id.ranking)).setText("N/A");
            ((ImageView) mRootView.findViewById(R.id.summoner_icon)).setImageResource(R.drawable.summonericon1);
        }
    }

    private void setImageView(Map<String, String> profileMap) {
        int drawResId;
        switch (profileMap.get(LeagueMatchHandler.ICON_KEY)) {
            case "1":
                drawResId = R.drawable.summonericon1;
                break;

            case "2":
                drawResId = R.drawable.summonericon2;
                break;

            case "3":
                drawResId = R.drawable.summonericon3;
                break;

            default:
                drawResId = R.drawable.summonericon1;
        }

        ((ImageView) mRootView.findViewById(R.id.summoner_icon)).setImageResource(drawResId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.match_button:
                mMatchHandler.matchProfile();
                updateTextFields();
                mRootView.postInvalidate();
                break;

            case R.id.pass_button:
                mMatchHandler.passProfile();
                updateTextFields();
                mRootView.postInvalidate();
                break;

            case R.id.findMatchesButton:
                // If we are switching the forms, we need to make one visible and one not visible
                mRootView.findViewById(R.id.matchesFormLayout).setVisibility(View.INVISIBLE);
                mRootView.findViewById(R.id.matchScreenLayout).setVisibility(View.VISIBLE);
                updateTextFields();
                mRootView.postInvalidate();
                break;

            case R.id.new_search_button:
                // Now we will bring back the form instead of the search method
                mRootView.findViewById(R.id.matchesFormLayout).setVisibility(View.VISIBLE);
                mRootView.findViewById(R.id.matchScreenLayout).setVisibility(View.INVISIBLE);
                mRootView.postInvalidate();
                break;

            case R.id.createProfileButton:
                mRootView.findViewById(R.id.matchesFormLayout).setVisibility(View.VISIBLE);
                mRootView.findViewById(R.id.createProfileLayout).setVisibility(View.INVISIBLE);
                createProfile();
                mRootView.postInvalidate();
                break;
        }
    }

    /**
     * This handle should be used to create the users profile on the server for league
     */
    private void createProfile() {
        User.sUser.getGames().add("League");
    }
}