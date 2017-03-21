package com.example.ekkert.cashapp;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import java.io.IOException;

public class CurrencyIntentService extends IntentService {
        public CurrencyIntentService() {
            super("CurrencyIntentService");
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            if (intent != null) {
                try {
                    String type = MyStorage.getInstance(getApplicationContext()).loadCurrentType();
                    MoneyCurrency moneyCurrency = new CurrencyLoader().loadCurrency(type);
                    if (moneyCurrency != null) {
                        MyStorage.getInstance(getApplicationContext()).saveCurrency(moneyCurrency);
                        Intent resultIntent = new Intent(MainActivity.ACTION_NEW_CURRENCY);
                        sendBroadcast(resultIntent);
                    } else {
                        Intent resultIntent = new Intent(MainActivity.ACTION_ERROR);
                        sendBroadcast(resultIntent);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Intent resultIntent = new Intent(MainActivity.ACTION_ERROR);
                    sendBroadcast(resultIntent);
                }
            }
        }
}