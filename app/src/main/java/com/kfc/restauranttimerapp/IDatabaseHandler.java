package com.kfc.restauranttimerapp;

import java.util.List;

public interface IDatabaseHandler {
    public void addTime(TimeChronometer timeChronometer);
    public TimeChronometer getTime(int id);
    public List<TimeChronometer> getAllValues();
    public int updateTime(TimeChronometer timeChronometer);

}
