package com.example.android.placeholder_inventory.ItemLists;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.placeholder_inventory.Authentication.AuthActivity;
import com.example.android.placeholder_inventory.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * This activity is responsible for handling fragments switching,
 * fragment communication, and the navigation elements (top bar
 * and navigation drawer).
 *
 * Code related to FireBase inspired after following the documents at
 * https://firebase.google.com and the accompanying examples.
 **/


public class RoomListActivity extends AppCompatActivity
    implements ShowListFragment.OnFragmentInteractionListener,
        AddItemFragment.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof ShowListFragment) {
            ShowListFragment showListFragment = (ShowListFragment) fragment;
            showListFragment.setOnFragmentInteractionListener(this);
        }
        if (fragment instanceof AddItemFragment) {
            AddItemFragment addItemFragment = (AddItemFragment) fragment;
            addItemFragment.setOnFragmentInteractionListener(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // Creating toolbar at the top
        Toolbar myToolbar = findViewById(R.id.app_toolbar);
        setSupportActionBar(myToolbar);

        // Creating navigation drawer on the left
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.inflateHeaderView(R.layout.navigation_header);
        TextView navUser = headerView.findViewById(R.id.nav_user);
        navUser.setText("Hello User!");

        navigationView.setNavigationItemSelectedListener(this);

        // Adding a test background image
        drawerLayout.setBackgroundResource(R.drawable.bg_grungy_hor);

        // Setting up the first fragment
        if (findViewById(R.id.main_fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            ShowListFragment firstFragment = new ShowListFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_fragment_container, firstFragment)
                    .commit();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override

    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.toolmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_add:
                launchAddNewFragment();
                return true;
            case R.id.action_remove:
                //fragment method
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_user_profile:{
                break;
            }
            case R.id.nav_log_out:{
                onBackToAuth(true);
                break;
            }
            case R.id.nav_convert:{
                Bundle bundle = new Bundle();
                bundle.putString("attempt_to_convert", "true");
                mFirebaseAnalytics.logEvent("attempt_to_convert", bundle);
                onBackToAuth(false);
                break;
            }
            case R.id.nav_home: {
                launchShowListFragment();
            }
        }
        menuItem.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void launchAddNewFragment() {
        AddItemFragment fragment = new AddItemFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void launchShowListFragment() {
        ShowListFragment fragment = new ShowListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void onBackToAuth(boolean loggingOut) {
        Intent intent = new Intent(this, AuthActivity.class);
        intent.putExtra("loggingOut", loggingOut);
        startActivity(intent);
    }


    @Override
    public void onItemClick(String itemId) {
        // Creating details fragment and sending arguments to it
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString("itemId", itemId);
        fragment.setArguments(args);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, itemId);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
