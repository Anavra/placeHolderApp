package com.example.android.placeholder_inventory.itemLists;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.example.android.placeholder_inventory.authentication.AuthActivity;
import com.example.android.placeholder_inventory.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * This activity is responsible for handling fragments switching,
 * fragment communication, and the navigation elements (top bar
 * and navigation drawer) for the Main itemLists package.
 **/


public class ItemListActivity extends AppCompatActivity
        implements ShowListFragment.OnFragmentInteractionListener,
        AddItemFragment.OnFragmentInteractionListener,
        NavigationDrawerFragment.OnNavItemSelectedListener,
        UserProfileFragment.OnFragmentInteractionListener {

    private static final String TAG = "TAG_ITEM_LIST_ACTIVITY";
    private DrawerLayout mDrawerLayout;
    private FrameLayout detailLayout;
    private Toolbar mToolbar;
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        setUpTopToolBar();
        setUpNavigationDrawer();


        // Setting up the first fragment
        if (findViewById(R.id.main_fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            launchInitialFragment();
        }

        // For tablets, the detail_fragment_container will show side by side with
        // main_fragment_container
        detailLayout = findViewById(R.id.detail_fragment_container);

    }


    @Override
    public void onStart() {
        super.onStart();
        checkAuthenticationStatus();
    }


    @Override
    public void onResume() {
        super.onResume();
        checkAuthenticationStatus();
    }

    /* Inflating the mToolbar menu*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.toolmenu, menu);
        return true;
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
        if (detailLayout != null) {
            transaction.replace(R.id.detail_fragment_container, fragment);
        } else {
            transaction.replace(R.id.main_fragment_container, fragment);
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add: {
                launchAddNewFragment();
                return true;
            }
            case R.id.home: {
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onNavItemClicked(String navItemId) {
        switch (navItemId) {
            case "home": {

                launchShowListFragment();
                break;
            }
            case "profile": {
                Bundle bundle = new Bundle();
                bundle.putBoolean("user_checks_profile", true);
                mFirebaseAnalytics.logEvent("user_checks_profile", bundle);
                launchUserProfileFragment();
                break;
            }
            case "logout": {
                launchAuthentication(true);
                break;
            }
            case "convert": {
                Bundle bundle = new Bundle();
                bundle.putString("attempt_to_convert", "true");
                mFirebaseAnalytics.logEvent("attempt_to_convert", bundle);
                launchAuthentication(false);
                break;
            }

            default: {
                Log.d(TAG, "Unknown nav drawer id");
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    private void setUpTopToolBar() {
        // Creating mToolbar at the top
        mToolbar = findViewById(R.id.app_toolbar);

        if (mToolbar != null){
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(null);
        }
    }


    private void setUpNavigationDrawer() {
        // Creating navigation drawer on the left
        mDrawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.Open, R.string.Close);

        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        mDrawerLayout.addDrawerListener(toggle);


        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setBackgroundColor(getResources().getColor(R.color.color_primary_variant));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return true;
            }
        });


        // Adding a test background image
        //mDrawerLayout.setBackgroundResource(R.drawable.bg_grungy_hor);
        mDrawerLayout.setBackgroundColor(getResources().getColor(R.color.color_primary_variant));

        // insert navigation drawer fragment into container
        NavigationDrawerFragment navFragment = new NavigationDrawerFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.navigation_view, navFragment);
        transaction.commit();
    }

    public void launchInitialFragment() {
        ShowListFragment firstFragment = new ShowListFragment();
        HelpFragment helpFragment = new HelpFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_fragment_container, firstFragment);
        if (detailLayout != null) {
            transaction.add(R.id.detail_fragment_container, helpFragment);
        }
        transaction.commit();
    }

    private void launchAddNewFragment() {
        AddItemFragment fragment = new AddItemFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (detailLayout != null) {
            transaction.replace(R.id.detail_fragment_container, fragment);
        } else {
            transaction.replace(R.id.main_fragment_container, fragment);
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void launchShowListFragment() {
        ShowListFragment fragment = new ShowListFragment();
        HelpFragment helpFragment = new HelpFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.addToBackStack(null);
        if (detailLayout != null) {
            transaction.replace(R.id.detail_fragment_container, helpFragment);
        }
        transaction.commit();
    }

    public void launchUserProfileFragment() {
        UserProfileFragment fragment = new UserProfileFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private void checkAuthenticationStatus() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return;
        } else {
            launchAuthentication(false);
        }
    }

    /* Communication with the authentication module in case of login, logout, etc. */
    private void launchAuthentication(boolean loggingOut) {
        Intent intent = new Intent(this, AuthActivity.class);
        intent.putExtra("loggingOut", loggingOut);
        startActivity(intent);
    }

    public void changeBackgroundColor(int color){
        mToolbar.setBackgroundColor(color);
        mDrawerLayout.setBackgroundColor(color);
    }

}