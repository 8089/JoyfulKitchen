package com.joyful.joyfulkitchen.listener;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.joyful.joyfulkitchen.RefreshAndLoad.PullToRefreshLayout;
import com.joyful.joyfulkitchen.adapter.SearchListAdapter;
import com.joyful.joyfulkitchen.asynctask.MeauListAsynctask;
import com.joyful.joyfulkitchen.model.SearchMeauList;
import com.joyful.joyfulkitchen.util.NetHelper;
import com.joyful.joyfulkitchen.util.ToastUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 */

public class RefleshListenerListView implements  PullToRefreshLayout.OnRefreshListener {
    private Context context;
    private List<SearchMeauList> data;
    private SearchListAdapter searchListAdapter;
    private String name;

    public RefleshListenerListView(Context context, List<SearchMeauList> data, SearchListAdapter searchListAdapter, String name){
        this.context = context;
        this.data = data;
        this.searchListAdapter = searchListAdapter;
        this.name = name;
    }

    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        // 下拉刷新操作
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                boolean b = NetHelper.IsHaveInternet(context);
                if(b){
                    //当前有可用网络
                    // 加载成功
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    ToastUtil.toastMessage((Activity) context,"成功刷新"+data.size());

                    // 刷新数据......
                    MeauListAsynctask task = new MeauListAsynctask((Activity) context,data,searchListAdapter,-1);
                    task.execute(name);
                }else{
                    //当前无可用网络
                    //加载失败
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                    ToastUtil.toastMessage((Activity) context,"刷新失败"+data.size());
                }
            }
        }.sendEmptyMessageDelayed(0, 2000);


    }

    @Override
    public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
        // 加载操作
        new Handler() {
            @Override
            public void handleMessage(Message msg) {

                boolean b = NetHelper.IsHaveInternet(context);
                if(b){
                    //当前有可用网络
                    // 加载成功
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    ToastUtil.toastMessage((Activity) context,"加载更多");

                    // 加载数据......

                    MeauListAsynctask task = new MeauListAsynctask((Activity) context,data,searchListAdapter,data.size());
                    task.execute(name);
                }else{
                    //当前无可用网络
                    //加载失败
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                    ToastUtil.toastMessage((Activity) context,"加载啊更多失败");
                }
            }
        }.sendEmptyMessageDelayed(0, 2000);
    }



}
