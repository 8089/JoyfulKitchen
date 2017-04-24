package com.joyful.joyfulkitchen.asynctask;

import android.app.Activity;
import android.os.AsyncTask;

import com.joyful.joyfulkitchen.adapter.MeauTypeRightAdapter;
import com.joyful.joyfulkitchen.model.MeauType;
import com.joyful.joyfulkitchen.util.MeauUtil;
import com.joyful.joyfulkitchen.util.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 菜单类型右边 AsyncTask
 */
public class MeauTypeLeftItemAsynctask extends AsyncTask {
    private Activity activity;

    private List<MeauType> rightDate;
    private MeauTypeRightAdapter meauTypeRightAdapter;

    public MeauTypeLeftItemAsynctask(Activity activity, List<MeauType> rightDate, MeauTypeRightAdapter meauTypeRightAdapter) {
        this.activity = activity;
        this.rightDate = rightDate;
        this.meauTypeRightAdapter = meauTypeRightAdapter;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        int parentId = (int) params[0];
        String rs = MeauUtil.getCategory(parentId);
//        Log.i(TAG, "doInBackground: ++====="+rs);
        try {

            JSONObject data = new JSONObject(rs);
            String resultcode = data.getString("resultcode");
            String reason = data.getString("reason");
            if (resultcode.equals("200")) {
                if (reason.equalsIgnoreCase("Success")) {
                    rightDate.clear();

                    JSONArray array = data.getJSONArray("result").getJSONObject(0).getJSONArray("list");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        int id = obj.getInt("id");
                        String name = obj.getString("name");
                        int pid = obj.getInt("parentId");
                        MeauType meauType = new MeauType(id,name,pid);

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
        return rs;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        meauTypeRightAdapter.notifyDataSetChanged();
    }
}
