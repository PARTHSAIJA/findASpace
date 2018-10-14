package com.findaspace.findaspace.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by xwq on 2018/9/30.
 */

public class SpaceMonitorActivity extends AppCompatActivity {

    TextView spaceTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_monitor);

        findView();

    }

    private void findView() {
        Intent intent = getIntent();

        spaceTextView = findViewById(R.id.space_name);

        spaceTextView.setText(intent.getStringExtra("space"));


    }

}
