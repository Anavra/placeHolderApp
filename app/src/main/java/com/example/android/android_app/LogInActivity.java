package com.example.android.android_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.support.v4.app.FragmentActivity;


public class LogInActivity extends AppCompatActivity {
    private static final String LOG_TAG =
            roomListActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}

/* To do1:
*  Maybe have a big activity and then login and register can be fragments. Login can be the "default"
* fragment to begin with, replace with register and then bring back login if needed.
* Note: When you add a fragment to an activity layout by defining the fragment in the layout XML
* file, you cannot remove the fragment at runtime. If you plan to swap your fragments in and out
* during user interaction, you must add the fragment to the activity when the activity first
* starts, as shown in Build a flexible UI.
* https://developer.android.com/training/basics/fragments/fragment-ui
* */

/* TO do2:
Add methods to control lifecycle. Right now we only have onCreate and onCreateView(in fragment)
 */