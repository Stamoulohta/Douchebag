package com.stamoulohta.douchebag;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceManager;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String CAMERA_KEY = "CAMERA";
    public static final String SCREEN_KEY = "SCREEN";
    public static final String TOUCH_KEY = "TOUCH";
    public static final String FREQ_KEY = "FREQ";
    public static final String MOD_KEY = "MOD";

    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onResume() {
        setDisabledPreferences(sharedPreferences.getBoolean(TOUCH_KEY, true));
        super.onResume();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(TOUCH_KEY)) {
            setDisabledPreferences(sharedPreferences.getBoolean(TOUCH_KEY, true));
        }
    }

    private void setDisabledPreferences(boolean touch) {
        getPreferenceScreen().findPreference(MOD_KEY).setEnabled(touch);
        getPreferenceScreen().findPreference(FREQ_KEY).setEnabled(!touch);

    }
}
