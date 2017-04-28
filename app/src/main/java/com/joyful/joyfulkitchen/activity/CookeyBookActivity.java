package com.joyful.joyfulkitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.adapter.MeauTypeLeftAdapter;
import com.joyful.joyfulkitchen.adapter.MeauTypeRightAdapter;
import com.joyful.joyfulkitchen.asynctask.MeauTypeLeftAsynctask;
import com.joyful.joyfulkitchen.asynctask.MeauTypeLeftItemAsynctask;
import com.joyful.joyfulkitchen.model.MeauType;
import com.joyful.joyfulkitchen.model.MeauTypeParent;

import java.util.ArrayList;
import java.util.List;

import static com.joyful.joyfulkitchen.base.BaseApplication.getContext;

/**
 * Created by Administrator on 2017/4/27.
 */

public class CookeyBookActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView go_soupage;
    private RecyclerView meau_recyclerView_left, meau_recyclerView_right;
    private MeauTypeLeftAdapter meauTypeLeftAdapter;
    private MeauTypeRightAdapter meauTypeRightAdapter;

    private List<MeauTypeParent> meauTypeParentData = new ArrayList<MeauTypeParent>();
    private List<MeauType> meauTypeData = new ArrayList<MeauType>();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookeybook);

        initView();

        addListeener();
        
    }


    private void initView() {
        go_soupage = (TextView) findViewById(R.id.go_soupage);
        go_soupage.setOnClickListener(this);


        // 左边
        meau_recyclerView_left = (RecyclerView) findViewById(R.id.meau_recyclerView_left);
        meauTypeLeftAdapter = new MeauTypeLeftAdapter(this, meauTypeParentData);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        meau_recyclerView_left.setAdapter(meauTypeLeftAdapter);
        meau_recyclerView_left.setLayoutManager(linearLayoutManager);


        // 右边
        meau_recyclerView_right = (RecyclerView) findViewById(R.id.meau_recyclerView_right);
        meauTypeRightAdapter = new MeauTypeRightAdapter(this, meauTypeData);
        meau_recyclerView_right.setAdapter(meauTypeRightAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        meau_recyclerView_right.setLayoutManager(staggeredGridLayoutManager);

    }

    private void addListeener() {
        //加载左边数据 和右边 第一次显示的数据
        if (meauTypeParentData.size() == 0) {
            getMeauTypeParent();
        }

        // 当点击每一个大类型item时 加载右边不同的数据
        meauTypeLeftAdapter.setmOnItemClickListener(new MeauTypeLeftAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int layoutPosition, int position, int parentId) {

                getMeauTypeByParentId(parentId);

            }

            @Override
            public void onItemLongClick(View view, int layoutPosition, int position, int parentId) {

            }
        });

        // 每个 大类型中的小类型 点击时 搜索页面
        meauTypeRightAdapter.setmOnItemClickListener(new MeauTypeRightAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int layoutPosition, int position, String name) {
                // 搜索页面
                Intent intent = new Intent(getContext(), SearchListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("pname", name);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int layoutPosition, int position, String name) {

            }
        });
        
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_soupage:
                // 搜索页面
                // 搜索页面
                Intent intent = new Intent(getContext(), SearchListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("pname", "家常菜");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * 获取所有的类型
     */
    public void getMeauTypeParent() {
        MeauTypeLeftAsynctask task = new MeauTypeLeftAsynctask(this, meauTypeParentData, meauTypeLeftAdapter, meauTypeData, meauTypeRightAdapter);
        task.execute();
    }

    /**
     * 通过 parentID 获取 对应的子类型
     *
     * @param parentId
     */
    public void getMeauTypeByParentId(int parentId) {
        MeauTypeLeftItemAsynctask task = new MeauTypeLeftItemAsynctask(this, meauTypeData, meauTypeRightAdapter);
        task.execute(parentId);
    }


}
