package com.example.android.placeholder_inventory.Activities;

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
import com.example.android.placeholder_inventory.Models.User;
import com.example.android.placeholder_inventory.R;
import com.example.android.placeholder_inventory.Models.Room;
import com.example.android.placeholder_inventory.Adapters.RoomListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;


public class RoomListActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private RoomListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private DatabaseReference mDatabase;
    private DatabaseReference mRooms;
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
        //mRooms = mDatabase.child("rooms").child()

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
        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (TextUtils.isEmpty(name)) {
            mAddNewField.setError(REQUIRED);
            return;
        }

        //mDatabase.child("users").child(UserId).setValue(user);

        mDatabase.child("users").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if(user == null) {
                    Toast.makeText(RoomListActivity.this,
                            "Error: could not fetch user.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // Actually adding the room here:
                    Toast.makeText(RoomListActivity.this, "Posting...", Toast.LENGTH_SHORT).show();
                    Room room = new Room(name, userID);

                    // Making the userID "represent" the room
                    Map<String, Object> roomProperties = room.makeMap();

                    // New room saved to rooms
                    String key = mDatabase.child("rooms").push().getKey();
                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/user-rooms/" + userID + "/" + key, roomProperties);
                    mDatabase.updateChildren(childUpdates);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}


/**
 * Code related to FireBase inspired after following the documents at
 * https://firebase.google.com and the accompanying examples.
 **/