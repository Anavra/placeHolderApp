package com.example.android.placeholder_inventory.itemLists;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.android.placeholder_inventory.BaseFragment;
import com.example.android.placeholder_inventory.R;

/**
 *  This fragment is responsible for displaying the Navigation Drawer
 */

public class NavigationDrawerFragment extends BaseFragment {
    private OnNavItemSelectedListener mCallback;
    private TextView navLogOut;
    private TextView navConvert;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNavItemSelectedListener) {
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
        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onNavItemClicked("home");
            }
        });

        final TextView navProfile = navView.findViewById(R.id.nav_drawer_profile);
        navProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onNavItemClicked("profile");
            }
        });

        navLogOut = navView.findViewById(R.id.nav_drawer_log_out);


        navLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onNavItemClicked("logout");
            }
        });

        navConvert = navView.findViewById(R.id.nav_drawer_convert);


        navConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onNavItemClicked("convert");
            }
        });


        return navView;
    }

    @Override
    public void onStart() {
        super.onStart();
        /**
        if (isUserAnonymous()) {
            // Only show log out option if user is not anon to avoid losing access to anon info
            navLogOut.setVisibility(View.GONE);
        } else {
            // Only show register option if user is anon to allow conversion
            navConvert.setVisibility(View.GONE);
        }**/
    }

    public interface OnNavItemSelectedListener {
        void onNavItemClicked(String navItemId);
    }
}


