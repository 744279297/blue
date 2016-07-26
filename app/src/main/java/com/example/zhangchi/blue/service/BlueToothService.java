package com.example.zhangchi.blue.service;

import android.app.Activity;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.zhangchi.blue.event.BluetoothMsg;
import com.example.zhangchi.blue.event.ConnectBluetooth;
import com.example.zhangchi.blue.event.ConnectedBluetooth;
import com.example.zhangchi.blue.event.SendMsg;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by zhangchi on 2016/5/18.
 */
public class BlueToothService extends Service {
    private ConnectThread connectThread;
    private ConnectedThread connectedThread;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("service", "create");
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        connectThread.cancel();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
    @Subscribe
    public void connect(ConnectBluetooth connectBluetooth) {
        connectThread = new ConnectThread(connectBluetooth.device, BluetoothAdapter.getDefaultAdapter());
        connectThread.start();
    }

    @Subscribe
    public void connected(ConnectedBluetooth connectedBluetooth) {
        connectedThread = new ConnectedThread(connectedBluetooth.socket, BluetoothAdapter.getDefaultAdapter());
        connectedThread.start();
    }

    @Subscribe
    public void send(SendMsg sendMsg) {
        connectedThread.write(sendMsg.msg);
    }
}

class ConnectedThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;

    public ConnectedThread(BluetoothSocket socket, BluetoothAdapter adapter) {

        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
        }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    public void run() {
        byte[] buffer = new byte[1024];

        String msg = null;
        while (true) {
            try {
                int i = mmInStream.read(buffer);
                Log.d("service1", String.valueOf(i));
                for (int j = 0; j < i; j++) {
                    if ((int) buffer[j] == -1) {
                        msg = "";
                        continue;
                    }
                    if ((int) buffer[j] == -2) {
                        Log.d("service", msg);
                        EventBus.getDefault().post(new BluetoothMsg(msg));
                        msg = "";
                        continue;
                    }
                    msg += String.valueOf(buffer[j]);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public static int byte2Int(byte[] b) {
        int l = 0;
        l = b[0];
        l &= 0xff;
        l |= ((int) b[1] << 8);
        l &= 0xffff;
        l |= ((int) b[2] << 16);
        l &= 0xffffff;
        l |= ((int) b[3] << 24);
        l &= 0xffffffff;
        return l;
    }

    /* Call this from the main activity to send data to the remote device */
    public void write(byte[] bytes) {
        try {
            mmOutStream.write(bytes);
        } catch (IOException e) {
        }
    }

    /* Call this from the main activity to shutdown the connection */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
        }
    }

}


//class AcceptThread extends Thread {
//    private final BluetoothServerSocket mmServerSocket;
//    private final UUID uuid = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
//
//    public AcceptThread(BluetoothAdapter mBluetoothAdapter) {
//        // Use temporary object that is later assigned to mmServerSocket,
//        // because mmServerSocket is final
//        BluetoothServerSocket tmp = null;
//        try {
//            // MY_UUID is the app's UUID string, also used by the client code
//            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("blue", uuid);
//        } catch (IOException e) {
//        }
//        mmServerSocket = tmp;
//    }
//
//    public void run() {
//        BluetoothSocket socket = null;
//        // Keep listening until exception occurs or a socket is returned
//        while (true) {
//            try {
//                socket = mmServerSocket.accept();
//            } catch (IOException e) {
//                break;
//            }
//            // If a connection was accepted
//            if (socket != null) {
//                // Do work to manage the connection (in a separate thread)
//
//                try {
//                    mmServerSocket.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                break;
//            }
//        }
//    }
//
//    /**
//     * Will cancel the listening socket, and cause the thread to finish
//     */
//    public void cancel() {
//        try {
//            mmServerSocket.close();
//        } catch (IOException e) {
//        }
//    }
//}

class ConnectThread extends Thread {
    private final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private final BluetoothAdapter mBluetoothAdapter;

    public ConnectThread(BluetoothDevice device, BluetoothAdapter adapter) {
        // Use a temporary object that is later assigned to mmSocket,
        // because mmSocket is final
        BluetoothSocket tmp = null;
        mmDevice = device;
        mBluetoothAdapter = adapter;
        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            tmp = mmDevice.createRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) {
        }
        mmSocket = tmp;
    }

    public void run() {
        // Cancel discovery because it will slow down the connection
        mBluetoothAdapter.cancelDiscovery();

        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            mmSocket.connect();
            EventBus.getDefault().post(new ConnectedBluetooth(mmSocket));
            Log.d("service", "connect");
        } catch (IOException connectException) {
            Log.d("service", "shibai");
            // Unable to connect; close the socket and get out
            try {
                mmSocket.close();
            } catch (IOException closeException) {
            }
            return;
        }

//            // Do work to manage the connection (in a separate thread)
//            manageConnectedSocket(mmSocket);
    }

    /**
     * Will cancel an in-progress connection, and close the socket
     */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
        }
    }
}