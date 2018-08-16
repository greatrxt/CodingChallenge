package com.shaadi.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shaadi.adapter.RecyclerViewAdapter;
import com.shaadi.app.R;
import com.shaadi.custom.SwipeableRecyclerViewTouchListener;
import com.shaadi.model.User;
import com.shaadi.network.LoadResults;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setUpRecyclerView();
        new LoadResults(this).execute();
    }

}
