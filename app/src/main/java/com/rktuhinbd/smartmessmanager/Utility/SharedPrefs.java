package com.rktuhinbd.smartmessmanager.Utility;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by Mr. Forhad on 6/20/2019.
 */
public class SharedPrefs {

    private SharedPreferences sharedPreferences;

    private static final String PREFERENCE = "pref";

    public static final String MEMBERS = "members";
    public static final String MONTH_SELECTED = "month_selected";


    public SharedPrefs(Context mContext) {
        sharedPreferences = mContext.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
    }

    public void setSharedPrefDataInt(String KEY, int value) {
        sharedPreferences.edit().putInt(KEY, value).apply();
    }

    public int getSharedPrefDataInt(String KEY) {
        return sharedPreferences.getInt(KEY, -111);
    }


    public void setSharedPrefDataString(String KEY, String value) {
        sharedPreferences.edit().putString(KEY, value).apply();
    }

    public String getSharedPrefDataString(String KEY) {
        return sharedPreferences.getString(KEY, "");
    }

    public void setSharedPrefDataBoolean(String KEY, Boolean value) {
        sharedPreferences.edit().putBoolean(KEY, value).apply();
    }

    public Boolean getSharedPrefDataBoolean(String KEY) {
        return sharedPreferences.getBoolean(KEY, false);
    }

    public void setSharedPrefDataArray(String KEY, Set<String> value) {
        sharedPreferences.edit().remove(KEY).apply();
        sharedPreferences.edit().putStringSet(KEY, value).apply();
    }

    public Set<String> getSharedPrefDataArray(String KEY) {
        return sharedPreferences.getStringSet(KEY, null);
    }

    public void removeSharedPref(String KEY) {
        sharedPreferences.edit().remove(KEY).apply();
    }

    public void removeAllSharedPref() {
//        sharedPreferences.edit().remove(NEED_REMOVE_BTN).apply();
    }
}
