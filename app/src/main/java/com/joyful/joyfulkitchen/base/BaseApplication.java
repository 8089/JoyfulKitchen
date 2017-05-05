package com.joyful.joyfulkitchen.base;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.joyful.joyfulkitchen.dao.GreenDaoManager;
import com.joyful.joyfulkitchen.model.SearchMeauList;
import com.joyful.joyfulkitchen.model.User;

import java.util.List;

public class BaseApplication extends Application {

    private static BaseApplication mApplication;
    private static Context mContext;

    // 菜谱
    private SearchMeauList searchMeauList;

    // 用户信息
    private User user;


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


    public SearchMeauList getSearchMeauList() {
        return searchMeauList;
    }

    public void setSearchMeauList(SearchMeauList searchMeauList) {
        this.searchMeauList = searchMeauList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
