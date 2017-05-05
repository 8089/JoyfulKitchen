package com.joyful.joyfulkitchen.activity;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.base.BaseApplication;
import com.joyful.joyfulkitchen.dao.GreenDaoManager;
import com.joyful.joyfulkitchen.dao.RecordDao;
import com.joyful.joyfulkitchen.fragment.HealthyFragment;
import com.joyful.joyfulkitchen.model.Food;
import com.joyful.joyfulkitchen.model.Record;
import com.joyful.joyfulkitchen.model.SearchMeauList;
import com.joyful.joyfulkitchen.service.BluetoothService;
import com.joyful.joyfulkitchen.util.ToastUtils;
import com.joyful.joyfulkitchen.util.UnitConversionUtil;
import com.joyful.joyfulkitchen.view.RoundIndicatorView;
import com.joyful.joyfulkitchen.volley.FoodByNameVolley;
import com.joyful.joyfulkitchen.volley.FoodSearchVolley;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static com.joyful.joyfulkitchen.base.BaseApplication.getContext;


/**
 * 食谱称量
 */
public class ManyFoodWeighingActivity extends AppCompatActivity {

    public static final String TAG = "ManyFoodWeighingActivity";

    private TextView tv_ke, tv_show_unit;

    // 显示 放入材料的 view
    private TextView tv_show_next_food;

    //  下一步的按钮呢
    private Button btn_next;
    // 单位的下标
    private int index = 0;

    private String[] units = {"克", "两", "磅", "毫升", "安士"};

    // 中间的圆
    private RoundIndicatorView mRoundIndicatorView;

    // 蓝牙6.0以上需要权限
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;

    private BluetoothAdapter.LeScanCallback lazyCallback;
    private BluetoothAdapter mBluetoothAdapter;
    private String mDeviceAddress = null;
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics = new ArrayList();
    private BluetoothGattCharacteristic mNotifyCharacteristic;
    private BluetoothService mBluetoothLeService;
    private static final int REQUEST_ENABLE_BT = 0;

    private List<Record> records = new ArrayList<Record>();
    //  菜谱
    private SearchMeauList searchMeauList;
    //食材
    private List<SearchMeauList.Matail> foodMaterialData;
    // 步骤
    private List<SearchMeauList.StepsBean> foodStepsData;
    // 默认显示食材
    private boolean rs = true;
    // 下标
    private int foodIndex = -1;

    private Context mContext = this;


    private Handler handler = new Handler() {
        Intent intent = new Intent();

        @Override
        public void handleMessage(Message msg) {

            int a = msg.arg1;

            if (foodIndex != -1) {
                if (foodMaterialData.get(foodIndex).getCount().indexOf("g") > 0) {
                    foodMaterialData.get(foodIndex).setWeight(a);
                }
            }


            String s = (String) msg.obj;
            a = UnitConversionUtil.Conversion(a, index);
            mRoundIndicatorView.setCurrentNumAnim(a);
            tv_show_unit.setText(a + units[index]);
            char c1 = s.charAt(2);
            char c2 = s.charAt(6);

            Log.i("----------状态-----", "第二个" + c1 + "第6个" + c2);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_many_food_weighing);

        // 添加 屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initView();

        // 初始化数据
        initData();
        // 添加监听事件
        addListeners();

        // 初始化蓝牙
        initLanya();
    }


    private void initData() {
        BaseApplication baseApplication = (BaseApplication) getApplication();
        searchMeauList = baseApplication.getSearchMeauList();
        foodMaterialData = baseApplication.getSearchMeauList().getFoodMatail();
        foodStepsData = baseApplication.getSearchMeauList().getSteps();

        boolean rs = showFood();


    }

    // 当没有食物显示的时候 返回 false， 有食物要显示就返回 true
    private boolean showFood() {
        foodIndex = -1;
        for (SearchMeauList.Matail matail : foodMaterialData) {
            /*if ("适量".equals(matail.getCount())){
                matail.setComplete(true);
            }*/
            if (!matail.isComplete()) {
                tv_show_next_food.setText("请放入 " + matail.getName() + matail.getCount());
                foodIndex++;
                return true;
            }
            foodIndex++;
            continue;
        }
        return false;
    }

    // 判断所有食材是否 称量完毕, 是返回 true， 否返回 false
    private boolean isOk() {
        for (SearchMeauList.Matail matail : foodMaterialData) {
            if (!matail.isComplete()) {
                return false;
            }
        }
        return true;
    }


