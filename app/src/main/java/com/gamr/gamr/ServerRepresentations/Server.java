package com.gamr.gamr.ServerRepresentations;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    private static final String BASE_URL = "http://gamr.buildism.net/api.php?version=1&function=";

    private static final String GET_MESSAGE_FUNCTION = "get_messages";
    private static final String GET_MATCHES_FUNCTION = "match";

    private static final String USE_GAMES_PARAM = "&use_games=";
    private static final String USE_LOCATION_PARAM = "&use_location=";
    private static final String USER_ID_PARAM = "&user_id=";
    private static final String OTHER_USER_ID_PARAM = "&other_user_id=";

    public static void main(String[] args){
        List<MatchJson> matches = getMatches("d49f9b92-b927-11e4-847c-8bb5e9000002", "true", "true");
        System.out.println(matches.get(0));
    }

    public static List<MessageJson> getConversation(String fromUserId, String toUserId) {
        String response = null;
        try {
            response = get(GET_MESSAGE_FUNCTION, fromUserId, toUserId);
        } catch (IOException e) {
            //If request fails, return empty message list. Probably a better way to handle this but needs discussion
            return new ArrayList<MessageJson>();
        }
        List<MessageJson> conversation = new Gson().fromJson(response, new TypeToken<List<MessageJson>>(){}.getType());
        return conversation;
    }

    public static List<MatchJson> getMatches(String userId, String useLocation, String useGames) {
        String response = null;
        try {
            response = get(GET_MATCHES_FUNCTION, userId, useLocation, useGames);
            System.out.println(response);
        } catch (IOException e) {
            return new ArrayList<MatchJson>();
        }
        List<MatchJson> matches = new Gson().fromJson(response, new TypeToken<List<MatchJson>>(){}.getType());
        return matches;
    }

    private static String get(String... params) throws IOException {
        try {
            URL url = createUrl(params);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String output;
            while((output = responseReader.readLine()) != null){
                response.append(output);
            }

            return response.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static URL createUrl(String[] params) throws MalformedURLException {
        String function = params[0];
        switch (function) {
            case GET_MESSAGE_FUNCTION:
                return buildGetMessageUrl(params[1], params[2]);
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

    private static URL buildGetMatchesUrl(String userId, boolean useLocation, boolean useGames) throws MalformedURLException {
            String url = BASE_URL + GET_MATCHES_FUNCTION + USER_ID_PARAM + userId + USE_LOCATION_PARAM + useLocation + USE_GAMES_PARAM + useGames;
            return new URL(url.toString());
    }
}
