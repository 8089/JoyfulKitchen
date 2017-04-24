package com.joyful.joyfulkitchen.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.adapter.FoodMaterialsAdapter;
import com.joyful.joyfulkitchen.adapter.FoodStepsAdapter;
import com.joyful.joyfulkitchen.model.SearchMeauList;
import com.joyful.joyfulkitchen.util.BitmapCache;
import com.joyful.joyfulkitchen.view.DampView;
import com.joyful.joyfulkitchen.view.ListViewUtility;

import java.util.ArrayList;
import java.util.List;

public class FoodDetailActivity extends AppCompatActivity {

    private static final String TAG = "FoodDetailActivity";
    private Context context = this;


    private ImageLoader mImageLoader;

    private DampView dampView;
    private NetworkImageView food_caitu;
    private TextView food_cainame;
    private TextView food_imtro;

    private ListView food_materials, food_steps;
    private FoodMaterialsAdapter foodMaterialsAdapter;
    private FoodStepsAdapter foodStepsAdapter;
    // 菜图
    private String caiUrl = "";
    // 名称
    private String cainame = "";
    // 描述
    private String miaosu = "";
    //食材
    private List<SearchMeauList.Matail> foodMaterialData = new ArrayList<SearchMeauList.Matail>();
    ;
    // 步骤
    private List<SearchMeauList.StepsBean> foodStepsData = new ArrayList<SearchMeauList.StepsBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_detail);

        Intent intent = this.getIntent();

        SearchMeauList searchMeauList = (SearchMeauList) intent.getSerializableExtra("searchMeauList");

        if (searchMeauList != null) {
            this.foodMaterialData.clear();
            this.foodStepsData.clear();
            this.caiUrl = "";
            this.cainame = "";
            this.miaosu = "";

            this.foodMaterialData = searchMeauList.getFoodMatail();
            this.foodStepsData = searchMeauList.getSteps();
            this.caiUrl = searchMeauList.getAlbums().get(0);
            this.cainame = searchMeauList.getTitle();
            this.miaosu = searchMeauList.getImtro();

            Log.i(TAG, "onCreate=====FoodDetailActivity" + this.foodMaterialData);
            Log.i(TAG, "onCreate=====FoodDetailActivity" +  this.foodStepsData );
        }
        // 消息列队 缓存图片
        RequestQueue queue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(queue, new BitmapCache());

        init();
    }

    private void init() {
        // 头部
        dampView = (DampView) findViewById(R.id.dampView);
        food_caitu = (NetworkImageView) findViewById(R.id.food_caitu);
        food_cainame = (TextView) findViewById(R.id.food_cainame);
        food_imtro = (TextView) findViewById(R.id.food_imtro);

        food_materials = (ListView) findViewById(R.id.food_materials);
        food_steps = (ListView) findViewById(R.id.food_steps);

        // 头部
        food_cainame.setText(cainame);
        food_imtro.setText(miaosu);
        food_caitu.setImageUrl(caiUrl, mImageLoader);
        dampView.setImageView(food_caitu);

        // 食材
        foodMaterialsAdapter = new FoodMaterialsAdapter(context, foodMaterialData);
        food_materials.setAdapter(foodMaterialsAdapter);

        // 步骤
        foodStepsAdapter = new FoodStepsAdapter(context, foodStepsData);
        food_steps.setAdapter(foodStepsAdapter);


        ListViewUtility.setListViewHeightBasedOnChildren(food_materials);
        ListViewUtility.setListViewHeightBasedOnChildren(food_steps);

    }
}
