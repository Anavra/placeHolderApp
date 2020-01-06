package com.example.android.placeholder_inventory.itemLists;


import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.android.placeholder_inventory.BaseFragment;
import com.example.android.placeholder_inventory.models.User;
import com.example.android.placeholder_inventory.models.UserItem;
import com.example.android.placeholder_inventory.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * This fragment is responsible for creating a new item on the database
 * based on the input given by the user,
 * and sending clicks back to its containing activity.
 */

public class AddItemFragment extends BaseFragment {
    private static final String REQUIRED = "Required";
    private OnFragmentInteractionListener mCallback;
    private DatabaseReference mDatabase;
    private FirebaseAnalytics mFirebaseAnalytics;
    // Text field to add new item
    private EditText mNameField;
    private EditText mDescriptionField;

    public AddItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mCallback = (OnFragmentInteractionListener) context;
        } else {
            throw new ClassCastException(context.toString() + "not implemented in context");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        final View addItemView = inflater.inflate(R.layout.fragment_add_item, container,
                false);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());

        //Add new item fields
        mNameField = addItemView.findViewById(R.id.name_field);
        mDescriptionField = addItemView.findViewById(R.id.item_description_field);

        // Buttons
        Button AddNewButton = addItemView.findViewById(R.id.add_button);
        Button CancelButton = addItemView.findViewById(R.id.cancel_button);

        AddNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = mNameField.getText().toString();
                if (formIsValid(name)) {
                    addNewRoom(name);
                    mCallback.launchShowListFragment();
                }
            }
        });

        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.launchShowListFragment();
            }
        });

        return addItemView;
    }


    private boolean formIsValid(String name) {
        if (TextUtils.isEmpty(name)) {
            mNameField.setError(REQUIRED);
            return false;
        } else {
            return true;
        }
    }

    private void addNewRoom(String name) {
        final String roomName = name;
        final String itemDescription = mDescriptionField.getText().toString();
        final String userID = getUserId();

        mDatabase.child("users").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if (user == null) {
                    Toast.makeText(getActivity(),
                            "Error: could not fetch user.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // Actually adding a new userItem to the database:
                    final String itemId = mDatabase.child("rooms").push().getKey();
                    Toast.makeText(getActivity(), "Posting...", Toast.LENGTH_SHORT).show();
                    UserItem userItem = new UserItem(roomName, itemId, itemDescription);
                    // Making the userID "represent" the userItem
                    Map<String, Object> roomProperties = userItem.makeMap();

                    // New userItem saved to rooms
                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/user-rooms/" + userID + "/" + itemId, roomProperties);
                    mDatabase.updateChildren(childUpdates);

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, roomName);
                    mFirebaseAnalytics.logEvent("Add_Item", bundle);
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

    public interface OnFragmentInteractionListener {
        void launchShowListFragment();
    }
}
