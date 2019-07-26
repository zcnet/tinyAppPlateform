package com.tinyapp.tinyappplateform.weexapps;

import android.content.Context;

import com.z.tinyapp.utils.common.FileUtil;

import java.io.BufferedReader;
import java.io.FileReader;

class Utils {
    public static String getJson(final Context context, String pathname){
        String sdPath = FileUtil.getFilePath(context);
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(sdPath+"/weexapps"+pathname));
            String s = new String("");
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(s);//.replace("\t","").replace(" ", "")
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        String ret = result.toString();
        return ret;
    }
}
