package com.joyful.joyfulkitchen.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.adapter.FoodTypesSelectedAdapter;
import com.joyful.joyfulkitchen.model.Food;
import com.joyful.joyfulkitchen.volley.FoodTypeSelectedVolley;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FoodTypeSelectedActivity extends AppCompatActivity{
    private Context context = this;
    public static final String TAG = "FoodTypeSeleted";

    private TextView foodtype_name;
    private ListView foodNameslistView;
    private List<Food> foodlist = new ArrayList<Food>();
    private FoodTypesSelectedAdapter adapter;


    private long foodTypeid;
    private String foodTypeName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_type_selected);

        initView();
        initData();
    }

    // 初始化view
    private void initView() {
        foodtype_name = (TextView) findViewById(R.id.foodtype_name);
        foodNameslistView = (ListView) findViewById(R.id.foodNameslistView);
    }

    // 处理数据
    private void initData() {

        Intent intent = this.getIntent();
        foodTypeid = intent.getLongExtra("foodTypeid", -1);
        foodTypeName = intent.getStringExtra("foodTypeName");

        // 标题名称
        foodtype_name.setText(foodTypeName);

        // 获取数据
        adapter = new FoodTypesSelectedAdapter(context,foodlist);
        foodNameslistView.setAdapter(adapter);
        new FoodTypeSelectedVolley(context,foodlist,adapter,foodTypeid).doVolley();

        foodNameslistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String add = getIntent().getStringExtra("add");
                if(add !=null&& add.equalsIgnoreCase("add")){
//                    ToastUtil.toastMessage(FoodTypeSelectedActivity.this, "FoodTypeSelectActivity" + add);

                    Intent intent = new Intent(context,FoodValueActivity.class);
                    intent.putExtra("foodvalue", (Serializable) foodlist.get(position));
                    intent.putExtra("add","add");
                    startActivityForResult(intent,1);
                }else{
                    Intent intent = new Intent(context,FoodValueActivity.class);
                    intent.putExtra("foodvalue", (Serializable) foodlist.get(position));
                    startActivity(intent);

                }
            }
        });
   }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                Bundle MarsBuddle = data.getExtras();
                Food food= (Food) MarsBuddle.getSerializable("foodvalue");
                Log.i("ccc", "FoodTypeSelectedActivity"+food);

                Intent intent= new Intent(context, FoodTypeSelectActivity.class);
                intent.putExtra("foodvalue", (Serializable)food);

                Log.i("ccc", "onActivityResult: FoodTypeSelectedActivity"+requestCode);
                setResult(1,intent);
                finish();
                break;
            default:
                break;
        }
    }
}
