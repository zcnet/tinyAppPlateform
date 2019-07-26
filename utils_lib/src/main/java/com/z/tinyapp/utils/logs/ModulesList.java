package com.z.tinyapp.utils.logs;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by GongDongdong on 2018/7/19.
 */

public class ModulesList {
    public Map<String, LogSpec> getMyMap() {
        return myMap;
    }

    public void setMyMap(Map<String, LogSpec> myMap) {
        this.myMap = myMap;
    }

    public void addOneMod(String modTag, LogSpec logSpec) {
        if(this.myMap != null){
            this.myMap.put(modTag, logSpec);
        }
        else{
            myMap = new Hashtable<>();
            this.myMap.put(modTag, logSpec);
        }

    }

    public LogSpec getLogSpec(String modTag){
        LogSpec logSpec = null;

        if(myMap != null){
            logSpec = myMap.get(modTag);
        }
        return logSpec;
    }

    private Map<String, LogSpec> myMap = new Hashtable<>();

    private ModulesList(){}

    private static ModulesList sInstance = null;
    public static ModulesList getInstance(){
        if(sInstance == null){
            sInstance = new ModulesList();
            return sInstance;
        }
        else{
            return sInstance;
        }
    }
    public void initLogModule(Context context){
        StringBuffer sb = null;
        try {
            InputStream is = context.getAssets().open("logConfig.conf");
            byte[] buff = new byte[1024];
            sb = new StringBuffer(1024);
            int length = 0;
            while((length = is.read(buff)) > 0){
                sb.append(new String(buff, 0, length));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if(sb == null) return;
            JSONArray jsonArray = new JSONArray(sb.toString());
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject oneSpecJson = jsonArray.getJSONObject(i);
                LogSpec oneSpec = new LogSpec();
                oneSpec.setDebug(oneSpecJson.getBoolean("isDebug"));
                oneSpec.setModuleName(oneSpecJson.getString("modulename"));
                oneSpec.setDefaultLevel(oneSpecJson.getInt("loglevel"));
                addOneMod(oneSpec.getModuleName(), oneSpec);
            }
            printMyModules();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void printMyModules() {
        StringBuffer sb = new StringBuffer();
        if(myMap != null){
            for(String key : myMap.keySet()){
                LogSpec oneSpec = myMap.get(key);
                sb.append(oneSpec.toString() + "\n");
            }
        }
        sLog.e("gdd", sb.toString());
    }
}
