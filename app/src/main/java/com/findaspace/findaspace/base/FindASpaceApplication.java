package com.findaspace.findaspace.base;

import android.app.Application;


/**
 * Created by Administrator on 2018/10/5.
 */

public class FindASpaceApplication extends Application {

    private static FindASpaceApplication sFindASpaceApplication;

    /**
     * record the UserFlag
     */
    public enum UserFlag {
        USER, ADMIN, SECURITY
    }

    /**
     * Default UserFlag is User；
     */
    public UserFlag mUserFlag = UserFlag.USER;

    @Override
    public void onCreate() {
        super.onCreate();
        sFindASpaceApplication = this;
    }

    /**
     * Get FindAspace project
     *
     */
    public static FindASpaceApplication getInstance() {
        return sFindASpaceApplication;
    }
}
