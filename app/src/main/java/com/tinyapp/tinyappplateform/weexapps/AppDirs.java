package com.tinyapp.tinyappplateform.weexapps;

import android.content.Context;

import com.z.tinyapp.utils.common.FileUtil;

import java.io.File;
import java.util.ArrayList;

public class AppDirs {
    Context context = null;
    String appName;
    public AppDirs(Context context, String appName) {
        this.context = context;
        this.appName = appName;
    }
    public void checkAppDirs(){
        ArrayList<String> dirs = getAppDirs();
        for (String dir:dirs){
            newDir(dir);
        }

    }

    public String getPackageDir(String packagePath) {
        return getWeexAppRootDir() + "/" + packagePath;
    }

    public String getAppDir(){
        return getWeexAppRootDir();
    }

    public String getRootDir() {
        String sdPath = FileUtil.getFilePath(context);
        return sdPath + "/weexapps";
    }

    private void newDir(String path){
        File file = new File(path);
        file.mkdirs();// 如果文件夹不存在，则递归
    }

    private String getWeexAppRootDir() {
        String sdPath = FileUtil.getFilePath(context);
        return sdPath + "/weexapps/" + appName;
    }

    private ArrayList<String> getAppDirs() {
        String root = getWeexAppRootDir();
        ArrayList<String> ret = new ArrayList<>();
        ret.add(root + "/images");
        ret.add(root + "/WeexCard");
        ret.add(root + "/WeexMain");
        ret.add(root + "/WeexInfoFlowCard");
        ret.add(root + "/WeexVRCard");
        return ret;
    }
}
