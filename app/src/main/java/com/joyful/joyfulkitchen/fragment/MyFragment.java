package com.joyful.joyfulkitchen.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.joyful.joyfulkitchen.base.BaseApplication;
import com.joyful.joyfulkitchen.model.User;

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

    private User user = null;

    // 登录注册
    private TextView login;

    // 头像名称
    private String imageName = "head.jpg";


    // listview
    private ListView mylv;

    private ImageView myFace;

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

        user = BaseApplication.getmApplication().getUser();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);



        initView(view);




        //登录  注册
        login = (TextView) view.findViewById(R.id.loginning);
        //点击之后
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user != null) {
                    login.setText(user.getNickName() == null ? user.getNickName() : user.getEmail());

                }else {
                    login.setText(R.string.long_or_regist);
                    Intent lo = new Intent(getContext(), LoginActivity.class);
                    startActivity(lo);
                }
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

    private void initView(View view) {

        myFace = (ImageView) view.findViewById(R.id.myFace);

    }


    @Override
    public void onResume() {
        super.onResume();
        user = BaseApplication.getmApplication().getUser();
        Log.i("aaa", user == null ? "没有数据" : user.toString());
        if (user != null) {
            login.setText(user.getNickName() == null ? user.getNickName() : user.getEmail());
            // 判断头像在不在
            Bitmap bt = BitmapFactory.decodeFile(getActivity().getFilesDir() + UpdateActivity.path + "head.jpg");
            if(bt != null){
                @SuppressWarnings("deprecation")
                Drawable drawable = new BitmapDrawable(bt);//转换成drawable
                myFace.setImageDrawable(drawable);

            }else{
                /**
                 *	如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
                 *
                 */
                myFace.setImageDrawable(getResources().getDrawable(R.mipmap.me));
                login.setText(R.string.long_or_regist);
            }

        }
    }
}


