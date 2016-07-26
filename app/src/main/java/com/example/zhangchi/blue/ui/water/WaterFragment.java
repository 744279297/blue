package com.example.zhangchi.blue.ui.water;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.zhangchi.blue.BaseFragment;
import com.example.zhangchi.blue.R;
import com.example.zhangchi.blue.event.BluetoothMsg;
import com.example.zhangchi.blue.event.ConnectBluetooth;
import com.example.zhangchi.blue.event.SendMsg;
import com.example.zhangchi.blue.service.BlueToothService;
import com.example.zhangchi.blue.ui.statistics.StatisticsActivity;
import com.example.zhangchi.blue.ui.statistics.StatisticsContract;
import com.example.zhangchi.blue.ui.widget.BlueToothHeadView;
import com.example.zhangchi.blue.ui.widget.WaterVIew;
import com.example.zhangchi.blue.util.BluetoothUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhangchi on 2016/5/7.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
@ContentView(R.layout.content_water)
public class WaterFragment extends BaseFragment implements WaterContract.View {
    private String lowString = "";
    private String highString = "";
    private String startString = "";
    private String endString = "";
    private int ll = 2240;
    private ArrayList<String> times = new ArrayList<>();

    @ViewInject(R.id.water_view)
    private WaterVIew waterView;

    @ViewInject(R.id.bluetooth_list)
    private ListView bluetoothLv;

    @ViewInject(R.id.now_water_value)
    private TextView nowWaterValue;

    @ViewInject(R.id.control)
    private View control;

    @ViewInject(R.id.control_view)
    private View controlView;

    @Event(R.id.close)
    private void close(View view) {
        byte[] bytes = new byte[]{49, 49, 49, 49, 49, 49, 49, 49, 49, 49};
        EventBus.getDefault().post(new SendMsg(bytes));
    }

    @Event(R.id.open)
    private void open(View view) {
        byte[] bytes = new byte[]{48, 48, 48, 48, 48, 48, 48, 48, 48, 48};
        EventBus.getDefault().post(new SendMsg(bytes));
    }

    @Event(R.id.close_control_view)
    private void closeControlView(View view) {
        controlView.setVisibility(View.GONE);
    }

    @Event(R.id.close_control)
    private void closeControl(View view) {
        control.setVisibility(View.GONE);
    }

    ProgressDialog dialog;
    private WaterContract.Presenter presenter;
    @ViewInject(R.id.low_edit)
    private EditText lowEdit;

    @ViewInject(R.id.high_edit)
    private EditText highEdit;

    @ViewInject(R.id.start_time)
    private Spinner startEdit;

    @ViewInject(R.id.end_time)
    private Spinner endEdit;

    @Event(R.id.send)
    private void submit(View view) {
        lowString = lowEdit.getText().toString();
        highString = highEdit.getText().toString();


//        startString = startEdit.getText().toString();
//        endString = endEdit.getText().toString();
        if (lowString.length() > 1) {
            int low = Integer.parseInt(lowString);
            low += ll;
            lowString = "2" + String.valueOf(low);
            char[] buffer = new char[5];
            lowString.getChars(0, 5, buffer, 0);
            byte[] bytes = new byte[1];
            for (int i = 0; i < 5; i++) {
                Log.d("char", String.valueOf((int) buffer[4 - i]));
                bytes[0] = (byte) buffer[4 - i];
                EventBus.getDefault().post(new SendMsg(bytes));
            }

        }
        if (highString.length() > 1) {
            int high = Integer.parseInt(highString);
            high += ll;
            highString = "2" + String.valueOf(high);
            Log.d("string", highString);
            highString = "1" + highString;
            char[] buffer = new char[5];
            highString.getChars(0, 5, buffer, 0);
            byte[] bytes = new byte[1];
            for (int i = 0; i < 5; i++) {
                Log.d("char", String.valueOf((int) buffer[4 - i]));
                bytes[0] = (byte) buffer[4 - i];
                EventBus.getDefault().post(new SendMsg(bytes));
            }
        }
        if ("5s".equals(startString)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);
                        byte[] bytes = new byte[]{48, 48, 48, 48, 48, 48, 48, 48, 48, 48};
                        EventBus.getDefault().post(new SendMsg(bytes));
                        close();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        if ("10s".equals(startString)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10000);
                        byte[] bytes = new byte[]{48, 48, 48, 48, 48, 48, 48, 48, 48, 48};
                        EventBus.getDefault().post(new SendMsg(bytes));
                        close();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        if ("15ss".equals(startString)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(15000);
                        byte[] bytes = new byte[]{48, 48, 48, 48, 48, 48, 48, 48, 48, 48};
                        EventBus.getDefault().post(new SendMsg(bytes));
                        close();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

//        if (!"".equals(startString)) {
//            char[] buffer = new char[4];
//            lowString.getChars(0, 4, buffer, 0);
//            byte[] bytes = new byte[1];
//            for (int i = 0; i < 4; i++) {
//                Log.d("char", String.valueOf((int) buffer[3 - i]));
//                bytes[0] = (byte) buffer[3 - i];
//                EventBus.getDefault().post(new SendMsg(bytes));
//            }
//        }
//        if (!"".equals(endString)) {
//            char[] buffer = new char[4];
//            lowString.getChars(0, 4, buffer, 0);
//            byte[] bytes = new byte[1];
//            for (int i = 0; i < 4; i++) {
//                Log.d("char", String.valueOf((int) buffer[3 - i]));
//                bytes[0] = (byte) buffer[3 - i];
//                EventBus.getDefault().post(new SendMsg(bytes));
//            }
//        }
    }

    private void close() {
        Log.d("time", endString);
        if ("5s".equals(endString)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);
                        byte[] bytes = new byte[]{49, 49, 49, 49, 49, 49, 49, 49, 49, 49};
                        EventBus.getDefault().post(new SendMsg(bytes));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        if ("10s".equals(endString)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10000);
                        byte[] bytes = new byte[]{49, 49, 49, 49, 49, 49, 49, 49, 49, 49};
                        EventBus.getDefault().post(new SendMsg(bytes));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        if ("15s".equals(endString)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(15000);
                        byte[] bytes = new byte[]{49, 49, 49, 49, 49, 49, 49, 49, 49, 49};
                        EventBus.getDefault().post(new SendMsg(bytes));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDatas();

        EventBus.getDefault().register(this);
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, times);
        startEdit.setAdapter(adapter);
        startEdit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                startString = times.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, times);
        endEdit.setAdapter(adapter1);
        endEdit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                endString = times.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dialog = new ProgressDialog(getActivity());
        waterView = waterView.setWaveColor(0xff0099CC)
                .setAmplitude(20)
                .setPalstance(0.5f)
                .setRefreshTime(4);
    }

