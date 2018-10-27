package com.stamoulohta.douchebag;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

class ProxyTimer implements View.OnTouchListener {

    static final int duration = 20;
    static boolean active = false;

    static boolean camera; // true
    static boolean screen; // true
    static boolean touch;  // true
    static int frequency;  // 200 - duration
    static int modifier;   // 300 - duration

    private FlashLightController flashController;

    ProxyTimer(Context context) {
        flashController = new FlashLightController(context);
    }

    @Override
    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                active = true;
                if (camera) flashController.strobeOn();
                if (screen) ((StrobeView) v).strobeOn();
                break;
            case MotionEvent.ACTION_UP:
                active = false;
        }

        if (touch) frequency = modifier - (int) ((event.getX() / v.getWidth()) * 100);
        return true;
    }
}
