package com.example.android.placeholder_inventory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInFragment extends Fragment {
    private OnButtonPressedListener callback;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private TextView mAuthStateTextView;
    private static final String LOG_TAG =
            LogInFragment.class.getSimpleName();

    // LogIn fields:
    private EditText mEmailField;
    private EditText mUserNameField;
    private EditText mPasswordField;


    public LogInFragment() {
        // Required empty public constructor
    }

    // package-private: no access modifier needs to be declared
    void setOnButtonPressedListener(OnButtonPressedListener callback) {
       this.callback = callback;
    }

    public interface OnButtonPressedListener {
        // Interface defined here is implemented in AuthActivity
        void launchRegisterFragment();
        void launchRoomList();
    }

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

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_log_in, container,
                false);

        // LogIn fields:
        mEmailField = rootView.findViewById(R.id.email_field);
        mUserNameField = rootView.findViewById(R.id.user_name_field);
        mPasswordField = rootView.findViewById(R.id.password_field);
        mAuthStateTextView = rootView.findViewById(R.id.auth_state_text);

        // Buttons:
        Button logInButton = (Button) rootView.findViewById(R.id.log_in_button);
        Button switchToRegisterButton = (Button) rootView.findViewById(R.id.register_button);

        //Button Listeners:
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                signInAnonymously();
            }
        });

        switchToRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
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

    private void signInAnonymously() {
        mAuth.signInAnonymously()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String message;
                        if (task.isSuccessful()) {
                            message = "Signed in anonymously";
                            callback.launchRoomList();
                        } else {
                            message = "Failed anonymous sign in: " + task.getException();
                        }
                        mAuthStateTextView.setText(message);
                    }
                });
    }

    private void linkAccount() {
        String email = mEmailField.getText().toString();
        String username = mUserNameField.getText().toString();
        String password = mPasswordField.getText().toString();

        AuthCredential credential = EmailAuthProvider.getCredential(email, password);

        mAuth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String message;
                        if (task.isSuccessful()) {
                            message = "This was a triumph";
                            FirebaseUser user = task.getResult().getUser();
                        } else {
                            message = "Could not link account";
                        }
                        mAuthStateTextView.setText(message);
                    }
                });
    }

    private void validateLinkForm() {

    }
 }

