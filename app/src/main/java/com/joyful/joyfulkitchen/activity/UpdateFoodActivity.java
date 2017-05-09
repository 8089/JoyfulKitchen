package com.joyful.joyfulkitchen.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.adapter.UpdateFoodAdapter;
import com.joyful.joyfulkitchen.base.BaseApplication;
import com.joyful.joyfulkitchen.model.Food;
import com.joyful.joyfulkitchen.model.SearchMeauList;

import java.util.ArrayList;
import java.util.List;

// 修改  菜谱中的 食材
public class UpdateFoodActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context = this;

    private ImageButton back;
    private TextView food_title, food_edit_save;
    private ListView edit_list;
    private Button food_add, start_chen;

    private SearchMeauList searchMeauList;
    //食材
    private List<SearchMeauList.Matail> foodMaterialData = new ArrayList<SearchMeauList.Matail>();
    private UpdateFoodAdapter adpate;

    private BaseApplication baseApplication;


    // 添加的食物
    private Food food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_food);

        Intent intent = this.getIntent();
        this.searchMeauList = (SearchMeauList) intent.getSerializableExtra("searchMeauList");
        initView();
    }


    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        back = (ImageButton) findViewById(R.id.back);
        food_title = (TextView) findViewById(R.id.food_title);
        food_edit_save = (TextView) findViewById(R.id.food_edit_save);
        food_add = (Button) findViewById(R.id.food_add);
        start_chen = (Button) findViewById(R.id.start_chen);
        edit_list = (ListView) findViewById(R.id.edit_list);
    }

    private void initData() {
        baseApplication = (BaseApplication) getApplication();
        baseApplication.setSearchMeauList(searchMeauList);

        foodMaterialData = searchMeauList.getFoodMatail();
        food_title.setText(searchMeauList.getTitle());

        back.setOnClickListener(this);
        food_edit_save.setOnClickListener(this);
        food_add.setOnClickListener(this);
        start_chen.setOnClickListener(this);

        adpate = new UpdateFoodAdapter(this, foodMaterialData);
        edit_list.setAdapter(adpate);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                this.finish();
                break;
            // 保存
            case R.id.food_edit_save:
//                adpate.notifyDataSetChanged();
//                baseApplication.getSearchMeauList().setFoodMatail(foodMaterialData);
//                ToastUtil.toastMessage(UpdateFoodActivity.this,"保存成功!");
                break;
            // 添加食材
            case R.id.food_add:
                adpate.notifyDataSetChanged();
                Intent intent = new Intent(context, FoodTypeSelectActivity.class);
                intent.putExtra("add","add");
                startActivityForResult(intent,1);
                break;
            // 开始称量
            case R.id.start_chen:
                this.finish();
                Intent gore = new Intent(context, ManyFoodWeighingActivity.class);
                startActivity(gore);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
//                ToastUtil.toastMessage(this,"UpdateFoodActivity"+requestCode);

                food = (Food) data.getSerializableExtra("foodvalue");
                Log.i("ccc", "onActivityResult: UpdateFoodActivity"+food.getFoodName());

                SearchMeauList.Matail matail = new SearchMeauList.Matail();
                matail.setName(food.getFoodName());
                foodMaterialData.add(matail);

                adpate.notifyDataSetChanged();

                break;
            default:
                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //释放焦点
        }
        return super.onKeyDown(keyCode, event);
    }


}
