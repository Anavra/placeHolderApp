package com.example.android.placeholder_inventory.Authentication;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.android.placeholder_inventory.ItemLists.RoomListActivity;
import com.example.android.placeholder_inventory.Models.User;
import com.example.android.placeholder_inventory.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This activity is responsible for handling fragment switching, fragment
 * communication.
 * **/

public class AuthActivity extends AppCompatActivity
        implements LogInFragment.OnButtonPressedListener,
        RegisterFragment.OnButtonPressedListener,
        AuthFragment.OnButtonPressedListener {

    private DatabaseReference mDatabase;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onAttachFragment(Fragment fragment) {
        // Implementing the fragment interfaces defined in fragments
        if (fragment instanceof AuthFragment) {
            AuthFragment authFragment = (AuthFragment) fragment;
            authFragment.setOnButtonPressedListener(this);
        } if (fragment instanceof LogInFragment) {
            LogInFragment logInFragment = (LogInFragment) fragment;
            logInFragment.setOnButtonPressedListener(this);
        } if (fragment instanceof RegisterFragment) {
            RegisterFragment registerFragment = (RegisterFragment) fragment;
            registerFragment.setOnButtonPressedListener(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bundle receiveBundle = this.getIntent().getExtras();
        if (receiveBundle != null) {
            boolean loggingOut = receiveBundle.getBoolean("flag");
            if (loggingOut) {
                FirebaseAuth mAuth;
                mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
            }
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        if (findViewById(R.id.auth_fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            //Set up the first fragment
            launchInitialFragment();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void onValidAuth(FirebaseUser user) {
        addNewUserToDataBase(user.getUid());
        launchRoomList();
    }

    public void launchInitialFragment() {
        AuthFragment firstFragment = new AuthFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.auth_fragment_container, firstFragment)
                .commit();
    }

    public void launchRegisterFragment() {
        RegisterFragment fragment = new RegisterFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.auth_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void launchLogInFragment() {

        LogInFragment fragment = new LogInFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.auth_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void launchRoomList() {
        Intent inIntent = new Intent(this, RoomListActivity.class);
        startActivity(inIntent);
    }

    private void addNewUserToDataBase(String UserId){
        User user = new User(UserId);
        mDatabase.child("users").child(UserId).setValue(user);
    }

}


/* TO do2:
Add more methods to control lifecycle.
 */