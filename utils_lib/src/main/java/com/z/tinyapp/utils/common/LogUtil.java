package com.z.tinyapp.utils.common;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;

/**
 * 日志(用户行为监控)
 *
 */
public class LogUtil {

    public final static String TAG = "tinyApp";

    /**
     * 添加国双日志
     *
     * @param context 上下文对象
     * @param log     日志内容
     */
    public static void addLog(Context context, String log) {

    }

    /**
     * Log日志的方法
     *
     * @param type 日志类型
     * @param tag  日志TAG
     * @param msg  日志内容
     */
    public static void Log(LogType type, String tag, String msg) {
        //TODO 监听是否为生产版本
        if (false) {
            // 如果是生产版本 则日志不打印出来了
            return;
        }
        switch (type) {
            case d:
                android.util.Log.d(CheckTag(tag), msg);
                break;
            case i:
                android.util.Log.i(CheckTag(tag), msg);
                break;
            case w:
                android.util.Log.w(CheckTag(tag), msg);
                break;
            case e:
                android.util.Log.e(CheckTag(tag), msg);
                break;
            case v:
            default:
                android.util.Log.v(CheckTag(tag), msg);
                break;
        }
    }

    /**
     * Log日志的方法
     *
     * @param msg 日志内容
     */
    public static void Log(String msg) {
        Log(LogType.i, LogUtil.TAG, msg);
    }

    /**
     * Log日志的方法
     *
     * @param type 日志类型
     * @param msg  日志内容
     */
    public static void Log(LogType type, String msg) {
        Log(type, null, msg);
    }

    /**
     * Log日志的方法
     *
     * @param tag 日志TAG
     * @param msg 日志内容
     */
    public static void Log(String tag, String msg) {
        Log(LogType.i, tag, msg);
    }

    /**
     * 检测tag的方法
     *
     * @param tag
     */
    public static String CheckTag(String tag) {
        if (TextUtils.isEmpty(tag)) {
            return LogUtil.TAG;
        }
        return tag;
    }

    /**
     * 文件日志
     *
     * @param filePath 日志文件存放路径
     * @param msg      日志信息
     */
    public static void FileLog(String filePath, final String msg) {
        try {
            final File file = new File(filePath);

            File pf = file.getParentFile();

            if (!pf.exists()) {
                pf.mkdirs();
            }

            if (!file.exists()) {
                file.createNewFile();
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    FileWriter fw = null;
                    BufferedWriter bw = null;
                    String datetime = "";
                    try {
                        SimpleDateFormat tempDate = new SimpleDateFormat(
                                "yyyy-MM-dd hh:mm:ss");
                        datetime = tempDate.format(new java.util.Date())
                                .toString();
                        fw = new FileWriter(file, true);//
                        // 创建FileWriter对象，用来写入字符流
                        bw = new BufferedWriter(fw); // 将缓冲对文件的输出
                        String myreadline = datetime + "\t\t" + msg;
                        bw.write(myreadline); // 写入文件
                        bw.newLine();
                        bw.flush(); // 刷新该流的缓冲
                        bw.close();
                        fw.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {

                        try {
                            bw.close();
                            fw.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件日志
     *
     * @param msg 日志信息
     */
    public static void FileLog(final String msg) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String datetime = simpleDateFormat.format(new java.util.Date())
                .toString();

        String filePath = Environment.getExternalStorageDirectory().toString()
                + File.separator + "日志" + File.separator + datetime + ".txt";
        FileLog(filePath, msg);
    }

    /**
     * 枚举 日志类型
     */
    public static enum LogType {
        v, d, i, w, e
    }
}
