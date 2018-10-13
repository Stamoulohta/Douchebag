package com.stamoulohta.douchebag;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

class ProxyTimer implements View.OnTouchListener {

    static int duration = 20;
    static int interval = 100;
    static boolean active = false;
    static boolean state = false;

    private FlashLightController flashController;

    ProxyTimer(Context context) {
        flashController = new FlashLightController(context);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                active = true;
                flashController.strobeOn();
                ((StrobeView) v).strobeOn();
                break;
            case MotionEvent.ACTION_UP:
                active = false;
        }

        interval = 110 - (int) ((event.getX() / v.getWidth()) * 100);
        return true;
    }
}
