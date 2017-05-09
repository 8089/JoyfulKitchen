package com.joyful.joyfulkitchen.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joyful.joyfulkitchen.R;

/**
 * Created by Administrator on 2017/5/9.
 */

public class FoodItemContentView extends LinearLayout {

    // 营养元素
    private TextView nutrient;
    // 每100 克的 含量
    private TextView content;
    // 当前含量
    private TextView currentContent;

    // 营养元素文本
    private String str = "";

    public FoodItemContentView(Context context) {
        this(context, null);
    }

    public FoodItemContentView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FoodItemContentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //设置孩子排列的方向是水平的
        setOrientation(HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.item_food_content, this);
        initView();

        //读取自定义属性
        readAttr(context, attrs);


    }

    private void readAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FoodItemContentView);

        str = typedArray.getString(R.styleable.FoodItemContentView_nutrient);
        nutrient.setText(str);

        typedArray.recycle();
    }

    private void initView() {
         nutrient = (TextView) findViewById(R.id.nutrient);
         content = (TextView) findViewById(R.id.content);
         currentContent = (TextView) findViewById(R.id.currentContent);
    }

    // 设置营养元素的值
    public FoodItemContentView setNutrientText(CharSequence text){
        nutrient.setText(text);
        return this;
    }

    // 设置每100 克的含量
    public FoodItemContentView setContentText(CharSequence text) {
        content.setText(text);
        return this;
    }

    // 设置 当前含量的值
    public FoodItemContentView setCurrentContentText(CharSequence text) {
        currentContent.setText(text);
        return this;
    }

}
