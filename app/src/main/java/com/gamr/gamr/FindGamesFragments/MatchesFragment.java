package com.gamr.gamr.FindGamesFragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gamr.gamr.R;
import com.gamr.gamr.Server.Match;
import com.gamr.gamr.Server.Server;
import com.gamr.gamr.Server.User;
import com.gamr.gamr.Utils.LogAndErrors;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment that represents the viewing of matches in the area
 */
public class MatchesFragment extends Fragment implements View.OnClickListener{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private View mRootView;
    private List<Match> mMatches;
    private boolean mSearching;

    /**
     * Returns a new instance of this fragment for the given section number.
     * @param sectionNumber
     * @return
     */
    public static MatchesFragment newInstance(int sectionNumber) {
        MatchesFragment fragment = new MatchesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Constructor for MatchesFragment
     */
    public MatchesFragment() {
        mMatches = null;
        mSearching = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.matches_fragment, container, false);

        ((ImageButton) mRootView.findViewById(R.id.pass_button)).setOnClickListener(this);
        ((ImageButton) mRootView.findViewById(R.id.match_button)).setOnClickListener(this);
        ((Button) mRootView.findViewById(R.id.findMatchesButton)).setOnClickListener(this);
        ((Button) mRootView.findViewById(R.id.new_search_button)).setOnClickListener(this);
        ((Button) mRootView.findViewById(R.id.createProfileButton)).setOnClickListener(this);

        if (mSearching) {
            // If we are switching the forms, we need to make one visible and one not visible
            mRootView.findViewById(R.id.matchesFormLayout).setVisibility(View.INVISIBLE);
            mRootView.findViewById(R.id.matchScreenLayout).setVisibility(View.VISIBLE);
        } else {
            // Now we will bring back the form instead of the search method
            mRootView.findViewById(R.id.matchesFormLayout).setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.matchScreenLayout).setVisibility(View.INVISIBLE);
        }

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
        if (mMatches != null && mMatches.size() != 0) {
            Match match = mMatches.get(0);
            ((TextView) mRootView.findViewById(R.id.matchScreenSummonerName)).setText(
                    match.getUsername());

            ((TextView) mRootView.findViewById(R.id.role)).setText(
                    match.getRole().get(0));
            ((TextView) mRootView.findViewById(R.id.ranking)).setText(
                    match.getRank());
            ((TextView) mRootView.findViewById(R.id.role)).setVisibility(View.VISIBLE);
            ((TextView) mRootView.findViewById(R.id.ranking)).setVisibility(View.VISIBLE);
        } else {
            ((TextView) mRootView.findViewById(R.id.matchScreenSummonerName)).setText("There are no summoners near you");
            ((TextView) mRootView.findViewById(R.id.role)).setVisibility(View.INVISIBLE);
            ((TextView) mRootView.findViewById(R.id.ranking)).setVisibility(View.INVISIBLE);
            ((ImageView) mRootView.findViewById(R.id.summoner_icon)).setImageResource(R.drawable.summonericon1);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.match_button:
                passCall(true);
                updateTextFields();
                mRootView.postInvalidate();
                break;

            case R.id.pass_button:
                passCall(false);
                updateTextFields();
                mRootView.postInvalidate();
                break;

            case R.id.findMatchesButton:
                if (!findMatchesFieldsFilled()) {
                    LogAndErrors.displayToast(this.getActivity(), "Please fill out all fields");
                    break;
                }
                // If we are switching the forms, we need to make one visible and one not visible
                mRootView.findViewById(R.id.matchesFormLayout).setVisibility(View.INVISIBLE);
                mRootView.findViewById(R.id.matchScreenLayout).setVisibility(View.VISIBLE);
                mSearching = true;
                newSearch();
                updateTextFields();
                mRootView.postInvalidate();
                break;

            case R.id.new_search_button:
                // Now we will bring back the form instead of the search method
                mRootView.findViewById(R.id.matchesFormLayout).setVisibility(View.VISIBLE);
                mRootView.findViewById(R.id.matchScreenLayout).setVisibility(View.INVISIBLE);
                mSearching = false;
                mRootView.postInvalidate();
                break;

            case R.id.createProfileButton:
                if (!profileFieldsFilled()) {
                    LogAndErrors.displayToast(this.getActivity(), "Please fill out all fields");
                    break;
                }
                mRootView.findViewById(R.id.matchesFormLayout).setVisibility(View.VISIBLE);
                mRootView.findViewById(R.id.createProfileLayout).setVisibility(View.INVISIBLE);
                createProfile();
                mRootView.postInvalidate();
                break;
        }
    }

    /**
     * Returns a boolean representing whether the user has selected a mode list.
     * @return
     */
    private boolean findMatchesFieldsFilled() {
        return (getModesList().size() != 0);
    }

    /**
     * Returns a boolean representing whether the user has filled out their profile.
     * @return
     */
    private boolean profileFieldsFilled() {
        if (String.valueOf(((EditText) mRootView.findViewById(R.id.summoner_name)).getText()).trim().equals("")) {
            return false;
        }

        if (getRolesList().size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * Updates the server whether the user has approved or rejected a user as a match.
     * @param isMatch
     */
    private void passCall(boolean isMatch) {
        MatchTask task = new MatchTask();
        task.execute(isMatch);
        updateTextFields();
    }

    /**
     * Runs a new search for potential users to match with.
     */
    private void newSearch() {
        List<String> modes = getModesList();
        UpdateModesTask task = new UpdateModesTask();
        task.execute(modes);

        SearchTask searchTask = new SearchTask();
        searchTask.execute();
    }

    /**
     * This handle should be used to create the users profile on the server for league
     */
    private void createProfile() {
        String summonerName = String.valueOf(((EditText) mRootView.findViewById(R.id.summoner_name)).getText());
        User.sUser.getGames().add("League");
        UpdateSummonerTask summonerTask = new UpdateSummonerTask();
        summonerTask.execute(summonerName);

        List<String> roles = getRolesList();
        UpdateRoleTask updateRoleTask = new UpdateRoleTask();
        updateRoleTask.execute(roles);
    }

    /**
     * Returns a list of roles selected by the user
     * @return
     */
    private List<String> getRolesList() {
        List<String> roles = new ArrayList<String>();

        if ( ((CheckBox)(mRootView.findViewById(R.id.adCheck))).isChecked() ) {
            roles.add("AD Carry");
        }

        if ( ((CheckBox)(mRootView.findViewById(R.id.midCheck))).isChecked() ) {
            roles.add("Mid");
        }

        if ( ((CheckBox)(mRootView.findViewById(R.id.jungleCheck))).isChecked() ) {
            roles.add("Jungler");
        }

        if ( ((CheckBox)(mRootView.findViewById(R.id.supCheck))).isChecked() ) {
            roles.add("Support");
        }

        if ( ((CheckBox)(mRootView.findViewById(R.id.topCheck))).isChecked() ) {
            roles.add("Top");
        }

        return roles;
    }

    /**
     * Returns a list of game modes selected by the user.
     * @return
     */
    private List<String> getModesList() {
        List<String> roles = new ArrayList<String>();

        if ( ((CheckBox)(mRootView.findViewById(R.id.normalBlind))).isChecked() ) {
            roles.add("Normal Blind");
        }

        if ( ((CheckBox)(mRootView.findViewById(R.id.ranked5))).isChecked() ) {
            roles.add("Ranked 5v5");
        }

        if ( ((CheckBox)(mRootView.findViewById(R.id.rankedDuo))).isChecked() ) {
            roles.add("Ranked Duo Queue");
        }

        if ( ((CheckBox)(mRootView.findViewById(R.id.normalDraft))).isChecked() ) {
            roles.add("Normal Draft");
        }

        if ( ((CheckBox)(mRootView.findViewById(R.id.normal3))).isChecked() ) {
            roles.add("Normal 3v3");
        }

        if ( ((CheckBox)(mRootView.findViewById(R.id.ranked3))).isChecked() ) {
            roles.add("Ranked 3v3");
        }

        if ( ((CheckBox)(mRootView.findViewById(R.id.dominion))).isChecked() ) {
            roles.add("Dominion");
        }

        if ( ((CheckBox)(mRootView.findViewById(R.id.aram))).isChecked() ) {
            roles.add("ARAM");
        }

        return roles;
    }

    /**
     * Updates the server with the user's summoner name
     */
    private class UpdateSummonerTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            Server.updateSummonerName(User.sUser.getAccountID(), params[0]);
            return null;
        }
    }

    /**
     * Updates the server with the role selected by the user.
     */
    private class UpdateRoleTask extends AsyncTask<List<String>, Void, Void> {

        @Override
        protected Void doInBackground(List<String>... params) {
            Server.updateLeagueRoles(User.sUser.getAccountID(), params[0]);
            return null;
        }
    }

    /**
     * Updates the server with the modes selected by the user.
     */
    private class UpdateModesTask extends AsyncTask<List<String>, Void, Void> {

        @Override
        protected Void doInBackground(List<String>... params) {
            Server.updateLeagueGameModes(User.sUser.getAccountID(), params[0]);
            return null;
        }
    }

    /**
     * Asynchronously retrieves new potential matches from the server.
     */
    private class SearchTask extends AsyncTask<Void, Void, List<Match>> {

        @Override
        protected List<Match> doInBackground(Void... params) {
            return Server.getMatches(User.sUser.getAccountID(), true, true);
        }

        @Override
        protected void onPostExecute(List<Match> matches) {
            if (matches != null) {
                Log.d("Testing", "Testing1");
                mMatches = matches;
                updateTextFields();
            }
        }
    }

    /**
     * Asynchronously sends the user's decision for a potential match to the server.
     */
    private class MatchTask extends AsyncTask<Boolean, Void, Void> {

        @Override
        protected Void doInBackground(Boolean... params) {
            if (mMatches != null && mMatches.size() != 0) {
                Server.respondToMatch(User.sUser.getAccountID(), mMatches.get(0).getMatchId(), params[0]);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (mMatches != null && mMatches.size() > 0) {
                Match m = mMatches.remove(0);
                updateTextFields();
            }
        }
    }
}