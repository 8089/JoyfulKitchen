package com.joyful.joyfulkitchen.volley;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.joyful.joyfulkitchen.adapter.FoodTypeSelectAdapter;
import com.joyful.joyfulkitchen.model.FoodType;
import com.joyful.joyfulkitchen.util.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * 加载所有食材的类型
 */
public class FoodTypeSelectVolley {

    public static final String TAG = "FoodTypeSelectVolley";

    private Context context;
    private List<FoodType> data;
    private FoodTypeSelectAdapter adpter;

    public FoodTypeSelectVolley(Context context, List<FoodType> data, FoodTypeSelectAdapter adpter) {
        super();
        this.context = context;
        this.data = data;
        this.adpter = adpter;
    }

    // 请求列队对象
    RequestQueue mQueue = VolleySingleton.getInstance().getRequestQueue();

    public void doVolley() {
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.GET
                , "http://www.xicode.cn/one/food/types/"
                , null
                , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i(TAG, "onResponse: " + response.toString());
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            Long foodTypeId = obj.getLong("id");
                            String typeName = obj.getString("typeName");
                            String foodTypeImg = obj.getString("foodTypeImg");
                            Date createTime = new Date(obj.getLong("createTime"));
                            Date updateTime = new Date(obj.getLong("createTime"));
                            FoodType type = new FoodType(foodTypeId, typeName, foodTypeImg, createTime, updateTime);
                            data.add(type);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
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
