package com.example.zhangchi.blue.event;

import android.bluetooth.BluetoothSocket;

/**
 * Created by zhangchi on 2016/5/23.
 */
public class ConnectedBluetooth {
    public BluetoothSocket socket;
    public ConnectedBluetooth(BluetoothSocket socket){
        this.socket = socket;
    }
}
