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
    Map<Integer, Integer> chridTimeMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        spinner = findViewById(R.id.spinner);
        editText = findViewById(R.id.editText);
        intent = new Intent(SettingsActivity.this, MainActivity.class);

        for (int i = 0; i < 8; i++) {
            int[] initTime = getResources().getIntArray(R.array.chrInitValues);
            chridTimeMap.put(i, initTime[i]);
        }

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            intent.putExtra("map", (Serializable) chridTimeMap);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickApplyBtn(View view) {

        String time = editText.getText().toString();
        if (!time.isEmpty()) {
            int position = spinner.getSelectedItemPosition();
            chridTimeMap.put(position, Integer.parseInt(time));

        } else
            Toast.makeText(this, R.string.editTextCheck, Toast.LENGTH_SHORT).show();
    }
}
