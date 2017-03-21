package com.example.ekkert.cashapp;

/**
 * Created by ekkert on 21.03.17.
 */
public class MoneyCurrency {
    private String currency;
    private double value;

    public MoneyCurrency(String cur, double val) {
        currency = cur;
        value = val;
    }

    public String getValue() {
        return String.valueOf(this.value);
    }
}
