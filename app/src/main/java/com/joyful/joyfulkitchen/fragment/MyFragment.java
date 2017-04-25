package com.joyful.joyfulkitchen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.activity.LoginActivity;
import com.joyful.joyfulkitchen.activity.MyselfActivity;
import com.joyful.joyfulkitchen.activity.UpdateActivity;

import java.util.ArrayList;
import java.util.List;

// my  我的个人信息页面
public class MyFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_Name = "ARG_Name";
    private static final String TAG = "RoundImage";

    private String mParam1;

    //修改个人信息
    private ImageView xiugai;

    //进入个人空间
    private TextView yourname;

    private List<String> mylist;


    // 登录注册
    private TextView login;



    // listview
    private ListView mylv;
    public static MyFragment newInstance(String param1) {
        MyFragment fragment = new MyFragment();
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
        View view = inflater.inflate(R.layout.fragment_my, container, false);

        //登录  注册
        login = (TextView) view.findViewById(R.id.loginning);
        //点击之后
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lo = new Intent(getContext(), LoginActivity.class);
                startActivity(lo);
            }
        });


        //修改个人资料
        xiugai = (ImageView) view.findViewById(R.id.xiugai);
        //点击事件
        xiugai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), UpdateActivity.class);
                startActivity(i);
            }
        });

        //进入个人空间
       /* yourname = (TextView) view.findViewById(R.id.yourname);
        yourname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent my = new Intent(getContext(), MyselfActivity.class);
                startActivity(my);
            }
        });*/


        // listview  模块
        mylv  = (ListView) view.findViewById(R.id.MyListView);

        //生成动态数组，并且转载数据
        mylist = new ArrayList<String>();

        mylist.add("我的食谱");
        mylist.add("我的圈子");
        mylist.add("我的收藏");
        mylist.add("我的下载");

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.my_listview, mylist);
//        mylv.setAdapter(adapter);
        mylv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mylist.size();
            }

            @Override
            public String getItem(int position) {
                return mylist.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_listview, null);
                TextView viewById = (TextView) view1.findViewById(R.id.ItemTitle);
                viewById.setText(getItem(position));

                return view1;
            }
        });
        return  view;

    }


    }


