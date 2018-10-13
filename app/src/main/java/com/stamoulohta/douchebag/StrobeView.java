package com.stamoulohta.douchebag;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

class StrobeView extends View {

    private Handler handler;
    private int i = 0;
    private int[] vColors = new int[]{R.color.lightOff, R.color.lightOn};

    public StrobeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(new ProxyTimer(getContext()));
        handler = new Handler();
    }

    void strobeOn() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                i = (i + 1) % 2;
                setBackgroundResource(vColors[i]);
                if (ProxyTimer.active || i == 1)
                    handler.postDelayed(this, i == 1 ? ProxyTimer.duration : ProxyTimer.interval);
            }
        }, 0);
    }
}
