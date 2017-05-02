package com.joyful.joyfulkitchen.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.date.CustomDatePicker;
import com.joyful.joyfulkitchen.model.User;
import com.joyful.joyfulkitchen.util.ToastUtil;
import com.joyful.joyfulkitchen.view.WheelView;
import com.joyful.joyfulkitchen.volley.RegiestVolley;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

/**
 *   个人信息 录入
 */

public class DatePickerMainActivity extends Activity implements OnClickListener {

    private Context context = this;
    private String TAG = "DatePickerMainActivity";
    // 生日
    private TextView currentDate;
    // 自定义时间
    private CustomDatePicker customDatePicker1;
    private TextView tv_target; // 目标
    private static final String[] PLANETS_target = new String[]{"减脂", "增肌", "维持体重"};
    private TextView tv_work_intensity; // 强度
    private static final String[] PLANETS_work_intensity = new String[]{"较轻体力劳动", "轻体力劳动", "中等体力劳动"};

    //返回上一页
    private ImageView back;
    private TextView finish;

    // 保存个人的信息
    private User user;

    // 上一个时间
    private long lastTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_b_main);

        Intent intent = this.getIntent();
        user = (User) intent.getSerializableExtra("userinfo");

        initView();
        initData();

        // 初始化日期选择
        initDatePicker();

    }

    private void initView() {
        //返回上一页
        back = (ImageView) findViewById(R.id.back);
        //完成
        finish = (TextView) findViewById(R.id.finish);
        // 日期
        currentDate = (TextView) findViewById(R.id.currentDate);

        tv_target = (TextView) findViewById(R.id.tv_target);

        tv_work_intensity = (TextView) findViewById(R.id.tv_work_intensity);
    }

    private void initData() {
        //返回
        back.setOnClickListener(this);
        // 完成
        finish.setOnClickListener(this);
        // 时间
        findViewById(R.id.selectDate).setOnClickListener(this);
        // 目标
        findViewById(R.id.show_dialog_target).setOnClickListener(this);
        // 工作强度
        findViewById(R.id.show_dialog_work_intensity).setOnClickListener(this);
        // 设置默认显示的目标
        tv_target.setText(PLANETS_target[0]);

        // 设置默认工作强度
        tv_work_intensity.setText(PLANETS_work_intensity[0]);


        // 默认保存 userinfo中
        user.setBirth(new Date());
        user.setPower(0);
        user.setTarget(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                this.finish();
                break;
            case R.id.finish:
                // 当距离 上一时间小于一秒时 ,不能够再次提交
                if (System.currentTimeMillis() - lastTime <= 1000) {
                    ToastUtil.toastMessage((Activity) context, "不能够重复的提交数据哦...");
                }
                Log.i(TAG, "initData: " + user.toString());
                new RegiestVolley(this, user).doVolley();

                break;
            case R.id.selectDate:
                // 日期格式为yyyy-MM-dd 生日
                customDatePicker1.show(currentDate.getText().toString());
                break;
            case R.id.show_dialog_target:
                // 目标
                WheelViewTarget();
                break;
            case R.id.show_dialog_work_intensity:
                WheelViewIntensity();
                break;
        }
    }

    /**
     * 生日
     */
    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        currentDate.setText(now.split(" ")[0]);

        customDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {

            @Override
            public void handle(String time) throws ParseException { // 回调接口，获得选中的时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date parse = sdf.parse(time);
                // 设置生日
                user.setBirth(parse);
                currentDate.setText(time.split(" ")[0]);
            }
        }, "1940-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(false); // 不显示时和分
        customDatePicker1.setIsLoop(false); // 不允许循环滚动

    }

    /**
     * 目标
     */
    private void WheelViewTarget() {
        View outerView = LayoutInflater.from(this).inflate(R.layout.register_b_b, null);
        WheelView wv = (WheelView) outerView.findViewById(R.id.wheel_view_wv_target);
        wv.setOffset(2);    // 2是显示五个 1=3
        wv.setItems(Arrays.asList(PLANETS_target));
//        wv.setSeletion(3);  // 默认弹框高亮度显示第四个
        wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d(TAG, "[Dialog]selectedIndex: " + selectedIndex + ", item: " + item);
                tv_target.setText(item);
                // 保存目标
                user.setTarget(selectedIndex - 2);

            }
        });

        new AlertDialog.Builder(this)
                .setTitle("你计划要达到的目标")
                .setView(outerView)
                .setPositiveButton("OK", null)
                .show();
    }

    /**
     * 工作强度
     */
    private void WheelViewIntensity() {
        View outerView = LayoutInflater.from(this).inflate(R.layout.register_b_c, null);
        WheelView wv = (WheelView) outerView.findViewById(R.id.wheel_view_wv_intensity);
        wv.setOffset(2);    // 2是显示五个 1=3
        wv.setItems(Arrays.asList(PLANETS_work_intensity));
//        wv.setSeletion(3);  // 默认弹框高亮度显示第四个
        wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d(TAG, "[Dialog]selectedIndex: " + selectedIndex + ", item: " + item);
                tv_work_intensity.setText(item);
                // 设置工作强度
                user.setPower(selectedIndex - 2);
            }
        });

        new AlertDialog.Builder(this)
                .setTitle("你现在的工作强度")
                .setView(outerView)
                .setPositiveButton("OK", null)
                .show();
    }


}
