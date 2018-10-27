package com.stamoulohta.douchebag;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    SettingsFragment settingsFragment;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
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

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case SettingsFragment.CAMERA_KEY:
                break;
            case SettingsFragment.SCREEN_KEY:
                break;
            case SettingsFragment.TOUCH_KEY:
                break;
            case SettingsFragment.FREQ_KEY:
                break;
            case SettingsFragment.MOD_KEY:
                break;
        }
    }

    private void setHomeButtonEnabled(boolean show) {
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(show);
            actionBar.setDisplayHomeAsUpEnabled(show);
        }
    }
}