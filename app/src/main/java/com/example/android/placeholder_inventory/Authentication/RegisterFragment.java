package com.example.android.placeholder_inventory.Authentication;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.placeholder_inventory.BaseFragment;
import com.example.android.placeholder_inventory.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class RegisterFragment extends BaseFragment {
    private OnButtonPressedListener mCallback;
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
        this.mCallback = callback;
    }

    public interface OnButtonPressedListener {
        // Interface defined here is implemented in AuthActivity
        void launchLogInFragment();
        void onValidAuth(FirebaseUser user);
    }


    private static final String LOG_TAG =
            RegisterFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize FireBase Auth
        mAuth = FirebaseAuth.getInstance();
        checkAuthState();
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
        Button LogInButton = rootView.findViewById(R.id.register_auth_button);
        Button SwitchToLogInButton = rootView.findViewById(R.id.switch_to_login_button);

        // Button Listeners
        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (mAuth.getCurrentUser() != null) {
                    linkAccount();
                } else {
                    createAccount();
                }
            }
        });

        SwitchToLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                mCallback.launchLogInFragment();
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

    private void checkAuthState(){
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
        if(validateForm()){
            return;
        }
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
                            mCallback.onValidAuth(user); //pass user into it later
                            // Can send it and depending on that it'll show something different
                        } else {
                            message = "Could not link account. " + task.getException();
                        }
                        mAuthStateTextView.setText(message);
                    }
                });
    }

    private void createAccount(){
        if(validateForm()){
            return;
        }
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            mCallback.onValidAuth(user);
                        } else {
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;
        final String REQUIRED = getResources().getString(R.string.required);
        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError(REQUIRED);
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)){
            mPasswordField.setError(REQUIRED);
            valid = false;
        } else {
            mPasswordField.setError(null);
        }
        return !valid;
    }
}
