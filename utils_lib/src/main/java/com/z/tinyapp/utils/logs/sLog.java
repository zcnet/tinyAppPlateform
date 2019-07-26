package com.z.tinyapp.utils.logs;

import android.util.Log;

public class sLog {
    private static boolean closed = false;
    private static boolean force_default = true;
    private static final String DefaultTag = "VASLOG";
    public static void v(String tag, String msg){
        tag = null;
        if (!closed)
            Log.v((force_default||tag==null)?DefaultTag:tag,msg);
    }
    public static void d(String tag, String msg){
        if (!closed)
            Log.d((force_default||tag==null)?DefaultTag:tag,msg);
    }
    public static void d(String tag, String msg, Throwable tr){
        if (!closed)
            Log.d((force_default||tag==null)?DefaultTag:tag,msg,tr);
    }
    public static void i(String tag, String msg){
        if (!closed)
            Log.i((force_default||tag==null)?DefaultTag:tag,msg);
    }
    public static void i(String tag, String msg, Throwable tr) {
        if (!closed)
            Log.i((force_default || tag == null) ? DefaultTag : tag, msg, tr);
    }
    public static void w(String tag, String msg){
        if (!closed)
            Log.w((force_default||tag==null)?DefaultTag:tag,msg);
    }
    public static void e(String tag, String msg){
        if (!closed)
            Log.e((force_default||tag==null)?DefaultTag:tag,msg);
    }
    public static void e(String tag, String msg, Throwable tr){
        if (!closed)
            Log.e((force_default||tag==null)?DefaultTag:tag,msg, tr);
    }
    public static void printCallStatck() {
        /*Throwable ex = new Throwable();
        StackTraceElement[] stackElements = ex.getStackTrace();
        if (stackElements != null) {
            for (int i = 0; i < stackElements.length; i++) {
                i(null, stackElements[i].getClassName()+" " + stackElements[i].getFileName()+" "+stackElements[i].getLineNumber()+" "+stackElements[i].getMethodName());
            }
        }*/
    }
}
