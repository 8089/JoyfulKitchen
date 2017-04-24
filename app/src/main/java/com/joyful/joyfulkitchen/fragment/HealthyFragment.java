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
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.activity.FoodSelectActivity;
import com.joyful.joyfulkitchen.service.BluetoothService;
import com.joyful.joyfulkitchen.util.ToastUtils;
import com.joyful.joyfulkitchen.view.RoundIndicatorView;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import noman.weekcalendar.WeekCalendar;
import noman.weekcalendar.listener.OnDateClickListener;

import static com.joyful.joyfulkitchen.RefreshAndLoad.PullToRefreshLayout.TAG;

// 健康饮食
public class HealthyFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_Name = "ARG_Name";

    private String mParam1;

    private BluetoothAdapter.LeScanCallback lazyCallback;

    private BluetoothAdapter mBluetoothAdapter;
    private String mDeviceAddress =null;
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics = new ArrayList();
    private BluetoothGattCharacteristic mNotifyCharacteristic;
    private BluetoothService mBluetoothLeService;
    private static final int REQUEST_ENABLE_BT = 0;


    private RoundIndicatorView roundIndicatorView;
    private EditText editText;
    private TextView tv_food_select;
    private WeekCalendar weekCalendar;

    protected boolean isVisible;
    /**
     * 在这里实现Fragment数据的缓加载.
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
        } else {
            isVisible = false;
        }
    }


    private Handler handler = new Handler(){
        Intent intent = new Intent();
        @Override
        public void handleMessage(Message msg) {

            int a = msg.arg1 ;
            String s = (String )msg.obj ;
            roundIndicatorView.setCurrentNumAnim(a) ;
            editText.setText(a+"") ;
            char c1 = s.charAt(2) ;
            char c2 = s.charAt(6) ;
            Log.i("----------状态-----","第二个"+c1+"第6个"+c2);
        }
    };

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

        initView(view);
        setOnListeners();

        initLanya();

        return view;
    }

    private void initLanya() {
        //打开蓝牙
        BluetoothManager bluetoothManager =
                (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);

        mBluetoothAdapter = bluetoothManager.getAdapter();

    }

    private void initView(View view) {
        tv_food_select = (TextView) view.findViewById(R.id.tv_food_select);
        weekCalendar = (WeekCalendar) view.findViewById(R.id.weekCalendar);
        editText = (EditText) view.findViewById(R.id.edit);
        roundIndicatorView = (RoundIndicatorView) view.findViewById(R.id.riv_view);
    }

    private void setOnListeners() {
        tv_food_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FoodSelectActivity.class);
                startActivityForResult(intent, 1);
            }
        });


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ToastUtils.showToast(getContext(), "beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    if(s.length() >0 ) {
                        int a = Integer.valueOf(s.toString());
                        roundIndicatorView.setCurrentNumAnim(a);
                    }else{
                        roundIndicatorView.setCurrentNumAnim(0);
                    }
                } catch (Exception e){
                    ToastUtils.showToast(getContext(), "数字太大了");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        weekCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(DateTime dateTime) {
                ToastUtils.showToast(getContext(), "您选择的日期是:" + dateTime.toLocalDate());
            }
        });

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
            lazyCallback = new LazyCallback();
        }
        Log.i("lazyCallback","new 后");


        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    boolean d = mBluetoothAdapter.startLeScan(lazyCallback);
                    Log.i("扫描状态：",d+"");
                }catch (Exception e){
                    Log.i("异常：",e.toString()+"扫描异常");
                    e.printStackTrace();
                }

            }
        }).start();


        /*registerReceiver(mGattUpdateReceiver,makeGattUpdateIntentFilter());*/

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
