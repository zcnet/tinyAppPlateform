package com.z.tinyapp.utils.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhengfei on 2018/9/11.
 */

public class DateUtil {
    public static SimpleDateFormat sdf_mydh;
    public static SimpleDateFormat sdf_ymdhm;
    public static int timeToLong(String time){
        if(sdf_mydh==null){
            sdf_mydh = new SimpleDateFormat("yyyyMMddhhmm");
        }
        try {
            Date nowTime = new Date();
            Date date= sdf_mydh.parse(time);
            long pasttime = nowTime.getTime() - date.getTime();
            int passedmin = (int) (pasttime / 60/ 1000);
            return passedmin;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getNowData(){
        if(sdf_ymdhm==null){
            sdf_ymdhm=new SimpleDateFormat("yyyyMMddHHmmss");
        }
        return sdf_ymdhm.format(new Date());
    }
}
