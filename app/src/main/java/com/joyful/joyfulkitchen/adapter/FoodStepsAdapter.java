package com.joyful.joyfulkitchen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.model.SearchMeauList;
import com.joyful.joyfulkitchen.util.BitmapCache;

import java.util.List;

/**
 * 一道菜的 食材 item adapter
 */
public class FoodStepsAdapter extends BaseAdapter {
    private List<SearchMeauList.StepsBean> data;
    private Context context;
    private LayoutInflater inflater;

    private ImageLoader mImageLoader;


    public FoodStepsAdapter(Context context,List<SearchMeauList.StepsBean> data) {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);

        // 消息列队
        RequestQueue queue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(queue, new BitmapCache());
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
        private NetworkImageView food_step_img;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder item;
        if (convertView == null) {
            item = new ViewHolder();
            convertView = inflater.inflate(R.layout.food_steps_item, null);
            item.food_step_start = (TextView) convertView.findViewById(R.id.food_step_start);
            item.food_step_img = (NetworkImageView) convertView.findViewById(R.id.food_step_img);
            convertView.setTag(item);
        } else {
            item = (ViewHolder) convertView.getTag();
        }

        item.food_step_start.setText(data.get(position).getStep());
        item.food_step_img.setImageUrl(data.get(position).getImg(),mImageLoader);

        return convertView;
    }


}
