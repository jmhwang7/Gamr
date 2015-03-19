package com.gamr.gamr.AsyncTasks;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jennifer on 3/6/15.
 */
public class UpdateLocationTask extends AsyncTask<Double, Void, Void> {
    private final String LOG_TAG = UpdateLocationTask.class.getSimpleName();

    /**
     * Returns the number of affected rows in db as given by server response
     * @param responseJsonStr response returned by server
     * @return integer representing the number of affected rows
     * @throws JSONException
     */
    private int getDataFromJson(String responseJsonStr)
            throws JSONException {

        final String AFFECTED_ROWS = "affected_rows";

        JSONObject responseJson = new JSONObject(responseJsonStr);
        return responseJson.getInt(AFFECTED_ROWS);
    }

    protected Void doInBackground(Double... location) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String responseJsonStr = null;

        try {
            // Construct the URL for server endpoint
            final String UPDATE_LOCATION_BASE_URL = "http://gamr.buildism.net/api1/update_location?";
            final String USER_ID_PARAM = "user_id";
            final String LATITUDE_PARAM = "lat";
            final String LONGITUDE_PARAM = "lon";

            Uri uri = Uri.parse(UPDATE_LOCATION_BASE_URL).buildUpon()
                    // TODO: Change hardcoded userID later
                    .appendQueryParameter(USER_ID_PARAM, "d49f9b92-b927-11e4-847c-8bb5e9000002")
                    .appendQueryParameter(LATITUDE_PARAM, Double.toString(location[0]))
                    .appendQueryParameter(LONGITUDE_PARAM, Double.toString(location[1]))
                    .build();
            URL url = new URL(uri.toString());

            // Create the request to the server, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("PUT");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                responseJsonStr = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                responseJsonStr = null;
            }
            responseJsonStr = buffer.toString();

            try {
                // We can also move this to an overriden onPostExecute
                if (urlConnection.getResponseCode() == 200 && getDataFromJson(responseJsonStr) >= 0) {
                    Log.d(LOG_TAG, "Updated!");
                }
                else Log.d(LOG_TAG, "Update Failed.");
            }
            catch(Exception e) {
                Log.d(LOG_TAG, "Update Failed.");
                return null;
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return null;
    }

}
