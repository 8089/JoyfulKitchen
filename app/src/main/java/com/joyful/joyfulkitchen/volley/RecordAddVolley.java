package com.joyful.joyfulkitchen.volley;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.joyful.joyfulkitchen.activity.LoginActivity;
import com.joyful.joyfulkitchen.activity.RegistActivity;
import com.joyful.joyfulkitchen.model.Record;
import com.joyful.joyfulkitchen.model.User;
import com.joyful.joyfulkitchen.util.ToastUtil;

import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户注册 请求
 */

public class RecordAddVolley {

    private String TAG = "DatePickerMainActivity";
    private Activity activity;
    private Record record;

    public RecordAddVolley(Activity activity,Record record) {
        this.activity = activity;
        this.record = record;
    }

    // 请求列队对象
    RequestQueue mQueue = VolleySingleton.getInstance().getRequestQueue();

    public void doVolley() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.xicode.cn/one/record", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtil.toastMessage(activity, "添加数据失败");
            }
        }) {
            // 设置请求参数
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();


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
