package com.joyful.joyfulkitchen.adapter;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.model.WeighingFood;

import java.util.List;

public class WeighingAdapter extends RecyclerView.Adapter<WeighingAdapter.ViewHolder> {

    private Context mContext;
    private List<WeighingFood> weighingFoodList;
    public WeighingAdapter(Context context, List<WeighingFood> datas){
        this.mContext = context;
        this.weighingFoodList = datas;



    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_food_weighing, null);


        return new WeighingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.shicai.setText(weighingFoodList.get(position).getName());
        holder.chengzhong.setText(weighingFoodList.get(position).getWeight());
        if (weighingFoodList.get(position).isSelected()){
            Drawable drawable2 = mContext.getResources().getDrawable(R.drawable.item_selector2);
            holder.root_weighing.setBackground(drawable2);
        }else{
            Drawable drawable1 = mContext.getResources().getDrawable(R.drawable.item_selector);
            holder.root_weighing.setBackground(drawable1);
        }
    }

    @Override
    public int getItemCount() {
        return weighingFoodList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView shicai;
        TextView chengzhong;
        LinearLayout root_weighing;
        public ViewHolder(View itemView) {
            super(itemView);
            shicai = (TextView) itemView.findViewById(R.id.shicai);
            chengzhong = (TextView) itemView.findViewById(R.id.chengzhong);
            root_weighing = (LinearLayout) itemView.findViewById(R.id.root_weighing);

        }
    }

}
