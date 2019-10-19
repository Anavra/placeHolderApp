package com.example.android.placeholder_inventory;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity
        implements LogInFragment.OnRegisterButtonPressedListener,
        RegisterFragment.OnLogInButtonPressedListener {

    private FirebaseAuth mAuth;

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof LogInFragment) {
            LogInFragment logInFragment = (LogInFragment) fragment;
            logInFragment.setOnRegisterButtonPressedListener(this);
        }
        if (fragment instanceof RegisterFragment) {
            RegisterFragment registerFragment = (RegisterFragment) fragment;
            registerFragment.setOnLogInButtonPressedListener(this);
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
}

/* To do1:
*  Maybe have a big activity and then login and register can be fragments. Login can be the "default"
* fragment to begin with, replace with register and then bring back login if needed.
* Note: When you add a fragment to an activity layout by defining the fragment in the layout XML
* file, you cannot remove the fragment at runtime. If you plan to swap your fragments in and out
* during user interaction, you must add the fragment to the activity when the activity first
* starts, as shown in Build a flexible UI.
* https://developer.android.com/training/basics/fragments/fragment-ui
* */

/* TO do2:
Add methods to control lifecycle. Right now we only have onCreate and onCreateView(in fragment)
 */