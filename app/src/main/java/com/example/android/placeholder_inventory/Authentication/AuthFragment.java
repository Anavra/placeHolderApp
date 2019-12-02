package com.example.android.placeholder_inventory.Authentication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.placeholder_inventory.BaseFragment;
import com.example.android.placeholder_inventory.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * This is the first fragment that is shown when opening the app.
 * Contained in activity AuthActivity, which implements interface.
 */

public class AuthFragment extends BaseFragment {
    private OnButtonPressedListener callback;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private TextView mAuthStateTextView;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseAnalytics mFirebaseAnalytics;

    private static final String LOG_TAG =
            AuthFragment.class.getSimpleName();

    public AuthFragment() {
        // Required empty public constructor
    }


    public void setOnButtonPressedListener(OnButtonPressedListener callback) {
        this.callback = callback;
    }

    public interface OnButtonPressedListener {
        // When switching to Register or Log In
        void launchLogInFragment();
        void launchRegisterFragment();

        // When pressing the button skip, log in anonymously.
        void onValidAuth(FirebaseUser user);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());

        Log.i(LOG_TAG, "Created.");
        // Initialize FireBase Auth
        mAuth = getInstance();
        mUser = getCurrentUser();
        startAuthState();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_auth, container,
                false);
        mAuthStateTextView = rootView.findViewById(R.id.auth_state_text);

        // Buttons
        Button SkipButton = rootView.findViewById(R.id.skip_button);

        // This button reads "Log in" if no user is currently logged in and "Continue"
        // if a user is already logged in.
        Button SwitchToLogInButton = rootView.findViewById(R.id.login_auth_button);
        if (mAuth.getCurrentUser() != null) {
            SwitchToLogInButton.setText(getString(R.string.continue_text));
            callback.onValidAuth(getCurrentUser());
        } else {
            callback.launchLogInFragment();
        }

       Button SwitchToRegisterButton = rootView.findViewById(R.id.register_button);

        // Button Listeners
        SkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInAnonymously();
            }
        });

        SwitchToLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth.getCurrentUser() != null) {
                    callback.onValidAuth(getCurrentUser());
                } else {
                    callback.launchLogInFragment();
                }
            }
        });

        SwitchToRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.launchRegisterFragment();
            }
        });

        // Return the View for the fragment's UI
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthStateListener);

        FirebaseUser currentUser = getCurrentUser();

        // Go in automatically if non-anon user logged in
        if (currentUser != null){
            if (!currentUser.isAnonymous()) {
                callback.onValidAuth(currentUser);
            }
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    private void startAuthState() {
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                String message;
                if (user != null) {
                    message = "signed in: " + user.getUid();
                } else {
                    message = "not signed in";
                }
                mAuthStateTextView.setText(message);
            }
        };
    }
    private void signInAnonymously() {
        mAuth.signInAnonymously()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String message;
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            message = "Signed in anonymously";

                            Bundle bundle = new Bundle();
                            bundle.putString(FirebaseAnalytics.Param.METHOD, "Anonymous");
                            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);

                            callback.onValidAuth(user);
                        } else {
                            message = "Failed anonymous sign in: " + task.getException();
                        }
                        mAuthStateTextView.setText(message);
                    }
                });
    }
}