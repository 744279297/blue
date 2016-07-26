package com.example.zhangchi.blue.ui.water;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.example.zhangchi.blue.service.BlueToothService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangchi on 2016/5/8.
 */
@SuppressLint("NewApi")
public class WaterPresenter implements WaterContract.Presenter {
    private WaterContract.View view;
    private BluetoothReceiver receiver;




    BluetoothAdapter adapter;

    public WaterPresenter(WaterContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void getWaterValue() {

    }

    @Override
    public void getWaterRatio() {

    }

    @Override
    public void getWaveUpdate() {

    }

    @Override
    public void getBlueTooth(Activity activity) {
        view.loadDialog(true);
        Intent startService = new Intent(activity, BlueToothService.class);
        activity.startService(startService);
        //得到BluetoothAdapter对象
        Intent intent;
        adapter = BluetoothAdapter.getDefaultAdapter();
        //判断BluetoothAdapter对象是否为空，如果为空，则表明本机没有蓝牙设备
        if (adapter != null) {
            System.out.println("本机拥有蓝牙设备");
            //调用isEnabled()方法判断当前蓝牙设备是否可用
            if (!adapter.isEnabled()) {
                //如果蓝牙设备不可用的话,创建一个intent对象,该对象用于启动一个Activity,提示用户启动蓝牙适配器
                intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                activity.startActivity(intent);

            }
            // Device scan callback.
            receiver = new BluetoothReceiver();
            activity.registerReceiver(receiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
            activity.registerReceiver(receiver, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED));
            adapter.startDiscovery();
            //得到所有已经配对的蓝牙适配器对象
//            Set<BluetoothDevice> devices = adapter.getBondedDevices();
//            if (devices.size() > 0) {
//                //用迭代
//                for (Iterator iterator = devices.iterator(); iterator.hasNext(); ) {
//                    //得到BluetoothDevice对象,也就是说得到配对的蓝牙适配器
//                    BluetoothDevice device = (BluetoothDevice) iterator.next();
//                    //得到远程蓝牙设备的地址
//                    Log.d("mytag", device.getAddress());
//
//                }
//            }
        } else {
            System.out.println("没有蓝牙设备");
        }

    }

    @Override
    public void closeBlueTooth(Activity activity) {
        activity.unregisterReceiver(receiver);
    }

    @Override
    public void connectBLueTooth(BluetoothDevice device) {

    }

    private class BluetoothReceiver extends BroadcastReceiver {
        private List<BluetoothDevice> bluetoothDevices = new ArrayList<>();

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                //只要BluetoothReceiver接收到来自于系统的广播,这个广播是什么呢,是我找到了一个远程蓝牙设备
                //Intent代表刚刚发现远程蓝牙设备适配器的对象,可以从收到的Intent对象取出一些信息
                BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                bluetoothDevices.add(bluetoothDevice);
                view.loadDialog(false);
                view.showBLueTooth(bluetoothDevices);
            }
            if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {

            }
        }


    }


    @Override
    public void start() {

    }

}
