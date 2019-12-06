package com.example.android.placeholder_inventory;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A base fragment with common functionality.
 */
public class BaseFragment extends Fragment {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    public BaseFragment() {
        // Required empty public constructor
    }

    public FirebaseAuth getInstance(){
        mAuth = FirebaseAuth.getInstance();
        return mAuth;
    }

    public FirebaseUser getCurrentUser(){
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        return mUser;
    }

    public String getUserId(){
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser != null) {
            return mUser.getUid();
        } else {
            return "No user active";
        }
    }

    boolean isUserAnonymous(){
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        return mUser.isAnonymous();
    }

    public static void hideSoftKeyboard(Context context, View view) {
        InputMethodManager input = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        input.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
