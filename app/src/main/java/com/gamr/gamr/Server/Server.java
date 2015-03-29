package com.gamr.gamr.Server;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
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
import java.util.Arrays;
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
    private static final String GET_MATCHES_FUNCTION = "match";
    private static final String GET_PROFILE_FUNCTION = "get_profile";
    private static final String SEND_MESSAGE_FUNCTION = "send_message";
    private static final String UPDATE_LOCATION_FUNCTION = "update_location";
    private static final String UPDATE_PROFILE_FUNCTION = "update_profile";
    private static final String UPDATE_GAME_FIELD_FUNCTION = "update_game_field";
    private static final String RESPOND_TO_MATCH_FUNCTION = "match_response";

    /**
     * Get a list of messages between two users
     * @param userId first user
     * @param otherUserId second user
     * @return a list of messages between users, ordered by date
     */
    public static List<Message> getConversation(String userId, String otherUserId) {
        Map<String, String> params = new HashMap<>();
        params.put("user_id", userId);

        if (otherUserId != null) {
            params.put("other_user_id", otherUserId);
        }
        
        try {
            String response = get(GET_MESSAGE_FUNCTION, params);
            List<Message> conversation = new Gson().fromJson(response, new TypeToken<List<Message>>() {}.getType());
            return conversation;
        } catch (IOException e) {
            //If request fails, return empty message list. Probably a better way to handle this but needs discussion
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static boolean respondToMatch(String userId, String otherUserId, boolean accepted){
        Map<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("other_user_id", otherUserId);
        Map<String, String> body = new HashMap<>();
        body.put("matched", Boolean.toString(accepted));

        try {
            String response = post(RESPOND_TO_MATCH_FUNCTION, params, body);
            return new Gson().fromJson(response, Boolean.class);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void createUser(String userId, String username){
        Map<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        Map<String, String> body = new HashMap<>();
        body.put("username", username);

        try{
            post(UPDATE_PROFILE_FUNCTION, params, body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Profile getProfile(String userId){
        Map<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        try{
            String response = get(GET_PROFILE_FUNCTION, params);
            Profile profile = new Gson().fromJson(response, Profile.class);
            return profile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void updateUsername(String userId, String newUsername){
        Map<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        Map<String, String> body = new HashMap<>();
        body.put("username", newUsername);

        try{
            post(UPDATE_PROFILE_FUNCTION, params, body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateSummonerName(String userId, String newSummonerName){
        Map<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("game", "1");
        Map<String, String> body = new HashMap<>();
        body.put("in_game_name", newSummonerName);

        try{
            post(UPDATE_PROFILE_FUNCTION, params, body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateLeagueRoles(String userId, List<String> roles){
        Map<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("game", "1");
        Map<String, String> body = new HashMap<>();
        StringBuilder rolesList = new StringBuilder();
        for(String each : roles){
            rolesList.append(each).append(",");
        }
        body.put("field", "1");
        String list = rolesList.toString();
        list = list.substring(0, list.length()-1);
        body.put("value", list);

        try{
            post(UPDATE_GAME_FIELD_FUNCTION, params, body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateLeagueRank(String userId, String rank){
        Map<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("game", "1");
        Map<String, String> body = new HashMap<>();
        body.put("field", "2");
        body.put("value", rank);

        try{
            post(UPDATE_GAME_FIELD_FUNCTION, params, body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateLeagueGameModes(String userId, List<String> modes){
        Map<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("game", "1");
        Map<String, String> body = new HashMap<>();
        StringBuilder modeList = new StringBuilder();
        for(String each : modes){
            modeList.append(each).append(",");
        }
        body.put("field", "3");
        String list = modeList.toString();
        list = list.substring(0, list.length()-1);
        body.put("value", list);

        try{
            post(UPDATE_GAME_FIELD_FUNCTION, params, body);
        } catch (IOException e) {
            e.printStackTrace();
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
            return new ArrayList<>();
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
     * @param latitude latitude of user's location
     * @param longitude longitude of user's location
     * @return boolean indicating whether location update was successful
     */
    public static boolean updateLocation(String userId, double latitude, double longitude) {
        //Params to put
        Map<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        Map<String, String> body = new HashMap<>();
        body.put("lat", Double.toString(latitude));
        body.put("lon", Double.toString(longitude));

        try {
            String response = post(UPDATE_LOCATION_FUNCTION, params, body);
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
        URL url = createUrlJava(function, params);
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
        URL url = createUrlJava(function, params);
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

    private static String readResponseStream(InputStream responseStream) throws IOException {
        BufferedReader responseReader = new BufferedReader(new InputStreamReader(responseStream));
        StringBuilder response = new StringBuilder();
        for (String output; (output = responseReader.readLine()) != null; ) {
            response.append(output);
        }

        return response.toString();
    }

    private static URL createUrlAndroid(String function, Map<String, String> params) throws MalformedURLException {
        Uri.Builder builder = Uri.parse(BASE_URL).buildUpon().appendPath(function);
        for (Map.Entry<String, String> each : params.entrySet()) {
            if (!each.getKey().equals("body")) {
                builder.appendQueryParameter(each.getKey(), each.getValue());
            }
        }
        Uri uri = builder.build();
        return new URL(uri.toString());
    }

    private static URL createUrlJava(String function, Map<String, String> params) throws MalformedURLException {
        StringBuilder url = new StringBuilder(BASE_URL).append(function).append("?");
        for (Map.Entry each : params.entrySet()) {
            if (!each.getKey().equals("body")) {
                url.append(each.getKey()).append("=").append(each.getValue()).append("&");
            }
        }
        System.out.println(url.toString());
        return new URL(url.toString());
    }
}
