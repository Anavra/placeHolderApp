package com.example.android.placeholder_inventory;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class LogInFragment extends Fragment {
    private OnButtonPressedListener callback;

    public void setOnButtonPressedListener(OnButtonPressedListener callback) {
       this.callback = callback;
    }

    public interface OnButtonPressedListener {
        // Interface defined here is implemented in LogInActivity
        void launchRegisterFragment();
        void launchRoomList();
    }

    private static final String LOG_TAG =
            LogInFragment.class.getSimpleName();

    public LogInFragment() {
        // Required empty public constructor
    }

    // LogIn fields:
    private EditText mUserNameField;
    private EditText mPasswordField;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_log_in, container,
                false);

        // LogIn fields:
        mUserNameField = rootView.findViewById(R.id.user_name_field);
        mPasswordField = rootView.findViewById(R.id.password_field);

        // Buttons:
        Button logInButton = (Button) rootView.findViewById(R.id.log_in_button);
        Button switchToRegisterButton = (Button) rootView.findViewById(R.id.register_button);

        //Button Listeners:
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                callback.launchRoomList();
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

    public void logIn(Boolean result)
    {

    }




 }