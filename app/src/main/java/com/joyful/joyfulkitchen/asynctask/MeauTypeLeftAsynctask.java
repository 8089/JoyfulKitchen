package com.joyful.joyfulkitchen.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.joyful.joyfulkitchen.adapter.MeauTypeLeftAdapter;
import com.joyful.joyfulkitchen.adapter.MeauTypeRightAdapter;
import com.joyful.joyfulkitchen.model.MeauType;
import com.joyful.joyfulkitchen.model.MeauTypeParent;
import com.joyful.joyfulkitchen.util.MeauUtil;
import com.joyful.joyfulkitchen.util.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * 菜单类型 左边 AsyncTask
 */
public class MeauTypeLeftAsynctask extends AsyncTask {
    private Activity activity;
    private List<MeauTypeParent> leftDate;
    private MeauTypeLeftAdapter meauTypeLeftAdapter;

    private List<MeauType> rightDate;
    private MeauTypeRightAdapter meauTypeRightAdapter;

    public MeauTypeLeftAsynctask(Activity activity, List<MeauTypeParent> data, MeauTypeLeftAdapter meauTypeLeftAdapter, List<MeauType> rightDate, MeauTypeRightAdapter meauTypeRightAdapter) {
        this.activity = activity;
        this.leftDate = data;
        this.meauTypeLeftAdapter = meauTypeLeftAdapter;
        this.rightDate = rightDate;
        this.meauTypeRightAdapter = meauTypeRightAdapter;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        String rs = MeauUtil.getCategory(0);
//        Log.i(TAG, "doInBackground: ++====="+rs);
        try {
            JSONObject data = new JSONObject(rs);
            String resultcode = data.getString("resultcode");
            String reason = data.getString("reason");
            if (resultcode.equals("200")) {
                if (reason.equalsIgnoreCase("Success")) {
                    leftDate.clear();
                    rightDate.clear();
                    // 左边
                    JSONArray leftResult = data.getJSONArray("result");
                    Log.i(TAG, "doInBackground: leftResult"+leftResult+"==="+leftResult.length());
                    for (int i = 0; i < leftResult.length(); i++) {
                        JSONObject obj = leftResult.getJSONObject(i);
                        String name = obj.getString("name");
                        int parentId = obj.getInt("parentId");
                        MeauTypeParent meauTypeParent = new MeauTypeParent(parentId, name);
                        //
                        leftDate.add(meauTypeParent);
                    }

                    Log.i(TAG, "doInBackground:" + leftDate.size() + ":::" + leftDate);

                    // 右边
                    JSONArray rightResult = leftResult.getJSONObject(0).getJSONArray("list");
                    Log.i(TAG, "doInBackground: rightResult"+rightResult+"==="+rightResult.length());
                    for (int i = 0; i < rightResult.length(); i++) {
                        JSONObject obj = rightResult.getJSONObject(i);
                        int id = obj.getInt("id");
                        String name = obj.getString("name");
                        int parentId = obj.getInt("parentId");
                        MeauType meauType = new MeauType(id,name,parentId);

                        rightDate.add(meauType);
                    }


                } else {
                    ToastUtil.toastMessage(activity, "请求失败" + reason);
                }
            } else {
                ToastUtil.toastMessage(activity, "获取数据异常" + resultcode);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return leftDate;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        meauTypeLeftAdapter.notifyDataSetChanged();
        meauTypeRightAdapter.notifyDataSetChanged();
    }
}
