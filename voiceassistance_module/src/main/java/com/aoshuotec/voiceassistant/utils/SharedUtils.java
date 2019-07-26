package com.aoshuotec.voiceassistant.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.aoshuotec.voiceassistant.application.MApplication;

/**
 * Created by sun on 2019/3/26
 */

public class SharedUtils {

    public static final String FILE_ACCESS_TOKEN = "ttt";

    public static final String KEY_ACCESS_TOKEN = "KEY_ACCESS_TOKEN";
    public static final String KEY_SGM_TOKEN = "KEY_SGM_TOKEN";
    public static final String KEY_BD_LOGIN = "KEY_BD_LOGIN";
    public static final String KEY_BD_OPEN_ID = "KEY_BD_OPEN_ID";
    public static final String KEY_SGM_LOGIN = "KEY_SGM_LOGIN";
    public static final String KEY_USER_NAME = "KEY_USER_NAME";

    public static void save(String fileName, String key, String value) {
        SharedPreferences sharedPreferences = MApplication.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static void save(String fileName, String key, boolean value) {
        SharedPreferences sharedPreferences = MApplication.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public static String getStr(String fileName, String key) {
        SharedPreferences sharedPreferences = MApplication.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static boolean getBoolean(String fileName, String key) {
        SharedPreferences sharedPreferences = MApplication.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }
}
