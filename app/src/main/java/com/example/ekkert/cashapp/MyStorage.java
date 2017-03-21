package com.example.ekkert.cashapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Created by ekkert on 21.03.17.
 */
public class MyStorage {
    private static MyStorage INSTANCE;
    private SharedPreferences preferences;
    private static final Gson gson = new Gson();
    private final static String TAG = CurrencyLoader.class.getSimpleName();


    public static synchronized MyStorage getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = new MyStorage(context.getApplicationContext());
        }
        return INSTANCE;
    }

    private MyStorage(Context context) {
        this.preferences = context.getSharedPreferences("Currency", 0);
    }

    public String loadCurrentType() {
        return this.preferences.getString("Type", "");
    }

    public void saveCurrentType(String type) {
        this.preferences.edit().putString("Type", type).apply();
    }

    public void saveCurrency(MoneyCurrency moneyCurrency) {
        if(moneyCurrency == null) {
            this.preferences.edit().remove("Currency").apply();
        } else {
            this.preferences.edit().putString("Currency", serialize(moneyCurrency)).apply();
        }
    }

    public MoneyCurrency getLastSavedCurrency() {
        String result = this.preferences.getString("Currency", "");
        return result.isEmpty()?null:deserialaze(result);
    }

    static MoneyCurrency deserialaze(String json) {
        return (MoneyCurrency)gson.fromJson(json, MoneyCurrency.class);
    }

    static String serialize(MoneyCurrency moneyCurrency) {
        return gson.toJson(moneyCurrency);
    }

    static MoneyCurrency fromAnswer(String answer) {
        JsonObject jobj = new Gson().fromJson(answer, JsonObject.class);
        double value =  jobj.get("value").getAsDouble();
        String currency =  jobj.get("currency").getAsString();
        MoneyCurrency moneyCurrency = new MoneyCurrency(currency, value);
        return moneyCurrency;
    }

    private static class Response {
        MoneyCurrency currency;

        private Response() {
        }
    }
}