package com.example.android.placeholder_inventory.Fragments;


import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A base fragment with common functionality.
 */
public class BaseFragment extends Fragment {


    public BaseFragment() {
        // Required empty public constructor
    }

    String getUserId(){
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
