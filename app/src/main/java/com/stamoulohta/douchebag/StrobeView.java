package com.stamoulohta.douchebag;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

class StrobeView extends View implements View.OnTouchListener {

    private static final int rad = 160;
    FlashLightController flc;
    Handler handler;
    private int hRad = rad / 2;
    private int x = 0, y = 0, i = 0;
    private boolean show = false;
    private boolean active = false;
    private Paint[] yinYang = new Paint[2];
    private int[] vColors = new int[]{R.color.lightOff, R.color.lightOn};

    public StrobeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        int val = 0x00;
        for (int i = 0; i <= 1; i++) {
            yinYang[i] = new Paint(Paint.ANTI_ALIAS_FLAG);
            if (i != 0) val = 0x210513;
            yinYang[i].setARGB(0xff, val, val, val);
        }
        setOnTouchListener(this);
        handler = new Handler();
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (show) {
            canvas.drawCircle(x, y, rad, this.yinYang[i]);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                show = true;
                if (flc != null) {
                    flc.active = true;
                    flc.strobeOn();
                }
                strobeOn();
                break;
            case MotionEvent.ACTION_UP:
                if (flc != null) flc.active = false;
                active = false;
                show = false;
        }
        float evX = event.getX();
        if (flc != null) flc.setInterval(evX, getWidth());
        x = (int) evX - hRad;
        y = (int) event.getY() - hRad;

        invalidate();
        return true;
    }

    void setFlashLightController(FlashLightController flc) {
        this.flc = flc;
    }

    private void strobeOn() {
        active = true;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                i = (i + 1) % 2;
                Log.d("INDEX", "" + i);
                setBackgroundResource(vColors[i]);
                if (active) handler.postDelayed(this, flc.interval);
            }
        }, 0);
    }
}
