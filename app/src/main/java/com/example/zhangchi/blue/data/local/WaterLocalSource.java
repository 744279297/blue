package com.example.zhangchi.blue.data.local;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.zhangchi.blue.data.*;


import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangchi on 2016/5/8.
 */
public class WaterLocalSource implements WaterDataSource {
    private static WaterLocalSource INSTANCE;
    DbManager.DaoConfig daoConfig;
    private WaterLocalSource() {
        daoConfig = new DbManager.DaoConfig()
                .setDbName("water.db")
                .setDbDir(new File("/sdcard"))
                .setDbVersion(2)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        // 开启WAL, 对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        // TODO: ...
                        // db.addColumn(...);
                        // ...
                        // or
                        // db.dropDb();
                    }
                });
        Log.d("db", "create");

        try {
            DbManager db = x.getDb(daoConfig);
            if (!db.getTable(Water.class).tableIsExist()){
                db.save(new Water("5月1日", 450, 150, 90, 30, "星期日"));
                db.save(new Water("5月2日", 250, 150, 50, 30, "星期一"));
                db.save(new Water("5月3日", 400, 200, 80, 40, "星期二"));
                db.save(new Water("5月4日", 300, 150, 60, 30, "星期三"));
                db.save(new Water("5月5日", 200, 150, 40, 30, "星期四"));
                db.save(new Water("5月6日", 450, 150, 90, 30, "星期五"));
                db.save(new Water("5月7日", 250, 150, 50, 90, "星期六"));
                db.save(new Water("5月8日", 400, 200, 80, 40, "星期日"));
                db.save(new Water("5月9日", 300, 150, 60, 30, "星期一"));
                db.save(new Water("5月10日", 200, 150, 40, 30, "星期二"));
                db.save(new Water("5月11日", 250, 150, 50, 90, "星期三"));
                db.save(new Water("5月12日", 400, 200, 80, 40, "星期四"));
                db.save(new Water("5月13日", 300, 150, 60, 30, "星期五"));
            }

            Log.d("db", "addend");
        } catch (DbException e) {
            e.printStackTrace();
        }


    }

    public synchronized static WaterLocalSource getInstance() {
        if (INSTANCE == null) {
            Log.d("db", "getInstance");
            INSTANCE = new WaterLocalSource();
        }
        return INSTANCE;
    }


    @Override
    public void getWaters(@NonNull LoadWaterCallback callback) {
        DbManager db = x.getDb(daoConfig);
        List<Water> waters;
        try {
            waters = db.selector(Water.class).findAll();
            callback.onWaterLoaded(waters);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getWater(@NonNull int waterId, @NonNull GetWaterCallback callback) {
        try {
            DbManager db = x.getDb(daoConfig);
            Water water = db.selector(Water.class).where("id", "=", waterId).findFirst();
            callback.onWaterLoaded(water);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveWater(@NonNull Water water) {
        try {
            DbManager db = x.getDb(daoConfig);
            db.save(water);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refreshWaters() {

    }

    @Override
    public void deleteAllWaters() {
        try {
            DbManager db = x.getDb(daoConfig);
            db.delete(Water.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteWater(@NonNull int waterId) {
        try {
            DbManager db = x.getDb(daoConfig);
            db.deleteById(Water.class, waterId);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isEmpty() {
        List<Water> waters;
        try {
            DbManager db = x.getDb(daoConfig);
            waters = db.selector(Water.class)
                    .findAll();
            if (waters.size() > 0) {
                return false;
            }
            return true;
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
    }
}
