package com.joyful.joyfulkitchen.volley;

import android.app.Activity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.joyful.joyfulkitchen.model.Record;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户注册 请求
 */

public class RecordAddVolley {

    private String TAG = "oooo";
    private Activity activity;
    private Record record;

    public RecordAddVolley(Activity activity,Record record) {
        this.activity = activity;
        this.record = record;
    }

    // 请求列队对象
    RequestQueue mQueue = VolleySingleton.getInstance().getRequestQueue();

    public void doVolley() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.xicode.cn/one/record/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("success")){
                    Log.i(TAG, "+++++++=======+++"+response+"添加记录到服务器中成功");
                }else{
                    Log.i(TAG, "+++++++=======+++"+response+"添加记录到服务器中失败");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "onErrorResponse: "+ error.getMessage()+"添加数据失败");
            }
        }) {
            // 设置请求参数
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                map.put("total_energy", String.valueOf(record.getTotalEnergy()));
                map.put("meau_name",record.getMeauName());
                map.put("totalWeight", String.valueOf(record.getTotalWeight()));
                map.put("createTime", sdf.format(record.getCreateTime()));
                return map;
            }

            //设置请求头部
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/x-www-form-urlencoded");
                return map;
            }
        };
        // 加入到消息列队
        mQueue.add(stringRequest);
    }

}
