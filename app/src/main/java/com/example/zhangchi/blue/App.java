package com.example.zhangchi.blue;

import android.app.Application;

import com.example.zhangchi.blue.data.local.Water;
import com.example.zhangchi.blue.data.local.WaterLocalSource;

import org.xutils.x;

import java.util.Random;

/**
 * Created by zhangchi on 2016/5/2.
 */
public class App extends Application {
    private static int lastResult = 0;
    private static int[] colors = new int[]{
            R.color.IndianRed,
            R.color.Tomato,
            R.color.Violet,
            R.color.SpringGreen,
            R.color.waterColor
    };

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);

    }

    public static int getColor() {
        Random random = new Random();
        int result = random.nextInt(5);
        if (result == lastResult) {
            getColor();
        }
        lastResult = result;
        return colors[result];
    }
}
