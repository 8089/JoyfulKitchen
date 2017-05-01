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
import com.joyful.joyfulkitchen.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户注册 请求
 */

public class LoginVolley {

    private String TAG = "DatePickerMainActivity";
    private Activity activity;
    private String email;
    private String pwd;

    public LoginVolley(Activity activity,String email,String pwd) {
        this.activity = activity;
        this.email = email;
        this.pwd = pwd;
    }

    // 请求列队对象
    RequestQueue mQueue = VolleySingleton.getInstance().getRequestQueue();

    public void doVolley() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.xicode.cn/one/login", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtil.toastMessage(activity, "登录失败,请从新登录");
                activity.finish();
                Intent intent = new Intent(activity, LoginActivity.class);
                activity.startActivity(intent);
                Log.e(TAG, error.getMessage(), error);
            }
        }) {
            // 设置请求参数
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

//   User{userId=null, nickName='null', phone='null', email='oooo@163.com', pwd='www', img='null', birth=Thu Jan 01 00:00:00 GMT+08:00 2015, sex=1, city='null', country='null', weight=0.0, heigth=0.0, target=4, power=4, active=0, token='null', createTime=null, updateTime=null}

                map.put("email", email);
                map.put("pwd", pwd);

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
