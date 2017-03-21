package com.example.ekkert.cashapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;


public class MyStorage {
    private static MyStorage INSTANCE;
    private SharedPreferences preferences;
    private static final Gson gson = new Gson();

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
        try {
            return gson.fromJson(json, MoneyCurrency.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    static String serialize(MoneyCurrency moneyCurrency) {
        return gson.toJson(moneyCurrency);
    }


}