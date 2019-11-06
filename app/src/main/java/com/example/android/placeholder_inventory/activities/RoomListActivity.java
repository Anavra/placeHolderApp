package com.example.android.placeholder_inventory.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.placeholder_inventory.Models.Containers;
import com.example.android.placeholder_inventory.R;
import com.example.android.placeholder_inventory.Models.Room;
import com.example.android.placeholder_inventory.Adapters.RoomListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class RoomListActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private RoomListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private DatabaseReference mDatabase;
    private EditText mAddNewField;
    private Button mAddNewButton;
    private static final String REQUIRED = "@string/required";

    private static final String LOG_TAG =
            RoomListActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);
        recyclerView = (RecyclerView) findViewById(R.id.room_list_recycler_view);

        // Using a grid layout manager for the recycler view
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        // Adapter for the room list
        mAdapter = new RoomListAdapter(Containers.getContainers());
        recyclerView.setAdapter(mAdapter);

        //Add new item things
        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        mAddNewField = findViewById(R.id.addNewField);
        mAddNewButton = findViewById(R.id.addNewButton);

        mAddNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewRoom();
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();

    }

    private void addNewRoom(){
        final String name = mAddNewField.getText().toString();

        // A name is required to submit
        if (TextUtils.isEmpty(name)) {
            mAddNewField.setError(REQUIRED);
            return;
        }

        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

        Room room = new Room(name);
        mDatabase.child("rooms").child(room.name).setValue(room);
    }

    public void readWrite(){
        // [START write_message]
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mRef = database.getReference("rooms");

        // [END write_message]

        // [START read_message]
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d(LOG_TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(LOG_TAG, "Failed to read value.", databaseError.toException());
            }
        });
        // [END read_message]
    }
}


