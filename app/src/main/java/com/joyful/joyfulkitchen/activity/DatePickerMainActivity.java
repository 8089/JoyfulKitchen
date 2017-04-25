package com.joyful.joyfulkitchen.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.joyful.joyfulkitchen.date.CustomDatePicker;
import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.view.WheelView;
import com.joyful.joyfulkitchen.view.XTabHost;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2017/3/29.
 */

public class DatePickerMainActivity extends Activity implements OnClickListener {

    private TextView currentDate;
    private CustomDatePicker customDatePicker1;
    // ===============
    private String TAG_target = DatePickerMainActivity.class.getSimpleName();
    private static final String[] PLANETS_target = new String[]{"减脂", "增肌", "维持体重"};
    private TextView tv_target;
    private static final String[] PLANETS_work_intensity = new String[]{"较轻体力劳动", "轻体力劳动", "中等体力劳动"};
    private TextView tv_work_intensity;

    //返回上一页
    private ImageView back;
    private TextView finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_b_main);

        //返回上一页
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //完成
        finish = (TextView) findViewById(R.id.finish);
        finish.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gofinish  = new Intent(DatePickerMainActivity.this, MainActivity.class);
                startActivity(gofinish);
            }
        });

        findViewById(R.id.selectDate).setOnClickListener(this);
        currentDate = (TextView) findViewById(R.id.currentDate);

        //===============
        findViewById(R.id.show_dialog_target).setOnClickListener(this);
        tv_target = (TextView) findViewById(R.id.tv_target);
        tv_target.setText(PLANETS_target[0]);
        //===============
        findViewById(R.id.show_dialog_work_intensity).setOnClickListener(this);
        tv_work_intensity = (TextView) findViewById(R.id.tv_work_intensity);
        tv_work_intensity.setText(PLANETS_work_intensity[0]);
        //===============


        initDatePicker();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selectDate:
                // 日期格式为yyyy-MM-dd
                customDatePicker1.show(currentDate.getText().toString());
                break;
            case R.id.show_dialog_target:
                WheelViewTarget();
                break;
            case R.id.show_dialog_work_intensity:
                WheelViewIntensity();
                break;
        }
    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        currentDate.setText(now.split(" ")[0]);

        customDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                currentDate.setText(time.split(" ")[0]);
            }
        }, "1940-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(false); // 不显示时和分
        customDatePicker1.setIsLoop(false); // 不允许循环滚动

    }

    private void WheelViewTarget() {
        View outerView = LayoutInflater.from(this).inflate(R.layout.register_b_b, null);
        WheelView wv = (WheelView) outerView.findViewById(R.id.wheel_view_wv_target);
        wv.setOffset(2);    // 2是显示五个 1=3
        wv.setItems(Arrays.asList(PLANETS_target));
//        wv.setSeletion(3);  // 默认弹框高亮度显示第四个
        wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d(TAG_target, "[Dialog]selectedIndex: " + selectedIndex + ", item: " + item);
                tv_target.setText(item);
            }
        });

        new AlertDialog.Builder(this)
                .setTitle("你计划要达到的目标")
                .setView(outerView)
                .setPositiveButton("OK", null)
                .show();
    }

    private void WheelViewIntensity() {
        View outerView = LayoutInflater.from(this).inflate(R.layout.register_b_c, null);
        WheelView wv = (WheelView) outerView.findViewById(R.id.wheel_view_wv_intensity);
        wv.setOffset(2);    // 2是显示五个 1=3
        wv.setItems(Arrays.asList(PLANETS_work_intensity));
//        wv.setSeletion(3);  // 默认弹框高亮度显示第四个
        wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d(TAG_target, "[Dialog]selectedIndex: " + selectedIndex + ", item: " + item);
                tv_work_intensity.setText(item);
            }
        });

        new AlertDialog.Builder(this)
                .setTitle("你现在的工作强度")
                .setView(outerView)
                .setPositiveButton("OK", null)
                .show();
    }


}
