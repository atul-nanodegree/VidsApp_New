package com.vidsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by somendra on 11-Jan-17.
 */
public class VidsPreference {
    private static VidsPreference instance = null;
    private static SharedPreferences pref;
    private static Context context;

    // key

    private static final String KEY_RADIO_AD_COUNTER = "radioad";


    public static VidsPreference getInstance(Context ctx) {
        if (instance == null) {
            instance = new VidsPreference();
            context = ctx;
            pref = PreferenceManager.getDefaultSharedPreferences(context);
        }
        return instance;
    }

    public int getRadioAdCounter() {
        return pref.getInt(KEY_RADIO_AD_COUNTER, 0);
    }

    public void setRadioAdCounter(int n_iPriority) {
        pref.edit().putInt(KEY_RADIO_AD_COUNTER, n_iPriority).commit();
    }
}
