package com.kfc.restauranttimerapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {
    Intent intent;
    EditText editText;
    Spinner spinner;

    @SuppressLint("UseSparseArrays")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        spinner = findViewById(R.id.spinner);
        editText = findViewById(R.id.editText);
        intent = new Intent(SettingsActivity.this, MainActivity.class);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickApplyBtn(View view) {

        String time = editText.getText().toString();
        if (!time.isEmpty()) {
            int position = spinner.getSelectedItemPosition();

            DatabaseHandler db = new DatabaseHandler(this);
            db.updateTime(new TimeChronometer(position + 1, Integer.parseInt(time)));

        } else
            Toast.makeText(this, R.string.editTextCheck, Toast.LENGTH_SHORT).show();
    }
}
