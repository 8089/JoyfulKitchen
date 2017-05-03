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
import com.joyful.joyfulkitchen.volley.ForgetPwdVolley;

/**
 * 忘记密码
 */
public class ForgetPwdActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context = this;
    private String  TAG = "ForgetPwdActivity";

    // 返回
    private ImageView back;
    // 去注册
    private TextView regist;
    // 新密码
    private EditText user_newpwd,user_renewpwd,user_oldemail;

    // 找回密码
    private Button user_forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_pwd_activity);
        initView();
        initData();
    }

    private void initView() {
        //返回上一个页面
        back = (ImageView) findViewById(R.id.back);
        //跳转注册页面
        regist = (TextView) findViewById(R.id.goregist);

        // 邮箱
        user_oldemail = (EditText) findViewById(R.id.user_oldemail);
        // 新密码
        user_newpwd = (EditText) findViewById(R.id.user_newpwd);
        user_renewpwd = (EditText) findViewById(R.id.user_renewpwd);

        //
        user_forget = (Button) findViewById(R.id.user_forget);
    }

    private void initData() {
        back.setOnClickListener(this);
        regist.setOnClickListener(this);
        // 登录
        user_forget.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                this.finish();
                break;
            case R.id.goregist:
                this.finish();
                Intent gore = new Intent(ForgetPwdActivity.this, RegistActivity.class);
                startActivity(gore);
                break;
            case R.id.user_forget:
                // 登录操作
                String email = user_oldemail.getText().toString();
                String pwd = user_newpwd.getText().toString();
                String repwd = user_renewpwd.getText().toString();
                if (!email.matches("[a-zA-Z0-9._-]+@[a-zA-Z0-9]+.[a-z]+") || email.length() < 1) {
                    ToastUtil.toastMessage((Activity) context, "电子邮箱输入有误!");
                } else if (pwd.length()<1||repwd.length()<1||!pwd.equalsIgnoreCase(repwd)) {
                    ToastUtil.toastMessage((Activity) context, "输入密码有误!");
                } else {
                    // 找回密码
                    new ForgetPwdVolley(this,email,repwd).doVolley();
                }
                break;
            default:
                break;
        }
    }
}
