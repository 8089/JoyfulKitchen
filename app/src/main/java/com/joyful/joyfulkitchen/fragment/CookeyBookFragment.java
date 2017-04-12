package com.joyful.joyfulkitchen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.activity.TestActivity;
import com.joyful.joyfulkitchen.adapter.CookeyBookImageLoopAdapter;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;

// cookeybook  菜谱
public class CookeyBookFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_Name = "ARG_Name";
    //图片轮播
    private RollPagerView mViewPager;

    private String mParam1;


    public static CookeyBookFragment newInstance(String param1) {
        CookeyBookFragment fragment = new CookeyBookFragment();
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
        View view = inflater.inflate(R.layout.fragment_cookeybook, container, false);

        mViewPager = (RollPagerView) view.findViewById(R.id.view_pager);
        mViewPager.setAdapter(new CookeyBookImageLoopAdapter(mViewPager));



        mViewPager.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent = new Intent(getContext(), TestActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }




}
