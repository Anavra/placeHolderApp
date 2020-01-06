package com.example.android.placeholder_inventory.authentication;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.android.placeholder_inventory.itemLists.ItemListActivity;
import com.example.android.placeholder_inventory.models.User;
import com.example.android.placeholder_inventory.R;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * This activity is responsible for handling fragment switching and fragment
 * communication for the authentication Package.
 * **/

public class AuthActivity extends AppCompatActivity
        implements LogInFragment.OnButtonPressedListener,
        RegisterFragment.OnButtonPressedListener,
        AuthFragment.OnButtonPressedListener {

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ConstraintLayout mLayout = findViewById(R.id.auth_fragment_container);
        mLayout.setBackgroundColor(getResources().getColor(R.color.color_primary_variant));

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean mUserLoggedInWithFacebook = accessToken != null && !accessToken.isExpired();

        Bundle receiveBundle = this.getIntent().getExtras();
        if (receiveBundle != null) {
            boolean loggingOut = receiveBundle.getBoolean("loggingOut");
            if (loggingOut) {
                FirebaseAuth mAuth;
                mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                if(mUserLoggedInWithFacebook) {
                    LoginManager.getInstance().logOut();
                }
            }
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        if (findViewById(R.id.auth_fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            //Set up the first fragment
            launchInitialFragment();
        }
    }


    public void onValidAuth(FirebaseUser user) {
        addNewUserToDataBase(user.getUid(), user.isAnonymous());
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
        Intent inIntent = new Intent(this, ItemListActivity.class);
        startActivity(inIntent);
    }

    private void addNewUserToDataBase(String UserId, Boolean isAnon){
        User user = new User(UserId, isAnon);
        mDatabase.child("users").child(UserId).setValue(user);
    }
}


