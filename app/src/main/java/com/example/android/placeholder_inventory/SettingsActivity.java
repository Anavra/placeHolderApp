package com.example.android.placeholder_inventory;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        /*Below we get data back from the parent activity*/
        Intent intent = getIntent();
        String message = intent.getStringExtra(roomListActivity.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.text_message);
        textView.setText(message);
    }
}


//USE LISTFRAGMENT ?? for the different levels of the inventory