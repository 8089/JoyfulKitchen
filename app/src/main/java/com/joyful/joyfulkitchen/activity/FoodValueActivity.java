package com.joyful.joyfulkitchen.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.model.Food;

import java.text.DecimalFormat;

/**
 *    食物 营养 价值页面
 */
public class FoodValueActivity extends AppCompatActivity implements View.OnClickListener{
    private Context context = this;
    private Food food;
    private String TAG ="FoodValueActivity";

    private ImageView back;

    private SimpleDraweeView food_img;

    private TextView  foodName,energy,protein,fat,carbohydrate,fiber,cholesterol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_value);

        Intent intent = this.getIntent();

        food = (Food) intent.getSerializableExtra("foodvalue");
        Log.i(TAG, "onCreate: "+food.toString());

        initview();
        
        initData();

    }

    private void initview() {
        back = (ImageView) findViewById(R.id.back);
        food_img = (SimpleDraweeView) findViewById(R.id.food_img);
        foodName = (TextView) findViewById(R.id.foodName);
        energy =(TextView)  findViewById(R.id.energy);
        protein= (TextView) findViewById(R.id.protein);
        fat= (TextView) findViewById(R.id.fat);
        carbohydrate= (TextView) findViewById(R.id.carbohydrate);
        fiber= (TextView) findViewById(R.id.fiber);
        cholesterol= (TextView) findViewById(R.id.cholesterol);
    }

    private void initData() {

        back.setOnClickListener(this);

        Uri uri = Uri.parse(food.getFoodImg());
        food_img.setImageURI(uri);
        foodName.setText(food.getFoodName());

        // 小数点格式化, 保留金两位
        DecimalFormat decimalFormat=new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.

        energy.setText(decimalFormat.format(food.getEnergy()));
        protein.setText(decimalFormat.format(food.getProtein()));
        fat.setText(decimalFormat.format(food.getFat()));
        carbohydrate.setText(decimalFormat.format(food.getCarbohydrate()));
        fiber.setText(decimalFormat.format(food.getFiber()));
        cholesterol.setText(decimalFormat.format(food.getCholesterol()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                this.finish();
                break;
            default:
                break;
        }
    }
}
