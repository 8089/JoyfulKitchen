package com.joyful.joyfulkitchen.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.joyful.joyfulkitchen.fragment.TabPageFragment;
import com.joyful.joyfulkitchen.model.WeighingFood;

import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */

public class TabFragmentPageAdapter extends FragmentPagerAdapter {

    private List<WeighingFood> datas;
    private Context context;

    public TabFragmentPageAdapter(FragmentManager fm, Context context, List<WeighingFood> datas) {
        super(fm);
        this.datas = datas;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return TabPageFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return datas.get(position).getName();
    }
}
