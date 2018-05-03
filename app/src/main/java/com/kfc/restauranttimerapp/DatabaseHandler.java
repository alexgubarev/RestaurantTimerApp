package com.kfc.restauranttimerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper implements IDatabaseHandler {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "chronometerDB";
    private static final String TABLE_TIME = "timeTable";
    private static final String KEY_ID = "id";
    private static final String KEY_TIME = "time";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_TIME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TIME + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIME);

        onCreate(db);
    }

    @Override
    public void addTime(TimeChronometer timeChronometer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TIME, timeChronometer.get_time());

        db.insert(TABLE_TIME, null, values);
        db.close();
    }

    @Override
    public TimeChronometer getTime(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TIME, new String[] { KEY_ID,
                        KEY_TIME }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        TimeChronometer timeChronometer = new TimeChronometer(cursor.getInt(0), cursor.getInt((1)));

        return timeChronometer;
    }

    @Override
    public List<TimeChronometer> getAllValues() {
        List<TimeChronometer> timeList = new ArrayList<TimeChronometer>();
        String selectQuery = "SELECT  * FROM " + TABLE_TIME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                TimeChronometer timeChronometer = new TimeChronometer();
                timeChronometer.set_id(cursor.getInt(0));
                timeChronometer.set_time(cursor.getInt(1));
                timeList.add(timeChronometer);
            } while (cursor.moveToNext());
        }

        return timeList;
    }

    @Override
    public int updateTime(TimeChronometer timeChronometer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TIME, timeChronometer.get_time());

        return db.update(TABLE_TIME, values, KEY_ID + " = ?",
                new String[] { String.valueOf(timeChronometer.get_id()) });


    }
}
