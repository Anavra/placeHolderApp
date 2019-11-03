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
    private OnButtonPressedListener callback;

    public void setOnButtonPressedListener(OnButtonPressedListener callback) {
        this.callback = callback;
    }

    public interface OnButtonPressedListener {
        // Interface defined here is implemented in AuthActivity
        void launchLogInFragment();
        void launchRoomList();
    }

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

        // Buttons
        Button LogInButton = (Button) rootView.findViewById(R.id.log_in_button);
        Button SwitchToLogInButton = (Button) rootView.findViewById(R.id.button);

        // Button Listeners
        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                callback.launchRoomList();
            }
        });

        SwitchToLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                callback.launchLogInFragment();
            }
        });

        // Return the View for the fragment's UI
        return rootView;
    }
}
