package com.joyful.joyfulkitchen.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.codbking.calendar.CaledarAdapter;
import com.codbking.calendar.CalendarBean;
import com.codbking.calendar.CalendarDateView;
import com.codbking.calendar.CalendarUtil;
import com.codbking.calendar.CalendarView;
import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.adapter.RecordFragmentAdapter;
import com.joyful.joyfulkitchen.dao.GreenDaoManager;
import com.joyful.joyfulkitchen.dao.RecordDao;
import com.joyful.joyfulkitchen.model.Record;
import com.joyful.joyfulkitchen.model.RecordItem;
import com.joyful.joyfulkitchen.util.ToastUtils;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RecordFragment extends Fragment {

    private Context context = getActivity();
    public static final String ARG_Name = "arg_name";
    private String mParam1;

    private CalendarDateView mCalendarDateView;
    private TextView mTitle;
    private ListView mList;
    private RecordFragmentAdapter adapter;
    private List<Record> records = new ArrayList<Record>();


    private int year, month, day;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static RecordFragment newInstance(String param1) {
        Bundle args = new Bundle();

        args.putString(ARG_Name, param1);
        RecordFragment fragment = new RecordFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_Name);
        }
        Calendar now = Calendar.getInstance();
        this.year =  now.get(Calendar.YEAR);
        this.month = now.get(Calendar.MONTH) + 1;
        this.day = now.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void onResume() {
        super.onResume();
        // 添加数据
        addListData(year, month, day);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_main, container, false);

        initView(view);
        // 初始化数据
        addData();

        return view;
    }


    public static int px(float dipValue) {
        Resources r = Resources.getSystem();
        final float scale =r.getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private void addData() {

        mCalendarDateView.setAdapter(new CaledarAdapter() {
            @Override
            public View getView(View convertView, ViewGroup parentView, CalendarBean bean) {
                TextView view;
                if (convertView == null) {
                    convertView = LayoutInflater.from(parentView.getContext()).inflate(R.layout.item_calendar, null);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(px(48), px(48));
                    convertView.setLayoutParams(params);
                }

                view = (TextView) convertView.findViewById(R.id.text);

                view.setText("" + bean.day);
                if (bean.mothFlag != 0) {
                    view.setTextColor(0xff9299a1);
                } else {
                    view.setTextColor(0xffffffff);
                }
                return convertView;
            }
        });




        // 单击日期的点击事件
        mCalendarDateView.setOnItemClickListener(new CalendarView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion, CalendarBean bean) {
                records.clear();
                mTitle.setText(bean.year + "/" + getDisPlayNumber(bean.moth) + "/" + getDisPlayNumber(bean.day));
                ToastUtils.showToast(getContext(), bean.year + "/" + getDisPlayNumber(bean.moth) + "/" + getDisPlayNumber(bean.day));

                year = bean.year;
                month = bean.moth;
                day = bean.day;
                // 获取数据库  记录列表
                addListData(bean.year, bean.moth, bean.day);
            }
        });

        int[] data = CalendarUtil.getYMD(new Date());
        mTitle.setText(data[0] + "/" + data[1] + "/" + data[2]);

        // listview 显示 记录
        mList.setAdapter(adapter);
    }

    private void initView(View view) {
        mCalendarDateView = (CalendarDateView) view.findViewById(R.id.calendarDateView);
        mTitle = (TextView) view.findViewById(R.id.title);
        mList = (ListView) view.findViewById(R.id.list);

        adapter = new RecordFragmentAdapter(getActivity(), records);
    }
    private String getDisPlayNumber(int num) {
        return num < 10 ? "0" + num : "" + num;
    }



    // 添加数据
    private void addListData(int year, int month, int day){
        records.clear();
        String startDate = year + "-" + month + "-" + day + " 00:00:00";
        String endDate = year + "-" + month + "-" + day + " 23:59:59";
        Query query = null;
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = simpleDateFormat.parse(startDate);
            date2 = simpleDateFormat.parse(endDate);
            query = GreenDaoManager.getInstance().getSession().getRecordDao().queryBuilder().where(RecordDao.Properties.CreateTime.between(date1, date2)).build();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (query != null) {
            records.addAll(query.list());

            if(records.isEmpty() || records.size() <= 0){
                Record record = new Record();
                record.setMeauName("当前日期没有数据");
                record.setTotalEnergy(0);
                record.setTotalWeight(0);

                records.add(record);
            }
            if (adapter != null)
                adapter.notifyDataSetChanged();
        }
    }


}