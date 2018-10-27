package com.stamoulohta.douchebag;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    SettingsFragment settingsFragment;
    SharedPreferences sharedPreferences;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        setProxyTimer();
        settingsFragment = new SettingsFragment();
        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction().add(R.id.fragment_holder, new StrobeFragment()).commit();
        setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));
        actionBar = getSupportActionBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.d("MENU_NEW", "new menu created");
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                if (getFragmentManager().findFragmentById(R.id.fragment_holder) instanceof SettingsFragment)
                    return true;
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_holder, settingsFragment)
                        .addToBackStack(null)
                        .commit();
                setHomeButtonEnabled(true);
                return true;

            case android.R.id.home:
                super.onBackPressed();
                setHomeButtonEnabled(false);
                return true;

            default:
                return false;
        }
    }

    private void setHomeButtonEnabled(boolean show) {
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(show);
            actionBar.setDisplayHomeAsUpEnabled(show);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case SettingsFragment.CAMERA_KEY:
                ProxyTimer.camera = sharedPreferences.getBoolean(SettingsFragment.CAMERA_KEY, true);
                break;
            case SettingsFragment.SCREEN_KEY:
                ProxyTimer.screen = sharedPreferences.getBoolean(SettingsFragment.SCREEN_KEY, true);
                break;
            case SettingsFragment.TOUCH_KEY:
                ProxyTimer.touch = sharedPreferences.getBoolean(SettingsFragment.TOUCH_KEY, true);
                // no break for resetting the frequency as well.
            case SettingsFragment.FREQ_KEY:
                ProxyTimer.frequency = sharedPreferences.getInt(SettingsFragment.FREQ_KEY, 200) + 50 - ProxyTimer.duration;
                break;
            case SettingsFragment.MOD_KEY:
                ProxyTimer.modifier = sharedPreferences.getInt(SettingsFragment.MOD_KEY, 300) + 150 - ProxyTimer.duration;
                break;
        }
    }

    private void setProxyTimer() {
        ProxyTimer.camera = sharedPreferences.getBoolean(SettingsFragment.CAMERA_KEY, true);
        ProxyTimer.screen = sharedPreferences.getBoolean(SettingsFragment.SCREEN_KEY, true);
        ProxyTimer.touch = sharedPreferences.getBoolean(SettingsFragment.TOUCH_KEY, true);
        ProxyTimer.frequency = sharedPreferences.getInt(SettingsFragment.FREQ_KEY, 200) + 50 - ProxyTimer.duration;
        ProxyTimer.modifier = sharedPreferences.getInt(SettingsFragment.MOD_KEY, 300) + 150 - ProxyTimer.duration;
    }
}