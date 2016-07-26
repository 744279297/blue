package com.example.zhangchi.blue.ui.statistics;

import com.example.zhangchi.blue.BasePresenter;
import com.example.zhangchi.blue.BaseView;
import com.example.zhangchi.blue.data.local.Water;

import java.util.List;

/**
 * Created by zhangchi on 2016/5/10.
 */
public interface StatisticsContract {
    public interface View extends BaseView<Presenter> {
        void showWaters(List<Water> waterList);

        void showWaterDetail(int waterId);

        void showLoadingWatersError();

        void showNoWater();

        void changeHeadView();

    }

    public interface Presenter extends BasePresenter {
        void getWaters();

        void sortWaters(int Type);

        void loadHeadView();

    }
}
