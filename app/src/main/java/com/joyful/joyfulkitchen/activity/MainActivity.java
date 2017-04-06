package com.joyful.joyfulkitchen.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.fragment.CookeyBookFragment;
import com.joyful.joyfulkitchen.fragment.GourmetCircleFragment;
import com.joyful.joyfulkitchen.fragment.HealthyFragment;
import com.joyful.joyfulkitchen.fragment.MyFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements BottomNavigationBar.OnTabSelectedListener  {


    private FragmentManager mFragmentManager;
    // fragment 集合
    private List<Fragment> fragments;
    private BottomNavigationBar mBottomNavigationBar;
    private BadgeItem badgeItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化
        initView();
        // 初始化 fragment
        initFragment();

    }

    // 初始化底部导航
    private void initFragment() {
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
        //设置默认颜色
        mBottomNavigationBar
                .setInActiveColor(R.color.grey);//设置未选中的Item的颜色，包括图片和文字
//                .setActiveColor(R.color.colorPrimary)
//                .setBarBackgroundColor(R.color.chocolate);//设置整个控件的背景色
        //设置徽章
        badgeItem = new BadgeItem()//
                //             .setBorderWidth(2)//Badge的Border(边界)宽度
                //                .setBorderColor("#FF0000")//Badge的Border颜色
                //                .setBackgroundColor("#9ACD32")//Badge背景颜色
                //                .setGravity(Gravity.RIGHT| Gravity.TOP)//位置，默认右上角
                                .setText("12");//显示的文本
                //                .setTextColor("#F0F8FF")//文本颜色
                //                .setAnimationDuration(2000)
//                                .setHideOnSelect(true);//当选中状态时消失，非选中状态显示
        mBottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.healthy, "健康饮食").setActiveColorResource(R.color.orange))
                .addItem(new BottomNavigationItem(R.mipmap.ckb, "菜谱").setActiveColorResource(R.color.orange))
                .addItem(new BottomNavigationItem(R.mipmap.gourmet_circle, "美食圈").setActiveColorResource(R.color.orange).setBadgeItem(badgeItem))
                .addItem(new BottomNavigationItem(R.mipmap.my, "我的").setActiveColorResource(R.color.orange))
                .initialise();
        fragments = getFragments();
        mBottomNavigationBar.setTabSelectedListener(this);
        setDefaultFragment();
    }



    private void initView() {
        mFragmentManager = getSupportFragmentManager();
        mBottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

    }

    // 设置默认的 fragment
    private void setDefaultFragment() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.layFrame, fragments.get(0));
        transaction.commitAllowingStateLoss();
    }

    private List<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(HealthyFragment.newInstance("健康饮食"));
        fragments.add(CookeyBookFragment.newInstance("菜谱"));
        fragments.add(GourmetCircleFragment.newInstance("美食圈"));
        fragments.add(MyFragment.newInstance("我的"));

        return fragments;
    }




    @Override
    public void onTabSelected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentTransaction ft = mFragmentManager.beginTransaction();
                Fragment fragment = fragments.get(position);
                if (fragment.isAdded()) {
                    ft.show(fragment);
                }
                else {
                    ft.add(R.id.layFrame,fragment);
                }
                if (position == 2 && badgeItem != null) {
                    badgeItem.hide();
                }


                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabUnselected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentTransaction ft = mFragmentManager.beginTransaction();
                Fragment fragment = fragments.get(position);
                ft.hide(fragment);
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabReselected(int position) {

    }
}