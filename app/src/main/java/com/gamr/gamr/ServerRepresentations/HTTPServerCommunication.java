package com.gamr.gamr.ServerRepresentations;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * An interface that allows for http communication between the APP and Server providing methods
 * for our specific needs.
 */
public class HTTPServerCommunication {
    private static final String LOG_TAG = HTTPServerCommunication.class.getSimpleName();

    // HTTP Methods
    private static final String GET_STRING = "GET";
    private static final String POST_STRING = "POST";

    // Base URL
    public static final String BASE_URL = "http://gamr.buildism.net/api1/";

    // Endpoints
    private static final String MESSAGES_STRING = "get_messages";
    private static final String MATCH_STRING = "match";
    private static final String USE_GAMES_STRING = "use_games";
    private static final String USE_LOCATION_STRING = "use_location";
    private static final String UPDATE_LOCATION_STRING = "update_location";

    // Query Params
    public static final String USER_ID_PARAM = "user_id";
    public static final String OTHER_USER_ID_PARAM = "other_user_id";
    public static final String BEFORE_TIME_PARAM = "before";
    public static final String USE_GAMES_PARAM = "use_games";
    public static final String USE_LOCATION_PARAM = "use_location";

    public void getMessages() {
        // First we build the string for the given user and then pass that as a get message
        String uri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(MESSAGES_STRING)
                .appendQueryParameter(USER_ID_PARAM, "d49f9b92-b927-11e4-847c-8bb5e9000002")
                .appendQueryParameter(OTHER_USER_ID_PARAM, "d49f9b92-b927-11e4-847c-8bb5e9000003")
                .build().toString();

        new AsyncHelper().execute(new String[]{GET_STRING, MESSAGES_STRING, uri});
    }

    /**
     * Used to get those who we have matched with
     */
    public void getMatch() {
        // // TODO: This was exactly the same as TEST_MATCHES_STRING; Change later.
        String uri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(MATCH_STRING)
                .appendQueryParameter(USER_ID_PARAM, "d49f9b92-b927-11e4-847c-8bb5e9000002")
                .appendQueryParameter(USE_GAMES_PARAM, Boolean.toString(true))
                .appendQueryParameter(USE_LOCATION_PARAM, Boolean.toString(true))
                .build().toString();

        Log.d(LOG_TAG, uri);
        new AsyncHelper().execute(new String[]{GET_STRING, MATCH_STRING, uri});
    }

    private static String getHelper(String url) {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);

        HttpResponse response;
        String responseString = null;

        try {
            response = client.execute(request);
            responseString = EntityUtils.toString(response.getEntity());

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseString;
    }

    private class AsyncHelper extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // We need to differentiate between gets and posts
            if (params[0].equals(GET_STRING)) {
                String response = getHelper(params[2]);
                Log.d(LOG_TAG, response);
                // Once we have our response, we pass it along depending on the appropriate method
                // that should handle it
                if (params[1].equals(MESSAGES_STRING)) {
                    getMessagesHandler(response);
                } else if (params[1].equals(MATCH_STRING)) {
                    getMatchHandler(response);
                }

                // TODO Implement the rest of the GET requests

            } else {
                // TODO Implement POST
            }
            return null;
        }

        /**
         * Deals with the response from the server when getting messages
         */
        private void getMessagesHandler(String response) {
            Type collectionType = new TypeToken<ArrayList<Message>>() {
            }.getType();
            Gson gson = new Gson();
            ArrayList<Message> messages = gson.fromJson(response, collectionType);
            Log.d(LOG_TAG, messages.toString());
        }

        /**
         * Deals with the response from the server when getting matches
         */
        private void getMatchHandler(String response) {
            // TODO Implement
        }
    }
}
