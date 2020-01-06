package com.example.android.placeholder_inventory.itemLists;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.placeholder_inventory.adapters.ItemListAdapter;
import com.example.android.placeholder_inventory.BaseFragment;
import com.example.android.placeholder_inventory.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This fragment is contained by ItemListActivity and its action buttons implemented
 * by it.
 * This fragment is responsible for showing the items list with its adapter,
 * catching the input and sending it to the parent activity.
 */

public class ShowListFragment extends BaseFragment
        implements ItemListAdapter.OnAdapterInteractionListener {

    private static final String TAG = "TAG_SHOW_LIST_FRAGMENT";
    private OnFragmentInteractionListener mCallback;

    // RecyclerView to show the list of items
    private RecyclerView recyclerView;

    // Getting the item list from the database
    private DatabaseReference mItemList;

    private ItemListAdapter mAdapter;

    public ShowListFragment() {
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
        final View roomView = inflater.inflate(R.layout.fragment_item_list, container,
                false);

        // [START create_database_reference]
        final String userID = getUserId();
        // Only the list of rooms of that user is acquired.
        mItemList = FirebaseDatabase.getInstance()
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
        int numColumns = calculateColumns();
        layoutManager = new GridLayoutManager(getContext(), numColumns);
        recyclerView.setLayoutManager(layoutManager);

        // Adapter for the recyclerView
        mAdapter = new ItemListAdapter(getActivity(), mItemList, this);
        mAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.clearListener();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }


    public void onItemClick(String itemId) {
        mCallback.onItemClick(itemId);
    }

    private int calculateColumns() {
        int numColumns;
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        float screenWidth = displayMetrics.widthPixels / displayMetrics.density;
        float screenHeight = displayMetrics.heightPixels / displayMetrics.density;
        Log.i(TAG, "Width: " + screenWidth);
        if (screenWidth > 800) {
            // Tablets will show this as a mostly square display
            numColumns = (int) (Math.min(screenWidth, screenHeight) / 180 + 0.5);
        } else {
            // phones will have a lot more columns on landscape mode
            numColumns = (int) (screenWidth / 180 + 0.5);
        }
        return numColumns;
    }

    public interface OnFragmentInteractionListener {
        void onItemClick(String itemId);
    }

}
