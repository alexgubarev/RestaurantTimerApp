package com.kfc.restauranttimerapp;

public class TimeChronometer {
    int _id;
    int _time;

    public TimeChronometer(int id, int time) {
        this._id = id;
        this._time = time;
    }

    public TimeChronometer() {
    }

    public int get_id() {
        return this._id;
    }

    public void set_id(int id) {
        this._id = id;
    }

    public int get_time() {
        return this._time;
    }

    public void set_time(int time) {
        this._time = time;
    }
}
