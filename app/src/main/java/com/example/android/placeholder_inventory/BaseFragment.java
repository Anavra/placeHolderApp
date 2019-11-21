package com.example.android.placeholder_inventory;

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
}
