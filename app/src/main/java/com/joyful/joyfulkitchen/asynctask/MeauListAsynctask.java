package com.joyful.joyfulkitchen.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.BaseAdapter;

import com.joyful.joyfulkitchen.model.SearchMeauList;
import com.joyful.joyfulkitchen.util.MeauUtil;
import com.joyful.joyfulkitchen.util.StringUtil;
import com.joyful.joyfulkitchen.util.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * 菜谱列表 AsyncTask
 */
public class MeauListAsynctask extends AsyncTask {

    private Activity activity;
    private List<SearchMeauList> datalist;
    private BaseAdapter adapter;
    private String name;

    private int fristIndex = -1;
    private int row = 20;

    private int total ;

    public MeauListAsynctask(Activity activity, List<SearchMeauList> data, BaseAdapter searchListAdapter, int fristIndex) {
        this.activity = activity;
        this.datalist = data;
        this.adapter = searchListAdapter;
        this.fristIndex =fristIndex;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        name = (String) params[0];
        if(fristIndex==-1){
            datalist.clear(); // 刷新时重新加载数据 ,清除
        }
        String rs = MeauUtil.getFood(name, fristIndex, row);

        Log.i(TAG, "doInBackground---++++=====MeauListAsynctask" + rs);

        try {
            JSONObject data = new JSONObject(rs);
            String resultcode = data.getString("resultcode");
            String reason = data.getString("reason");
            if (resultcode.equals("200")) {
                if (reason.equalsIgnoreCase("Success")) {

                    JSONArray results = data.getJSONObject("result").getJSONArray("data");
                    total = results.length();
                    Log.i(TAG, "doInBackground:JSONArray results " + results.length());
                   if(total>0){



                       List<String> foodmatails = new ArrayList<String>();
                       List<String> foodMatail2 = new ArrayList<String>();
                       for (int i = 0; i < results.length(); i++) {
                           JSONObject obj = results.getJSONObject(i);

                           String id = obj.getString("id");
                           String title = obj.getString("title");
                           String tags = obj.getString("tags");
                           String imtro = obj.getString("imtro");
                           String ingredients = obj.getString("ingredients");
                           String burden = obj.getString("burden");

                           foodmatails.clear();
                           foodMatail2.clear();
                           foodmatails = StringUtil.StringSplit(foodmatails, ingredients);
                           foodMatail2 = StringUtil.StringSplit(foodMatail2, burden);

                           // 连个list 整合到一起
                           foodmatails = StringUtil.listAdd(foodmatails,foodMatail2);
                           List<SearchMeauList.Matail> matails = new ArrayList<SearchMeauList.Matail>();
                           for (int j = 0; j < foodmatails.size()/2; j++) {
                               SearchMeauList.Matail m = new SearchMeauList.Matail();
                               m.setName(foodmatails.get(j*2));
                               m.setCount(foodmatails.get(j*2+1));
                               matails.add(m);
                           }


                           JSONArray albumss = obj.getJSONArray("albums");
                           JSONArray stepss = obj.getJSONArray("steps");

                           List<String> albums = new ArrayList<String>();
                           List<SearchMeauList.StepsBean> steps = new ArrayList<SearchMeauList.StepsBean>();

                           for (int j = 0; j < albumss.length(); j++) {
                               albums.add(albumss.getString(j));
                           }
                           for (int j = 0; j < stepss.length(); j++) {
                               JSONObject object = stepss.getJSONObject(j);
                               String img = object.getString("img");
                               String step = object.getString("step");
                               SearchMeauList.StepsBean bean = new SearchMeauList.StepsBean(img, step);
                               steps.add(bean);
                           }

                           SearchMeauList searchMeauList = new SearchMeauList(id, title, tags, imtro, ingredients, burden,matails, albums, steps);
                           datalist.add(searchMeauList);
                       }
                       Log.i(TAG, "doInBackground: datalist======" + datalist.size());
                   }
                }else{
                    return null;
                }
            } else {
                ToastUtil.toastMessage(activity, "获取数据异常" + resultcode);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if(total>0){
            adapter.notifyDataSetChanged();
        }
    }
}
