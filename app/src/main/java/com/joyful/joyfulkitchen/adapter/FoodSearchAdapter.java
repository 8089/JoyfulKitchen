package com.joyful.joyfulkitchen.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joyful.joyfulkitchen.R;

import java.util.List;

public class FoodSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    private Context mContext;
    private List<String> datas;

    //自定义监听事件
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view);
        void onItemLongClick(View view);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }



    //适配器初始化
    public FoodSearchAdapter(Context context, List<String> datas) {
        mContext=context;
        this.datas = datas;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_foodlist, parent,
                false);
        FoodListHolder holder = new FoodListHolder(view);


        return holder;
    }


    class FoodListHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public FoodListHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_foodlist);
        }
    }




    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((FoodListHolder)holder).textView.setText(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v);
        }
    }

    @Override
    public boolean onLongClick(View v) {

        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemLongClick(v);
        }
        return false;
    }





}
