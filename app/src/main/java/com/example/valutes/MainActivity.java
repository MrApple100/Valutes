package com.example.valutes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends FragmentActivity {

    static MainActivity instance;
    static MainActivity getinstance(){

        return instance;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance=this;
        setContentView(R.layout.activity_main);
        ValutesFragment valutesFragment=new ValutesFragment();
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.add(R.id.Frame,valutesFragment);
        ft.commit();
    }
}