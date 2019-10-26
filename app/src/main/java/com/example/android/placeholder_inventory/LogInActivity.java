package com.example.android.placeholder_inventory;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity
        implements LogInFragment.OnButtonPressedListener,
        RegisterFragment.OnButtonPressedListener {

    private FirebaseAuth mAuth;

    @Override
    public void onAttachFragment(Fragment fragment) {
        // Implementing the fragment interfaces defined in fragments
        if (fragment instanceof LogInFragment) {
            LogInFragment logInFragment = (LogInFragment) fragment;
            logInFragment.setOnButtonPressedListener(this);
        }
        if (fragment instanceof RegisterFragment) {
            RegisterFragment registerFragment = (RegisterFragment) fragment;
            registerFragment.setOnButtonPressedListener(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            //Set up the first fragment
            LogInFragment firstFragment = new LogInFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();

            // Initialize Firebase Auth
            mAuth = FirebaseAuth.getInstance();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    private void signInAnonymously() {

    }

    public void launchRegisterFragment() {
        RegisterFragment fragment = new RegisterFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void launchLogInFragment() {
        LogInFragment fragment = new LogInFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void launchRoomList() {
        Intent inIntent = new Intent(this, roomListActivity.class);
        startActivity(inIntent);
    }
}


/* TO do2:
Add more methods to control lifecycle.
 */