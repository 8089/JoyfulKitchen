package com.joyful.joyfulkitchen.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.model.MeauType;

import java.util.List;

/**
 * 菜单右边 类型 adapter
 */
public class MeauTypeRightAdapter extends RecyclerView.Adapter<MeauTypeRightAdapter.MyViewHolder> {
    protected LayoutInflater mInflater;
    protected Context mContxt;
    protected List<MeauType> mDatas;


    //自己添加一个 Item 点击事件
    public interface OnItemClickListener {
        void onItemClick(View view, int layoutPosition, int position, String name);
        void onItemLongClick(View view, int layoutPosition, int position, String name);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public MeauTypeRightAdapter(Context context, List<MeauType> data) {
        this.mContxt = context;
        this.mDatas = data;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.item_meau_type_right, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.btnLeft.setText(mDatas.get(position).getName());
        setUpItemEvent(holder,position,mDatas.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView btnLeft;
        public MyViewHolder(View itemView) {
            super(itemView);
            btnLeft = (TextView) itemView.findViewById(R.id.id_btn_right);
        }
    }


    protected void setUpItemEvent(final MyViewHolder holder,final int position,final String name) {

        if (mOnItemClickListener != null) {

            // 各组件设置一个点击事件  holder.itemView 返回一个view
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 获取相对于当前布局的位置
                    int layoutPosition = holder.getLayoutPosition();


                    // 进而个每一个 item 添加一个点击事件(把相关的参数传递过去)
                    mOnItemClickListener.onItemClick(holder.itemView, layoutPosition,position,name);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //获取点击的位置
                    int layoutPosition = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView, layoutPosition,position,name);
                    return false;
                }
            });
        }
    }


}