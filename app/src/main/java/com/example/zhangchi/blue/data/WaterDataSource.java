package com.example.zhangchi.blue.data;

import android.support.annotation.NonNull;

import com.example.zhangchi.blue.data.local.Water;

import java.util.List;

/**
 * Created by zhangchi on 2016/5/8.
 */
public interface WaterDataSource {
    interface LoadWaterCallback {

        void onWaterLoaded(List<Water> waters);

        void onDataNotAvailable();
    }

    interface GetWaterCallback {

        void onWaterLoaded(Water water);

        void onDataNotAvailable();
    }

    void getWaters(@NonNull LoadWaterCallback callback);

    void getWater(@NonNull int waterId, @NonNull GetWaterCallback callback);

    void saveWater(@NonNull Water water);

    void refreshWaters();

    void deleteAllWaters();

    void deleteWater(@NonNull int waterId);

    boolean isEmpty();
}
