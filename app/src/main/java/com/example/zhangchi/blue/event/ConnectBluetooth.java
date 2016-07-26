package com.example.zhangchi.blue.event;

import android.bluetooth.BluetoothDevice;

/**
 * Created by zhangchi on 2016/5/22.
 */
public class ConnectBluetooth {
    public boolean isConnected;
    public BluetoothDevice device;

    public ConnectBluetooth(boolean isConnected, BluetoothDevice device) {
        this.isConnected = isConnected;
        this.device = device;
    }
}
