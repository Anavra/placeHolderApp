package com.example.android.placeholder_inventory.Authentication;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.placeholder_inventory.BaseFragment;
import com.example.android.placeholder_inventory.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInFragment extends BaseFragment {
    private OnButtonPressedListener mCallback;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private TextView mAuthStateTextView;
    private static final String LOG_TAG =
            LogInFragment.class.getSimpleName();

    // LogIn fields:
    private EditText mEmailField;
    private EditText mPasswordField;


    public LogInFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof OnButtonPressedListener){
            mCallback = (OnButtonPressedListener) context;
        } else {
            throw new ClassCastException(context.toString() + "not implemented in context");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize FireBase Auth
        mAuth = FirebaseAuth.getInstance();
        startAuthState();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_log_in, container,
                false);

        // Authentication variables:
        mEmailField = rootView.findViewById(R.id.email_field);
        mPasswordField = rootView.findViewById(R.id.password_field);
        mAuthStateTextView = rootView.findViewById(R.id.auth_state_text);

        // Buttons:
        Button logInButton = rootView.findViewById(R.id.login_auth_button);
        Button switchToRegisterButton = rootView.findViewById(R.id.switch_to_register_button);

        //Button Listeners:
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                signIn();
            }
        });

        switchToRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                mCallback.launchRegisterFragment();
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

    // package-private: no access modifier needs to be declared
    public void setOnButtonPressedListener(OnButtonPressedListener callback) {
        this.mCallback = callback;
    }

    public interface OnButtonPressedListener {
        // Interface defined here is implemented in AuthActivity
        void launchRegisterFragment();
        void onValidAuth(FirebaseUser user);
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

    private void signIn() {
        if(!validateForm()){
            return;
        }
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String message;
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            message = "Huge success, " + user;
                            mCallback.onValidAuth(user); //pass user into it later
                        } else {
                            message = "Could not login: " + task.getException();
                        }
                        mAuthStateTextView.setText(message);
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
        return valid;
    }
 }

