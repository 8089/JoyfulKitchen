package com.joyful.joyfulkitchen.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.model.User;
import com.joyful.joyfulkitchen.util.ToastUtil;

/**
 *  注册页面
 */
public class RegistActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context = this;

    private ImageView back;

    private TextView login;
    private TextView register;

    private EditText user_email, user_pwd, user_repwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_a_a_main);
        initView();
        initData();
    }

    // 初始化view
    private void initView() {
        //返回上一个页面
        back = (ImageView) findViewById(R.id.back);
        //跳转到登录页面
        login = (TextView) findViewById(R.id.gologin);
        //点击注册后  填写注册信息
        register = (TextView) findViewById(R.id.register);

        user_email = (EditText) findViewById(R.id.user_email);
        user_pwd = (EditText) findViewById(R.id.user_pwd);
        user_repwd = (EditText) findViewById(R.id.user_repwd);
    }

    // 初始化数据
    private void initData() {
        // 返回
        back.setOnClickListener(this);
        // 去登录
        login.setOnClickListener(this);
        // 下一步注册
        register.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                this.finish();
                break;
            case R.id.gologin:
                Intent golo = new Intent(RegistActivity.this, LoginActivity.class);
                startActivity(golo);
                this.finish();
            case R.id.register:
                String email = user_email.getText().toString();
                String pwd = user_pwd.getText().toString();
                String repwd = user_repwd.getText().toString();
                if (!email.matches("[a-zA-Z0-9._-]+@[a-zA-Z0-9]+.[a-z]+") || email.length() < 1) {
                    ToastUtil.toastMessage((Activity) context, "电子邮箱输入有误!");
                } else if (!pwd.equalsIgnoreCase(repwd) || pwd.length()<1 || repwd.length()<1) {
                    ToastUtil.toastMessage((Activity) context, "两次输入密码不一致!");
                } else {
                    User user= new User();
                    user.setEmail(email);
                    user.setPwd(repwd);
                    Intent intent = new Intent(RegistActivity.this, RuleViewMainActivity.class);
                    intent.putExtra("userinfo",user);
                    startActivity(intent);
                    this.finish();
                }

            default:
                break;
        }

    }



}
