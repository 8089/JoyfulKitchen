package com.joyful.joyfulkitchen.base;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.joyful.joyfulkitchen.dao.GreenDaoManager;

public class BaseApplication extends Application {

    private static BaseApplication mApplication;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化
        mApplication = this;
        mContext = getApplicationContext();

        // greenDao全局配置,只希望有一个数据库操作对象
        GreenDaoManager.getInstance();

        // 图片 Fresco
        Fresco.initialize(this);


        // volley


    }

    public static Context getContext() {
        return mContext;
    }

    public static BaseApplication getmApplication() {
        return mApplication;
    }
}
