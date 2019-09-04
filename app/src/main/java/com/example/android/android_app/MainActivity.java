package com.example.android.android_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView mContainerListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContainerListView = (TextView) findViewById(R.id.containers_list);
        String[] containerNames = Containers.getContainers();
        for (String container : containerNames) {
            mContainerListView.append(container + "\n\n\n");
        }
    }
}
