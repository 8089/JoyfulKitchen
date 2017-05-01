package com.joyful.joyfulkitchen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.codbking.calendar.CalendarDateView;
import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.activity.CookeyBookActivity;
import com.joyful.joyfulkitchen.activity.FoodTypeSelectActivity;

/**
 * Created by Administrator on 2017/4/27.
 */

public class HomeFragment extends Fragment {

    public static final String ARG_Name = "arg_name";
    private String mParam1;

    private Button btn_noe;
    private Button btn_many;


    public static HomeFragment newInstance(String param1) {
        Bundle args = new Bundle();

        args.putString(ARG_Name, param1);
        HomeFragment fragment = new HomeFragment();
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initView(view);
        addListener();



        return view;
    }

    private void addListener() {
        // 单个食材跳转
        btn_noe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FoodTypeSelectActivity.class);
                startActivity(intent);
            }
        });

        //  多个 食材 点击跳转
        btn_many.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CookeyBookActivity.class);
                startActivity(intent);
            }
        });


    }


    private void initView(View view) {
        btn_noe = (Button) view.findViewById(R.id.btn_one);
        btn_many = (Button) view.findViewById(R.id.btn_many);
    }


}