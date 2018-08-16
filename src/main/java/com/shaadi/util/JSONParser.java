package com.shaadi.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.Html;
import android.text.TextUtils;
import android.util.Log;

import com.shaadi.app.AppController;
import com.shaadi.app.Constants;

public class JSONParser {

    private static String APP_TAG = AppController.TAG, ACTIVITY_TAG = JSONParser.class.getSimpleName();
    public static String JSON_PARSER_ERROR = "jsonParserError";

    /**
     * Make a GET request to fetch JSON
     * @param url
     * @return
     */
    public static JSONObject doGetToFetchJSON(String url) {
        JSONObject jObj = null;
        String json = HttpUtils.doGetToFetchString(url);
        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);

        } catch (Exception e) {
            Log.d(APP_TAG, ACTIVITY_TAG + ": Error parsing data - " + json +"\n Error message - "+ e.toString());
            try {
                //send error string
                if(!TextUtils.isEmpty(json)){
                    jObj = new JSONObject();
                    jObj.put(Constants.RESULT, Constants.ERROR);
                    jObj.put(JSON_PARSER_ERROR, Html.fromHtml(json).toString());
                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }

        return jObj;
    }


    /**
     * Make a GET request to fetch JSON
     * @param url
     * @return
     */
    public static JSONArray doGetToFetchJSONArray(String url) {
        JSONArray jsonArray = new JSONArray();
        String json = HttpUtils.doGetToFetchString(url);
        try {
            jsonArray = new JSONArray(json);
        } catch (Exception e) {
            Log.d(APP_TAG, ACTIVITY_TAG + ": Error parsing data - " + json +"\n Error message - "+ e.toString());
        }

        return jsonArray;
    }

    /**
     * Make a GET request to fetch JSON
     * @param url
     * @return
     */
    public static JSONObject doPostToFetchJSON(String url, JSONObject payload) {
        JSONObject jObj = null;
        String json = HttpUtils.doPostToSendJson(url, payload);
        // try parse the string to a JSON object
        try {
            if(json.trim().equals(Constants.SUCCESS)){
                jObj = new JSONObject();
                jObj.put(Constants.RESULT, Constants.SUCCESS);
            } else {
                jObj = new JSONObject(json);
            }
        } catch (Exception e) {
            Log.d(APP_TAG, ACTIVITY_TAG + ": Error parsing data - " + json +"\n Error message - "+ e.toString());
            try {
                //send error string
                if(!TextUtils.isEmpty(json)){
                    jObj = new JSONObject();
                    jObj.put(Constants.RESULT, Constants.ERROR);
                    jObj.put(JSON_PARSER_ERROR, Html.fromHtml(json).toString());
                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }

        return jObj;
    }
}