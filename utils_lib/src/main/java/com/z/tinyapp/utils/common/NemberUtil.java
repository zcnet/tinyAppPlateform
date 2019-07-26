package com.z.tinyapp.utils.common;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * Created by zhengfei on 2018/9/14.
 */

public class NemberUtil {
    public static DecimalFormat decimalFormat1;
    public static Double strToDouble(String str){
        double dou=0.0;
        try {
            dou=Double.parseDouble(str);
        }catch (Exception e){
            e.printStackTrace();
        }
        return dou;
    }
    public static String formatDistance(int distance){
        if(distance<1000){
            return distance+"m";
        }else{
            if (decimalFormat1==null){
                decimalFormat1=new DecimalFormat(".##");
            }
            return decimalFormat1.format((float)distance/1000)+"km";
        }
    }

    public static String formatDouble(double distance){
        if (decimalFormat1==null){
            decimalFormat1=new DecimalFormat(".##");
        }
        return decimalFormat1.format(distance)+"";
    }

    static Random random;
    public static int getRandom(){
        if(random==null){
            random = new Random();
        }
        return random.nextInt(10);
    }
}
