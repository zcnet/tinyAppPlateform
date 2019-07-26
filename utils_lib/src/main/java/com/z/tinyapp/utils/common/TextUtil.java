package com.z.tinyapp.utils.common;

/**
 * Created by zhengfei on 2018/8/8.
 */

public class TextUtil {
    public static boolean isEmpty(String str){
        if(null==str||"".equals(str)){
            return true;
        }
        return false;
    }
}
