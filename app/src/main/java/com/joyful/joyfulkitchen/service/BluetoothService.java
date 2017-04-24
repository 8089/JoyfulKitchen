package com.joyful.joyfulkitchen.service;


import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.joyful.joyfulkitchen.util.RateUtil;

import java.util.List;
import java.util.UUID;

public class BluetoothService extends Service{


    private final static String TAG = BluetoothService.class.getSimpleName();

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt mBluetoothGatt;

    private int MConnectionState = STATE_DISCONNECTED;

    private String mBluetoothDeviceAddress;

    private final IBinder mBinder = new LocalBinder();

    private static final int STATE_DISCONNECTED = 0; //断开
    private static final int STATE_CONNECTING = 1; //连接
    private static final int STATE_CONNECTED = 2; //有链接的

    public final static String ACTION_GATT_CONNECTED =
            "com.example.bluetooth.le.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED =
            "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED =
            "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE =
            "com.example.bluetooth.le.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_DATA =
            "com.example.bluetooth.le.EXTRA_DATA";

    //这就是我们项目蓝牙要用到的uuid
    public final static UUID UUID_WEIGHT = UUID.fromString("00002a37-0000-1000-8000-00805f9b34fb");

    private Handler myHandler ;

    String intentAction;
    //
    private final BluetoothGattCallback mBluetoothGattCallback = new BluetoothGattCallback() {

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            Log.i(TAG, "onConnectionStateChange");
            //连通状态
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                intentAction = ACTION_GATT_CONNECTED;
                MConnectionState = STATE_CONNECTED;
                broadcastUpdate(intentAction);
                //试图打开服务
                mBluetoothGatt.discoverServices();
            } else if (newState == 0) {
                Log.i("断开连接？", "ss");
                if (mBluetoothGatt != null) {
                    intentAction = ACTION_GATT_SERVICES_DISCOVERED;
                    MConnectionState = STATE_CONNECTING;
                    broadcastUpdate(intentAction);
                    Log.i("state=0", "000000000");
                    mBluetoothGatt.discoverServices();
                }
            }
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {

            System.out.println(gatt.getDevice() + " RSSI:" + rssi + "db ");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gatt.readRemoteRssi();

        }





        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            Log.i(TAG,"onServicesDiscovered");
            if (status == 0) {
                broadcastUpdate(BluetoothService.ACTION_GATT_SERVICES_DISCOVERED);
            }else {
                Log.i("已收到",status+"");
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            Log.i(TAG,"onCharacteristicRead");
            if (status == 0) {
                broadcastUpdate(BluetoothService.ACTION_DATA_AVAILABLE,characteristic);
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            Log.i(TAG,"onCharacteristicChanged");
            broadcastUpdate(BluetoothService.ACTION_DATA_AVAILABLE,characteristic);
        }
    };

    //广播更新
    private void broadcastUpdate(final String action){
        Log.i(TAG,"广播更新1");
        final Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    //广播更新
    private void broadcastUpdate(final String action,final BluetoothGattCharacteristic characteristic){
        Log.i(TAG,"广播更新2");
        final Intent intent = new Intent(action);
        if (UUID_WEIGHT.equals(characteristic.getUuid())) {
            int format;
            if ((characteristic.getProperties() & STATE_CONNECTING) != 0) {
                format = 18;
                Log.d(TAG, "Heart rate format UINT16.");
            } else {
                format = 17;
                Log.d(TAG, "Heart rate format UINT8.");
            }
            int heartRate = characteristic.getIntValue(format, STATE_CONNECTING).intValue();
            String str = TAG;
            Object[] objArr = new Object[STATE_CONNECTING];
            objArr[0] = Integer.valueOf(heartRate);
            Log.d(str, String.format("Received heart rate: %d", objArr));
            intent.putExtra(EXTRA_DATA, String.valueOf(heartRate));
        } else {
            byte[] data = characteristic.getValue();
            if (data != null && data.length > 0) {
                StringBuilder stringBuilder = new StringBuilder(data.length);
                int length = data.length;
                for (int i = 0; i < length; i += STATE_CONNECTING) {
                    Object[] objArr2 = new Object[STATE_CONNECTING];
                    objArr2[0] = Byte.valueOf(data[i]);
                    stringBuilder.append(String.format("%02X ", objArr2));
                }
                int n = (stringBuilder.toString()).length();
                String state = RateUtil.hexString2binaryString((stringBuilder.toString().substring(0,2)));
                Integer total = (Integer.parseInt((stringBuilder.toString()).substring(3,5),16)*255+ Integer.parseInt((stringBuilder.toString()).substring(n-9,n-7),16));
                intent.putExtra("EXTRA_STATE",state);
                intent.putExtra(EXTRA_DATA, total+"");
            }
        }
        sendBroadcast(intent);
    }

    public class LocalBinder extends Binder {
        public BluetoothService getService(){
            Log.i(TAG,"LocalBinder");
            return BluetoothService.this;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"IBinder");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG,"onUnbind");
        /*
        * 使用一个给定的设备后,你应该确保BluetoothGatt.close() / /这样资源清理干净。
        * 在这个特殊的例子中,() / /调用UI时断开服务
        * */
        close();
        return super.onUnbind(intent);
    }

