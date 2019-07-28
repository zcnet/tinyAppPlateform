package com.z.voiceassistant.utils;

import com.z.voiceassistant.application.MApplication;
import com.z.tinyapp.utils.logs.sLog;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by sun on 2019/4/1
 */

public class FileUtils {

    private static final String TAG = "sunFileUtils";

    private static final String sPath = "/sdcard/tinyApp/style.data";

    private static FileUtils sInstance;

    private FileUtils() {
        loadFile();
    }

    public static FileUtils getInstance() {
        synchronized (FileUtils.class) {
            if (sInstance == null) {
                sInstance = new FileUtils();
            }
        }
        return sInstance;
    }

    private void loadFile() {

        File file = new File(sPath);

        if (file.exists()) {
            sLog.i(TAG, "loadFile: file exists !!!");
            return;
        }

        boolean b;
        InputStream inputStream = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        if (!file.exists()) {
            b = file.mkdirs();
            sLog.i(TAG, "loadFile: b - > " + b);
        }
        try {
            inputStream = MApplication.getContext().getAssets().open("style.data");
            if (file.exists()) {
                b = file.delete();
                sLog.i(TAG, "loadFile: b ->> " + b);
            }
            b = file.createNewFile();
            sLog.i(TAG, "loadFile: b ->>> " + b);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            byte[] bytes = new byte[1024];
            while (inputStream.read(bytes) > 0) {
                bos.write(bytes, 0, bytes.length);
            }
            sLog.i(TAG, "loadFile: copy over -> ");
        } catch (IOException e) {
            sLog.i(TAG, "loadFile: error -> ", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (bos != null) {
                    bos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                sLog.i(TAG, "loadFile: error ->> ", e);
            }
        }
    }


}
