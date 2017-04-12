package com.joyful.joyfulkitchen.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.activity.TestActivity;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;

/**
 * Created by Administrator on 2017/4/10.
 */

public class CookeyBookImageLoopAdapter extends LoopPagerAdapter {
    int[] imgs = new int[]{
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5,
    };

    public CookeyBookImageLoopAdapter(RollPagerView viewPager) {
        super(viewPager);
    }

    @Override
    public View getView(final ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setImageResource(imgs[position]);

        return view;
    }

    @Override
    public int getRealCount() {
        return imgs.length;
    }
}
