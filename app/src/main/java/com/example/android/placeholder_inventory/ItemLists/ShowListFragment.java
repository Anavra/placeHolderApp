package com.example.android.placeholder_inventory.ItemLists;

import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.placeholder_inventory.Adapters.ItemListAdapter;
import com.example.android.placeholder_inventory.BaseFragment;
import com.example.android.placeholder_inventory.R;
import com.example.android.placeholder_inventory.Utils.Utils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import okhttp3.internal.Util;

/**
 * This fragment is contained by ItemListActivity and its action buttons implemented
 * by it.
 * This fragment is responsible for showing the items list with its adapter,
 * catching the input and sending it to the parent activity.
 */

public class ShowListFragment extends BaseFragment
        implements ItemListAdapter.OnAdapterInteractionListener{

    private OnFragmentInteractionListener mCallback;

    // RecyclerView to show the list of items
    private RecyclerView recyclerView;

    // Getting the room list from the database
    private DatabaseReference mRoomList;
    private Context mContext;

    public ShowListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof OnFragmentInteractionListener){
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
        final View roomView = inflater.inflate(R.layout.fragment_item_list, container,
                false);

        // [START create_database_reference]
        final String userID = getUserId();
        // Only the list of rooms of that user is acquired.
        mRoomList = FirebaseDatabase.getInstance()
                .getReference()
                .child("user-rooms")
                .child(userID);
        // [END create_database_reference]

        // Binding the recyclerView for the list
        recyclerView = roomView.findViewById(R.id.room_list_recycler_view);

        return roomView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Using a grid layout manager for the recyclerView
        RecyclerView.LayoutManager layoutManager;
        int numColumns = Utils.makeColumnsFit(getContext(), 180);
        layoutManager = new GridLayoutManager(getContext(), numColumns);
        recyclerView.setLayoutManager(layoutManager);

        // Adapter for the recyclerView
        ItemListAdapter mAdapter;
        mAdapter = new ItemListAdapter(getActivity(), mRoomList, this);
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
