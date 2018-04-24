package com.kfc.restauranttimerapp;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    EditText editText;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        spinner = findViewById(R.id.spinner3);
        editText = findViewById(R.id.editText);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickApplyBtn(View view) {
        String time = editText.getText().toString();
        if (!time.isEmpty()) {
            int position = spinner.getSelectedItemPosition();
            getIntent().putExtra("position", position);
            getIntent().putExtra("timeValue", time);

//            Chronometer chr = chronometerMap.get(position + POSITION_INDEX_SHIFT);
//            timeValuesChronometerIdMap.put(chr.getId(), Integer.parseInt(time));
//                chr.setBase(SystemClock.elapsedRealtime() + BASE_TIME * timeValuesChronometerIdMap.get(chr.getId()));
//                chr.setText(DateUtils.formatElapsedTime(Integer.parseInt(time)));

        } else
            Toast.makeText(this, R.string.editTextCheck, Toast.LENGTH_SHORT).show();
    }
}