    /*使用给定的BLE设备后,应用程序必须调用这个方法,以确保资源 *正常发布。*/
    private void close() {
        Log.i(TAG,"close");
        if (mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    /**
     * 初始化本地蓝牙适配器的引用。
     * @return true :初始化成功
     */
    public boolean initialize(){
        Log.i(TAG,"initialize");
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null) {
                Log.i(TAG,"无法初始化mBluetoothManager");
                return false;
            }
        }
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            Log.i(TAG,"无法获得BluetoothAdapter");
            return false;
        }
        return true;
    }

    /**
     *连接到关贸总协定服务器托管在蓝牙设备
     * @param address 目的设备的设备地址
     * @return 如果返回的是true 连接成功启动,连接的结果 *通过异步报道
     */
    public boolean connect(final String address){
        Log.i(TAG,"connect");
        if (mBluetoothAdapter == null || address == null) {
            Log.i(TAG,"BluetoothAdapter没有初始化或未指明的地址。");
            return false;
        }
        if (mBluetoothDeviceAddress != null && address.equals(mBluetoothDeviceAddress) && mBluetoothGatt != null) {
            Log.i("0-------0",mBluetoothDeviceAddress+"---"+address+"+++++"+mBluetoothGatt);
            if (mBluetoothGatt.connect()) {
                MConnectionState = STATE_CONNECTING;
                return true;
            }else {
                return false;
            }
        }
        //设备
        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            Log.i(TAG,"设备没找到，无法连接");
            return false;
        }
        //我们想直接连接到设备,所以我们设置符
        mBluetoothGatt = device.connectGatt(getApplicationContext(),false,mBluetoothGattCallback);
        Log.i(TAG, "尝试创建一个新的连接");
        mBluetoothDeviceAddress = address;
        MConnectionState = STATE_CONNECTING;
        return true;
    }

    /**
     * 取消等待或断开一个现有的连接。断开的结果 *通过异步报道
     */
    public void discinnect(){
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.i(TAG, "BluetoothAdapter 没有初始化");
            return;
        }
        mBluetoothGatt.disconnect();
    }

    public void readCharacteristic(BluetoothGattCharacteristic characteristic){
        Log.i(TAG,"readCharacteristic");
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.i(TAG, "BluetoothAdapter 没有初始化");
            return;
        }
        mBluetoothGatt.readCharacteristic(characteristic);
    }

    /**
     * 启用或禁用通知给 characteristic
     * @param characteristic
     * @param enabled
     */
    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic,boolean enabled){
        Log.i(TAG,"setCharacteristicNotification");
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.i(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.setCharacteristicNotification(characteristic,enabled);

        if (UUID_WEIGHT.equals(characteristic.getUuid())) {
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(characteristic.getUuid());
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            mBluetoothGatt.writeDescriptor(descriptor);
        }
    }

    /*
    * 检索列表支持关贸总协定在连接设备上的服务。
    * 这应该是之后才调用* { @code BluetoothGatt # discoverServices()}成功完成。
    * */
    public List<BluetoothGattService> getSupportedGattServices(){
        Log.i(TAG,"getSupportedGattServices");
        if (mBluetoothGatt == null) {
            return null;
        }
        Log.i(TAG,""+mBluetoothGatt.getServices());
        return mBluetoothGatt.getServices();
    }
}
