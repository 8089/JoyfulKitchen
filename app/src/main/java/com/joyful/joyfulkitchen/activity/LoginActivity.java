package com.joyful.joyfulkitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.joyful.joyfulkitchen.R;

/**
 *    登录注册 页面
 */
public class LoginActivity extends AppCompatActivity{

    private ImageView back;

    private TextView regist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_a);

        //返回上一个页面
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //跳转注册页面
        regist = (TextView) findViewById(R.id.goregist);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gore = new Intent(LoginActivity.this, RegistActivity.class);
                startActivity(gore);
            }
        });





    }



}
