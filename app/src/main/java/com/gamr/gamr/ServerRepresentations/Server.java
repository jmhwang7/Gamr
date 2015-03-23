package com.gamr.gamr.ServerRepresentations;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Thomas on 3/21/2015.
 */
public class Server {
    // Base URL
    public static final String BASE_URL = "http://gamr.buildism.net/api1/";
    private static final String LOG_TAG = Server.class.getSimpleName();

    // Endpoints
    private static final String GET_MESSAGE_FUNCTION = "get_messages";
    private static final String SEND_MESSAGE_FUNCTION = "send_message";
    private static final String GET_MATCHES_FUNCTION = "match";
    private static final String UPDATE_LOCATION_FUNCTION = "update_location";

//    public static void main(String[] args) {
//        int messageId = sendMessage("d49f9b92-b927-11e4-847c-8bb5e9000003", "d49f9b92-b927-11e4-847c-8bb5e9000002", "Yay");
//        System.out.println(messageId);
//
//        List<Message> convo = getConversation("d49f9b92-b927-11e4-847c-8bb5e9000003", "d49f9b92-b927-11e4-847c-8bb5e9000002");
//        System.out.println(convo.get(0));
//    }

    /**
     * Get a list of messages between two users
     * @param userId first user
     * @param otherUserId second user
     * @return
     */
    public static List<Message> getConversation(String userId, String otherUserId) {
        Map<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("other_user_id", otherUserId);
        try {
            String response = get(GET_MESSAGE_FUNCTION, params);
            List<Message> conversation = new Gson().fromJson(response, new TypeToken<List<Message>>() {}.getType());
            return conversation;
        } catch (IOException e) {
            //If request fails, return empty message list. Probably a better way to handle this but needs discussion
            e.printStackTrace();
            return new ArrayList<Message>();
        }
    }

    /**
     * Gets a list of Matches for the specified user
     * @param userId user to get matches for
     * @param useLocation whether to use physical location to match or not
     * @param useGames whether to match based on game or not
     * @return List of matches for the user
     */
    public static List<Match> getMatches(String userId, boolean useLocation, boolean useGames) {
        Map<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("use_location", Boolean.toString(useLocation));
        params.put("use_games", Boolean.toString(useGames));
        try {
            String response = get(GET_MATCHES_FUNCTION, params);
            List<Match> matches = new Gson().fromJson(response, new TypeToken<List<Match>>() {}.getType());
            return matches;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<Match>();
        }
    }

    /**
     * Sends a message between users
     * @param fromUserId user the message is being sent from
     * @param toUserId user the message is being sent to
     * @param text body of the message
     * @return database id of the message
     */
    public static int sendMessage(String fromUserId, String toUserId, String text) {
        //Params to put
        Map<String, String> params = new HashMap<>();
        params.put("user_id", fromUserId);
        params.put("other_user_id", toUserId);

        //Params to put in the body of the message,
        Map<String, String> body = new HashMap<>();
        params.put("text", text);

        try {
            String response = post(SEND_MESSAGE_FUNCTION, params, body);
            MessageId messageId = new Gson().fromJson(response, MessageId.class);
            return messageId.getMessageId();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Updates location of the user
     * @param userId app user
     * @param  double latitude of user's location
     * @param double longitude of user's location
     * @return boolean indicating whether location update was successful
     */
    public static boolean updateLocation(String userId, double latitude, double longitude) {
        //Params to put
        Map<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("lat", Double.toString(latitude));
        params.put("lon", Double.toString(longitude));

        try {
            String response = put(UPDATE_LOCATION_FUNCTION, params);
            UpdateResponse deserialized = new Gson().fromJson(response, UpdateResponse.class);
            return deserialized.getAffectedRows() >= 0;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Performs HTTP get for the function provided and the given parameters
     * @param function API endpoint for the URL
     * @param params Map of parameters to append to the URL
     * @return the server response
     * @throws IOException
     */
    private static String get(String function, Map<String, String> params) throws IOException {
        URL url = createUrl(function, params);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        return readResponseStream(connection.getInputStream());
    }

    /**
     * Performs HTTP post for the function provided, it's given parameters, and it's given body
     * @param function API endpoint for the URL
     * @param params Map of parameters to append to the URL
     * @param body Map of parameters to put in the body x-www-form-urlencoded style: key=value&key=value
     * @return the server response
     * @throws IOException
     */
    private static String post(String function, Map<String, String> params, Map<String, String> body) throws IOException {
        URL url = createUrl(function, params);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        //Identify as a POST
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        //Construct the body based on the body map provided
        StringBuilder text = new StringBuilder();
        for(Map.Entry each : body.entrySet()){
            text.append(each.getKey()).append("=").append(each.getValue()).append("&");
        }
        //Set content length header
        connection.setFixedLengthStreamingMode(text.toString().getBytes().length);

        //Open connection and write text
        PrintWriter post = new PrintWriter(connection.getOutputStream());
        post.write(text.toString());
        post.close();

        return readResponseStream(connection.getInputStream());
    }

    /**
     * Performs HTTP put for the function provided and the given parameters
     * @param function API endpoint for the URL
     * @param params Map of parameters to append to the URL
     * @return the server response
     * @throws IOException
     */
    private static String put(String function, Map<String, String> params) throws IOException {
        URL url = createUrl(function, params);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");

        connection.connect();
        return readResponseStream(connection.getInputStream());
    }

    private static String readResponseStream(InputStream responseStream) throws IOException {
        BufferedReader responseReader = new BufferedReader(new InputStreamReader(responseStream));
        StringBuilder response = new StringBuilder();
        for (String output; (output = responseReader.readLine()) != null; ) {
            response.append(output);
        }

        return response.toString();
    }

    private static URL createUrl(String function, Map<String, String> params) throws MalformedURLException {

        Uri.Builder builder = Uri.parse(BASE_URL).buildUpon().appendPath(function);
        for (Map.Entry<String, String> each : params.entrySet()) {
            if (!each.getKey().equals("body")) {
                builder.appendQueryParameter(each.getKey(), each.getValue());
            }
        }
        Uri uri = builder.build();
        return new URL(uri.toString());

    }
}
