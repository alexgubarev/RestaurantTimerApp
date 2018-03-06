package com.kfc.restauranttimerapp;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Chronometer.OnChronometerTickListener, CompoundButton.OnCheckedChangeListener {

    private static final int POSITION_INDEX_SHIFT = 1;
    private static final int BASE_TIME = 1000;
    public static int ANIM_DURATION = 60;
    int chronometerId;
    int toggleButtonId;
    EditText editText;
    Spinner spinner;
    Ringtone ringtone;
    Uri notification;
    @SuppressLint("UseSparseArrays")
    HashMap<Integer, Chronometer> chronometerMap = new HashMap<>();
    @SuppressLint("UseSparseArrays")
    Map<Integer, Integer> toggleButtonChronometerMap = new HashMap<>();
    @SuppressLint("UseSparseArrays")
    Map<Integer, Integer> timeValuesChronometerIdMap = new HashMap<>();
    ArrayList<ToggleButton> toggleButtonList = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner3);
        editText = findViewById(R.id.editText);
        notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);

        for (int i = 1; i < 9; i++) {
            toggleButtonId = getResources().getIdentifier(getString(R.string.toggleBtn) + i, getString(R.string.id), getPackageName());
            chronometerId = getResources().getIdentifier(getString(R.string.chr) + i, getString(R.string.id), getPackageName());
            chronometerMap.put(i, (Chronometer) findViewById(chronometerId));
            toggleButtonList.add((ToggleButton) findViewById(toggleButtonId));
            toggleButtonChronometerMap.put(toggleButtonId, chronometerId);
            chronometerMap.get(i).setOnChronometerTickListener(this);
            toggleButtonList.get(i - POSITION_INDEX_SHIFT).setOnCheckedChangeListener(this);

            if (savedInstanceState != null) {
                String currentCountStr = savedInstanceState.getString(getString(R.string.chrCurrentTime) + i);
                int currentCount = convertString(currentCountStr);
                int startTime = savedInstanceState.getInt(getString(R.string.chrStartTime) + i);
                timeValuesChronometerIdMap.put(chronometerId, startTime);
                chronometerMap.get(i).setBase(SystemClock.elapsedRealtime() + BASE_TIME * currentCount);
            } else {
                int[] initTime = getResources().getIntArray(R.array.chrInitValues);
                timeValuesChronometerIdMap.put(chronometerId, (initTime[i - POSITION_INDEX_SHIFT]));
                chronometerMap.get(i).setBase(SystemClock.elapsedRealtime() + BASE_TIME * initTime[i - POSITION_INDEX_SHIFT]);
                chronometerMap.get(i).setText(DateUtils.formatElapsedTime(initTime[i - POSITION_INDEX_SHIFT]));
            }
        }
    }

    @Override
    protected void onDestroy() {
        ringtone.stop();
        super.onDestroy();
    }

    private static int convertString(String time) {
        int quoteInd = time.indexOf(":");
        int min = Integer.valueOf(time.substring(0, quoteInd));
        int sec = Integer.valueOf(time.substring(++quoteInd));
        return ((min * 60) + sec);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onChronometerTick(Chronometer chronometer) {
        long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
        if (elapsedMillis > 0) {
            chronometer.stop();
            chronometer.setText(R.string.zeroTime);
            Toast.makeText(MainActivity.this, chronometer.getTransitionName() + " " + getString(R.string.outOfTime),
                    Toast.LENGTH_SHORT).show();
            ringtone.play();
            Animation animation = new AlphaAnimation(0.0f, 1.0f);
            animation.setDuration(ANIM_DURATION); //You can manage the blinking time with this parameter
            animation.setStartOffset(20);
            animation.setRepeatMode(Animation.REVERSE);
            animation.setRepeatCount(Animation.INFINITE);
            chronometer.setTextColor(Color.RED);
            chronometer.startAnimation(animation);
        }
    }

    public void onClickApplyBtn(View view) {
        String time = editText.getText().toString();
        if (!time.isEmpty()) {
            int position = spinner.getSelectedItemPosition();
            Chronometer chr = chronometerMap.get(position + POSITION_INDEX_SHIFT);
            timeValuesChronometerIdMap.put(chr.getId(), Integer.parseInt(time));
            if (!toggleButtonList.get(position).isChecked()) {
                chr.setBase(SystemClock.elapsedRealtime() + BASE_TIME * timeValuesChronometerIdMap.get(chr.getId()));
                chr.setText(DateUtils.formatElapsedTime(Integer.parseInt(time)));
            }
        } else
            Toast.makeText(this, R.string.editTextCheck, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int chronometerId = toggleButtonChronometerMap.get(buttonView.getId());
        Chronometer chronometer = findViewById(chronometerId);
        boolean isAllNotChecked = true;
        for (ToggleButton toggleButton : toggleButtonList) {
            if (toggleButton.isChecked()) {
                isAllNotChecked = false;
                break;
            }
        }
        if (isChecked) {
            String currentTime = chronometer.getText().toString();
            int time = convertString(currentTime);
            chronometer.setBase(SystemClock.elapsedRealtime() + BASE_TIME * time);
            chronometer.setText(DateUtils.formatElapsedTime(time));
            chronometer.start();
        } else {
            chronometer.stop();
            if (isAllNotChecked) {
                ringtone.stop();
            }
            chronometer.clearAnimation();
            chronometer.setTextColor(Color.WHITE);
            chronometer.setBase(SystemClock.elapsedRealtime() + BASE_TIME * timeValuesChronometerIdMap.get(chronometerId));
            chronometer.setText(DateUtils.formatElapsedTime(timeValuesChronometerIdMap.get(chronometerId)));
        }
    }

    public void onClickResetBtn(View view) {
        for (ToggleButton toggleButton : toggleButtonList) {
            toggleButton.setChecked(false);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        for (int i = 1; i < 9; i++) {
            chronometerId = getResources().getIdentifier(getString(R.string.chr) + i, getString(R.string.id), getPackageName());
            outState.putString(getString(R.string.chrCurrentTime) + i, chronometerMap.get(i).getText().toString());
            outState.putInt(getString(R.string.chrStartTime) + i, timeValuesChronometerIdMap.get(chronometerId));
        }
        super.onSaveInstanceState(outState);
    }
}
