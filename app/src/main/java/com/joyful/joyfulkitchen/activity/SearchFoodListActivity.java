package com.joyful.joyfulkitchen.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;

import com.joyful.joyfulkitchen.Itemdecoration.FoodSearchItemDecoration;
import com.joyful.joyfulkitchen.Itemdecoration.MyItemDecoration;
import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.adapter.RecyclerAdapter;
import com.joyful.joyfulkitchen.adapter.RecyclerViewHolder;
import com.joyful.joyfulkitchen.model.Food;
import com.joyful.joyfulkitchen.util.ToastUtils;
import com.joyful.joyfulkitchen.volley.FoodSearchVolley;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// 搜索单个食材 列表
public class SearchFoodListActivity extends AppCompatActivity {

    public Context context = this;

    // 搜索框  小麦
    private SearchView mSearchView;
    // 列表
    private RecyclerView mRecyclerView;

    // 食物列表
    private List<Food> foodList = new ArrayList<>();

    // 适配器
    private RecyclerAdapter<Food> recyclerAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_food_list);


        initView();

        addListener();



    }

    private void addListener() {
        // 为该SearchView组件设置事件监听器
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ToastUtils.showToast(getApplicationContext(), "hhhh" + query);
                new FoodSearchVolley(SearchFoodListActivity.this, query, recyclerAdapter, foodList).doVolley();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 实际应用中应该在该方法内执行实际查询
                // 此处仅使用Toast显示用户输入的查询内容
                ToastUtils.showToast(getApplicationContext(), "bbbb" + newText);
                return false;
            }
        });

    }

    private void initView() {

        mSearchView = (SearchView) findViewById(R.id.sv_food_search);
        // 设置该SearchView默认是否自动缩小为图标
        mSearchView.setIconifiedByDefault(false);
        // 设置该SearchView显示搜索按钮
        mSearchView.setSubmitButtonEnabled(true);
        // 设置该SearchView内默认显示的提示文本
        mSearchView.setQueryHint("查找食材");

        SearchView.SearchAutoComplete textView = ( SearchView.SearchAutoComplete) mSearchView.findViewById(R.id.search_src_text);
        textView.setTextColor(Color.WHITE);


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new MyItemDecoration());
        mRecyclerView.setAdapter(recyclerAdapter = new RecyclerAdapter<Food>(this, foodList, R.layout.recyclerview_item_show_food) {
            @Override
            public void convert(RecyclerViewHolder holder, Food data, int position) {
                holder.setText(R.id.tv_food_name, data.getFoodName());
            };
        });


        recyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {

            @Override
            public void OnItemClickListener(View view, int position) {
                Intent intent = new Intent(context,FoodValueActivity.class);
                intent.putExtra("foodvalue", (Serializable) foodList.get(position));
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("requestCode = ", requestCode + "");
        Log.i("requestCode = ", resultCode + "");
        switch (resultCode){
            case 1: // 点击了选择
                finish();
                break;

        }
    }
}
