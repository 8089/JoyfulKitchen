package com.joyful.joyfulkitchen.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joyful.joyfulkitchen.R;

import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */

public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {

    private Context mContext;
    private List<T> mDatas;
    private int mLayoutId;
    private LayoutInflater mInflater;

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onLongClickListener;

    public RecyclerAdapter(Context mContext, List<T> mDatas, int mLayoutId) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.mLayoutId = mLayoutId;
        mInflater = LayoutInflater.from(mContext);
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //这里是创建ViewHolder的地方，RecyclerAdapter内部已经实现了ViewHolder的重用
        //这里我们直接new就好了
        return new RecyclerViewHolder(mInflater.inflate(mLayoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        if (onItemClickListener != null) {
            //设置背景
            holder.itemView.setBackgroundResource(R.drawable.item_selector);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //注意，这里的position不要用上面参数中的position，会出现位置错乱
                    onItemClickListener.OnItemClickListener(holder.itemView, holder.getLayoutPosition());
                }
            });
        }

        if (onLongClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onLongClickListener.OnItemLongClickListener(holder.itemView, holder.getLayoutPosition());
                    return false;
                }
            });
        }

        convert(holder, mDatas.get(position), position);
    }

    public abstract void convert(RecyclerViewHolder holder, T data, int position);


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    /**自定义RecyclerView item的点击事件的点击事件*/
    public interface OnItemClickListener {
        void OnItemClickListener(View view, int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onLongClickListener = listener;
    }
    /**自定义RecyclerView item的长点击事件的点击事件*/
    public interface OnItemLongClickListener {
        void OnItemLongClickListener(View view, int position);
    }
}