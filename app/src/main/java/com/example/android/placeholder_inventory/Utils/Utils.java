package com.example.android.placeholder_inventory.Utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

class Utils {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

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


