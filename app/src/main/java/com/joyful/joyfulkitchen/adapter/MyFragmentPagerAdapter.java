package com.joyful.joyfulkitchen.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 *  美食圈中 三个 fragment  切换
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private FragmentManager fm;
    private List<Fragment> fragementList;

    /*
     * ���构造方法��
     */
    public MyFragmentPagerAdapter(FragmentManager fm,List<Fragment> fragementList) {
        super(fm);
        this.fm = fm;
        this.fragementList = fragementList;
    }

    @Override
    public Fragment getItem(int fi) {
        // TODO Auto-generated method stub
        return fragementList.get(fi);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return fragementList.size();
    }

}

