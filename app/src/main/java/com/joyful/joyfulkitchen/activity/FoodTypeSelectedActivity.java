package com.joyful.joyfulkitchen.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.adapter.FoodTypesSelectedAdapter;
import com.joyful.joyfulkitchen.model.Food;
import com.joyful.joyfulkitchen.volley.FoodTypeSelectedVolley;

import java.util.ArrayList;
import java.util.List;

public class FoodTypeSelectedActivity extends AppCompatActivity {
    private Context context = this;
    public static final String TAG = "FoodTypeSeleted";

    private ListView foodNameslistView;
    private List<Food> foodlist = new ArrayList<Food>();
    private FoodTypesSelectedAdapter adapter;


    private long foodTypeid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_type_selected);

        initView();
        initData();
    }

    // 初始化view
    private void initView() {
        foodNameslistView = (ListView) findViewById(R.id.foodNameslistView);
    }

    // 处理数据
    private void initData() {

        Intent intent = this.getIntent();
        foodTypeid = intent.getLongExtra("foodTypeid", -1);

        adapter = new FoodTypesSelectedAdapter(context,foodlist);
        foodNameslistView.setAdapter(adapter);

        // 获取数据
        new FoodTypeSelectedVolley(context,foodlist,adapter,foodTypeid).doVolley();

    }

    
    
}
