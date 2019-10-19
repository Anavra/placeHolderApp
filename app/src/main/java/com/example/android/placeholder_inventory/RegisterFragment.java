package com.example.android.placeholder_inventory;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class RegisterFragment extends Fragment {
    public RegisterFragment() {
        // Required empty public constructor
    }
    private static final String LOG_TAG =
            RegisterFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_register, container,
                false);

        // Create a button to show the list and add a listener to it.
        Button inButton = (Button) rootView.findViewById(R.id.log_in_button);
        inButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                launchRoomList();
            }
        });

        // Create a button to show login and add a listener to it.
        // TO DO: Change button names, it's confusing! Button rename to log_in_button
        // log_in_button rename to "start_main" or something
        Button logButton = (Button) rootView.findViewById(R.id.button);
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                launchLogInFragment();
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

    private void launchLogInFragment() {
        Log.d(LOG_TAG, "Clicked log in button!");
        LogInFragment logfragment = new LogInFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, logfragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }
}