    private void addListeners() {

        // 转换单位
        tv_ke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new MaterialDialog.Builder(mContext)
                        .title(R.string.unit_title)
                        .items(R.array.unit)
                        .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                                tv_ke.setText(text);
                                //克, 两, 磅, 毫升, 安士
                                index = which;
                                return true;
                            }
                        })
//                        .widgetColor(Color.GRAY)  改按钮颜色
                        //widgetColor(), widgetColorRes(), widgetColorAttr(), and choiceWidgetColor()
                        .positiveText(R.string.unit_positive)
                        .show();
            }
        });

        // 点击下一步  进入 下一个食材
        btn_next.setOnClickListener(new View.OnClickListener() {
            boolean complete = false;

            @Override
            public void onClick(View v) {
                foodMaterialData.get(foodIndex).setComplete(true);

                showFood();

                if (complete) {
                    Record record = new Record();
                    record.setMeauName(searchMeauList.getTitle());
                    record.setCreateTime(new Date());
                    for (int i = 0; i < foodMaterialData.size(); i++) {
                        // 根据食材名称获取食材
                        // new FoodByNameVolley((Activity) mContext, foodMaterialData.get(i).getName(), list).doVolley();
                        new FoodByNameVolley((Activity) mContext, foodMaterialData.get(i), foodMaterialData.get(i).getName(), record, foodMaterialData.size()).doVolley();
                    }

                    Intent intent = new Intent(getContext(), ShowStepsActivity.class);
                    startActivity(intent);
                    finish();
                }

                if (isOk()) {
                    tv_show_next_food.setText("所有食材已称量完毕.");
                    btn_next.setText("进入制作步骤");
                    complete = true;
                }

            }
        });


    }

    private void initView() {
        mRoundIndicatorView = (RoundIndicatorView) findViewById(R.id.riv_view);
        tv_ke = (TextView) findViewById(R.id.tv_ke);
        tv_show_unit = (TextView) findViewById(R.id.tv_show_unit);
        tv_show_next_food = (TextView) findViewById(R.id.tv_show_next_food);
        btn_next = (Button) findViewById(R.id.btn_next);
        tv_show_unit.setText("0g");

    }


    /**
     * 蓝牙模块
     */
    private void initLanya() {

        //打开蓝牙
        BluetoothManager bluetoothManager =
                (BluetoothManager) this.getSystemService(Context.BLUETOOTH_SERVICE);

        mBluetoothAdapter = bluetoothManager.getAdapter();


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//请求权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
//判断是否需要 向用户解释，为什么要申请该权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                ToastUtils.showToast(this, "打开蓝牙");
            }
        }

    }

    // 代码管理服务生命周期
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        //服务保持连接
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {

            mBluetoothLeService = ((BluetoothService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
//                Log.i(TAG, "无法初始化蓝牙");
                finish();
            }

            Toast.makeText(mContext, "连接设备", Toast.LENGTH_LONG).show();
            // 自动连接到设备成功启动初始化。
            mBluetoothLeService.connect(mDeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };
    //广播接收
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothService.ACTION_GATT_CONNECTED.equals(action)) {
                Toast.makeText(mContext, "连接成功", Toast.LENGTH_LONG).show();
            } else if (BluetoothService.ACTION_GATT_DISCONNECTED.equals(action)) {
                Toast.makeText(mContext, "断开连接", Toast.LENGTH_LONG).show();

            } else if (BluetoothService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // 显示所有用户界面上的支持服务和特色
                displayGattServices(mBluetoothLeService.getSupportedGattServices());
            } else if (BluetoothService.ACTION_DATA_AVAILABLE.equals(action)) {
                String data = intent.getStringExtra(BluetoothService.EXTRA_DATA);
                final String state = intent.getStringExtra("EXTRA_STATE");
                final Integer data1 = Integer.parseInt(data);
                new Thread() {
                    @Override
                    public void run() {
                        Message message = Message.obtain();
                        message.arg1 = data1;
                        message.obj = state;
                        handler.sendMessage(message);
                    }
                }.start();
            }
        }
    };

    //发现服务
    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices != null) {
            ArrayList<HashMap<String, String>> gattServiceData = new ArrayList();
            //ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData = new ArrayList();
            mGattCharacteristics = new ArrayList();
            for (BluetoothGattService gattService : gattServices) {
                HashMap<String, String> currentServiceData = new HashMap();
                String uuid = gattService.getUuid().toString();
                if (Objects.equals(uuid, "0000fff0-0000-1000-8000-00805f9b34fb")) {
                    currentServiceData.put("UUID", uuid);
                    gattServiceData.add(currentServiceData);
                    ArrayList<HashMap<String, String>> gattCharacteristicGroupData = new ArrayList();
                    List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
                    ArrayList<BluetoothGattCharacteristic> charas = new ArrayList();
                    for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                        charas.add(gattCharacteristic);
                        HashMap<String, String> currentCharaData = new HashMap();
                        currentCharaData.put("UUID", gattCharacteristic.getUuid().toString());
                        gattCharacteristicGroupData.add(currentCharaData);
                    }
                    mGattCharacteristics.add(charas);
                    if (mGattCharacteristics != null) {
                        BluetoothGattCharacteristic characteristic = (BluetoothGattCharacteristic) ((ArrayList) this.mGattCharacteristics.get(0)).get(3);
                        int charaProp = characteristic.getProperties();
                        if ((charaProp | 2) > 0) {
                            if (mNotifyCharacteristic != null) {
                                mBluetoothLeService.setCharacteristicNotification(this.mNotifyCharacteristic, false);
                                mNotifyCharacteristic = null;
                            }
                            mBluetoothLeService.readCharacteristic(characteristic);
                        }
                        if ((charaProp | 16) > 0) {
                            mNotifyCharacteristic = characteristic;
                            mBluetoothLeService.setCharacteristicNotification(characteristic, true);
                        }
                    }
                }
            }
        }
    }

    //懒加载回调
    private class LazyCallback implements BluetoothAdapter.LeScanCallback {
        @Override
        public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
            String name = bluetoothDevice.getName();
            String address = bluetoothDevice.getAddress();
            if (address != null) {
                Log.i("查到的地址:", address);
                //7C:EC:79:55:63:29
            }
            if (name != null && "BIGCARE_BC301".equals(name)) {
                //String address2 = bluetoothDevice.getAddress();
                mDeviceAddress = bluetoothDevice.getAddress();
                Log.i("---查到的名字-----:", mDeviceAddress);

                mBluetoothAdapter.stopLeScan(lazyCallback);

                Intent gattServiceIntent = new Intent(mContext, BluetoothService.class);
                Log.i("--------------", "" + getContext() + "--" + this);
                boolean ble = getApplicationContext().bindService(gattServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);//绑定服务
                if (ble) {
                    Log.i("绑定成功", "dd");
                } else {
                    Log.i("绑定失败", "dd");
                }
                Log.i("---------------", gattServiceIntent + "--" + mServiceConnection + "---" + Context.BIND_AUTO_CREATE);

                registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
            }
        }
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mDeviceAddress != null) {
            Intent gattServiceIntent = new Intent(this, BluetoothService.class);
            if (mBluetoothLeService == null)
                this.bindService(gattServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);//绑定服务
            this.registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        }
        Log.i("------onStart", "开始");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mBluetoothAdapter.isEnabled()) {
            Toast.makeText(mContext, "打开蓝牙成功", Toast.LENGTH_LONG).show();
            Log.i("打开蓝牙成功", "BluetoothConnection");
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        Toast.makeText(mContext, "打开蓝牙后", Toast.LENGTH_LONG).show();

        if (lazyCallback == null) {
            Log.i("lazyCallback", "lazyCallback  new前");
            lazyCallback = new ManyFoodWeighingActivity.LazyCallback();
        }
        Log.i("lazyCallback", "new 后");


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    boolean d = mBluetoothAdapter.startLeScan(lazyCallback);
                    Log.i("扫描状态：", d + "");
                } catch (Exception e) {
                    Log.i("异常：", e.toString() + "扫描异常");
                    e.printStackTrace();
                }

            }
        }).start();


        this.registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());

        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
//            Log.i(TAG, "连接请求的结果是否连接=" + result);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("------OnDestory", "销毁");


        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.disable();
        }

        mBluetoothLeService = null;

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mDeviceAddress != null) {
            if (mBluetoothLeService.isRestricted()) {
                if (mServiceConnection != null) {
                    if (mBluetoothLeService != null)
                        this.unbindService(mServiceConnection);
                }
            }
        }

        if (mDeviceAddress != null && mGattCharacteristics != null) {
            this.unregisterReceiver(mGattUpdateReceiver);
        }
        Log.i("------onStop", "停止");

    }

    /**  蓝牙模块结束 */


}
