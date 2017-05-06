package com.joyful.joyfulkitchen.volley;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.joyful.joyfulkitchen.activity.LoginActivity;
import com.joyful.joyfulkitchen.adapter.RecyclerAdapter;
import com.joyful.joyfulkitchen.model.Food;
import com.joyful.joyfulkitchen.util.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户注册 请求
 */

public class FoodSearchVolley {

    private String TAG = "DatePickerMainActivity";
    private Activity activity;
    private String foodName;

    private List<Food> data;
    private RecyclerAdapter<Food> recyclerAdapter;

    private FrameLayout mNoLayout;
    // 列表
    private RecyclerView mRecyclerView;


    public FoodSearchVolley(Activity activity, String foodName, RecyclerAdapter<Food> recyclerAdapter, List<Food> data, FrameLayout mNoLayout, RecyclerView mRecyclerView) {
        this.activity = activity;
        this.recyclerAdapter = recyclerAdapter;
        this.mNoLayout = mNoLayout;
        this.mRecyclerView = mRecyclerView;
        this.data = data;
        try {
            this.foodName=URLEncoder.encode(foodName,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    // 请求列队对象
    RequestQueue mQueue = VolleySingleton.getInstance().getRequestQueue();

    public void doVolley() {
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.GET
                , "http://www.xicode.cn/one/food/getfood?foodname=" + foodName
                , null
                , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i(TAG, "onResponse: " + response.toString());
                data.clear();
                if (response.length() > 0) {
                    // 清除数据
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);

                            Long foodId = obj.getLong("id");
                            String foodName = obj.getString("foodName");
                            String food_img = obj.getString("foodImg");
                            float energy = Float.parseFloat(obj.getString("energy"));  //(Kcal 千卡)
                            float protein = Float.parseFloat(obj.getString("protein")); //   蛋白(g)
                            float fat = Float.parseFloat(obj.getString("fat")); // 脂肪(g)
                            float carbohydrate = Float.parseFloat(obj.getString("carbohydrate"));//  碳水化合物(g)
                            float fiber = Float.parseFloat(obj.getString("fiber"));   // - 纤维(g)
                            float cholesterol = Float.parseFloat(obj.getString("cholesterol"));//胆固醇(毫克)(g)
                            Date createTime = new Date(obj.getLong("createTime"));
//                            Date updateTime = new Date(obj.getLong("updateTime"));
//                            Log.i(TAG, "onResponse: "+updateTime);

                            Food food = new Food(foodId, foodName, food_img, energy, protein, fat, carbohydrate, fiber, cholesterol, new Long(0), createTime, new Date());
                            data.add(food);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.i(TAG, "onResponse: " + data.size());

                    if (data.size() > 0) {
                        mNoLayout.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                    }else{
                        mNoLayout.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.INVISIBLE);
                    }

                    recyclerAdapter.notifyDataSetChanged();
                } else {
                    ToastUtil.toastMessage(activity, "请求数据不存在!");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
            }
        }
        );
        mQueue.add(jsonObjectRequest);
    }
}
