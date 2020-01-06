package com.example.android.placeholder_inventory.itemLists;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.placeholder_inventory.R;

/**
 * This fragment is responsible for displaying helpful information.
 */
public class HelpFragment extends Fragment {

    private static final String TAG = "HelpFragment";
    public HelpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);

        final View view = inflater.inflate(R.layout.fragment_help,container,false);
        Log.i(TAG, "created.");

        return view;

    }

}


