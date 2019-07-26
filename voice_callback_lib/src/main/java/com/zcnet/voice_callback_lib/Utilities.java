package com.zcnet.voice_callback_lib;

import java.util.Collection;
import java.util.Map;

public class Utilities {
    // 对象判空，如果为空，返回 true，如果非空，返回 false。
    public static boolean isNull(Object obj ){
        if(obj == null){
            return true;
        }
        if(obj instanceof String){
            return "".equals( obj );
        }
        if(obj instanceof Object[]){
            return ((Object[])obj).length == 0;
        }
        if(obj instanceof Collection){
            return ((Collection<?>)obj).size() == 0;
        }
        if(obj instanceof Map){
            return ((Map<?,?>)obj).size() == 0;
        }
        return false;
    }
}