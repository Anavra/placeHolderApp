package com.example.android.placeholder_inventory.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.placeholder_inventory.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFragment extends Fragment {
    private OnButtonPressedListener callback;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private TextView mAuthStateTextView;

    // Authentication fields:
    private EditText mEmailField;
    private EditText mPasswordField;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public void setOnButtonPressedListener(OnButtonPressedListener callback) {
        this.callback = callback;
    }

    public interface OnButtonPressedListener {
        // Interface defined here is implemented in AuthActivity
        void launchLogInFragment();
        void launchRoomList();
    }


    private static final String LOG_TAG =
            RegisterFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        startAuthState();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_register, container,
                false);

        // Authentication variables:
        mEmailField = rootView.findViewById(R.id.email_field);
        mPasswordField = rootView.findViewById(R.id.password_field);
        mAuthStateTextView = rootView.findViewById(R.id.auth_state_text);

        // Buttons
        Button LogInButton = (Button) rootView.findViewById(R.id.log_in_button);
        Button SwitchToLogInButton = (Button) rootView.findViewById(R.id.button);

        // Button Listeners
        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                linkAccount();
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

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthStateListener != null){
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    private void startAuthState(){
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

    private void linkAccount() {
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        AuthCredential credential = EmailAuthProvider.getCredential(email, password);

        mAuth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String message;
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            message = "This was a triumph, " + user;
                            callback.launchRoomList(); //pass user into it later
                            // Can send it and depending on that it'll show something different
                        } else {
                            message = "Could not link account. " + task.getException();
                        }
                        mAuthStateTextView.setText(message);
                    }
                });
    }

}
