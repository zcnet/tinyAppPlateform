package com.z.tinyapp.network.httputil;


import com.z.tinyapp.userinfo.user.UserInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhengfei on 2018/8/14.
 */

public class NetUtils {

    public static Map<String,Object> getBaseMap(){
        Map<String,Object> baseMap=new HashMap<>();
        baseMap.put("accessToken", "dfd02de6123fdffcbd55ae9a1316b349");
        baseMap.put("vin","vehicle0032");
        baseMap.put("oem","DELPHI");
        baseMap.put("hardwareVersion","0.0.0.1");
        baseMap.put("hardwareCode","0.0.0.1");
        baseMap.put("mdlwareCode","0.0.0.1");
        baseMap.put("mdlwareVersion","0.0.0.1");
        return baseMap;
    }

    public static Map<String,Object> getBaseMap1(){
        Map<String,Object> baseMap=new HashMap<>();
        baseMap.put("vin","vehicle0032");
        baseMap.put("oem","DELPHI");
        baseMap.put("hardwareVersion","0.0.0.1");
        baseMap.put("hardwareCode","0.0.0.1");
        baseMap.put("mdlwareCode","0.0.0.1");
        baseMap.put("mdlwareVersion","0.0.0.1");
        return baseMap;
    }
    public static Map<String,Object> getTokenMap(){
        Map<String,Object> baseMap=new HashMap<>();
        baseMap.put("accessToken",UserInfo.getInstance().getAccessToken());
        return baseMap;
    }
}
