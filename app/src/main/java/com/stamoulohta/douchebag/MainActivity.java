package com.stamoulohta.douchebag;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Boolean cf = this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        // TODO: Make sure app is downloadable only to phones with camera and flash.
        Log.d("CAMERA_FEATURE", cf ? "true " : "false");
        // TODO: Check for permissions on other phones!
        ((StrobeView) findViewById(R.id.strobe_view)).setFlashLightController(new FlashLightController(this));
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        //TODO: release the camera.
        super.onStop();
    }

}
