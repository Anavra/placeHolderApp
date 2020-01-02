package com.example.android.placeholder_inventory.Utils;

import android.content.Context;
import android.util.DisplayMetrics;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Utils {
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

    public static int makeColumnsFit(Context context, float columnWidth) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidth = displayMetrics.widthPixels / displayMetrics.density;
        int numColumns = (int) (screenWidth / columnWidth + 0.5);
        return numColumns;
    }
}


