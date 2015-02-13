package com.gamr.gamr.FindGamesFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gamr.gamr.ProfileHandlers.LeagueMatchHandler;
import com.gamr.gamr.ProfileHandlers.MatchHandler;
import com.gamr.gamr.R;

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

        updateTextFields();
        return mRootView;
    }

    /**
     * Used to update the text fields for the given profile.
     */
    private void updateTextFields() {
        Map<String, String> profileMap = mMatchHandler.getNextMatch();
        if (profileMap != null) {
            ((TextView) mRootView.findViewById(R.id.summoner_name)).setText(
                    profileMap.get(LeagueMatchHandler.SUMMONER_NAME_KEY));
            ((TextView) mRootView.findViewById(R.id.role)).setText(
                    profileMap.get(LeagueMatchHandler.ROLE_KEY));
            ((TextView) mRootView.findViewById(R.id.ranking)).setText(
                    profileMap.get(LeagueMatchHandler.RANK_KEY));
        } else {
            ((TextView) mRootView.findViewById(R.id.summoner_name)).setText("No summoner");
            ((TextView) mRootView.findViewById(R.id.role)).setText("N/A");
            ((TextView) mRootView.findViewById(R.id.ranking)).setText("N/A");
        }
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
        }
    }
}