
package com.joyful.joyfulkitchen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.joyful.joyfulkitchen.Itemdecoration.FoodSearchItemDecoration;
import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.adapter.FoodSearchAdapter;
import com.joyful.joyfulkitchen.utils.ToastUtils;
import com.joyful.joyfulkitchen.view.XTabHost;

import java.util.ArrayList;
import java.util.List;


public class FoodSelectActivity extends AppCompatActivity {

    private ImageView iv_back;
    private RecyclerView mRecyclerView;
    private XTabHost xTabHost;

    private LinearLayoutManager mLinearLayoutManager;

    private List<String> dates;
    private FoodSearchAdapter mFoodSearchAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_select);

        initView();
        initData();

    }




    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_food_search);
        mLinearLayoutManager = new LinearLayoutManager(FoodSelectActivity.this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);



        xTabHost = (XTabHost) findViewById(R.id.tabhost1);
    }



    private void initData() {
        dates = new ArrayList<>();
        for (int i = 0; i< 20; i++) {
            dates.add("hello --->" + i);
        }

        if (mFoodSearchAdapter == null)
            mRecyclerView.setAdapter(mFoodSearchAdapter = new FoodSearchAdapter(FoodSelectActivity.this, dates));

        mRecyclerView.addItemDecoration(new FoodSearchItemDecoration(FoodSelectActivity.this));


        xTabHost.setOnSelectListener(new XTabHost.OnSelectListener() {
            @Override
            public void onSelect(int index, String text) {
                ToastUtils.showToast(FoodSelectActivity.this, index + "," + text);
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });






    }




}
