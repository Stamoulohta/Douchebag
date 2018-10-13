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
    int duration = 20;
    boolean active = false;
    private Handler handler;
    private Camera camera;
    private Camera.Parameters parameters;
    private CameraManager cameraManager;
    private String cameraId;
    boolean state = false;

    FlashLightController(Context context) {
        this.handler = new Handler();
        // Newer devices
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                if (cameraManager != null) {
                    cameraId = cameraManager.getCameraIdList()[0];
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
            // Older devices
        } else {
            camera = Camera.open();
            parameters = camera.getParameters();
        }
    }

    void strobeOn() {
        // Newer devices
        if (cameraManager != null) {
            handler.postDelayed(new Runnable() {
                @SuppressLint("NewApi")
                @Override
                public void run() {
                    state = !state;
                    try {
                        cameraManager.setTorchMode(cameraId, state && active);
                    } catch (IllegalArgumentException | CameraAccessException e) {
                        // TODO: change the camera id or inform the user.
                        e.printStackTrace();
                    }
                    if (active) handler.postDelayed(this, state ? duration : interval);
                }
            }, 0);
            // Older devices
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    state = !state;
                    if (state && active) {
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        camera.setParameters(parameters);
                        camera.startPreview();
                        if (active) handler.postDelayed(this, state ? duration : interval);
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