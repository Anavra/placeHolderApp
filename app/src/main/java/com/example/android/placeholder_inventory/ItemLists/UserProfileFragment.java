package com.example.android.placeholder_inventory.ItemLists;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.placeholder_inventory.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import org.w3c.dom.Text;

/**
 * This fragment is responsible for displaying the user profile information (right now
 * we don't have much user information to show)
 * It is also responsible for Remote Config color changes.
 */
public class UserProfileFragment extends Fragment {
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private OnFragmentInteractionListener mCallback;
    private static final String TAG = "UserProfileFragment";
    private TextView mWelcomeTextView;

    private static final String LOADING_PHRASE_CONFIG_KEY = "loading_phrase";
    private static final String WELCOME_MESSAGE_KEY = "welcome_message";
    private static final String WELCOME_MESSAGE_CAPS_KEY = "welcome_message_caps";
    private static final String BACKGROUND_COLOR = "background_color";

    public UserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mCallback = (OnFragmentInteractionListener) context;
        } else {
            throw new ClassCastException(context.toString() + "not implemented in context");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater,container,savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_user_profile,container,false);

        mWelcomeTextView = view.findViewById(R.id.user_welcome);

        Button CloseButton = view.findViewById(R.id.profile_close_button);
        CloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.launchShowListFragment();
            }
        });

        Button fetchButton = view.findViewById(R.id.fetchButton);
        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchRemoteConfig();
            }
        });
        setUpRemoteConfig();
        return view;
    }

    private void setUpRemoteConfig(){
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(300)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        // Setting default values as stored
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);

        fetchRemoteConfig();
    }

    private void fetchRemoteConfig() {
        mWelcomeTextView.setText(mFirebaseRemoteConfig.getString(LOADING_PHRASE_CONFIG_KEY));

        // [START fetch_config_with_callback]
        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            boolean updated = task.getResult();
                            Log.d(TAG, "Config params updated: " + updated);
                            Toast.makeText(getActivity(), "Fetch and activate succeeded",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getActivity(), "Fetch failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                        displayRemoteConfig();
                    }
                });
        // [END fetch_config_with_callback]
    }

    private void displayRemoteConfig() {
        // [START get_config_values]
        String welcomeMessage = mFirebaseRemoteConfig.getString(WELCOME_MESSAGE_KEY);
        String backgroundColor = mFirebaseRemoteConfig.getString(BACKGROUND_COLOR);
        int color = Color.parseColor(backgroundColor);
        // [END get_config_values]
        if (mFirebaseRemoteConfig.getBoolean(WELCOME_MESSAGE_CAPS_KEY)) {
            mWelcomeTextView.setAllCaps(true);
        } else {
            mWelcomeTextView.setAllCaps(false);
        }
        mWelcomeTextView.setText(welcomeMessage);

        getView().setBackgroundColor(color);
        getActivity().getWindow().getDecorView().setBackgroundColor(color);
        mCallback.changeBackgroundColor(color);
    }

    public interface OnFragmentInteractionListener {
        void launchShowListFragment();
        void changeBackgroundColor(int color);
    }

}
