package com.example.android.android_app;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

        // Create the button and add a listener to it.
        Button mButton = (Button) rootView.findViewById(R.id.log_in_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                launchRoomList();
            }
        });
        // Return the View for the fragment's UI
        return rootView;
    }

    private void launchRoomList() {
        Log.d(LOG_TAG, "Clicked Log in button!");
        Intent intent = new Intent(getActivity(), roomListActivity.class);
        startActivity(intent);
    }

    /**
    private void launchRegisterFragment() {
        Log.d(LOG_TAG, "Clicked register button!");
        setContentView(R.layout.fragment_register);
    }
     **/
}