package com.joyful.joyfulkitchen.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.model.SearchMeauList;

import java.util.List;

/**
 *  食材修改
 */
public class UpdateFoodAdapter extends BaseAdapter {
    private List<SearchMeauList.Matail> data;
    private Context context;
    private LayoutInflater inflater;

    public UpdateFoodAdapter(Context context, List<SearchMeauList.Matail> data) {
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


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.update_food_item, null);
        TextView foot_name = (TextView) convertView.findViewById(R.id.foot_name);
        TextView   food_weight = (EditText) convertView.findViewById(R.id.food_weight);

        foot_name.setText(data.get(position).getName());
        food_weight.setText(data.get(position).getCount());
        // food_weight 内容发生改变时
        food_weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Toast.makeText(context, "变化前:"+s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Toast.makeText(context, "变化:"+s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {
//                Toast.makeText(context, "变化后:"+s, Toast.LENGTH_SHORT).show();
                data.get(position).setCount(s.toString());
            }
        });


        return convertView;
    }

    // 去掉item 的单击
    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }


}
