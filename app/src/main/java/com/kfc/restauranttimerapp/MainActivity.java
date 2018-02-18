package com.kfc.restauranttimerapp;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements Chronometer.OnChronometerTickListener {

    private Chronometer mChronometer1;
    private Chronometer mChronometer2;
    private Chronometer mChronometer3;
    private Chronometer mChronometer4;
    private Chronometer mChronometer5;
    private Chronometer mChronometer6;
    private Chronometer mChronometer7;
    private Chronometer mChronometer8;

    long t1 = 10;
    long t2 = 180;
    long t3 = 60;
    long t4 = 240;
    long t5 = 300;
    long t6 = 360;
    long t7 = 120;
    long t8 = 180;

    private TimePicker timePicker;
    private Ringtone r;
    private Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mChronometer1 = findViewById(R.id.chronometer1);
        mChronometer2 = findViewById(R.id.chronometer2);
        mChronometer3 = findViewById(R.id.chronometer3);
        mChronometer4 = findViewById(R.id.chronometer4);
        mChronometer5 = findViewById(R.id.chronometer5);
        mChronometer6 = findViewById(R.id.chronometer6);
        mChronometer7 = findViewById(R.id.chronometer7);
        mChronometer8 = findViewById(R.id.chronometer8);

        ToggleButton toggle1 = findViewById(R.id.toggleButton1);
        ToggleButton toggle2 = findViewById(R.id.toggleButton2);
        ToggleButton toggle3 = findViewById(R.id.toggleButton3);
        ToggleButton toggle4 = findViewById(R.id.toggleButton4);
        ToggleButton toggle5 = findViewById(R.id.toggleButton5);
        ToggleButton toggle6 = findViewById(R.id.toggleButton6);
        ToggleButton toggle7 = findViewById(R.id.toggleButton7);
        ToggleButton toggle8 = findViewById(R.id.toggleButton8);


        // установим начальное значение
        mChronometer1.setBase(SystemClock.elapsedRealtime() + 1000 * t1);
        mChronometer2.setBase(SystemClock.elapsedRealtime() + 1000 * t2);
        mChronometer3.setBase(SystemClock.elapsedRealtime() + 1000 * t3);
        mChronometer4.setBase(SystemClock.elapsedRealtime() + 1000 * t4);
        mChronometer5.setBase(SystemClock.elapsedRealtime() + 1000 * t5);
        mChronometer6.setBase(SystemClock.elapsedRealtime() + 1000 * t6);
        mChronometer7.setBase(SystemClock.elapsedRealtime() + 1000 * t7);
        mChronometer8.setBase(SystemClock.elapsedRealtime() + 1000 * t8);

        timePicker = findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
//        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
//            @Override
//            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//                mChronometer1.setBase(SystemClock.elapsedRealtime() + 1000 * minute);
//                t1 = minute;
//
//            }
//        });



        mChronometer1.setOnChronometerTickListener(this);
        mChronometer2.setOnChronometerTickListener(this);
        mChronometer3.setOnChronometerTickListener(this);
        mChronometer4.setOnChronometerTickListener(this);
        mChronometer5.setOnChronometerTickListener(this);
        mChronometer6.setOnChronometerTickListener(this);
        mChronometer7.setOnChronometerTickListener(this);
        mChronometer8.setOnChronometerTickListener(this);

        onCheckChange(toggle1, mChronometer1, t1);
        onCheckChange(toggle2, mChronometer2, t2);
        onCheckChange(toggle3, mChronometer3, t3);
        onCheckChange(toggle4, mChronometer4, t4);
        onCheckChange(toggle5, mChronometer5, t5);
        onCheckChange(toggle6, mChronometer6, t6);
        onCheckChange(toggle7, mChronometer7, t7);
        onCheckChange(toggle8, mChronometer8, t8);

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        r = RingtoneManager.getRingtone(getApplicationContext(), notification);
    }


    @Override
    public void onChronometerTick(Chronometer chronometer) {
        long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
        if (elapsedMillis >= 0) {
            chronometer.stop();
            Toast.makeText(MainActivity.this,  chronometer.getTransitionName() + " is out of time",
                    Toast.LENGTH_SHORT).show();
            r.play();
            anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(50); //You can manage the blinking time with this parameter
            anim.setStartOffset(20);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);
            chronometer.setTextColor(Color.RED);
            chronometer.startAnimation(anim);


        }

        }


    private void onCheckChange(ToggleButton toggle, final Chronometer chronometer, final long time) {
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    chronometer.setBase(SystemClock.elapsedRealtime() + 1000 * time);
                    chronometer.start();
                } else {
                    chronometer.stop();
                    r.stop();
                    chronometer.clearAnimation();
                    chronometer.setTextColor(Color.WHITE);
                    chronometer.setBase(SystemClock.elapsedRealtime() + 1000 * time);
                }
            }
        });
    }
}
