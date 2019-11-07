package com.example.android.placeholder_inventory.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.example.android.placeholder_inventory.Fragments.ShowListFragment;
import com.example.android.placeholder_inventory.Models.User;
import com.example.android.placeholder_inventory.R;
import com.example.android.placeholder_inventory.Models.Room;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

/**
 * This activity is responsible for handling its fragments and interacting with
 * the database to retrieve user item lists.
 */


public class RoomListActivity extends AppCompatActivity
    implements ShowListFragment.OnFragmentInteractionListener {

    private DatabaseReference mDatabase;

    @Override
    public void onAttachFragment(Fragment fragment){
        if (fragment instanceof ShowListFragment) {
            ShowListFragment showListFragment = (ShowListFragment) fragment;
            showListFragment.setOnFragmentInteractionListener(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        if (findViewById(R.id.fragment_container) != null){
            if (savedInstanceState != null) {
                return;
            }

            ShowListFragment firstFragment = new ShowListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }

    @Override
    public void onStart(){
        super.onStart();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void addNewRoom(String name){
        final String roomName = name;
        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

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
                    Room room = new Room(roomName, userID);

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
                Toast.makeText(RoomListActivity.this,
                        "Error: "+databaseError.toException(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}


/**
 * Code related to FireBase inspired after following the documents at
 * https://firebase.google.com and the accompanying examples.
 **/