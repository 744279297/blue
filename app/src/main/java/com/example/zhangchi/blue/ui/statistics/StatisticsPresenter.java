package com.example.zhangchi.blue.ui.statistics;

import android.support.design.widget.Snackbar;

import com.example.zhangchi.blue.data.WaterDataSource;
import com.example.zhangchi.blue.data.local.Water;

import java.util.List;
import java.util.SortedMap;

/**
 * Created by zhangchi on 2016/5/11.
 */
public class StatisticsPresenter implements StatisticsContract.Presenter {
    private StatisticsContract.View view;
    private WaterDataSource data;
    private List<Water> waterList;

    public StatisticsPresenter(WaterDataSource waterDataSource, StatisticsContract.View view) {
        this.data = waterDataSource;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        getWaters();
    }

    @Override
    public void getWaters() {
        data.getWaters(new WaterDataSource.LoadWaterCallback() {
            @Override
            public void onWaterLoaded(List<Water> waters) {
                view.showWaters(waters);
                waterList = waters;
            }

            @Override
            public void onDataNotAvailable() {
            }
        });
    }

    @Override
    public void sortWaters(int Type) {
        for (int i = 0; i < waterList.size() - 1; i++) {
            for (int j = 1; j < waterList.size() - i; j++) {
                Water water;
                switch (Type) {
                    case 0:
                        if (waterList.get(j - 1).getHighValue() > waterList.get(j).getHighValue()) {
                            water = waterList.get(j - 1);
                            waterList.set(j - 1, waterList.get(j));
                            waterList.set(j, water);
                        }
                        break;
                    case 1:
                        if (waterList.get(j - 1).getHighValue() < waterList.get(j).getHighValue()) {
                            water = waterList.get(j - 1);
                            waterList.set(j - 1, waterList.get(j));
                            waterList.set(j, water);
                        }
                        break;
                    case 2:
                        if (waterList.get(j - 1).getLowValue() > waterList.get(j).getLowValue()) {
                            water = waterList.get(j - 1);
                            waterList.set(j - 1, waterList.get(j));
                            waterList.set(j, water);
                        }
                        break;
                    case 3:
                        if (waterList.get(j - 1).getLowValue() < waterList.get(j).getLowValue()) {
                            water = waterList.get(j - 1);
                            waterList.set(j - 1, waterList.get(j));
                            waterList.set(j, water);
                        }
                        break;
                }
            }
        }
        view.showWaters(waterList);
    }


    @Override
    public void loadHeadView() {
        view.changeHeadView();
    }

}
