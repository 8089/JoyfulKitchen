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

/**
 * 美食圈 ---》关注 fragment
 */
public class GourmetFollowFragment extends Fragment {
    private LinearLayout all;


    private RecyclerView mRecyclerview;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_gourmet_follow, null);
        mRecyclerview = (RecyclerView) view.findViewById(R.id.FollowRv);

        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        mRecyclerview.setAdapter(new GourmetFollowFragment.myAdapter());


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

    public class myAdapter extends RecyclerView.Adapter<GourmetFollowFragment.MyViewHolder>{

        @Override
        public GourmetFollowFragment.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_find, null);

            GourmetFollowFragment.MyViewHolder myViewHolder = new GourmetFollowFragment.MyViewHolder(view);



            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            GourmetFollowFragment.MyViewHolder myViewHolder = (GourmetFollowFragment.MyViewHolder) holder;

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
