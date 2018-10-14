package com.findaspace.findaspace.main.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.findaspace.findaspace.main.login.LoginActivity;

public class  BaseActivity extends AppCompatActivity implements BaseView {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startLoginActivity();
    }

    @Override
    public void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
