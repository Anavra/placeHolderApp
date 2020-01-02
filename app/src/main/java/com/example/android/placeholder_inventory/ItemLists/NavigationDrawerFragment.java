package com.example.android.placeholder_inventory.ItemLists;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.placeholder_inventory.R;

/**
 *
 */

public class NavigationDrawerFragment extends Fragment {
    private OnNavItemSelectedListener mCallback;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof OnNavItemSelectedListener){
            mCallback = (OnNavItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString() + "not implemented in context");
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View navView = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        final TextView navHome = navView.findViewById(R.id.nav_drawer_home);
        navHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCallback.onNavItemClicked("home");
            }
        });

        final TextView navProfile = navView.findViewById(R.id.nav_drawer_profile);
        navProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCallback.onNavItemClicked("profile");
            }
        });

        final TextView navLogOut = navView.findViewById(R.id.nav_drawer_log_out);
        navLogOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCallback.onNavItemClicked("logout");
            }
        });

        return navView;
    }

    public interface OnNavItemSelectedListener {
        void onNavItemClicked(String navItemId);
    }
}
