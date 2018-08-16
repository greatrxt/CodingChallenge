package com.shaadi.util;

import android.text.TextUtils;
import android.util.Log;
import com.shaadi.app.AppController;
import com.shaadi.app.Constants;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
    private static String APP_TAG = AppController.TAG, ACTIVITY_TAG = HttpUtils.class.getSimpleName();
    /**
     * Fetch string from URL using GET
     * @param url
     * @return
     */
    public static String doGetToFetchString(String url) {
        String result = "";
        HttpURLConnection urlConnection = null;
        // make HTTP request
        try {
            Log.d(APP_TAG, ACTIVITY_TAG + ": doGetToFetchString - URL :" + url);

            URL getUrl = new URL(url);
            urlConnection = (HttpURLConnection) getUrl.openConnection();


            urlConnection.setDoOutput(false);

            InputStream is;// = new BufferedInputStream(urlConnection.getInputStream());
            int status = urlConnection.getResponseCode();
            if(status >= 400)
                is = urlConnection.getErrorStream();
            else
                is = urlConnection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();

            int statusCode = urlConnection.getResponseCode();

            if(statusCode== 200 || statusCode==201){
                Log.d(APP_TAG, "doGetToFetchString response - 200 || 201 - " + result);
                return result;
            } else {
                String message = urlConnection.getResponseMessage();
                if(!TextUtils.isEmpty(SystemUtils.attemptFetchingErrorMessage(result))){
                    message+= " - "+SystemUtils.attemptFetchingErrorMessage(result);
                }
                Log.d(APP_TAG, "doGetToFetchString response - !(200 || 201) - error - " + result);
                return message;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        } finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
        }
    }

    /**
     * Send JSON via POST
     * @param url
     * @param json
     * @return
     */
    public static String doPostToSendJson(String url, JSONObject json){
        String result = "";
        HttpURLConnection urlConnection = null;
        // make HTTP request
        try {
            Log.d(APP_TAG, ACTIVITY_TAG + ": doPostToSendJson - URL :" + url + ", JSON - " + json);
            URL getUrl = new URL(url);
            urlConnection = (HttpURLConnection) getUrl.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-type", "application/json");
            OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
            writer.write(String.valueOf(json));
            writer.flush();

            InputStream is;// = new BufferedInputStream(urlConnection.getInputStream());
            int status = urlConnection.getResponseCode();
            if(status >= 400)
                is = urlConnection.getErrorStream();
            else
                is = urlConnection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();

            int statusCode = urlConnection.getResponseCode();

            if(statusCode==200
                    || statusCode==201
                        || statusCode==202){
                Log.d(APP_TAG, "doPostToSendJson response - 201 || 202 - "+result);
                if(TextUtils.isEmpty(result)){
                    return Constants.SUCCESS;
                }
                return result;
            } else {
                String message = urlConnection.getResponseMessage();
                if(!TextUtils.isEmpty(SystemUtils.attemptFetchingErrorMessage(result))){
                    message+= " - "+SystemUtils.attemptFetchingErrorMessage(result);
                }
                Log.d(APP_TAG, "doPostToSendJson response - !(200 || 201) - error - " + result);
                return message;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        } finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
        }
    }
}
