package com.joyful.joyfulkitchen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.activity.TestActivity;
import com.joyful.joyfulkitchen.adapter.CookeyBookImageLoopAdapter;
import com.joyful.joyfulkitchen.adapter.RecyclerAdapter;
import com.joyful.joyfulkitchen.adapter.RecyclerViewHolder;
import com.joyful.joyfulkitchen.adapter.TabFragmentPageAdapter;
import com.joyful.joyfulkitchen.dao.FoodDao;
import com.joyful.joyfulkitchen.dao.GreenDaoManager;
import com.joyful.joyfulkitchen.model.Food;
import com.joyful.joyfulkitchen.model.WeighingFood;
import com.joyful.joyfulkitchen.util.ToastUtils;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;

import java.util.ArrayList;
import java.util.List;

// cookeybook  菜谱
public class CookeyBookFragment2 extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_Name = "ARG_Name";
    //图片轮播
    private RollPagerView mViewPager;

    private String mParam1;

    private RecyclerView mRecyclerView;
    
    private RecyclerAdapter<Food> foodAdapter;
    
    private List<Food> foods;

    private TabLayout tabLayout;
    private TabFragmentPageAdapter tabFragmentPageAdapter;
    private int preIndex = 0;


    public static CookeyBookFragment2 newInstance(String param1) {
        CookeyBookFragment2 fragment = new CookeyBookFragment2();
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
        View view = inflater.inflate(R.layout.fragment_cookeybook2, container, false);

        initView(view);
        addListener();

        return view;
    }

    private void addListener() {
        mViewPager.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent = new Intent(getContext(), TestActivity.class);
                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(foodAdapter = new RecyclerAdapter<Food>(getContext(), foods, R.layout.item_food_weighing) {
            @Override
            public void convert(RecyclerViewHolder holder, Food data, int position) {
                holder.setText(R.id.shicai, data.getFoodName());
                holder.setText(R.id.chengzhong, data.getCholesterol() + "g");
                
            }
        });
        foodAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(View view, int position) {
                ToastUtils.showToast(getContext(), "点击" + position);
                /*datas.remove(position);
                foodAdapter.notifyItemRemoved(position);*/
                
                preIndex = position;
                foodAdapter.notifyDataSetChanged();

            }
        });
        foodAdapter.setOnItemLongClickListener(new RecyclerAdapter.OnItemLongClickListener() {
            @Override
            public void OnItemLongClickListener(View view, final int position) {

                new MaterialDialog.Builder(getContext())
                        .title("确定删除"+ foods.get(position).getFoodName() + "?")
                        .positiveText("确定")
                        .negativeText("取消")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                foods.remove(position);
                                tabFragmentPageAdapter.notifyDataSetChanged();
                                foodAdapter.notifyItemRemoved(position);
                            }
                        })
                        .show();
            }
        });

    }

    private void initView(View view) {
        mViewPager = (RollPagerView) view.findViewById(R.id.view_pager);
        mViewPager.setAdapter(new CookeyBookImageLoopAdapter(mViewPager));



        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_weighing);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        initWeighingFoods();

        //Fragment+ViewPager+FragmentViewPager组合的使用
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        tabFragmentPageAdapter = new TabFragmentPageAdapter(getActivity().getSupportFragmentManager(),getContext(), foods);
        viewPager.setAdapter(tabFragmentPageAdapter);

        //TabLayout
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);


    }


    public void initWeighingFoods() {
        FoodDao foodDao = GreenDaoManager.getInstance().getSession().getFoodDao();
        foods = foodDao.loadAll();
    }
}
