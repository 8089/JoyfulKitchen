package com.joyful.joyfulkitchen.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joyful.joyfulkitchen.R;

/**
 * 美食圈 ---》最新 fragment
 */
public class GourmetNewestFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gourmet_newest, container,false);

        return view;
    }

}
