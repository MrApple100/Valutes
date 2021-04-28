package com.example.valutes;

import android.graphics.Bitmap;

public class Country {
    public String value;
    public String name;
    public Bitmap pick;

    public Country(String value, String name, Bitmap pick) {
        this.value = value;
        this.name = name;
        this.pick = pick;
    }
}
