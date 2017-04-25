package com.joyful.joyfulkitchen.volley;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.joyful.joyfulkitchen.adapter.FoodTypesSelectedAdapter;
import com.joyful.joyfulkitchen.model.Food;
import com.joyful.joyfulkitchen.util.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * 加载所有食材的类型
 */
public class FoodTypeSelectedVolley {

    public static final String TAG = "FoodTypeSelectedVolley";

    private Context context;
    private List<Food> data;
    private FoodTypesSelectedAdapter adpter;
    private long foodTypeId;

    public FoodTypeSelectedVolley(Context context, List<Food> data, FoodTypesSelectedAdapter adpter, long foodTypeId) {
        super();
        this.context = context;
        this.data = data;
        this.adpter = adpter;
        this.foodTypeId = foodTypeId;
    }

    // 请求列队对象
    RequestQueue mQueue = VolleySingleton.getInstance().getRequestQueue();

    public void doVolley() {
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.GET
                , "http://www.xicode.cn/one/food/types/" + foodTypeId
                , null
                , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i(TAG, "onResponse: " + response.toString());
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);

                            // 还有一些 是否为空的处理.....

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
                    adpter.notifyDataSetChanged();
                } else {
                    ToastUtil.toastMessage((Activity) context, "请求数据不存在!");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
            }
        }
        );
        // 加入到消息列队
        mQueue.add(jsonObjectRequest);
    }

}
