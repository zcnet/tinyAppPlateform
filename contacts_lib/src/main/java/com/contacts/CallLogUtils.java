package com.contacts;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.z.tinyapp.utils.logs.sLog;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;



/**
 * Created by sun on 2019/3/4
 */

public class CallLogUtils {

    private static final String TAG = "sunCallLogUtils";

    //通话记录Map
    private static Map<Integer, RecentCallInfo> mCallLogMap ;

    @SuppressLint("UseSparseArrays")
    public static void addMap(Map<Integer, RecentCallInfo> map){
        sLog.i(TAG, "addMap: "+map.size());
        if (mCallLogMap == null) {
            mCallLogMap = new HashMap<>();
        }
        mCallLogMap.clear();
        mCallLogMap.putAll(map);
    }

    /**
     * 根据Type过滤通话记录
     */
    public static Map<Integer, RecentCallInfo> filterCallLogType(int type) {

        //type为-1 即不过滤
        if (type == -1) {
            return mCallLogMap;
        }

        Map<Integer, RecentCallInfo> map = new Gson().
                fromJson(
                        new Gson().toJson(mCallLogMap),
                        new TypeToken<Map<Integer, RecentCallInfo>>() {
                        }.getType());

        Set<Integer> set = map.keySet();
        Iterator<Integer> iterator = set.iterator();
        while (iterator.hasNext()){
            if(type!=map.get(iterator.next()).callType.value()){
                iterator.remove();
            }
        }
        return map;
    }



}