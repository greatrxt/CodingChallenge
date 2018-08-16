package com.shaadi.network;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shaadi.adapter.RecyclerViewAdapter;
import com.shaadi.app.Constants;
import com.shaadi.app.R;
import com.shaadi.custom.SweetAlertDialogBox;
import com.shaadi.custom.SwipeableRecyclerViewTouchListener;
import com.shaadi.model.User;
import com.shaadi.util.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoadResults extends AsyncTask<Void, Void, String> {
    private Activity activity;
    private SweetAlertDialog pDialog;
    private String RESULT_URL = "https://randomuser.me/api/?results=10";
    private List<User> userList;

    private String TAG_RESULTS = "results";

    public LoadResults(Activity activity){
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        pDialog.setTitleText(activity.getResources().getString(R.string.progress_dialog_loading));
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(Void... voids) {
        JSONObject result = JSONParser.doGetToFetchJSON(RESULT_URL);

        try {
            if(result.has(JSONParser.JSON_PARSER_ERROR)){
                return result.getString(JSONParser.JSON_PARSER_ERROR);
            }

            JSONArray resultJSONArray = result.getJSONArray(TAG_RESULTS);
            userList = new ArrayList<User>();
            for(int i = 0; i < resultJSONArray.length(); i++){
                userList.add(new User((JSONObject) resultJSONArray.get(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return e.getMessage();
        }

        return Constants.SUCCESS;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        pDialog.dismissWithAnimation();
        if(result.equals(Constants.SUCCESS)) {
            setUpRecyclerView();
        } else {
            SweetAlertDialogBox.showOkDialogBox(result, activity);
        }
    }



    /*  set up recycler view */
    private void setUpRecyclerView() {

        //find recycler view id and set layout manager
        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);

        //set recycler view adapter
        final RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(activity, userList);
        recyclerView.setAdapter(recyclerViewAdapter);

        /*  set swipe touch listener */
        SwipeableRecyclerViewTouchListener swipeTouchListener = new SwipeableRecyclerViewTouchListener(recyclerView, new SwipeableRecyclerViewTouchListener.SwipeListener() {
            @Override
            public boolean canSwipeLeft(int position) {
                //enable/disable left swipe on checkbox base else use true/false
                return true;
            }

            @Override
            public boolean canSwipeRight(int position) {
                //enable/disable right swipe on checkbox base else use true/false
                return true;
            }

            @Override
            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                //on cardview swipe left dismiss update adapter
                onCardViewDismiss(reverseSortedPositions, userList, recyclerViewAdapter);
            }

            @Override
            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                //on cardview swipe right dismiss update adapter
                onCardViewDismiss(reverseSortedPositions, userList, recyclerViewAdapter);
            }
        });

        recyclerViewAdapter.setOnSwipeTouchListener(swipeTouchListener);
        //add item touch listener to recycler view
        recyclerView.addOnItemTouchListener(swipeTouchListener);

    }

    private void onCardViewDismiss(int[] reverseSortedPositions, List<User> userList, RecyclerViewAdapter recyclerViewAdapter) {
        for (int position : reverseSortedPositions) {
            userList.remove(position);
            recyclerViewAdapter.notifyItemRemoved(position);
        }
        recyclerViewAdapter.notifyDataSetChanged();
    }
}
