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

    public static final String TEST_MATCHES_STRING = "http://gamr.buildism.net/api.php?version=1&function=match&user_id=d49f9b92-b927-11e4-847c-8bb5e9000002&use_games=true&use_location=true";


    public void getMessages() {
        // First we build the string for the given user and then pass that as a get message

        new AsyncHelper().execute(new String[] {GET_STRING, MESSAGES_STRING, TEST_MATCHES_STRING});
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

                // Once we have our response, we pass it along depending on the appropriate method
                // that should handle it
                if (params[1].equals(MESSAGES_STRING)) {
                    getMessagesHandler(response);
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
            
        }
    }
}
