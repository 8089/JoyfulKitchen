package com.joyful.joyfulkitchen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.adapter.FoodStepsAdapter;
import com.joyful.joyfulkitchen.base.BaseApplication;
import com.joyful.joyfulkitchen.model.SearchMeauList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/30.
 */

public class ShowStepsActivity extends AppCompatActivity {

    private ListView food_steps;
    // 步骤
    private List<SearchMeauList.StepsBean> foodStepsData;

    private FoodStepsAdapter foodStepsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_step);

        food_steps = (ListView) findViewById(R.id.food_steps);

        foodStepsData = ((BaseApplication)getApplication()).getFoodStepsData();

        // 步骤
        foodStepsAdapter = new FoodStepsAdapter(this, foodStepsData);
        food_steps.setAdapter(foodStepsAdapter);
    }
}
