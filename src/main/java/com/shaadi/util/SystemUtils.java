package com.shaadi.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.shaadi.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SystemUtils {


    private static String APP_TAG = AppController.TAG, ACTIVITY_TAG = SystemUtils.class.getSimpleName();

    public static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }


    /**
     * Checks if an element exists in a JSON Array
     * @param jsonArray
     * @param elementToFind
     * @return
     */
    public static boolean arrayHasElement(JSONArray jsonArray, String elementToFind){
        return jsonArray.toString().contains("\"" + elementToFind + "\"");
    }

    /**
     * Check if device is connected to the internet
     * @param activity
     * @return
     */
    public static boolean deviceIsOnline(Activity activity){
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            Toast.makeText(activity, "Please ensure device is online", Toast.LENGTH_LONG).show();
            return false;
        }
        boolean isConnected = ni.isConnected();
        if(!isConnected){
            Toast.makeText(activity, "Please ensure device is online", Toast.LENGTH_SHORT).show();
        }
        return isConnected;
    }


    public static boolean deviceIsOnlineSilent(Activity activity){
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        }
        boolean isConnected = ni.isConnected();
        return isConnected;
    }


    /**
     * Return date string. Ex - Tue Oct 25 00:00:00 UTC 2016
     * @return
     */
    public static String getCurrentDate(){
        //SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }



    /**
     * Save Bitmap
     * @param bitmap
     * @param filename
     */
    public static void saveBitmap(Bitmap bitmap, String filename, String filePath){
        FileOutputStream out = null;
        try {
            File file = new File(filePath.substring(0, filePath.lastIndexOf(File.separator)));
            if(!file.exists()){
                file.mkdirs();
            }
            out = new FileOutputStream(file.getAbsolutePath() + File.separator + filename);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param is
     * @return
     * @throws Exception
     */
    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }


    private static byte[] toByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }

    /**
     * Displays short toast message to user
     * @param message
     * @param activity
     */
    public static void showShortToast(String message, Activity activity){
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        Log.d(APP_TAG, ACTIVITY_TAG + ": Showing short toast - " + message);
    }

    /**
     * Display toast to user for a bit longer duration
     * @param message
     * @param activity
     */
    public static void showLongToast(String message, Activity activity){

        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();

        Log.d(APP_TAG, ACTIVITY_TAG + ": Showing long toast - " + message);
    }

    public static void showLongToastInCenter(String message, Activity activity){
        Toast toast = Toast.makeText(activity, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        Log.d(APP_TAG, ACTIVITY_TAG + ": Showing long center toast - " + message);
    }
    /**
     * Attempt fetching error message from a JSON String
     * @param message
     * @return
     */
    public static String attemptFetchingErrorMessage(String message) {
        try{
            JSONObject messageJson = new JSONObject(message);

            if(messageJson.has("Message")){
                return messageJson.getString("Message");
            }
        } catch (JSONException e){
            e.printStackTrace();
            return String.valueOf(Html.fromHtml(message));
        }
        return message;
    }

}
