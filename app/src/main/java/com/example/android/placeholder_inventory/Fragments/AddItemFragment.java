package com.example.android.placeholder_inventory.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.placeholder_inventory.Models.Room;
import com.example.android.placeholder_inventory.Models.User;
import com.example.android.placeholder_inventory.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * This fragment is responsible for creating a new item on the database
 *  based on the input given by the user,
 *  and sending clicks back to its containing activity.
 */

public class AddItemFragment extends Fragment {
    private OnFragmentInteractionListener callback;

    private DatabaseReference mDatabase;
    // Text field to add new item
    private EditText mNameField;
    private static final String REQUIRED = "Required";

    public AddItemFragment() {
        // Required empty public constructor
    }

    public void setOnFragmentInteractionListener(OnFragmentInteractionListener callback) {
        if (callback != null) {
            this.callback = callback;
        }
    }

    public interface OnFragmentInteractionListener {
        void launchShowListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        final View addItemView = inflater.inflate(R.layout.fragment_add_item, container,
                false);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Add new item field
        mNameField = addItemView.findViewById(R.id.name_field);

        // Buttons
        Button AddNewButton = addItemView.findViewById(R.id.add_button);
        Button CancelButton = addItemView.findViewById(R.id.cancel_button);

        AddNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                final String name = mNameField.getText().toString();
                if (formIsValid(name)) {
                    addNewRoom(name);
                    callback.launchShowListFragment();
                }
            }
        });

        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.launchShowListFragment();
            }
        });

        return addItemView;
    }
    private boolean formIsValid(String name){
        if (TextUtils.isEmpty(name)) {
            mNameField.setError(REQUIRED);
            return false;
        }
        else {
            return true;
        }
    }

    private void addNewRoom(String name) {
        final String roomName = name;
        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mDatabase.child("users").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if (user == null) {
                    Toast.makeText(getActivity(),
                            "Error: could not fetch user.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // Actually adding a new room to the database:
                    final String key = mDatabase.child("rooms").push().getKey();
                    Toast.makeText(getActivity(), "Posting...", Toast.LENGTH_SHORT).show();
                    Room room = new Room(roomName, userID, key);
                    // Making the userID "represent" the room
                    Map<String, Object> roomProperties = room.makeMap();

                    // New room saved to rooms

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/user-rooms/" + userID + "/" + key, roomProperties);
                    mDatabase.updateChildren(childUpdates);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),
                        "Error: " + databaseError.toException(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}
