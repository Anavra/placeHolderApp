package com.example.android.placeholder_inventory.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.placeholder_inventory.Adapters.RoomListAdapter;
import com.example.android.placeholder_inventory.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This fragment is contained by RoomListActivity and its action buttons implemented
 * by it.
 * This fragment is responsible for showing the list, catching the input
 * and sending it to the parent activity.
 */

public class ShowListFragment extends Fragment implements RoomListAdapter.OnAdapterInteractionListener{
    private OnFragmentInteractionListener mCallback;

    // RecyclerView adapter and related
    private RoomListAdapter mAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    // Getting the room list from the database
    private DatabaseReference mRoomList;

    public ShowListFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        final View roomView = inflater.inflate(R.layout.fragment_room_list, container,
                false);

        // [START create_database_reference]
        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        // Only the list of rooms of that user is acquired.
        mRoomList = FirebaseDatabase.getInstance()
                .getReference()
                .child("user-rooms")
                .child(userID);
        // [END create_database_reference]

        // Binding the recyclerView for the list
        recyclerView = (RecyclerView) roomView.findViewById(R.id.room_list_recycler_view);

        return roomView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Using a grid layout manager for the recycler view
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new RoomListAdapter(getActivity(), mRoomList, this);
        mAdapter.notifyDataSetChanged();

        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop(){
        super.onStop();
        //mAdapter.clearListener();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    public void setOnFragmentInteractionListener(OnFragmentInteractionListener callback) {
        if (callback != null) {
            this.mCallback = callback;
        }
    }

    public interface OnFragmentInteractionListener {
        void onItemClick(String itemId);
    }

    public void onItemClick(String itemId) {
        mCallback.onItemClick(itemId);
    }

}
