package com.joyful.joyfulkitchen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.activity.DatePickerMainActivity;
import com.joyful.joyfulkitchen.activity.RuleViewMainActivity;
import com.joyful.joyfulkitchen.view.RoundIndicatorView;

// 健康饮食
public class HealthyFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_Name = "ARG_Name";

    private String mParam1;


    private RoundIndicatorView roundIndicatorView;
    private EditText editText;
    private Button button;


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
//expected class or package
        view.findViewById(R.id.shengao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RuleViewMainActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.mubiao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DatePickerMainActivity.class);
                startActivity(intent);
            }
        });


        roundIndicatorView = (RoundIndicatorView) view.findViewById(R.id.my_view);
        editText = (EditText) view.findViewById(R.id.edit);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    Log.i("aaaaa","aaaaaaaaaaa="+ s);
                    if(s.length() >0 ) {
                        int a = Integer.valueOf(s.toString());
                        roundIndicatorView.setCurrentNumAnim(a);
                    }
                } catch (Exception e){
                    Toast.makeText(getContext(), "数字太大了", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        return view;
    }


}
