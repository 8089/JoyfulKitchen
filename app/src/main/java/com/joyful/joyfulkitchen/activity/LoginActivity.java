package com.joyful.joyfulkitchen.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.util.ToastUtil;
import com.joyful.joyfulkitchen.volley.LoginVolley;

/**
 *    登录注册 页面
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Context context = this;
    private String  TAG = "LoginActivity";


    // 返回
    private ImageView back;
    // 去注册 忘记密码
    private TextView regist,to_usrForgetPwd;
    // 邮箱密码
    private EditText user_email,user_pwd;
    // 登录
    private Button user_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_a);

        initView();
        initData();
    }

    private void initView() {
        //返回上一个页面
        back = (ImageView) findViewById(R.id.back);
        //跳转注册页面
        regist = (TextView) findViewById(R.id.goregist);

        // 邮箱
        user_email = (EditText) findViewById(R.id.user_email);
        // 密码
        user_pwd = (EditText) findViewById(R.id.user_pwd);

        // 登录
        user_login = (Button) findViewById(R.id.user_login);

        // 忘记密码
        to_usrForgetPwd = (TextView) findViewById(R.id.to_usrForgetPwd);
    }

    private void initData() {
        back.setOnClickListener(this);
        regist.setOnClickListener(this);
        // 登录
        user_login.setOnClickListener(this);

        // 去忘记密码
        to_usrForgetPwd.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
     switch (v.getId()){
         case R.id.back:
             this.finish();
             break;
         case R.id.goregist:
             Intent gore = new Intent(LoginActivity.this, RegistActivity.class);
             startActivity(gore);
             finish();
             break;
         case R.id.user_login:
             // 登录操作
             String email = user_email.getText().toString();
             String pwd = user_pwd.getText().toString();
             if (!email.matches("[a-zA-Z0-9._-]+@[a-zA-Z0-9]+.[a-z]+") || email.length() < 1) {
                 ToastUtil.toastMessage((Activity) context, "电子邮箱输入有误!");
             } else if (pwd.length()<1) {
                 ToastUtil.toastMessage((Activity) context, "输入密码有误!");
             } else {
                    new LoginVolley(this,email,pwd).doVolley();
             }
             break;
         case R.id.to_usrForgetPwd:
             Intent intent = new Intent(LoginActivity.this, ForgetPwdActivity.class);
             startActivity(intent);
             finish();
             break;
         default:
             break;
     }
    }
}
