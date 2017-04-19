package com.joyful.joyfulkitchen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.activity.SearchListActivity;
import com.joyful.joyfulkitchen.adapter.MeauTypeLeftAdapter;
import com.joyful.joyfulkitchen.adapter.MeauTypeRightAdapter;
import com.joyful.joyfulkitchen.asynctask.MeauTypeLeftAsynctask;
import com.joyful.joyfulkitchen.asynctask.MeauTypeLeftItemAsynctask;
import com.joyful.joyfulkitchen.model.MeauType;
import com.joyful.joyfulkitchen.model.MeauTypeParent;

import java.util.ArrayList;
import java.util.List;

// cookeybook  菜谱
public class CookeyBookFragment extends Fragment implements View.OnClickListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_Name = "ARG_Name";

    private String mParam1;

    private ImageView btn_back;
    private TextView go_soupage;
    private RecyclerView meau_recyclerView_left, meau_recyclerView_right;
    private MeauTypeLeftAdapter meauTypeLeftAdapter;
    private MeauTypeRightAdapter meauTypeRightAdapter;

    private List<MeauTypeParent> meauTypeParentData = new ArrayList<MeauTypeParent>();
    private List<MeauType> meauTypeData = new ArrayList<MeauType>();

    private View itemView;


    public static CookeyBookFragment newInstance(String param1) {
        CookeyBookFragment fragment = new CookeyBookFragment();
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
        View view = inflater.inflate(R.layout.fragment_cookeybook, container, false);

        initView(view);

        addListeener();


        return view;
    }



    private void initView(View view) {
        btn_back = (ImageView) view.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        go_soupage = (TextView) view.findViewById(R.id.go_soupage);
        go_soupage.setOnClickListener(this);


        // 左边
        meau_recyclerView_left = (RecyclerView) view.findViewById(R.id.meau_recyclerView_left);
        meauTypeLeftAdapter = new MeauTypeLeftAdapter(getContext(), meauTypeParentData);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        meau_recyclerView_left.setAdapter(meauTypeLeftAdapter);
        meau_recyclerView_left.setLayoutManager(linearLayoutManager);


        // 右边
        meau_recyclerView_right = (RecyclerView) view.findViewById(R.id.meau_recyclerView_right);
        meauTypeRightAdapter = new MeauTypeRightAdapter(getContext(), meauTypeData);
        meau_recyclerView_right.setAdapter(meauTypeRightAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        meau_recyclerView_right.setLayoutManager(staggeredGridLayoutManager);



    }

    // 为 view 添加事件
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
            case R.id.btn_back:
                // 关闭这个页面
//                this.finish();
                break;
            default:
                break;
        }
    }


    /**
     * 获取所有的类型
     */
    public void getMeauTypeParent() {
        MeauTypeLeftAsynctask task = new MeauTypeLeftAsynctask(getActivity(), meauTypeParentData, meauTypeLeftAdapter, meauTypeData, meauTypeRightAdapter);
        task.execute();
    }

    /**
     * 通过 parentID 获取 对应的子类型
     *
     * @param parentId
     */
    public void getMeauTypeByParentId(int parentId) {
        MeauTypeLeftItemAsynctask task = new MeauTypeLeftItemAsynctask(getActivity(), meauTypeData, meauTypeRightAdapter);
        task.execute(parentId);
    }


}
