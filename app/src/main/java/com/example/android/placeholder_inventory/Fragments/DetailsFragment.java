package com.example.android.placeholder_inventory.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.placeholder_inventory.Models.Room;
import com.example.android.placeholder_inventory.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * This fragment is contained by RoomListActivity. It is responsible
 * for retrieving and showing the item details.
 * Receives the item ID on creation and fetches the details from the database.
 */

public class DetailsFragment extends BaseFragment {

    private static final String ITEM_ID = "itemId";
    private DatabaseReference mItemReference;
    private ValueEventListener mItemListener;

    private TextView mItemNameView;
    private TextView mItemDescriptionView;
    private TextView mItemLocationView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View detailsView = inflater.inflate(R.layout.details_fragment,
                container, false);
        mItemNameView = detailsView.findViewById(R.id.details_item_name);
        mItemDescriptionView = detailsView.findViewById(R.id.details_item_description);
        mItemLocationView = detailsView.findViewById(R.id.details_item_location);

        // Receiving itemId from arguments
        Bundle args = getArguments();
        if (args ==  null) {
            throw new IllegalArgumentException("No arguments were passed.");
        }

        String itemId = args.getString(ITEM_ID);

        if (itemId == null) {
            // TODO: Change strings into translatable resources
            throw new IllegalArgumentException("Must pass ITEM_ID");
        }

        final String userID = getUserId();

        // TODO: Is this too long? Should room details be stored in another way?
        mItemReference = FirebaseDatabase.getInstance()
                .getReference()
                .child("user-rooms")
                .child(userID)
                .child(itemId);

        return detailsView;
    }

    @Override
    public void onStart(){
        super.onStart();

        // Shows database item details on the fragment view
        // [START item value listener]
        ValueEventListener itemListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Room room = dataSnapshot.getValue(Room.class);
                mItemNameView.setText(room.getName());
                mItemDescriptionView.setText(room.getItemDescription());
                mItemLocationView.setText("This is one of your rooms.");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Failed to load item details.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mItemReference.addValueEventListener(itemListener);
        //[END item value listener]

        // For later removal
        mItemListener = itemListener;
    }

    @Override
    public void onStop(){
        super.onStop();
        if (mItemListener != null){
            mItemReference.removeEventListener(mItemListener);
        }
    }


}
