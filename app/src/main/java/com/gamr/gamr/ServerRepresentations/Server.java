package com.gamr.gamr.ServerRepresentations;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 3/21/2015.
 */
public class Server {
    // Base URL
    public static final String BASE_URL = "http://gamr.buildism.net/api1/";
    // Query Params
    public static final String USER_ID_PARAM = "&user_id=";
    public static final String OTHER_USER_ID_PARAM = "&other_user_id=";
    public static final String BEFORE_TIME_PARAM = "&before=";
    public static final String USE_GAMES_PARAM = "&use_games=";
    public static final String USE_LOCATION_PARAM = "&use_location=";
    private static final String LOG_TAG = Server.class.getSimpleName();
    // Endpoints
    private static final String GET_MESSAGE_FUNCTION = "get_messages?";
    private static final String SEND_MESSAGE_FUNCTION = "send_message?";
    private static final String GET_MATCHES_FUNCTION = "match?";

    public static void main(String[] args) {
        List<Message> convo = getConversation("d49f9b92-b927-11e4-847c-8bb5e9000003", "d49f9b92-b927-11e4-847c-8bb5e9000002");
        System.out.println(convo.get(0));
        String messageId = sendMessage("d49f9b92-b927-11e4-847c-8bb5e9000003", "d49f9b92-b927-11e4-847c-8bb5e9000002", "Yay, message sending works!");
        System.out.println(messageId);
    }

    public static List<Message> getConversation(String fromUserId, String toUserId) {
        String response = null;
        try {
            response = get(GET_MESSAGE_FUNCTION, fromUserId, toUserId);
        } catch (IOException e) {
            //If request fails, return empty message list. Probably a better way to handle this but needs discussion
            e.printStackTrace();
            return new ArrayList<Message>();
        }
        List<Message> conversation = new Gson().fromJson(response, new TypeToken<List<Message>>() {
        }.getType());
        return conversation;
    }

    public static List<Match> getMatches(String userId, String useLocation, String useGames) {
        String response = null;
        try {
            response = get(GET_MATCHES_FUNCTION, userId, useLocation, useGames);
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<Match>();
        }
        List<Match> matches = new Gson().fromJson(response, new TypeToken<List<Match>>() {
        }.getType());
        return matches;
    }

    public static String sendMessage(String fromUserId, String toUserId, String text) {
        try {
            String messageId = post(SEND_MESSAGE_FUNCTION, fromUserId, toUserId, text);
            return messageId;
        } catch (IOException e) {
            e.printStackTrace();
            return "Message failed to send";
        }
    }

    private static String get(String... params) throws IOException {
        URL url = createUrl(params);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        return readResponseStream(connection.getInputStream());
    }

    private static String post(String... params) throws IOException {
        URL url = createUrl(params);
        System.out.println("posting to url: " + url.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        //Identify as a POST
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        String text = "text=" + params[3];
        //Set content length header
        connection.setFixedLengthStreamingMode(text.getBytes().length);

        //Open connection and write text
        PrintWriter post = new PrintWriter(connection.getOutputStream());
        post.write(text);
        post.close();

        return readResponseStream(connection.getInputStream());
    }

    private static String readResponseStream(InputStream responseStream) throws IOException {
        BufferedReader responseReader = new BufferedReader(new InputStreamReader(responseStream));
        StringBuilder response = new StringBuilder();
        for (String output; (output = responseReader.readLine()) != null;) {
            response.append(output);
        }

        return response.toString();
    }

    private static URL createUrl(String[] params) throws MalformedURLException {
        String function = params[0];
        switch (function) {
            case GET_MESSAGE_FUNCTION:
                return buildGetMessageUrl(params[1], params[2]);
            case SEND_MESSAGE_FUNCTION:
                return buildSendMessageUrl(params[1], params[2]);
            case GET_MATCHES_FUNCTION:
                boolean useLocation = Boolean.parseBoolean(params[2]);
                boolean useGames = Boolean.parseBoolean(params[3]);
                return buildGetMatchesUrl(params[1], useLocation, useGames);
            default:
                throw new UnsupportedOperationException("Function " + function + " not recognized or implemented yet");
        }
    }


    private static String encodeParams(String paramString) {
        String params = null;
        try {
            params = URLEncoder.encode(paramString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return params;
    }

    private static URL buildGetMessageUrl(String userId, String otherUserId) throws MalformedURLException {
        String url = BASE_URL + GET_MESSAGE_FUNCTION + USER_ID_PARAM + userId + OTHER_USER_ID_PARAM + otherUserId;
        return new URL(url);
    }

    private static URL buildSendMessageUrl(String userId, String otherUserId) throws MalformedURLException {
        String url = BASE_URL + SEND_MESSAGE_FUNCTION + USER_ID_PARAM + userId + OTHER_USER_ID_PARAM + otherUserId;
        return new URL(url);
    }

    private static URL buildGetMatchesUrl(String userId, boolean useLocation, boolean useGames) throws MalformedURLException {
        String url = BASE_URL + GET_MATCHES_FUNCTION + USER_ID_PARAM + userId + USE_LOCATION_PARAM + useLocation + USE_GAMES_PARAM + useGames;
        return new URL(url);
    }
}
