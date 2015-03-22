package com.gamr.gamr.ServerRepresentations;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * An interface that allows for http communication between the APP and Server providing methods
 * for our specific needs.
 */
public class HTTPServerCommunication {
    private static final String GET_STRING = "GET";
    private static final String POST_STRING = "POST";

    private static final String MESSAGES_STRING = "MESSAGES";
    private static final String MATCH_STRING = "MATCH";
    private static final String USE_GAMES_STRING = "use_games";
    private static final String USE_LOCATION_STRING = "use_location";

    public static final String BASE_URL = "http://gamr.buildism.net/api.php?version=1&function=";

    public static final String TEST_MATCHES_STRING = "http://gamr.buildism.net/api.php?version=1&function=match&user_id=d49f9b92-b927-11e4-847c-8bb5e9000002&use_games=true&use_location=true";


    public void getMessages() {
        // First we build the string for the given user and then pass that as a get message

        // TODO Need to implement this, we currently don't have a way to see those you are having a
        // conversation with
        new AsyncHelper().execute(new String[] {GET_STRING, MESSAGES_STRING, TEST_MATCHES_STRING});
    }

    /**
     * Used to get those who we have matched with
     */
    public void getMatch() {
        String url = BASE_URL;
        url += MATCH_STRING;
        url += "&" + USE_GAMES_STRING + "true";
        url += "&" + USE_LOCATION_STRING + "true";

        new AsyncHelper().execute(new String[]{GET_STRING, MATCH_STRING, url});
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
            String requestType = params[0];
            String function = params[1];
            String url = params[2];

            if (requestType.equals(GET_STRING)) {
                String response = getHelper(url);

                // Once we have our response, we pass it along depending on the appropriate method
                // that should handle it
                if (function.equals(MESSAGES_STRING)) {
                    getMessagesHandler(response);
                } else if (function.equals(MATCH_STRING)) {
                    getMatchHandler(response);
                }

                // TODO Implement the rest of the GET requests

            } else {
                if(function.equals(POST_STRING)){

                }
            }
            return null;
        }

        /**
         * Deals with the response from the server when getting messages
         */
        private void getMessagesHandler(String response) {
            // TODO Implement
        }

        /**
         * Deals with the response from the server when getting matches
         */
        private void getMatchHandler(String response) {
            // TODO Implement
        }
    }
}
