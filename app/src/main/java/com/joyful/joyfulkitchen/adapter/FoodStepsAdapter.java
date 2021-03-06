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
import com.joyful.joyfulkitchen.model.SearchMeauList;

import java.util.List;

/**
 * 一道菜的 食材 item adapter
 */
public class FoodStepsAdapter extends BaseAdapter {
    private List<SearchMeauList.StepsBean> data;
    private Context context;
    private LayoutInflater inflater;

    public FoodStepsAdapter(Context context,List<SearchMeauList.StepsBean> data) {
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
        private TextView food_step_start;
        private SimpleDraweeView food_step_img;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder item;
        if (convertView == null) {
            item = new ViewHolder();
            convertView = inflater.inflate(R.layout.food_steps_item, null);
            item.food_step_start = (TextView) convertView.findViewById(R.id.food_step_start);
            item.food_step_img = (SimpleDraweeView) convertView.findViewById(R.id.food_step_img);
            convertView.setTag(item);
        } else {
            item = (ViewHolder) convertView.getTag();
        }

        item.food_step_start.setText(data.get(position).getStep());

        Uri uri = Uri.parse(data.get(position).getImg());
        item.food_step_img.setImageURI(uri);

        return convertView;
    }

}
