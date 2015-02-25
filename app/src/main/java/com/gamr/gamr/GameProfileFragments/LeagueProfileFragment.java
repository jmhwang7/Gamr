package com.gamr.gamr.GameProfileFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gamr.gamr.FindGamesActivity;
import com.gamr.gamr.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 */
public class LeagueProfileFragment extends Fragment {
    public LeagueProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_league_profile, container, false);
        Button findMatchesBtn = (Button) rootView.findViewById(R.id.findMatchesButton);
        findMatchesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FindGamesActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

}
