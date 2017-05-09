
package com.joyful.joyfulkitchen.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.adapter.FoodTypeSelectAdapter;
import com.joyful.joyfulkitchen.model.Food;
import com.joyful.joyfulkitchen.model.FoodType;
import com.joyful.joyfulkitchen.view.XTabHost;
import com.joyful.joyfulkitchen.volley.FoodTypeSelectVolley;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 食材类型  查找
 */

public class FoodTypeSelectActivity extends AppCompatActivity {
    private Context context = this;
    public static final String TAG = "FoodTypeSelectActivity";
    private ImageView iv_back;
    private RecyclerView mRecyclerView;
    private XTabHost xTabHost;

    private LinearLayoutManager mLinearLayoutManager;

    private List<FoodType> foodTypeList = new ArrayList<FoodType>();

    private FoodTypeSelectAdapter mFoodTypeSelectAdapter;

    // 搜索框
    private TextView tv_food_select;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_type_select);




        initView();
        initData();
    }


    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);

        tv_food_select = (TextView) findViewById(R.id.tv_food_select);

        // 选项卡
//        xTabHost = (XTabHost) findViewById(R.id.tabhost1);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_food_search);
        mLinearLayoutManager = new LinearLayoutManager(FoodTypeSelectActivity.this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

    }

    private void initData() {

        tv_food_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String add = getIntent().getStringExtra("add");
                if(add !=null&& add.equalsIgnoreCase("add")){
//                    ToastUtil.toastMessage(FoodTypeSelectActivity.this,"setOnClickListener"+add);

                    Intent intent = new Intent(context,SearchFoodListActivity.class);
                    intent.putExtra("add","add");
                    startActivityForResult(intent,1);
                }else{
                    Intent intent = new Intent(context, SearchFoodListActivity.class);
                    startActivity(intent);

                }
            }
        });


        // 返回
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //  选项卡的 选择事件
        /*xTabHost.setOnSelectListener(new XTabHost.OnSelectListener() {
            @Override
            public void onSelect(int index, String text) {
                ToastUtils.showToast(FoodTypeSelectActivity.this, index + "," + text);
            }
        });*/

        mFoodTypeSelectAdapter = new FoodTypeSelectAdapter(this, foodTypeList);
        mRecyclerView.setAdapter(mFoodTypeSelectAdapter);
        // 网络请求数据
        new FoodTypeSelectVolley(context,foodTypeList, mFoodTypeSelectAdapter).doVolley();


        mFoodTypeSelectAdapter.setmOnItemClickListener(new FoodTypeSelectAdapter.OnItemClickListener() {

            // 点击每一个食材类型时
            @Override
            public void onItemClick(View view, int layoutPosition, int position) {
                String add = getIntent().getStringExtra("add");
                if(add !=null&& add.equalsIgnoreCase("add")){
//                    ToastUtil.toastMessage(FoodTypeSelectActivity.this,"FoodTypeSelectActivity"+add);

                    Intent intent = new Intent(context,FoodTypeSelectedActivity.class);
                    intent.putExtra("foodTypeid",foodTypeList.get(position).getFoodTypeId());
                    intent.putExtra("foodTypeName",foodTypeList.get(position).getTypeName());
                    intent.putExtra("add","add");

                    startActivityForResult(intent,1);
                }else{
                    Intent intent = new Intent(context,FoodTypeSelectedActivity.class);
                    intent.putExtra("foodTypeid",foodTypeList.get(position).getFoodTypeId());
                    intent.putExtra("foodTypeName",foodTypeList.get(position).getTypeName());
                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongClick(View view, int layoutPosition, int position) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
//                ToastUtil.toastMessage(this,"FoodTypeSelectActivity"+resultCode);

                Food food = (Food) data.getSerializableExtra("foodvalue");
                Log.i("ccc", "FoodTypeSelectActivity"+food);

                Intent intent= new Intent(context, UpdateFoodActivity.class);
                intent.putExtra("foodvalue", (Serializable)food);
                setResult(1,intent);
                finish();
                break;
            default:
                break;
        }
    }
}
