package com.example.android.placeholder_inventory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;


public class RoomListActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private RoomListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private static final String LOG_TAG =
            RoomListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);
        recyclerView = (RecyclerView) findViewById(R.id.room_list_recycler_view);

        // Using a grid layout manager
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RoomListAdapter(Containers.getContainers());
        recyclerView.setAdapter(mAdapter);
    }
}


