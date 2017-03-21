package com.example.ekkert.cashapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    private final View.OnClickListener onBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String type = ((Button)view).getText().toString();
            MyStorage.getInstance(SettingsActivity.this).saveCurrentType(type);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        findViewById(R.id.buttonUSD).setOnClickListener(onBtnClick);
        findViewById(R.id.buttonRUB).setOnClickListener(onBtnClick);
    }
}
