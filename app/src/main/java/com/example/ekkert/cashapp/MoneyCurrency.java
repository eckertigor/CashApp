package com.example.ekkert.cashapp;


public class MoneyCurrency {
    private String currency;
    private double value;

    public String getValue() {
        return String.valueOf(this.value);
    }
    public String getCurrency() {
        return this.currency;
    }
}
