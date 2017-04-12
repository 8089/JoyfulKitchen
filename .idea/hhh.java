package com.joyful.joyfulkitchen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.activity.TestActivity;

/**
 *  美食圈 --》发现 fragment
 */
 public class GourmetFindFragment extends Fragment {
    private LinearLayout all;


    private RecyclerView mRecyclerview;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_gourmet_find, null);
       
	   mRecyclerview = (RecyclerView) view.findViewById(R.id.rv);

        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        mRecyclerview.setAdapter(new GourmetFindFragment.myAdapter());


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*all  = (LinearLayout) getActivity().findViewById(R.id.all);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TestActivity.class);
                startActivity(intent);
            }
        });*/
    }

    public class myAdapter extends RecyclerView.Adapter<GourmetFindFragment.MyViewHolder>{

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_find, null);

            MyViewHolder myViewHolder = new MyViewHolder(view);

            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            MyViewHolder myViewHolder = (MyViewHolder) holder;

            myViewHolder.all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), TestActivity.class);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        LinearLayout all;
        public MyViewHolder(View itemView) {
            super(itemView);
            all = (LinearLayout) itemView.findViewById(R.id.all);
        }
    }
}
