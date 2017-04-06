package com.joyful.joyfulkitchen.fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.adapter.MyFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

// 美食圈
public class GourmetCircleFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_Name = "ARG_Name";

    private String mParam1;


    //    hahaha
    private List<Fragment> fragmentList;
    private TextView view1, view2, view3;
    private ViewPager pager;
    private ImageView iv;
    private int currIndex;// 当前页卡编号
    private int bmpW;// 横线图片宽度
    private int offset;// 图片移动的偏移量


    int screenW;


    public TextView getView1() {
        return view1;
    }


    public static GourmetCircleFragment newInstance(String param1) {
        GourmetCircleFragment fragment = new GourmetCircleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_Name, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_Name);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gourmetcircle, container, false);

        /*
		 * 初始化偏移位置
		 */
        iv = (ImageView) view.findViewById(R.id.cursor);
        // 获取横线图片宽度
        bmpW = BitmapFactory.decodeResource(getResources(), R.mipmap.a1)
                .getWidth();
        // 获取屏幕分辨率
        DisplayMetrics dm = new DisplayMetrics();
        // 把屏幕尺寸信息赋值给DisplayMetrics dm，注意不是set
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        // 屏幕宽度
        screenW = dm.widthPixels - 20;

        // 计算 每个位置的宽度
        int a = screenW / 5;
        //初始化位置
        //int init = a + bmpW;
        // 计算偏移量
        offset = (screenW / 5 - bmpW) / 2;

        Animation animation_init = new TranslateAnimation(0, a + offset, 0, 0);// 平移动画
        animation_init.setFillAfter(true);// 动画终止时停留在最后一帧，不然会回到没有执行前的状态
        animation_init.setDuration(200);// 动画持续时间0.2秒
        iv.startAnimation(animation_init);// 是用ImageView来显示动画的
		/*
		 * 第一步：创建List，保存所有的Fragment
		 */
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new GourmetFollowFragment());
        fragmentList.add(new GourmetFindFragment());
        fragmentList.add(new GourmetNewestFragment());
		/*
		 * 找到ViewPager控件
		 */
        // getFragmentManager() 返回android.app.xxx
        // getSupportFragmentManager() 返回android.support.v4.app.
        // FragmentActivity提供此方法，Activity本身不提供

        pager = (ViewPager) view.findViewById(R.id.vp);
        pager.setAdapter(new MyFragmentPagerAdapter(
                getFragmentManager(), fragmentList));
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            private int one = offset * 2 + bmpW;// 两个相邻页面的偏移量

            @Override
            public void onPageSelected(int arg0) {
                Animation animation = new TranslateAnimation(
                        currIndex * one + offset  + screenW/5, //原来的坐标
                        arg0 * one + offset + screenW/5 + arg0*10,//目标的坐标
                        0, 0 //垂直方向不变
                );// 平移动画
                currIndex = arg0;
                animation.setFillAfter(true);// 动画终止时停留在最后一帧，不然会回到没有执行前的状态
                animation.setDuration(200);// 动画持续时间0.2秒
                iv.startAnimation(animation);// 是用ImageView来显示动画的
                //Toast.makeText(MainActivity.this, "您选择了第"+(currIndex+1)+"个页卡", 100).show();
                view1.setTextColor(getResources().getColor(R.color.title_not_select_gray));
                view2.setTextColor(getResources().getColor(R.color.title_not_select_gray));
                view3.setTextColor(getResources().getColor(R.color.title_not_select_gray));
                if (arg0 == 0) {
                    view1.setTextColor(getResources().getColor(R.color.red_text));
                } else if (arg0 == 1) {
                    view2.setTextColor(getResources().getColor(R.color.red_text));
                } else {
                    view3.setTextColor(getResources().getColor(R.color.red_text));
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });

		/*
		 * 初始化各个链接
		 */
        view1 = (TextView) view.findViewById(R.id.tv1);
        view2 = (TextView) view.findViewById(R.id.tv2);
        view3 = (TextView) view.findViewById(R.id.tv3);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(0);
            }
        });
        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(1);
            }
        });
        view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(2);
            }
        });





    return view;
}




}
