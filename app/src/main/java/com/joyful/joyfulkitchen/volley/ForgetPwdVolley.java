package com.joyful.joyfulkitchen.volley;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.joyful.joyfulkitchen.activity.LoginActivity;
import com.joyful.joyfulkitchen.util.ToastUtil;

/**
 * 用户注册 请求
 */

public class ForgetPwdVolley {

    private String TAG = "DatePickerMainActivity";
    private Activity activity;
    private String email;
    private String pwd;

    public ForgetPwdVolley(Activity activity, String email, String pwd) {
        this.activity = activity;
        this.email = email;
        this.pwd = pwd;
    }

    // 请求列队对象
    RequestQueue mQueue = VolleySingleton.getInstance().getRequestQueue();

    public void doVolley() {

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, "http://www.xicode.cn/one/user/forgetpwd?oldemail="+email+"&newpwd="+pwd, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e(TAG, response);

                if(response.equalsIgnoreCase("success")){
                    ToastUtil.toastMessage(activity, "修改密码成功!");
                    activity.finish();
                    Intent intent = new Intent(activity, LoginActivity.class);
                    activity.startActivity(intent);
                }else{
                    ToastUtil.toastMessage(activity, "修改密码失败!");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtil.toastMessage(activity, "找回密码失败..");
                Log.e(TAG, error.getMessage(), error);
            }
        });
        // 加入到消息列队
        mQueue.add(stringRequest);
    }

}
