package com.example.zhangchi.blue.ui.water;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;

import com.example.zhangchi.blue.BasePresenter;
import com.example.zhangchi.blue.BaseView;

import java.util.List;

/**
 * Created by zhangchi on 2016/5/7.
 */
public interface WaterContract {
    public interface Presenter extends BasePresenter {
        void getWaterValue();

        void getWaterRatio();

        void getWaveUpdate();

        void getBlueTooth(Activity activity);

        void closeBlueTooth(Activity activity);

        void connectBLueTooth(BluetoothDevice device);


    }

    public interface View extends BaseView<Presenter> {
        void showWaterValue(int value);

        void showWaterRatio(int ratio);

        void showWaveUpdate();

        void showAddWaterRecord();

        void showWaterRecord();

        void showStatistics();

        void showControl();

        void showBLueTooth(List<BluetoothDevice> bluetoothDevices);

        void showControlView();

        void dismissBlueTooth();

        void loadDialog(boolean isShow);
    }

}
