package com.joyful.joyfulkitchen.volley;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.joyful.joyfulkitchen.adapter.RecyclerAdapter;
import com.joyful.joyfulkitchen.dao.GreenDaoManager;
import com.joyful.joyfulkitchen.dao.RecordDao;
import com.joyful.joyfulkitchen.model.Food;
import com.joyful.joyfulkitchen.model.Record;
import com.joyful.joyfulkitchen.model.SearchMeauList;
import com.joyful.joyfulkitchen.util.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * 用户注册 请求
 */

public class FoodByNameVolley {

    private String TAG = "DatePickerMainActivity";
    private Activity activity;

    private String foodName;
    private SearchMeauList.Matail matail;
    private Record record;
    private int size;
    public static int initSize = 0;

    public FoodByNameVolley(Activity activity,SearchMeauList.Matail matail, String foodName,Record record,int size) {
        this.activity = activity;
        this.matail = matail;
        this.size = size;
        this.record = record;
        try {
            this.foodName = URLEncoder.encode(foodName,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    // 请求列队对象
    RequestQueue mQueue = VolleySingleton.getInstance().getRequestQueue();

    public void doVolley() {

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.GET
                , "http://www.xicode.cn/one/food/getfood2?foodname=" + foodName
                , null
                , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i(TAG, "onResponse++++++++++++++++++=: " + response.toString());
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


                            if (matail.isComplete() && matail.getCount().indexOf("g") > 0) {
                                record.setTotalEnergy(record.getTotalEnergy() + food.getEnergy() / 100 * matail.getWeight());
                                record.setTotalWeight(record.getTotalWeight() + matail.getWeight());
                            }

                            initSize ++;
                            Log.i("aaa", "initSize =" + initSize + ",,,,TotalEnergy=" + record.getTotalEnergy() + ",,,,TotalWeight" + record.getTotalWeight());
                            if (initSize == size) {
                                RecordDao gecordDao = GreenDaoManager.getInstance().getSession().getRecordDao();
                                gecordDao.insert(record);

//                            Log.i("aaa", "insert = " + insert);
                                // 每一条记录上传到服务器
                                initSize = 0;
                            }


                            Log.i(TAG, food.getFoodName());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
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
