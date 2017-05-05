package com.joyful.joyfulkitchen.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.view.RoundIndicatorView;


public class WeightOneFoodActivity extends AppCompatActivity {

    private Context mContext = this;
    private TextView tv_ke, tv_show_unit;
    // 中间的圆
    private RoundIndicatorView mRoundIndicatorView;

    private int index = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_one_food);
        // 添加 屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initView();

        addListener();

    }

    private void addListener() {
        // 转换单位
        tv_ke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new MaterialDialog.Builder(mContext)
                        .title(R.string.unit_title)
                        .items(R.array.unit)
                        .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                                tv_ke.setText(text);
                                //克, 两, 磅, 毫升, 安士
                                index = which;
                                return true;
                            }
                        })
//                        .widgetColor(Color.GRAY)  改按钮颜色
                        //widgetColor(), widgetColorRes(), widgetColorAttr(), and choiceWidgetColor()
                        .positiveText(R.string.unit_positive)
                        .show();
            }
        });
    }

    private void initView() {
        mRoundIndicatorView = (RoundIndicatorView) findViewById(R.id.riv_view);
        tv_ke = (TextView) findViewById(R.id.tv_ke);
        tv_show_unit = (TextView) findViewById(R.id.tv_show_unit);
    }
}
