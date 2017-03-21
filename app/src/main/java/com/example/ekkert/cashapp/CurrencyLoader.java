package com.example.ekkert.cashapp;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class CurrencyLoader {
    private final OkHttpClient httpClient = new OkHttpClient();
    private final static String TAG = CurrencyLoader.class.getSimpleName();

    public CurrencyLoader() {
    }

    public MoneyCurrency loadCurrency(String type) throws IOException {
        Request request = (new Request.Builder()).url("https://community-bitcointy.p.mashape.com/average/" + type).
        addHeader("X-Mashape-Key", "2KQDyhGLBZmshpW5ZRwd5PyOuJ5Wp1e2Hw4jsnxW8JlNayruDU").build();
        Response response = this.httpClient.newCall(request).execute();
        MoneyCurrency moneyCurrency;
        try {
            if(!response.isSuccessful()) {
                throw new IOException("Wrong status: " + response.code() + "; body: " + response.body().string());
            }
            moneyCurrency = MyStorage.deserialaze(response.body().string());
        } finally {
            response.body().close();
        }
        return moneyCurrency;
    }
}
