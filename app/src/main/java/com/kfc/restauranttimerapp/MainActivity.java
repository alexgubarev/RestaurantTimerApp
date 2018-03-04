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
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Chronometer.OnChronometerTickListener, CompoundButton.OnCheckedChangeListener {

    private static final int POSITION_INDEX_SHIFT = 1;
    EditText editText;
    Spinner spinner;
    Ringtone ringtone;
    Map<Integer, Chronometer> chronometerMap = new HashMap();
    Map<Integer, Integer> toggleButtonChronometerMap = new HashMap<>();
    Map<Integer, Integer> timeValuesChronometerIdMap = new HashMap<>();
    ArrayList<ToggleButton> toggleButtonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner3);
        editText = findViewById(R.id.editText);
        toggleButtonList = new ArrayList<>();

        initializationLoop();

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
    }

    private void initializationLoop() {
        for (int i = 1; i < 9; i++) {
            String chronometerName = "chronometer" + i;
            String toggleButtonName = "toggleButton" + i;
            int chronometerId = getResources().getIdentifier(chronometerName, "id", getPackageName());
            int toggleButtonId = getResources().getIdentifier(toggleButtonName, "id", getPackageName());

            timeValuesChronometerIdMap.put(chronometerId, 10 + i * 2);

            chronometerMap.put(i, (Chronometer) findViewById(chronometerId));
            chronometerMap.get(i).setBase(SystemClock.elapsedRealtime() + 1000 * timeValuesChronometerIdMap.get(chronometerId));
            chronometerMap.get(i).setOnChronometerTickListener(this);

            toggleButtonChronometerMap.put(toggleButtonId, chronometerId);

            toggleButtonList.add((ToggleButton) findViewById(toggleButtonId));
            toggleButtonList.get(i - POSITION_INDEX_SHIFT).setOnCheckedChangeListener(this);
        }
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
        boolean isAllNotChecked = true;
        for (ToggleButton toggleButton : toggleButtonList) {
            if (toggleButton.isChecked()) {
                isAllNotChecked = false;
                break;
            }
        }
        if (isChecked) {
            chronometer.setBase(SystemClock.elapsedRealtime() + 1000 * timeValuesChronometerIdMap.get(chronometerId));
            chronometer.start();
        } else {
            chronometer.stop();
            if (isAllNotChecked) {
                ringtone.stop();
            }

            chronometer.clearAnimation();
            chronometer.setTextColor(Color.WHITE);
            chronometer.setBase(SystemClock.elapsedRealtime() + 1000 * timeValuesChronometerIdMap.get(chronometerId));
        }
    }
}
