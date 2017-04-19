package com.joyful.joyfulkitchen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.activity.FoodSelectActivity;
import com.joyful.joyfulkitchen.util.ToastUtils;
import com.joyful.joyfulkitchen.view.RoundIndicatorView;

import org.joda.time.DateTime;

import noman.weekcalendar.WeekCalendar;
import noman.weekcalendar.listener.OnDateClickListener;

// 健康饮食
public class HealthyFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_Name = "ARG_Name";

    private String mParam1;


    private RoundIndicatorView roundIndicatorView;
    private EditText editText;
    private TextView tv_food_select;
    private WeekCalendar weekCalendar;

    public static HealthyFragment newInstance(String param1) {
        HealthyFragment fragment = new HealthyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_Name, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_Name);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_healthy, container, false);

        initView(view);
        setOnListeners();

        return view;
    }

    private void initView(View view) {
        tv_food_select = (TextView) view.findViewById(R.id.tv_food_select);
        weekCalendar = (WeekCalendar) view.findViewById(R.id.weekCalendar);
        editText = (EditText) view.findViewById(R.id.edit);
        roundIndicatorView = (RoundIndicatorView) view.findViewById(R.id.riv_view);
    }

    private void setOnListeners() {
        tv_food_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FoodSelectActivity.class);
                startActivityForResult(intent, 1);
            }
        });


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ToastUtils.showToast(getContext(), "beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    if(s.length() >0 ) {
                        int a = Integer.valueOf(s.toString());
                        roundIndicatorView.setCurrentNumAnim(a);
                    }else{
                        roundIndicatorView.setCurrentNumAnim(0);
                    }
                } catch (Exception e){
                    ToastUtils.showToast(getContext(), "数字太大了");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        weekCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(DateTime dateTime) {
                ToastUtils.showToast(getContext(), "您选择的日期是:" + dateTime.toLocalDate());
            }
        });




    }


}
