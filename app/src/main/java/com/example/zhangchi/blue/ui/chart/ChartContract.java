package com.example.zhangchi.blue.ui.chart;

import com.example.zhangchi.blue.BasePresenter;
import com.example.zhangchi.blue.BaseView;
import com.example.zhangchi.blue.data.local.Water;

import java.util.List;

/**
 * Created by zhangchi on 2016/5/14.
 */
public interface ChartContract {
    public interface LineChartPresenter extends BasePresenter {
        void loadWaters();
    }

    public interface LineChartView extends BaseView<LineChartPresenter> {
        void showWaters(List<Water> waters);
    }
}
