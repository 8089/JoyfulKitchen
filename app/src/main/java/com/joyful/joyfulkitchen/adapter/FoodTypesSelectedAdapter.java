package com.joyful.joyfulkitchen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.model.Food;

import java.util.List;

/**
 * 一道菜的 食材 item adapter
 */
public class FoodTypesSelectedAdapter extends BaseAdapter {
    private List<Food> data;
    private Context context;
    private LayoutInflater inflater;

    public FoodTypesSelectedAdapter(Context context, List<Food> data) {
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
        private TextView foods_name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder item;
        if (convertView == null) {
            item = new ViewHolder();
            convertView = inflater.inflate(R.layout.food_type_selected_item, null);
            item.foods_name = (TextView) convertView.findViewById(R.id.foods_name);
            convertView.setTag(item);
        } else {
            item = (ViewHolder) convertView.getTag();
        }
        item.foods_name.setText(data.get(position).getFoodName());

        return convertView;
    }

}
