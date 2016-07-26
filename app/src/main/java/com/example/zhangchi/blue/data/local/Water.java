package com.example.zhangchi.blue.data.local;


import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by zhangchi on 2016/5/8.
 */
@Table(name = "water")
public class Water {
    @Column(name = "id", isId = true, autoGen = true)
    private int id;
    @Column(name = "highValue")
    private int highValue;
    @Column(name = "lowValue")
    private int lowValue;
    @Column(name = "highRatio")
    private int highRatio;
    @Column(name = "lowRatio")
    private int lowRatio;
    @Column(name = "time")
    private String time;
    @Column(name = "week")
    private String week;


    public Water(String time, int highValue, int lowValue, int highRatio, int lowRatio, String week) {
        this.time = time;
        this.highValue = highValue;
        this.lowValue = lowValue;
        this.lowRatio = lowRatio;
        this.highRatio = highRatio;
        this.week = week;
    }

    public Water() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getLowRatio() {
        return lowRatio;
    }

    public void setLowRatio(int lowRatio) {
        this.lowRatio = lowRatio;
    }

    public int getHighRatio() {
        return highRatio;
    }

    public void setHighRatio(int highRatio) {
        this.highRatio = highRatio;
    }

    public int getLowValue() {
        return lowValue;
    }

    public void setLowValue(int lowValue) {
        this.lowValue = lowValue;
    }

    public int getHighValue() {
        return highValue;
    }

    public void setHighValue(int highValue) {
        this.highValue = highValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }


    @Override
    public String toString() {
        return "Water{" +
                "id=" + id +
                ", highValue='" + highValue + '\'' +
                ", lowValue='" + lowValue + '\'' +
                ", highRatio=" + highRatio +
                ", lowRatio='" + lowRatio + '\'' +
                ", time='" + time + '\'' +
                ", week='" + week + '\'' +
                '}';

    }
}
