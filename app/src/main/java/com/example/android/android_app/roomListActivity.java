package com.example.android.android_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class roomListActivity extends AppCompatActivity {
    private static final String LOG_TAG =
            roomListActivity.class.getSimpleName();
    public static final String EXTRA_MESSAGE =
            "com.example.android.android-app.extra.MESSAGE";

    TextView mContainerListView;
    private EditText mMessageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);
        /*Bundle b = getIntent().getExtras();
        String name = b.getString("name");*/

        /*Text to submit*/
        mMessageEditText = findViewById(R.id.editText_main);


        mContainerListView = (TextView) findViewById(R.id.containers_list);
        String[] containerNames = Containers.getContainers();
        for (String container : containerNames) {
            mContainerListView.append(container + "\n\n\n");
        }
    }

    public void launchSettingsActivity(View view) {
        Log.d(LOG_TAG, "button clicked!");
        Intent intent = new Intent(this, SettingsActivity.class);
        String message = mMessageEditText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
