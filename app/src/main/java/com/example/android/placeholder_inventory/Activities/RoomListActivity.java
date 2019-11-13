package com.example.android.placeholder_inventory.Activities;

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

import com.example.android.placeholder_inventory.Fragments.AddItemFragment;
import com.example.android.placeholder_inventory.Fragments.DetailsFragment;
import com.example.android.placeholder_inventory.Fragments.ShowListFragment;
import com.example.android.placeholder_inventory.R;
import com.google.android.material.navigation.NavigationView;


import com.google.firebase.auth.FirebaseAuth;

/**
 * This activity is responsible for handling its fragments and the navigation
 * elements.
 */

/**
 * Code related to FireBase inspired after following the documents at
 * https://firebase.google.com and the accompanying examples.
 **/


public class RoomListActivity extends AppCompatActivity
    implements ShowListFragment.OnFragmentInteractionListener,
        AddItemFragment.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;



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

        // Creating toolbar at the top
        Toolbar myToolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(myToolbar);

        // Creating navigation drawer on the left
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

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
                logOut();
            }
        }
        menuItem.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void launchAddNewFragment() {
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

    //PLEASE MOVE THIS FUNCTION OUT SOON! Make a class that handles all authentication-related
    // things or just call intent on AuthActivity and have the logging out happen there.
    private void logOut() {
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(String itemId) {
        // Creating details fragment and sending arguments to it
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString("itemId", itemId);
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
