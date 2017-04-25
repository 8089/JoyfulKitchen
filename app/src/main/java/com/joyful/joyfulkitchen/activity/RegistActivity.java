package com.joyful.joyfulkitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.joyful.joyfulkitchen.R;

/**
 * 注册页面
 */
public class RegistActivity extends AppCompatActivity{

    private ImageView back;

    private TextView  login;
    private TextView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_a_a_main);

        //返回上一个页面
        back = (ImageView) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //跳转到登录页面

        login = (TextView) findViewById(R.id.gologin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent golo = new Intent(RegistActivity.this, LoginActivity.class);
                startActivity(golo);
            }
        });


        //点击注册后  填写注册信息
        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  goinfo = new Intent(RegistActivity.this, RuleViewMainActivity.class);
                startActivity(goinfo);
            }
        });


    }



}
