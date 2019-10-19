package com.example.android.placeholder_inventory;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.util.Log;

public class LogInFragment extends Fragment {
    private static final String LOG_TAG =
            LogInFragment.class.getSimpleName();

    public LogInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_log_in, container,
                false);

        // Create a button to show the list and add a listener to it.
        Button inButton = (Button) rootView.findViewById(R.id.log_in_button);
        inButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                launchRoomList();
            }
        });

        // Create a button to show register and add a listener to it.
        Button regButton = (Button) rootView.findViewById(R.id.register_button);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                launchRegisterFragment();
            }
        });

        // Return the View for the fragment's UI
        return rootView;
    }

    private void launchRoomList() {
        Log.d(LOG_TAG, "Clicked Log in button!");
        Intent inIntent = new Intent(getActivity(), roomListActivity.class);
        startActivity(inIntent);
    }

    private void launchRegisterFragment() {
        Log.d(LOG_TAG, "Clicked register button!");
        RegisterFragment fragment = new RegisterFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }
 }