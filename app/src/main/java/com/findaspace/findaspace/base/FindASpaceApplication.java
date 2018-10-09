package com.findaspace.findaspace.base;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 *
 */
public class FindASpaceApplication extends Application {

    private static FindASpaceApplication sFindASpaceApplication;

    /**
     * record UserFlag
     */
    public enum UserFlag {
        USER, ADMIN, SECURITY
    }

    /**
     * default UserFlag is  User；
     */
    public UserFlag mUserFlag = UserFlag.USER;

    @Override
    public void onCreate() {
        super.onCreate();
        sFindASpaceApplication = this;
        initOkGo();
    }

    private void initOkGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MICROSECONDS);
        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build())
                .setCacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .setCacheTime(3600 * 30)
                .setRetryCount(3);
    }

    /**
     * get FindASpace
     */
    public static FindASpaceApplication getInstance() {
        return sFindASpaceApplication;
    }
}
