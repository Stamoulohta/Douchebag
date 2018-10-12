package com.stamoulohta.douchebag;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Handler;


class FlashLightController {

    int interval = 100;
    boolean active = false;
    private Handler handler;
    private Camera camera;
    private Camera.Parameters parameters;
    private CameraManager cameraManager;
    private String cameraId;
    private boolean state = false;

    FlashLightController(Context context) {
        this.handler = new Handler();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                if (cameraManager != null) {
                    cameraId = cameraManager.getCameraIdList()[0];
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        } else {
            camera = Camera.open();
            parameters = camera.getParameters();
        }
    }

    void strobeOn() {
        if (cameraManager != null) {
            handler.postDelayed(new Runnable() {
                @SuppressLint("NewApi")
                @Override
                public void run() {
                    state = !state;
                    try {
                        cameraManager.setTorchMode(cameraId, state && active);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                    if (active) handler.postDelayed(this, interval);
                }
            }, 0);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    state = !state;
                    if (state && active) {
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        camera.setParameters(parameters);
                        camera.startPreview();
                        if (active) handler.postDelayed(this, interval);
                    } else {
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        camera.setParameters(parameters);
                        camera.stopPreview();
                    }
                }
            }, 0);
        }
    }

    void setInterval(float x, int w) {
        interval = 110 - (int) ((x / w) * 100);
    }
}