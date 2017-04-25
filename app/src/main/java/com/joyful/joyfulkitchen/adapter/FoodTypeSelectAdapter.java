package com.joyful.joyfulkitchen.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.model.FoodType;

import java.util.List;

//food_type_select_item
public class FoodTypeSelectAdapter extends RecyclerView.Adapter<FoodTypeSelectAdapter.MyViewHolder> {
    protected LayoutInflater mInflater;
    protected Context mContxt;
    protected List<FoodType> mDatas;


    //自己添加一个 Item 点击事件
    public interface OnItemClickListener {
        void onItemClick(View view, int layoutPosition, int position);
        void onItemLongClick(View view, int layoutPosition, int position);
    }

    private FoodTypeSelectAdapter.OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(FoodTypeSelectAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public FoodTypeSelectAdapter(Context context, List<FoodType> data) {
        this.mContxt = context;
        this.mDatas = data;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public FoodTypeSelectAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.food_type_select_item, parent, false);
        FoodTypeSelectAdapter.MyViewHolder myViewHolder = new FoodTypeSelectAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final FoodTypeSelectAdapter.MyViewHolder holder, final int position) {
        holder.miaoshu.setText(mDatas.get(position).getTypeName());
        Uri uri = Uri.parse(mDatas.get(position).getFoodTypeImg());
        holder.typeimg.setImageURI(uri);
        setUpItemEvent(holder,position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView typeimg;
        TextView miaoshu;
        public MyViewHolder(View itemView) {
            super(itemView);

            typeimg = (SimpleDraweeView) itemView.findViewById(R.id.typeimg);
            miaoshu = (TextView) itemView.findViewById(R.id.miaoshu);
        }
    }


    protected void setUpItemEvent(final FoodTypeSelectAdapter.MyViewHolder holder, final int position) {

        if (mOnItemClickListener != null) {

            // 各组件设置一个点击事件  holder.itemView 返回一个view
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 获取相对于当前布局的位置
                    int layoutPosition = holder.getLayoutPosition();


                    // 进而个每一个 item 添加一个点击事件(把相关的参数传递过去)
                    mOnItemClickListener.onItemClick(holder.itemView, layoutPosition,position);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //获取点击的位置
                    int layoutPosition = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView, layoutPosition,position);
                    return false;
                }
            });
        }
    }



}
