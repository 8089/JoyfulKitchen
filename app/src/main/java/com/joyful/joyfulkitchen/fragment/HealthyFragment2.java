package com.joyful.joyfulkitchen.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.util.ToastUtils;
import com.joyful.joyfulkitchen.view.RoundIndicatorView;


// 健康饮食
public class HealthyFragment2 extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_Name = "ARG_Name";

    private String mParam1;

    private RoundIndicatorView mRoundIndicatorView;
    private TextView tv_ke, tv_show_unit;
    private Toolbar toolbar;
    private String unit = "g";

    public static HealthyFragment2 newInstance(String param1) {
        HealthyFragment2 fragment = new HealthyFragment2();
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
        View view = inflater.inflate(R.layout.fragment_healthy2, container, false);

        initView(view);
        setOnListeners();

        return view;
    }

    private void initView(View view) {
        setHasOptionsMenu(true);
        toolbar = (Toolbar) view.findViewById(R.id.home_toolbar);
        toolbar.setTitle(null);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_search:
                        ToastUtils.showToast(getContext(), "action_search");
                        break;
                    case R.id.action_settings:
                        ToastUtils.showToast(getContext(), "action_settings");
                        break;
                }
                return true;
            }
        });

        mRoundIndicatorView = (RoundIndicatorView) view.findViewById(R.id.riv_view);
        tv_ke = (TextView) view.findViewById(R.id.tv_ke);
        tv_show_unit = (TextView) view.findViewById(R.id.tv_show_unit);


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.home_menu, menu);
    }

    private void setOnListeners() {
        tv_show_unit.setText("0g");

        tv_ke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getContext())
                        .title(R.string.unit_title)
                        .items(R.array.unit)
                        .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                                tv_ke.setText(text);
                                onSwitchUnit(which);

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

    private void onSwitchUnit(int which) {
        int size = 1000;
        //克,两,磅,毫升,安士
        switch (which){
            case 0:
                size = 1000;
                mRoundIndicatorView.setCurrentNumAnim(size);
                tv_show_unit.setText(size + "克");
                break;
            case 1:
                size = 3500;
                mRoundIndicatorView.setCurrentNumAnim(size);
                tv_show_unit.setText(size + "两");
                break;
            case 2:
                size = 1300;
                mRoundIndicatorView.setCurrentNumAnim(size);
                tv_show_unit.setText(size + "磅");
                break;
            case 3:
                size = 2800;
                mRoundIndicatorView.setCurrentNumAnim(size);
                tv_show_unit.setText(size + "毫升");
                break;
            case 4:
                size = 1500;
                mRoundIndicatorView.setCurrentNumAnim(size);
                tv_show_unit.setText(size + "安士");
                break;

        }
    }


}
