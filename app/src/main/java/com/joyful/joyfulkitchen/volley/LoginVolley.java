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
import com.joyful.joyfulkitchen.activity.MainActivity;
import com.joyful.joyfulkitchen.model.User;
import com.joyful.joyfulkitchen.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import static com.joyful.joyfulkitchen.base.BaseApplication.getmApplication;

/**
 * 用户注册 请求
 */

public class LoginVolley {

    private String TAG = "DatePickerMainActivity";
    private Activity activity;
    private String email;
    private String pwd;

    public LoginVolley(Activity activity, String email, String pwd) {
        this.activity = activity;
        this.email = email;
        this.pwd = pwd;
    }

    // 请求列队对象
    RequestQueue mQueue = VolleySingleton.getInstance().getRequestQueue();

    public void doVolley() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://www.xicode.cn/one/login?email=" + email + "&pwd=" + pwd + "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e(TAG, response);
                    JSONObject obj = new JSONObject(response);
                    String result = obj.getString("result");

                    if(result.equalsIgnoreCase("success")){
                        long userId = obj.getLong("id");
                        Log.i(TAG, "userId: "+userId);
                        String nickName = obj.getString("nickName");
                        Log.i(TAG, "nickName: "+nickName);

                        String phone = obj.getString("phone");
                        Log.i(TAG, "phone: "+phone);
                        String email = obj.getString("email");
                        Log.i(TAG, "email: "+email);
                        String img = obj.getString("img");
                        Log.i(TAG, "img: "+img);
                        int age = obj.getInt("age");
                        Log.i(TAG, "age: "+age);
                        int sex = obj.getInt("sex");
                        Log.i(TAG, "sex: "+sex);
//                        Date brith = new Date((Long) obj.get("brith"));
//                        Log.i(TAG, "brith: "+brith);
                        String city = obj.getString("city");
                        Log.i(TAG, "city: "+city);
                        String country = obj.getString("country");
                        Log.i(TAG, "country: "+country);
                        float weight = Float.parseFloat(obj.getString("weight"));
                        Log.i(TAG, "weight: "+weight);
                        float height = Float.parseFloat(obj.getString("height"));
                        Log.i(TAG, "height: "+height);
                        int target = obj.getInt("target");
                        Log.i(TAG, "target: "+target);
                        int power = obj.getInt("power");
                        Log.i(TAG, "power: "+power);
                        String token = obj.getString("token");
                        Log.i(TAG, "token: "+token);

                        Date createTime = new Date(obj.getLong("createTime"));
                        Log.i(TAG, "createTime: "+createTime);
                        User user = new User(userId, nickName, phone, email, pwd,
                                img, null, sex, city, country, weight,
                                height, target, power, 1, token, createTime,
                                null);

                        // 保存到application 中
                        getmApplication().setUser(user);

                        // 跳转到主页
                        activity.finish();
                        /*Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);*/

                    }else{
                        ToastUtil.toastMessage(activity,"登录失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtil.toastMessage(activity,"登录失败");
                }
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
        });
        // 加入到消息列队
        mQueue.add(stringRequest);
    }

}
