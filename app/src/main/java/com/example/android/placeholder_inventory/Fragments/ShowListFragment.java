package com.example.android.placeholder_inventory.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.placeholder_inventory.Adapters.RoomListAdapter;
import com.example.android.placeholder_inventory.Models.Containers;
import com.example.android.placeholder_inventory.R;

/**
 * This fragment is contained by RoomListActivity and its action buttons implemented
 * by it.
 * This fragment is responsible for showing the list, catching the input
 * and sending it to the parent activity.
 */

public class ShowListFragment extends Fragment {
    private OnFragmentInteractionListener callback;
    private RecyclerView recyclerView;
    private RoomListAdapter mAdapter;
    private EditText mAddNewField;
    private RecyclerView.LayoutManager layoutManager;
    private static final String REQUIRED = "@string/required";

    public ShowListFragment() {
        // Required empty public constructor
    }

    public void setOnFragmentInteractionListener(OnFragmentInteractionListener callback) {
        if (callback != null) {
            this.callback = callback;
        }
    }

    public interface OnFragmentInteractionListener {
        void addNewRoom(String name);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View roomView = inflater.inflate(R.layout.fragment_room_list, container,
                false);

        // Binding the recyclerView for the list
        recyclerView = (RecyclerView) roomView.findViewById(R.id.room_list_recycler_view);

        // Using a grid layout manager for the recycler view
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);


        // Adapter for the room list
        mAdapter = new RoomListAdapter(Containers.getContainers());
        recyclerView.setAdapter(mAdapter);

        //Add new item field
        mAddNewField = roomView.findViewById(R.id.addNewField);

        // Buttons
        Button mAddNewButton = roomView.findViewById(R.id.addNewButton);

        mAddNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                final String name = mAddNewField.getText().toString();
                if (formIsValid(name)) {
                    callback.addNewRoom(name);
                }
            }
        });

        return roomView;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    private boolean formIsValid(String name){
        if (TextUtils.isEmpty(name)) {
            mAddNewField.setError(REQUIRED);
            return false;
        }
        else {
            return true;
        }
    }
}
