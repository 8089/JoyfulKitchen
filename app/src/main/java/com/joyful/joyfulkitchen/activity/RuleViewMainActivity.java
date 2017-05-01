package com.joyful.joyfulkitchen.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.model.User;
import com.joyful.joyfulkitchen.view.RulerView;


/**
 *  性别 身高 体重 填写页面
 */

public class RuleViewMainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "RuleViewMainActivity";
    private Context context =this;

    private ImageView back;

    private CheckBox btn_register_info_sex;
    private TextView tv_register_info_height_value, tv_register_info_weight_value;
    private RulerView ruler_height,ruler_weight;   //身高的view 体重的view
    private Button gonext;
    private User user ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_a_mian);

        Intent intent = this.getIntent();
        user = (User) intent.getSerializableExtra("userinfo");

        Log.i(TAG, "onCreate: "+user.getEmail()+user.getPwd());

        initView();
        initData();

    }

    // 初始化view
    private void initView() {
        //返回上一个页面
        back = (ImageView) findViewById(R.id.back);
        // 下一步
        gonext = (Button) findViewById(R.id.gonext);

        // 性别
        btn_register_info_sex = (CheckBox) findViewById(R.id.btn_register_info_sex);

        // 身高和体重
        tv_register_info_height_value = (TextView) findViewById(R.id.tv_register_info_height_value);
        tv_register_info_weight_value = (TextView) findViewById(R.id.tv_register_info_weight_value);

        ruler_height = (RulerView) findViewById(R.id.ruler_height);
        ruler_weight = (RulerView) findViewById(R.id.ruler_weight);
    }

    // 初始化数据
    private void initData() {
        // 返回
        back.setOnClickListener(this);
        // 下一步
        gonext.setOnClickListener(this);

        // 判断性别
        btn_register_info_sex.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    user.setSex(1);
                }else{
                    user.setSex(0);
                }
            }
        });

        // 身高和体重


        // 设置默认的身高体重
        user.setHeigth(Double.parseDouble(tv_register_info_height_value.getText().toString()));
        user.setWeight(Double.parseDouble(tv_register_info_weight_value.getText().toString()));

        ruler_height.setOnValueChangeListener(new RulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                tv_register_info_height_value.setText(value + "");
                // 设置身高
                user.setHeigth(value);
            }
        });

        ruler_weight.setOnValueChangeListener(new RulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                tv_register_info_weight_value.setText(value + "");
                // 设置体重
                user.setWeight(value);
            }
        });
        ruler_height.setValue(165, 80, 250, 1);
        ruler_weight.setValue(55, 20, 200, 0.1f);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                this.finish();
                break;
            case R.id.gonext:

                Log.i(TAG, "onClick: "+user.getSex());

                Intent intent = new Intent(RuleViewMainActivity.this, DatePickerMainActivity.class);
                intent.putExtra("userinfo",user);
                startActivity(intent);
                break;
            default:
                break;
        }

    }
}