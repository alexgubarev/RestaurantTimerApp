package com.kfc.restauranttimerapp;

import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Chronometer.OnChronometerTickListener, CompoundButton.OnCheckedChangeListener {

    private static final int POSITION_INDEX_SHIFT = 1;
    EditText editText;
    Spinner spinner;
    Chronometer chronometer1;
    Chronometer chronometer2;
    Chronometer chronometer3;
    Chronometer chronometer4;
    Chronometer chronometer5;
    Chronometer chronometer6;
    Chronometer chronometer7;
    Chronometer chronometer8;
    ArrayList<Integer> timeValues;
    Ringtone ringtone;
    Map<Integer, Chronometer> chronometerMap = new HashMap();
    Map<Integer, Integer> toggleButtonChronometerMap = new HashMap<>();
    Map<Integer, Integer> timeValuesChronometerIdMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner3);
        editText = findViewById(R.id.editText);


        chronometer1 = findViewById(R.id.chronometer1);
        chronometer2 = findViewById(R.id.chronometer2);
        chronometer3 = findViewById(R.id.chronometer3);
        chronometer4 = findViewById(R.id.chronometer4);
        chronometer5 = findViewById(R.id.chronometer5);
        chronometer6 = findViewById(R.id.chronometer6);
        chronometer7 = findViewById(R.id.chronometer7);
        chronometer8 = findViewById(R.id.chronometer8);
        ToggleButton toggleButton1 = findViewById(R.id.toggleButton1);
        ToggleButton toggleButton2 = findViewById(R.id.toggleButton2);
        ToggleButton toggleButton3 = findViewById(R.id.toggleButton3);
        ToggleButton toggleButton4 = findViewById(R.id.toggleButton4);
        ToggleButton toggleButton5 = findViewById(R.id.toggleButton5);
        ToggleButton toggleButton6 = findViewById(R.id.toggleButton6);
        ToggleButton toggleButton7 = findViewById(R.id.toggleButton7);
        ToggleButton toggleButton8 = findViewById(R.id.toggleButton8);

        timeValues = new ArrayList<>();
        timeValues.addAll(Arrays.asList(10, 180, 60, 240, 300, 360, 120, 180));

        chronometerMap.put(1, (Chronometer) findViewById(R.id.chronometer1));
        toggleButtonChronometerMap.put(R.id.toggleButton1, R.id.chronometer1);
        timeValuesChronometerIdMap.put(R.id.chronometer1, 10);

        // установим начальное значение
        chronometer1.setBase(SystemClock.elapsedRealtime() + 1000 * timeValues.get(0));
        chronometer2.setBase(SystemClock.elapsedRealtime() + 1000 * timeValues.get(1));
        chronometer3.setBase(SystemClock.elapsedRealtime() + 1000 * timeValues.get(2));
        chronometer4.setBase(SystemClock.elapsedRealtime() + 1000 * timeValues.get(3));
        chronometer5.setBase(SystemClock.elapsedRealtime() + 1000 * timeValues.get(4));
        chronometer6.setBase(SystemClock.elapsedRealtime() + 1000 * timeValues.get(5));
        chronometer7.setBase(SystemClock.elapsedRealtime() + 1000 * timeValues.get(6));
        chronometer8.setBase(SystemClock.elapsedRealtime() + 1000 * timeValues.get(7));

        chronometer1.setOnChronometerTickListener(this);
        chronometer2.setOnChronometerTickListener(this);
        chronometer3.setOnChronometerTickListener(this);
        chronometer4.setOnChronometerTickListener(this);
        chronometer5.setOnChronometerTickListener(this);
        chronometer6.setOnChronometerTickListener(this);
        chronometer7.setOnChronometerTickListener(this);
        chronometer8.setOnChronometerTickListener(this);

        toggleButton1.setOnCheckedChangeListener(this);
        toggleButton2.setOnCheckedChangeListener(this);
        toggleButton3.setOnCheckedChangeListener(this);
        toggleButton4.setOnCheckedChangeListener(this);
        toggleButton5.setOnCheckedChangeListener(this);
        toggleButton6.setOnCheckedChangeListener(this);
        toggleButton7.setOnCheckedChangeListener(this);
        toggleButton8.setOnCheckedChangeListener(this);

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
    }

    @Override
    public void onChronometerTick(Chronometer chronometer) {
        long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
        if (elapsedMillis >= 0) {
            chronometer.stop();
            Toast.makeText(MainActivity.this, chronometer.getTransitionName() + " is out of time",
                    Toast.LENGTH_SHORT).show();
            ringtone.play();
            Animation animation = new AlphaAnimation(0.0f, 1.0f);
            animation.setDuration(50); //You can manage the blinking time with this parameter
            animation.setStartOffset(20);
            animation.setRepeatMode(Animation.REVERSE);
            animation.setRepeatCount(Animation.INFINITE);
            chronometer.setTextColor(Color.RED);
            chronometer.startAnimation(animation);
        }
    }

    public void onClick(View view) {
        String time = editText.getText().toString();
        int position = spinner.getSelectedItemPosition() + POSITION_INDEX_SHIFT;
        Chronometer chr = chronometerMap.get(position);
        timeValuesChronometerIdMap.put(chr.getId(), Integer.parseInt(time));
        chr.setBase(SystemClock.elapsedRealtime() + 1000 * timeValuesChronometerIdMap.get(chr.getId()));

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int chronometerId = toggleButtonChronometerMap.get(buttonView.getId());
        Chronometer chronometer = findViewById(chronometerId);
        if (isChecked) {
            chronometer.setBase(SystemClock.elapsedRealtime() + 1000 * timeValuesChronometerIdMap.get(chronometerId));
            chronometer.start();
        } else {
            chronometer.stop();
            ringtone.stop();
            chronometer.clearAnimation();
            chronometer.setTextColor(Color.WHITE);
            chronometer.setBase(SystemClock.elapsedRealtime() + 1000 * timeValuesChronometerIdMap.get(chronometerId));
        }
    }
}
