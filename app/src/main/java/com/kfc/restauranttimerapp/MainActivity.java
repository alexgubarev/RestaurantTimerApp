package com.kfc.restauranttimerapp;
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
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Chronometer.OnChronometerTickListener, CompoundButton.OnCheckedChangeListener {

    Button button;
    EditText editText;
    Spinner spinner;

    private Chronometer mChronometer1;
    private Chronometer mChronometer2;
    private Chronometer mChronometer3;
    private Chronometer mChronometer4;
    private Chronometer mChronometer5;
    private Chronometer mChronometer6;
    private Chronometer mChronometer7;
    private Chronometer mChronometer8;

    ArrayList<Integer> arrayList;

    private Ringtone r;
    private Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList = new ArrayList<>();

        arrayList.add(10);
        arrayList.add(180);
        arrayList.add(60);
        arrayList.add(240);
        arrayList.add(300);
        arrayList.add(360);
        arrayList.add(120);
        arrayList.add(180);

        button = findViewById(R.id.button);
        spinner = findViewById(R.id.spinner3);
        editText = findViewById(R.id.editText);

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
        mChronometer1.setBase(SystemClock.elapsedRealtime() + 1000 * arrayList.get(0));
        mChronometer2.setBase(SystemClock.elapsedRealtime() + 1000 * arrayList.get(1));
        mChronometer3.setBase(SystemClock.elapsedRealtime() + 1000 * arrayList.get(2));
        mChronometer4.setBase(SystemClock.elapsedRealtime() + 1000 * arrayList.get(3));
        mChronometer5.setBase(SystemClock.elapsedRealtime() + 1000 * arrayList.get(4));
        mChronometer6.setBase(SystemClock.elapsedRealtime() + 1000 * arrayList.get(5));
        mChronometer7.setBase(SystemClock.elapsedRealtime() + 1000 * arrayList.get(6));
        mChronometer8.setBase(SystemClock.elapsedRealtime() + 1000 * arrayList.get(7));

        mChronometer1.setOnChronometerTickListener(this);
        mChronometer2.setOnChronometerTickListener(this);
        mChronometer3.setOnChronometerTickListener(this);
        mChronometer4.setOnChronometerTickListener(this);
        mChronometer5.setOnChronometerTickListener(this);
        mChronometer6.setOnChronometerTickListener(this);
        mChronometer7.setOnChronometerTickListener(this);
        mChronometer8.setOnChronometerTickListener(this);


        toggle1.setOnCheckedChangeListener(this);
        toggle2.setOnCheckedChangeListener(this);
        toggle3.setOnCheckedChangeListener(this);
        toggle4.setOnCheckedChangeListener(this);
        toggle5.setOnCheckedChangeListener(this);
        toggle6.setOnCheckedChangeListener(this);
        toggle7.setOnCheckedChangeListener(this);
        toggle8.setOnCheckedChangeListener(this);


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

    public void onClick(View view) {
        Log.d("myLog", "spinner position = " + spinner.getSelectedItemPosition());
        String mtime;
        switch (spinner.getSelectedItemPosition()){
            case 0:
                mtime = editText.getText().toString();
                arrayList.set(0, Integer.parseInt(mtime));
                mChronometer1.setBase(SystemClock.elapsedRealtime() + 1000 * arrayList.get(0));
                break;
            case 1:
                mtime = editText.getText().toString();
                arrayList.set(1, Integer.parseInt(mtime));
                mChronometer2.setBase(SystemClock.elapsedRealtime() + 1000 * arrayList.get(1));
                break;
            case 2:
                mtime = editText.getText().toString();
                arrayList.set(2, Integer.parseInt(mtime));
                mChronometer3.setBase(SystemClock.elapsedRealtime() + 1000 * arrayList.get(2));
                break;
            case 3:
                mtime = editText.getText().toString();
                arrayList.set(3, Integer.parseInt(mtime));
                mChronometer4.setBase(SystemClock.elapsedRealtime() + 1000 * arrayList.get(3));
                break;
            case 4:
                mtime = editText.getText().toString();
                arrayList.set(4, Integer.parseInt(mtime));
                mChronometer5.setBase(SystemClock.elapsedRealtime() + 1000 * arrayList.get(4));
                break;
            case 5:
                mtime = editText.getText().toString();
                arrayList.set(5, Integer.parseInt(mtime));
                mChronometer6.setBase(SystemClock.elapsedRealtime() + 1000 * arrayList.get(5));
                break;
            case 6:
                mtime = editText.getText().toString();
                arrayList.set(6, Integer.parseInt(mtime));
                mChronometer7.setBase(SystemClock.elapsedRealtime() + 1000 * arrayList.get(6));
                break;
            case 7:
                mtime = editText.getText().toString();
                arrayList.set(7, Integer.parseInt(mtime));
                mChronometer8.setBase(SystemClock.elapsedRealtime() + 1000 * arrayList.get(7));
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            switch (buttonView.getId()){
                case R.id.toggleButton1:
                    mChronometer1.setBase(SystemClock.elapsedRealtime() + 1000 * arrayList.get(0));
                    mChronometer1.start();
                    break;
                case R.id.toggleButton2:
                    mChronometer2.setBase(SystemClock.elapsedRealtime() + 1000 * arrayList.get(1));
                    mChronometer2.start();
                    break;
                case R.id.toggleButton3:
                    mChronometer3.setBase(SystemClock.elapsedRealtime() + 1000 * arrayList.get(2));
                    mChronometer3.start();
                    break;
                case R.id.toggleButton4:
                    mChronometer4.setBase(SystemClock.elapsedRealtime() + 1000 * arrayList.get(3));
                    mChronometer4.start();
                    break;
                case R.id.toggleButton5:
                    mChronometer5.setBase(SystemClock.elapsedRealtime() + 1000 * arrayList.get(4));
                    mChronometer5.start();
                    break;
                case R.id.toggleButton6:
                    mChronometer6.setBase(SystemClock.elapsedRealtime() + 1000 * arrayList.get(5));
                    mChronometer6.start();
                    break;
                case R.id.toggleButton7:
                    mChronometer7.setBase(SystemClock.elapsedRealtime() + 1000 * arrayList.get(6));
                    mChronometer7.start();
                    break;
                case R.id.toggleButton8:
                    mChronometer8.setBase(SystemClock.elapsedRealtime() + 1000 * arrayList.get(7));
                    mChronometer8.start();
                    break;
            }

        } else {
            switch (buttonView.getId()){
                case R.id.toggleButton1:
                    mChronometer1.stop();
                    r.stop();
                    mChronometer1.clearAnimation();
                    mChronometer1.setTextColor(Color.WHITE);
                    mChronometer1.setBase(SystemClock.elapsedRealtime() + 1000 * arrayList.get(0));
                    break;
                case R.id.toggleButton2:
                    mChronometer2.stop();
                    r.stop();
                    mChronometer2.clearAnimation();
                    mChronometer2.setTextColor(Color.WHITE);
                    mChronometer2.setBase(SystemClock.elapsedRealtime() + 1000 * arrayList.get(1));
                    break;
                case R.id.toggleButton3:
                    mChronometer3.stop();
                    r.stop();
                    mChronometer3.clearAnimation();
                    mChronometer3.setTextColor(Color.WHITE);
                    mChronometer3.setBase(SystemClock.elapsedRealtime() + 1000 * arrayList.get(2));
                    break;
                case R.id.toggleButton4:
                    mChronometer4.stop();
                    r.stop();
                    mChronometer4.clearAnimation();
                    mChronometer4.setTextColor(Color.WHITE);
                    mChronometer4.setBase(SystemClock.elapsedRealtime() + 1000 * arrayList.get(3));
                    break;
                case R.id.toggleButton5:
                    mChronometer5.stop();
                    r.stop();
                    mChronometer5.clearAnimation();
                    mChronometer5.setTextColor(Color.WHITE);
                    mChronometer5.setBase(SystemClock.elapsedRealtime() + 1000 * arrayList.get(4));
                    break;
                case R.id.toggleButton6:
                    mChronometer6.stop();
                    r.stop();
                    mChronometer6.clearAnimation();
                    mChronometer6.setTextColor(Color.WHITE);
                    mChronometer6.setBase(SystemClock.elapsedRealtime() + 1000 * arrayList.get(5));
                    break;
                case R.id.toggleButton7:
                    mChronometer7.stop();
                    r.stop();
                    mChronometer7.clearAnimation();
                    mChronometer7.setTextColor(Color.WHITE);
                    mChronometer7.setBase(SystemClock.elapsedRealtime() + 1000 * arrayList.get(6));
                    break;
                case R.id.toggleButton8:
                    mChronometer8.stop();
                    r.stop();
                    mChronometer8.clearAnimation();
                    mChronometer8.setTextColor(Color.WHITE);
                    mChronometer8.setBase(SystemClock.elapsedRealtime() + 1000 * arrayList.get(7));
                    break;

            }

        }
    }
}
