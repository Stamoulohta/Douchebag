package com.stamoulohta.douchebag;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((StrobeView) findViewById(R.id.strobe_view)).setFlashLightController(new FlashLightController(this));
    }
}
