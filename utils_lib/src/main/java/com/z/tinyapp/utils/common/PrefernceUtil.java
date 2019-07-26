package com.z.tinyapp.utils.common;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengfei on 2018/8/7.
 */

public class PrefernceUtil {
    private static SharedPreferences mSharedPreferences;
    private static Context context;

    /**
     * 需在Application的onCreate方法中调用
     *
     * @param con
     */
    public static void initContext(Context con) {
        context = con;
    }

    private static synchronized SharedPreferences getPreferneces() {
        if (mSharedPreferences == null) {
            mSharedPreferences = context
                    .getSharedPreferences("tinyappplateform", Context.MODE_PRIVATE);
        }
        return mSharedPreferences;
    }


    /**
     * 清空保存在默认SharePreference下的所有数据
     */
    public static void clear() {
        getPreferneces().edit().clear().commit();
    }

    /**
     * 保存字符串
     *
     * @return
     */
    public static void putString(String key, String value) {
        getPreferneces().edit().putString(key, value).commit();
    }

    /**
     * 读取字符串
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        return getPreferneces().getString(key, "");
    }



    /**
     * 保存字符串
     *
     * @return
     */
    public static void putStrArray(String key, List<String> data) {
        StringBuffer sb = new StringBuffer();
        if (data != null && data.size() != 0) {
            for (int i = 0; i < data.size(); i++) {
                sb.append(data.get(i));

                if (i != data.size() - 1) {
                    sb.append(",");
                }
            }
        }

        getPreferneces().edit().putString(key, sb.toString()).commit();
    }

    /**
     * 保存字符串
     *
     * @return
     */
    public static void putStrArray(String key, String data, int max) {
        getPreferneces().getString(key, "");
        String str = getPreferneces().getString(key, "");
        try {
            List<String> list = getStrArray(key);

            if (str.contains(data)) {
                for (int i = 0; i < list.size(); i++) {
                    if (data.equals(list.get(i))) {
                        list.remove(i);
                        break;
                    }
                }
            }

            while (list.size() > max - 1) {
                list.remove(list.size() - 1);
            }

            list.add(0, data);

            putStrArray(key, list);
        } catch (Exception e) {
        }

    }

    /**
     * 读取字符串
     *
     * @param key
     * @return
     */
    public static List<String> getStrArray(String key) {
        String data = getPreferneces().getString(key, "");
        List<String> result = new ArrayList<String>();
        try {
            String[] ss = data.split(",");

            for (String s : ss) {
                if (s != null && !s.equals(""))
                    result.add(s);
            }

        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 读取字符串
     *
     * @param key
     * @return
     */
    public static String getString(String key, String defaultsString) {
        return getPreferneces().getString(key, defaultsString);

    }

    /**
     * 保存整型值
     *
     * @return
     */
    public static void putInt(String key, int value) {
        getPreferneces().edit().putInt(key, value).commit();
    }

    /**
     * 读取整型值
     *
     * @param key
     * @return
     */
    public static int getInt(String key, int defInt) {
        return getPreferneces().getInt(key, defInt);
    }

    /**
     * 保存布尔值
     *
     * @return
     */
    public static void putBoolean(String key, Boolean value) {
        getPreferneces().edit().putBoolean(key, value).commit();
    }

    public static void putLong(String key, long value) {
        getPreferneces().edit().putLong(key, value).commit();
    }

    public static long getLong(String key, long defLong) {
        return getPreferneces().getLong(key, defLong);
    }

    /**
     * t 读取布尔值
     *
     * @param key
     * @return
     */
    public static boolean getBoolean(String key, boolean defValue) {
        return getPreferneces().getBoolean(key, defValue);

    }

    public static void putFloat(String key, float value) {
        getPreferneces().edit().putFloat(key, value).commit();

    }

    public static float getFloat(String key, float defValue) {
        return getPreferneces().getFloat(key, defValue);

    }

    /**
     * 移除字段
     *
     * @return
     */
    public static void remove(String key) {
        getPreferneces().edit().remove(key).commit();
    }

}
