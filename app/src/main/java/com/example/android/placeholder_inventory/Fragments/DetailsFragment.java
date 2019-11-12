package com.example.android.placeholder_inventory.Fragments;

import androidx.lifecycle.ViewModelProviders;

import android.media.browse.MediaBrowser;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.placeholder_inventory.Models.Room;
import com.example.android.placeholder_inventory.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailsFragment extends Fragment {

    public static final String ITEM_ID = "itemId";
    private DatabaseReference mItemReference;
    private String mItemId;

    private TextView mItemNameView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View detailsView = inflater.inflate(R.layout.details_fragment, container, false);
        mItemNameView = detailsView.findViewById(R.id.details_item_name);
        Bundle args = getArguments();

        mItemId = args.getString(ITEM_ID);
        if (mItemId == null) {
            // TODO: Change strings into translatable resources
            throw new IllegalArgumentException("Must pass ITEM_ID");
        }

        // TODO: Make a base class with getUserId because it seems like we need it everywhere. YES
        // Maybe send userID to this detailview too idk. NO! OR send the entire dbreference :/ NO!
        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid(); //UGH

        // TODO: Is this too long? Should room details be stored in another way?
        mItemReference = FirebaseDatabase.getInstance()
                .getReference()
                .child("user-rooms")
                .child(userID)
                .child(mItemId);



        return detailsView;
    }

    @Override
    public void onStart(){
        super.onStart();

        //[START item value listener]
        ValueEventListener itemListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Room room = dataSnapshot.getValue(Room.class);
                mItemNameView.setText(room.name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Failed to load item details.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mItemReference.addValueEventListener(itemListener);
        //[END item value listener]

        //TODO: Handle removal of listeners
    }


}