    @Event(value = R.id.record)
    private void toStatistics(View view) {
        showStatistics();
    }

    @Override
    public void showWaterValue(int value) {

    }

    @Override
    public void showWaterRatio(int ratio) {

    }

    @Override
    public void showWaveUpdate() {

    }

    @Override
    public void showAddWaterRecord() {

    }

    @Override
    public void showWaterRecord() {

    }

    @Override
    public void showStatistics() {
        Intent intent = new Intent(getContext(), StatisticsActivity.class);
        startActivity(intent);
    }

    @Override
    public void showControl() {
        control.setVisibility(View.VISIBLE);
    }

    @Override
    public void showBLueTooth(final List<BluetoothDevice> bluetoothDevices) {
        if (bluetoothLv.getHeaderViewsCount() == 0) {
            BlueToothHeadView view = new BlueToothHeadView(getActivity());
            bluetoothLv.addHeaderView(view);
            view.clickClose(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissBlueTooth();
                }
            });
        }

        BlueToothDevicesAdapter devicesAdapter = new BlueToothDevicesAdapter(bluetoothDevices);
        bluetoothLv.setAdapter(devicesAdapter);
        bluetoothLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    EventBus.getDefault().post(new ConnectBluetooth(true, bluetoothDevices.get(position - 1)));
                    bluetoothLv.setVisibility(View.GONE);
                } catch (Exception e) {
                    Log.d("shibai", "shibai");
                    e.printStackTrace();
                }
            }
        });
        bluetoothLv.setVisibility(View.VISIBLE);
    }

    @Override
    public void showControlView() {
        controlView.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissBlueTooth() {
        bluetoothLv.setVisibility(View.GONE);
        presenter.closeBlueTooth(getActivity());
    }

    @Override
    public void loadDialog(boolean isShow) {
        if (isShow) {
            dialog.show();
        }
        if (!isShow) {
            dialog.dismiss();
        }
    }

    @Override
    public void setPresenter(WaterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
//        presenter.start();
    }

    class BlueToothDevicesAdapter extends BaseAdapter {
        class ViewHolder {
            @ViewInject(R.id.item_number)
            private TextView itemNumber;
            @ViewInject(R.id.item_time)
            private TextView itemTime;
            @ViewInject(R.id.item_ratio)
            private TextView itemRatio;
            @ViewInject(R.id.item_high_value)
            private TextView itemHighValue;
        }

        private List<BluetoothDevice> bluetoothDevices;

        public BlueToothDevicesAdapter(List<BluetoothDevice> bluetoothDevices) {
            this.bluetoothDevices = bluetoothDevices;
        }

        @Override
        public int getCount() {
            return bluetoothDevices.size();
        }

        @Override
        public Object getItem(int position) {
            return bluetoothDevices.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_statistics, null);
                viewHolder = new ViewHolder();
                x.view().inject(viewHolder, convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.itemNumber.setText(String.valueOf(position + 1));
            viewHolder.itemHighValue.setText("");
            viewHolder.itemTime.setText(bluetoothDevices.get(position).getName());
            viewHolder.itemRatio.setText(bluetoothDevices.get(position).getAddress());
            return convertView;
        }
    }

    @Subscribe
    public void getMsg(BluetoothMsg msg) {
        final int[] value = new int[]{Integer.parseInt(msg.msg)};
        value[0] -= ll;
        if (value[0] > 0) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    nowWaterValue.setText(String.valueOf(value[0]));
                }
            });

        }else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    nowWaterValue.setText("0");
                }
            });
        }
        Log.d("MSG", msg.msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initDatas() {
        times.add("0s");
        times.add("5s");
        times.add("10s");
        times.add("15s");

    }

}
