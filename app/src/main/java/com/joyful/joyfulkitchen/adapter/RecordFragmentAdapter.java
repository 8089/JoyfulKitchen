package com.joyful.joyfulkitchen.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.model.Record;
import com.joyful.joyfulkitchen.model.RecordItem;
import com.joyful.joyfulkitchen.model.SearchMeauList;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 一道菜的 食材 item adapter
 */
public class RecordFragmentAdapter extends BaseAdapter {
    private List<Record> data;
    private Context context;
    private LayoutInflater inflater;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
    private java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");

    public RecordFragmentAdapter(Context context, List<Record> data) {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    static class ViewHolder {
        private TextView record_foodmeau_name,food_total_weight,food_total_energy, food_total_time;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder item;
        if (convertView == null) {
            item = new ViewHolder();
            convertView = inflater.inflate(R.layout.record_fragment_item, null);
            item.record_foodmeau_name = (TextView) convertView.findViewById(R.id.record_foodmeau_name);
            item.food_total_weight= (TextView) convertView.findViewById(R.id.food_total_weight);
            item.food_total_energy= (TextView) convertView.findViewById(R.id.food_total_energy);
            item.food_total_time= (TextView) convertView.findViewById(R.id.food_total_time);
            convertView.setTag(item);
        } else {
            item = (ViewHolder) convertView.getTag();
        }

        // 设置 记录表中的 菜谱名
        item.record_foodmeau_name.setText(data.get(position).getMeauName());
        item.food_total_weight.setText(df.format(data.get(position).getTotalWeight())+"g");
        item.food_total_energy.setText(df.format(data.get(position).getTotalEnergy())+"kcl");
        if(data.get(position).getCreateTime() != null){
            item.food_total_time.setText(simpleDateFormat.format(data.get(position).getCreateTime()));
        }else
            item.food_total_time.setText("00:00");


        return convertView;
    }
}
