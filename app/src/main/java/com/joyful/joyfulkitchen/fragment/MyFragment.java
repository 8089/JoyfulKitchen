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
import android.widget.ImageView;

import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.activity.UpdateActivity;

// my  我的个人信息页面
public class MyFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_Name = "ARG_Name";
    private static final String TAG = "RoundImage";
    private ImageView mImg;
    private String mParam1;

    //修改个人信息
    private ImageView xiugai;

    private RecyclerView myRecyvler;

    public static MyFragment newInstance(String param1) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_Name, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_Name);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);

        //RecyclerView
        myRecyvler  = (RecyclerView) view.findViewById(R.id.rv);
        myRecyvler.setLayoutManager(new LinearLayoutManager(getContext()));
        myRecyvler.setAdapter(new MyFragment.myAdapter());

        xiugai = (ImageView) view.findViewById(R.id.xiugai);

        xiugai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), UpdateActivity.class);
                startActivity(i);
            }
        });

      /*  huatu(view);*/
        return  view;

    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public class myAdapter extends RecyclerView.Adapter{

        private final int ITEM_TYPE_TEXTVIEW = 10000;
        private final int ITEM_TYPE_BUTTON = 10001;

        @Override
        public int getItemViewType(int position) {
            if(position >0 && position <6 || position >15)
                return ITEM_TYPE_TEXTVIEW;
            else
                return ITEM_TYPE_BUTTON;



        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = null;
            switch (viewType) {
                case ITEM_TYPE_TEXTVIEW:
                    itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_info, null);
                    return new MyViewHolder(itemView);
                case ITEM_TYPE_BUTTON:
                    itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.button, null);
                    return new ButtonViewHolder(itemView);
            }



            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {



            switch (getItemViewType(position)){
                case ITEM_TYPE_TEXTVIEW:
                    MyViewHolder myViewHolder = (MyViewHolder)holder;

                    break;
                case ITEM_TYPE_BUTTON:

                    break;
            }



        }

        class ButtonViewHolder extends RecyclerView.ViewHolder{

            public ButtonViewHolder(View itemView) {
                super(itemView);

            }
        }


       /* @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

        }*/



        @Override
        public int getItemCount() {
            return 20;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
       // LinearLayout all;
        public MyViewHolder(View itemView) {
            super(itemView);
           // all = (LinearLayout) itemView.findViewById(R.id.all);
        }
    }

















        //画圆形图片
   /* public void huatu(View view) {
        //初始化控件
        mImg = (ImageView) view.findViewById(R.id.myFace);
        //裁剪图片
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.a, options);
        Log.d(TAG, "original outwidth:"+options.outWidth);
        //此宽度是目标 imageView 希望的大小，你可以自定义imageView 然后获得ImageView 的宽度


        //这里修改大小
        int dstWidth = 50;



        //我们需要加载的图片可能很大，我们先对原有的图片进行裁剪
        int sampleSize = calculateInSampleSize(options, dstWidth, dstWidth);
        options.inSampleSize = sampleSize;
        options.inJustDecodeBounds = false;
        Log.d(TAG, "sample size:" + sampleSize);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.a, options);
        //绘制图片
        Bitmap resultBmp = Bitmap.createBitmap(dstWidth, dstWidth, Bitmap.Config.ARGB_8888);
        Paint paint  = new Paint();
        paint.setAntiAlias(true);
        Canvas canvas = new Canvas(resultBmp);
        //画图
        canvas.drawCircle(dstWidth / 2, dstWidth / 2, dstWidth / 2, paint);
        //选择交集去上层图片
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bmp, new Rect(0, 0, bmp.getWidth(), bmp.getWidth()), new Rect(0, 0, dstWidth, dstWidth), paint);
        mImg.setImageBitmap(resultBmp);
        bmp.recycle();
    }


    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if( height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && ( halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }


        }

        return inSampleSize;
    }*/

    }


