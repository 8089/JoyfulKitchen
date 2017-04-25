package com.joyful.joyfulkitchen.fragment;

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
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.activity.FoodTypeSelectActivity;
import com.joyful.joyfulkitchen.adapter.TabFragmentPageAdapter;
import com.joyful.joyfulkitchen.dao.FoodDao;
import com.joyful.joyfulkitchen.dao.GreenDaoManager;
import com.joyful.joyfulkitchen.model.Food;
import com.joyful.joyfulkitchen.service.BluetoothService;
import com.joyful.joyfulkitchen.util.ToastUtils;
import com.joyful.joyfulkitchen.util.UnitConversionUtil;
import com.joyful.joyfulkitchen.view.RoundIndicatorView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.joyful.joyfulkitchen.RefreshAndLoad.PullToRefreshLayout.TAG;


// 健康饮食
public class HealthyFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_Name = "ARG_Name";

    private String mParam1;

    private RoundIndicatorView mRoundIndicatorView;
    private TextView tv_ke, tv_show_unit;
    private Toolbar toolbar;
    private int index = 0;
    private String[] units = {"克", "两", "磅", "毫升", "安士"};

    private TextView tv_food_select;

    // -----------
    private BluetoothAdapter.LeScanCallback lazyCallback;

    private BluetoothAdapter mBluetoothAdapter;
    private String mDeviceAddress = null;
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics = new ArrayList();
    private BluetoothGattCharacteristic mNotifyCharacteristic;
    private BluetoothService mBluetoothLeService;
    private static final int REQUEST_ENABLE_BT = 0;



    private List<Food> foods;

    private TabLayout tabLayout;
    private TabFragmentPageAdapter tabFragmentPageAdapter;

    protected boolean isVisible;


    private Handler handler = new Handler(){
        Intent intent = new Intent();
        @Override
        public void handleMessage(Message msg) {

            int a = msg.arg1 ;
            String s = (String )msg.obj ;
            a = Conversion(a, index);
            mRoundIndicatorView.setCurrentNumAnim(a);
            tv_show_unit.setText(a + units[index]);
            char c1 = s.charAt(2) ;
            char c2 = s.charAt(6) ;
            Log.i("----------状态-----","第二个" + c1 + "第6个" + c2);
        }
    };

    // 传入 g ，根据单位 转换
    private int Conversion(int a, int index) {
        int rs = 0;
        switch (index){
            case 0:
                rs = a;
                break;
            case 1:
                rs = UnitConversionUtil.g2Two(a);
                break;
            case 2:
                rs = UnitConversionUtil.g2Lb(a);
                break;
            case 3:
                rs = UnitConversionUtil.g2Ml(a);
                break;
            case 4:
                rs = UnitConversionUtil.g2Oz(a);
                break;
        }
        return rs;
    }


    /**
     * 在这里实现Fragment数据的缓加载.
     * @param
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(getUserVisibleHint()) {
            isVisible = true;
        } else {
            isVisible = false;
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    public static HealthyFragment newInstance(String param1) {
        HealthyFragment fragment = new HealthyFragment();
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

        View view = inflater.inflate(R.layout.fragment_healthy, container, false);

        // 初始化view
        initView(view);

        // 添加监听事件
        setOnListeners();

        initLanya();

        return view;
    }



    private void initView(View view) {
        setHasOptionsMenu(true);
        toolbar = (Toolbar) view.findViewById(R.id.home_toolbar);
        toolbar.setTitle(null);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_search:
                        ToastUtils.showToast(getContext(), "action_search");
                        break;
                    case R.id.action_settings:
                        ToastUtils.showToast(getContext(), "action_settings");
                        break;
                }
                return true;
            }
        });

        mRoundIndicatorView = (RoundIndicatorView) view.findViewById(R.id.riv_view);
        tv_ke = (TextView) view.findViewById(R.id.tv_ke);
        tv_show_unit = (TextView) view.findViewById(R.id.tv_show_unit);
        tv_show_unit.setText("0g");

        tv_food_select = (TextView) view.findViewById(R.id.tv_food_select);

        FoodDao foodDao = GreenDaoManager.getInstance().getSession().getFoodDao();
        foods = foodDao.loadAll();

        //Fragment+ViewPager+FragmentViewPager组合的使用
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager_home);
        tabFragmentPageAdapter = new TabFragmentPageAdapter(getActivity().getSupportFragmentManager(),getContext(), foods);
        viewPager.setAdapter(tabFragmentPageAdapter);

        //TabLayout
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout_home);
        tabLayout.setupWithViewPager(viewPager);




    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.home_menu, menu);
    }

    private void setOnListeners() {


        tv_ke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getContext())
                        .title(R.string.unit_title)
                        .items(R.array.unit)
                        .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                                tv_ke.setText(text);
                                onSwitchUnit(which);

                                return true;
                            }
                        })
//                        .widgetColor(Color.GRAY)  改按钮颜色
                        //widgetColor(), widgetColorRes(), widgetColorAttr(), and choiceWidgetColor()
                        .positiveText(R.string.unit_positive)
                        .show();
            }
        });

        tv_food_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FoodTypeSelectActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }

    private void onSwitchUnit(int which) {
        //克,两,磅,毫升,安士
        switch (which){
            case 0:
                index = 0;
                break;
            case 1:
                index = 1;
                break;
            case 2:
                index = 2;
                break;
            case 3:
                index = 3;
                break;
            case 4:
                index = 4;
                break;
        }

    }


    private void initLanya() {
        //打开蓝牙
        BluetoothManager bluetoothManager =
                (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);

        mBluetoothAdapter = bluetoothManager.getAdapter();

    }

    // 代码管理服务生命周期
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        //服务保持连接
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {

            mBluetoothLeService = ((BluetoothService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.i(TAG, "无法初始化蓝牙");
//                finish();
            }

            Toast.makeText(getContext(), "连接设备", Toast.LENGTH_LONG).show();
            // 自动连接到设备成功启动初始化。
            mBluetoothLeService.connect(mDeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(TAG, "无法连接");
            mBluetoothLeService = null;
        }
    };
    //广播接收
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothService.ACTION_GATT_CONNECTED.equals(action)) {
                Toast.makeText(getContext(), "连接成功", Toast.LENGTH_LONG).show();
            } else if (BluetoothService.ACTION_GATT_DISCONNECTED.equals(action)) {
                Toast.makeText(getContext(), "断开连接", Toast.LENGTH_LONG).show();
            } else if (BluetoothService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // 显示所有用户界面上的支持服务和特色
                displayGattServices(mBluetoothLeService.getSupportedGattServices());
            } else if (BluetoothService.ACTION_DATA_AVAILABLE.equals(action)) {
                String data = intent.getStringExtra(BluetoothService.EXTRA_DATA);
                final String state = intent.getStringExtra("EXTRA_STATE");
                final Integer data1 = Integer.parseInt(data);
                new Thread(){
                    @Override
                    public void run() {
                        Message message = Message.obtain();
                        message.arg1 = data1 ;
                        message.obj = state ;
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

                Intent gattServiceIntent = new Intent(getActivity().getApplicationContext(), BluetoothService.class);
                Log.i("--------------",""+getContext()+"--"+getActivity());
                boolean ble = getActivity().getApplicationContext().bindService(gattServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);//绑定服务
                if (ble) {
                    Log.i("绑定成功", "dd");
                } else {
                    Log.i("绑定失败", "dd");
                }
                Log.i("---------------",gattServiceIntent+"--"+mServiceConnection+"---"+Context.BIND_AUTO_CREATE);

                getActivity().registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
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
            Intent gattServiceIntent = new Intent(getActivity(), BluetoothService.class);
            if(mBluetoothLeService ==null)
                getActivity().bindService(gattServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);//绑定服务
            getActivity().registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        }
        Log.i("------onStart", "开始");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mBluetoothAdapter.isEnabled()) {
            Toast.makeText(getContext(), "打开蓝牙成功", Toast.LENGTH_LONG).show();
            Log.i("打开蓝牙成功", "BluetoothConnection");
            Intent enableBtIntent = new Intent( BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        Toast.makeText(getContext(), "打开蓝牙后",Toast.LENGTH_LONG).show();

        if (lazyCallback == null) {
            Log.i("lazyCallback","lazyCallback  new前");
            lazyCallback = new HealthyFragment.LazyCallback();
        }
        Log.i("lazyCallback","new 后");


        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    boolean d = mBluetoothAdapter.startLeScan(lazyCallback);
                    Log.i("扫描状态：",d + "");
                }catch (Exception e){
                    Log.i("异常：",e.toString()+"扫描异常");
                    e.printStackTrace();
                }

            }
        }).start();


        getActivity().registerReceiver(mGattUpdateReceiver,makeGattUpdateIntentFilter());

        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
            Log.i(TAG, "连接请求的结果是否连接=" + result);
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
            if (mBluetoothLeService.isRestricted()){
                if(mServiceConnection != null) {
                    if(mBluetoothLeService != null)
                        getActivity().unbindService(mServiceConnection);
                }
            }
        }

        if (mDeviceAddress != null && mGattCharacteristics != null) {
            getActivity().unregisterReceiver(mGattUpdateReceiver);
        }
        Log.i("------onStop", "停止");

    }

}
