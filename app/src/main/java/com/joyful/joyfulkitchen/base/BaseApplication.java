package com.joyful.joyfulkitchen.base;

import android.app.Application;
import android.content.Context;

import com.joyful.joyfulkitchen.dao.GreenDaoManager;

public class BaseApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        // greenDao全局配置,只希望有一个数据库操作对象
        GreenDaoManager.getInstance();
    }
    public static Context getContext() {
        return mContext;
    }


}
