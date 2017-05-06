package com.joyful.joyfulkitchen.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.joyful.joyfulkitchen.Itemdecoration.FoodSearchItemDecoration;
import com.joyful.joyfulkitchen.Itemdecoration.MyItemDecoration;
import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.adapter.RecyclerAdapter;
import com.joyful.joyfulkitchen.adapter.RecyclerViewHolder;
import com.joyful.joyfulkitchen.model.Food;
import com.joyful.joyfulkitchen.util.StringFormatUtil;
import com.joyful.joyfulkitchen.util.ToastUtils;
import com.joyful.joyfulkitchen.volley.FoodSearchVolley;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// 搜索单个食材 列表
public class SearchFoodListActivity extends AppCompatActivity {

    public Context mContext = this;

    // 列表
    private RecyclerView mRecyclerView;

    // 食物列表
    private List<Food> foodList = new ArrayList<>();

    // 适配器
    private RecyclerAdapter<Food> recyclerAdapter;

    // 搜索的文字
    private String mLightStr;
    private EditText et_search;
    private TextView tv_cancel;
    private FrameLayout mNoLayout;

    //是否显示搜索结果的状体标志
    private final static int NO_TTHING = 0;
    private final static int SHOW_DATA = 1;
    private static int STATE = 0;  //默认的是没有数据

    private String mKey;  //key值


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_food_list);


        initView();

        addListener();



    }

    private void addListener() {
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (!TextUtils.isEmpty(charSequence)) {

                    mKey = charSequence.toString().trim();
                    new FoodSearchVolley(SearchFoodListActivity.this, mKey, recyclerAdapter, foodList, mNoLayout, mRecyclerView).doVolley();
//                    initData(charSequence.toString());

                    changeStates(STATE);

                } else {
                    STATE = NO_TTHING;

                    changeStates(STATE);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search.setText("");
//                mDataBeen.clear();
//                mLoadMoreData.clear();
                et_search.setHint("请输入你搜索的关键字");
                mNoLayout.setVisibility(View.INVISIBLE);
                mRecyclerView.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void initView() {

        et_search = (EditText) findViewById(R.id.et_search);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        mNoLayout = (FrameLayout) findViewById(R.id.no_data);


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.addItemDecoration(new MyItemDecoration());

        mRecyclerView.setAdapter(recyclerAdapter = new RecyclerAdapter<Food>(this, foodList, R.layout.recyclerview_item_show_food) {
            @Override
            public void convert(RecyclerViewHolder holder, Food data, int position) {

                StringFormatUtil stringFormatUtil = new StringFormatUtil(mContext, data.getFoodName(), mKey, R.color.colorPrimaryDark).fillColor();

                holder.setText(R.id.tv_food_name, stringFormatUtil.getResult());

            };
        });


        recyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {

            @Override
            public void OnItemClickListener(View view, int position) {
                Intent intent = new Intent(mContext, FoodValueActivity.class);
                intent.putExtra("foodvalue", (Serializable) foodList.get(position));
                startActivityForResult(intent, 1);
            }
        });

    }

    /**
     * 改变搜索装填
     *
     * @param state 搜索key值
     */
    private void changeStates(int state) {
        switch (state) {
            case NO_TTHING:
                mNoLayout.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.INVISIBLE);
                break;

            case SHOW_DATA:
                mNoLayout.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                break;


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 1: // 点击了选择
                finish();
                break;

        }
    }
}
