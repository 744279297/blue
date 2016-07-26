package com.example.zhangchi.blue.ui.widget;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.zhangchi.blue.R;
import com.example.zhangchi.blue.ui.water.WaterContract;

import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by zhangchi on 2016/5/19.
 */
public class BlueToothHeadView extends FrameLayout {
    @ViewInject(R.id.close_bluetooth)
    private Button closeBluetooth;

    public BlueToothHeadView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.head_bluetooth_list, this) ;
        x.view().inject(this);
    }

    public BlueToothHeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BlueToothHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void clickClose(OnClickListener listener) {
        closeBluetooth.setOnClickListener(listener);
    }

}
