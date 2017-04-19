package com.joyful.joyfulkitchen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.model.SearchMeauList;

import java.util.List;

/**
 * 一道菜的 食材 item adapter
 */
public class FoodMaterialsAdapter extends BaseAdapter {
    private List<SearchMeauList.Matail> data;
    private Context context;
    private LayoutInflater inflater;

    public FoodMaterialsAdapter(Context context, List<SearchMeauList.Matail> data) {
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
        private TextView food_name;
        private TextView food_count;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder item;
        if (convertView == null) {
            item = new ViewHolder();
            convertView = inflater.inflate(R.layout.food_materials_item, null);
            item.food_name = (TextView) convertView.findViewById(R.id.food_name);
            item.food_count = (TextView) convertView.findViewById(R.id.food_count);
            convertView.setTag(item);
        } else {
            item = (ViewHolder) convertView.getTag();
        }
     /*   if(position<data.size()-1){
//            if(position%2==0){
                item.food_name.setText(data.get(position * 2).toString());
                item.food_count.setText(data.get(position * 2 +1).toString());
//            }
        }*/
        item.food_name.setText(data.get(position).getName());
        item.food_count.setText(data.get(position).getCount());
        return convertView;
    }

}
