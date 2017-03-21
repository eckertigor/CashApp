package com.example.ekkert.cashapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String ACTION_NEW_CURRENCY = "action.NEW_CURRENCY";
    public static final String ACTION_ERROR = "action.ERROR";
    private BroadcastReceiver broadcastReceiver = null;
    private final static String TAG = MainActivity.class.getSimpleName();

    private final View.OnClickListener onSettingsClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
    };

    private final View.OnClickListener onUpdateClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            loadCurrency();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_update).setOnClickListener(onUpdateClick);
        findViewById(R.id.btn_settings).setOnClickListener(onSettingsClick);
        if (MyStorage.getInstance(MainActivity.this).loadCurrentType().equals("")) {
            MyStorage.getInstance(MainActivity.this).saveCurrentType("RUB");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(MainActivity.ACTION_NEW_CURRENCY)) {
                    printCurrency();
                }
                if (intent.getAction().equals(MainActivity.ACTION_ERROR)) {
                    printError();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_NEW_CURRENCY);
        intentFilter.addAction(ACTION_ERROR);
        registerReceiver(broadcastReceiver, intentFilter);
        loadCurrency();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        loadCurrency();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    private void printCurrency() {
        MoneyCurrency moneyCurrency = MyStorage.getInstance(this).getLastSavedCurrency();
        String currencyValue;
        if (moneyCurrency == null) {
            currencyValue = "error";
        } else {
            currencyValue = moneyCurrency.getValue();
        }
        ((TextView) findViewById(R.id.textView)).setText(currencyValue);


    }

    private void printError() {
        ((TextView) findViewById(R.id.textView)).setText("error");
    }

    private void loadCurrency() {
        Intent intent = new Intent(MainActivity.this, CurrencyIntentService.class);
        startService(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
        if (broadcastReceiver!= null) {
            LocalBroadcastManager.getInstance(MainActivity.this).unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }
    }
}
