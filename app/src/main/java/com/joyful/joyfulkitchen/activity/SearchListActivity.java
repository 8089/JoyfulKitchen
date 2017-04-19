package com.joyful.joyfulkitchen.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.RefreshAndLoad.PullToRefreshLayout;
import com.joyful.joyfulkitchen.adapter.SearchListAdapter;
import com.joyful.joyfulkitchen.asynctask.MeauListAsynctask;
import com.joyful.joyfulkitchen.listener.RefleshListenerListView;
import com.joyful.joyfulkitchen.model.SearchMeauList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchListActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    private static final String TAG = "SearchListActivity";
    private Context context = this;

    private String pname = "家常菜";

    private ImageButton btn_back;

    private EditText search_input;

    private TextView tosearch_btn;

    private List<SearchMeauList> datalist = new ArrayList<SearchMeauList>();

    private SearchListAdapter searchListAdapter;

    private PullToRefreshLayout pullToRefreshLayout;

    private ListView search_listview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_list);
        Bundle bundle = this.getIntent().getExtras();
        String pname = bundle.getString("pname");
        if (pname != null) {
           this.pname = pname;
        } else {
            this.pname = "家常菜";
        }
        init();
    }

    private void init() {
        btn_back = (ImageButton) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        search_input = (EditText) findViewById(R.id.search_input);
        tosearch_btn = (TextView) findViewById(R.id.tosearch_btn);
        pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.pullToRefreshLayout);
        search_listview = (ListView) findViewById(R.id.search_listview);

        search_input.setText(this.pname);
        search_input.clearFocus();
        search_input.setSelected(false);

        searchListAdapter = new SearchListAdapter(this, datalist);
        search_listview.setAdapter(searchListAdapter);

        // 点击每一项时
        search_listview.setOnItemClickListener(this);


        // 加载更多,刷新....
        pullToRefreshLayout.setOnRefreshListener(new RefleshListenerListView(context, datalist, searchListAdapter, pname));

        // 获取查找的菜单
        getMeauList(this.pname);

        //点击查找按钮时
        tosearch_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String pname = search_input.getText().toString();
        switch (v.getId()){
            case R.id.tosearch_btn:
                if(pname!=null&&!"".equals(pname.trim())){
                    this.pname=pname;
                    getMeauList(this.pname);
                }
                break;
            case R.id.btn_back:
                // 结束这个 activity
                this.finish();
                break;
            default:
                break;
        }



    }

    public void getMeauList(String pname) {
        MeauListAsynctask task = new MeauListAsynctask((Activity) context, datalist, searchListAdapter,-1);
        task.execute(pname);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Log.i(TAG, "onItemClick: "+datalist.get(position).toString());
        Intent intent = new Intent(context,FoodDetailActivity.class);
        intent.putExtra("searchMeauList",(Serializable)datalist.get(position));
        startActivity(intent);
    }
}
