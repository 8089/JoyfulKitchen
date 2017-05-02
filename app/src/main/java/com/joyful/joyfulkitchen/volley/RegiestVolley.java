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
import com.joyful.joyfulkitchen.model.User;
import com.joyful.joyfulkitchen.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户注册 请求
 */

public class RegiestVolley {

    private String TAG = "DatePickerMainActivity";
    private Activity activity;
    private User user;

    public RegiestVolley(Activity activity, User user) {
        this.activity = activity;
        this.user = user;
    }

    // 请求列队对象
    RequestQueue mQueue = VolleySingleton.getInstance().getRequestQueue();

    public void doVolley() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.xicode.cn/one/regist", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, response);
                if (response.equalsIgnoreCase("toactive")) {
                    ToastUtil.toastMessage(activity, "快点去您的邮箱中激活账号..");
                    activity.finish();
                    Intent intent = new Intent(activity, LoginActivity.class);
                    activity.startActivity(intent);
                } else{
                    ToastUtil.toastMessage(activity, "注册失败,请重新注册");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtil.toastMessage(activity, "注册失败,请重新注册");
                activity.finish();
                Intent intent = new Intent(activity, RegistActivity.class);
                activity.startActivity(intent);
                Log.e(TAG, error.getMessage(), error);
            }
        }) {
            // 设置请求参数
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
               /* private long id;
                private String nickName;
                private String phone;
                private String email;
                private String pwd;
                private String img;
                private int age;
                private int sex;
                private String city;
                private String country;
                private float weight;
                private float height;
                private int target;
                private int power;
                private int active;
                private String token;*/
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                map.put("email", user.getEmail());
                map.put("pwd", user.getPwd());
                map.put("brith", sdf.format(user.getBirth()));
                map.put("sex", String.valueOf(user.getSex()));
                map.put("weight", String.valueOf(user.getWeight()));
                map.put("height", String.valueOf(user.getHeigth()));
                map.put("target", String.valueOf(user.getTarget()));
                map.put("power", String.valueOf(user.getPower()));
                // 各种请求参数
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
