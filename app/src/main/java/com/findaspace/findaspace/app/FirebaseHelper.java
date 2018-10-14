package com.findaspace.findaspace.app;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by xwq on 2018/9/30.
 */

public class FirebaseHelper {
    private static FirebaseHelper instance;

    public static FirebaseHelper getInstance() {
        if (instance == null) {
            instance = new FirebaseHelper();
        }

        return instance;
    }

    private FirebaseAuth mAuth = null;

    private FirebaseHelper() {
        mAuth = FirebaseAuth.getInstance();
    }
}
