package com.example.android.android_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LogInActivity extends AppCompatActivity {
    private static final String LOG_TAG =
            roomListActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ((Button)findViewById(R.id.log_in_button))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        launchRoomList();
                    }
                });
    }

    private void launchRoomList() {
        Log.d(LOG_TAG, "button clicked!");
        Intent intent = new Intent( this, roomListActivity.class);
        /*intent.putExtra("name", "Nessa");*/
        startActivity(intent);
    }
}
