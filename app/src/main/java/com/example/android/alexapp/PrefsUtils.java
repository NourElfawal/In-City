package com.example.android.alexapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by amany on 4/4/2017.
 */

public class PrefsUtils {

    public static final String PREFS_SERVICE_FLAG = "service";
    private static final String PREFS = "nawart";

    //region remove a pref

    public static void removePref(String key, Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        prefs.edit().remove(key).apply();
    }


    //endregion

    //region Write to Prefs

    public static void writePref(String key, String value, Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        prefs.edit().putString(key, value).apply();
    }

    public static void writePref(String key, int value, Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        prefs.edit().putInt(key, value).apply();
    }

    //endregion

    //region Read From Prefs

    public static String readPrefs(String key, String defaultValue, Context ctx) {
        String result = defaultValue;
        SharedPreferences prefs = null;
        if (ctx != null) {
            prefs = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        }
        if (prefs != null) {
            result = prefs.getString(key, defaultValue);
        }
        return result;
    }

    //endregion

    //region check prefs if key exist

    public static boolean checkPrefs(String key, Context ctx) {
        boolean result = false;
        SharedPreferences prefs = null;
        try {
            if (ctx != null) {
                prefs = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
            }
            if (prefs != null) {
                result = prefs.contains(key);
            }
        } catch (Exception e) {
            Log.e("visitPrefRead", e.getMessage());
        } finally {
            return result;
        }
    }

    //endregion
}