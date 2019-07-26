package com.z.tinyapp.common.base;

/**
 * 布局注解工具类
 * @author  zhengfei
 * @date  2017/01/04
 */

public class LayoutUtils {

    /** 通过此方法获取注解填写的布局 */
    public static int LayoutInflater(Object obj){
        int layoutRes=0;
        Layout layout =obj.getClass().getAnnotation(Layout.class);
        if(layout !=null){
            layoutRes= layout.value();
        }
        return layoutRes;
    }
}
