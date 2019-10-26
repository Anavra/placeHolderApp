package com.example.android.placeholder_inventory;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.TextView;

public class roomListActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private RoomListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private static final String LOG_TAG =
            roomListActivity.class.getSimpleName();
    public static final String EXTRA_MESSAGE =
            "com.example.android.android-app.extra.MESSAGE";

    private EditText mMessageEditText;

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


        /*Bundle b = getIntent().getExtras();
        String name = b.getString("name");*/

        /*Text to submit*/
        mMessageEditText = findViewById(R.id.editText_main);

        /*
        mContainerListView = (TextView) findViewById(R.id.containers_list);
        String[] containerNames = Containers.getContainers();
        for (String container : containerNames) {
            mContainerListView.append(container + "\n\n\n");
        }
        */

    }

    public void launchSettingsActivity(View view) {
        Log.d(LOG_TAG, "button clicked!");
        Intent intent = new Intent(this, SettingsActivity.class);
        String message = mMessageEditText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}


