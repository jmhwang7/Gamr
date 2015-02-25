package com.gamr.gamr.GameProfileFragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gamr.gamr.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class GamesListFragment extends Fragment {

    private ArrayAdapter<String> mGamesAdapter;
    private OnGameSelectedListener mListener;

    public GamesListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_games_list, container, false);

        ArrayList<String> games = new ArrayList<>();
        games.add("League of Legends");

        mGamesAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_game,
                R.id.list_item_game_textview, games);
        ListView listView = (ListView) rootView.findViewById(R.id.listview_games);
        listView.setAdapter(mGamesAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onGameSelected();
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnGameSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnGameSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnGameSelectedListener {
        // Currently no argument, since we only have 1 game
        public void onGameSelected();
    }
}
