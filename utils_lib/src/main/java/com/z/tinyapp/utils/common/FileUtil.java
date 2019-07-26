
package com.z.tinyapp.utils.common;

import android.content.Context;
import android.os.Environment;

import com.z.tinyapp.utils.logs.sLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FileUtil {
    //获取内部存储路径
    public static String getAbsolutePath(Context context){
        return  getFilePath(context)+"/weexapps";
    }

    //获取内部存储路径
    public static String getAudioPath(Context context){
        return  getFilePath(context)+"/audio";
    }

    public static String getFilePath(Context context) {
        String directoryPath="";
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ) {//判断外部存储是否可用
            directoryPath =context.getExternalFilesDir("").getAbsolutePath();
        }else{//没外部存储就使用内部存储
            directoryPath=context.getFilesDir().getAbsolutePath();
        }
        return directoryPath;
    }
    /**
     * 创建文件
     *
     * @param path     文件所在目录的目录名，如/java/test/0.txt,要在当前目录下创建一个文件名为1.txt的文件，<br>
     *                 <p>
     *                 则path为/java/test，fileName为1.txt
     * @param fileName 文件名
     * @return 文件新建成功则返回true
     */
    public static boolean createFile(String path, String fileName) {
        File file = new File(path + File.separator + fileName);
        if (file.exists()) {
            return false;
        } else {
            try {
                boolean isCreated = file.createNewFile();
                return isCreated;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 删除单个文件
     *
     * @param file 要删除的文件对象
     * @return 文件删除成功则返回true
     */

    public static boolean deleteFile(File file) {
        if (file.exists()) {
            boolean isDeleted = file.delete();
            return isDeleted;
        } else {
            return false;
        }
    }


    /**
     * 删除单个文件
     *
     * @param path     文件所在路径名
     * @param fileName 文件名
     * @return 删除成功则返回true
     */

    public static boolean deleteFile(String path, String fileName) {
        File file = new File(path + File.separator + fileName);
        if (file.exists()) {
            boolean isDeleted = file.delete();
            return isDeleted;
        } else {
            return false;
        }
    }

    public static void deleteDir(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDir(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    /**
     * 复制文件
     *
     * @param srcPath 源文件绝对路径
     * @param destDir 目标文件所在目录
     * @return boolean
     */
    public static boolean copyFile(String srcPath, String destDir) {
        boolean flag = false;
        File srcFile = new File(srcPath); // 源文件
        if (!srcFile.exists()) {
            // 源文件不存在
            return false;
        }
        // 获取待复制文件的文件名
        String fileName = srcPath.substring(srcPath.lastIndexOf(File.separator));
        String destPath = destDir + fileName;
        if (destPath.equals(srcPath)) {
            // 源文件路径和目标文件路径重复
            return false;
        }
        File destFile = new File(destPath); // 目标文件
        if (destFile.exists() && destFile.isFile()) {
            // 该路径下已经有一个同名文件
            return false;
        }
        File destFileDir = new File(destDir);
        destFileDir.mkdirs();
        try {
            FileInputStream fis = new FileInputStream(srcPath);
            FileOutputStream fos = new FileOutputStream(destFile);
            byte[] buf = new byte[1024];
            int c;
            while ((c = fis.read(buf)) != -1) {
                fos.write(buf, 0, c);
            }
            fis.close();
            fos.close();
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 根据文件名获得文件的扩展名
     *
     * @param fileName 文件名
     * @return 文件扩展名（不带点）
     */
    public static String getFileSuffix(String fileName) {
        int index = fileName.lastIndexOf(".");
        String suffix = fileName.substring(index + 1, fileName.length());
        return suffix;
    }

    /**
     * 重命名文件
     *
     * @param oldPath 旧文件的绝对路径
     * @param newPath 新文件的绝对路径
     * @return 文件重命名成功则返回true
     */
    public static boolean renameTo(String oldPath, String newPath) {
        if (oldPath.equals(newPath)) {
            return false;
        }
        File oldFile = new File(oldPath);
        File newFile = new File(newPath);
        boolean isSuccess = oldFile.renameTo(newFile);
        return isSuccess;
    }

    /**
     * 重命名文件
     *
     * @param oldFile 旧文件对象
     * @param newFile 新文件对象
     * @return 文件重命名成功则返回true
     */
    public static boolean renameTo(File oldFile, File newFile) {
        if (oldFile.equals(newFile)) {
            return false;
        }
        boolean isSuccess = oldFile.renameTo(newFile);
        return isSuccess;
    }

    /**
     * 重命名文件
     *
     * @param oldFile 旧文件对象，File类型
     * @param newName 新文件的文件名，String类型
     * @return 重命名成功则返回true
     */
    public static boolean renameTo(File oldFile, String newName) {
        File newFile = new File(oldFile.getParentFile() + File.separator + newName);
        boolean flag = oldFile.renameTo(newFile);
        System.out.println(flag);
        return flag;
    }

    /**
     * 计算某个文件的大小
     *
     * @param file 文件对象
     * @return 文件大小，如果file不是文件，则返回-1
     */
    public static long getFileSize(File file) {
        if (file.isFile()) {
            return file.length();
        } else {
            return -1;
        }
    }

    /**
     * 计算某个文件的大小
     *
     * @param path 文件的绝对路径
     * @return
     */
    public static long getFileSize(String path) {
        File file = new File(path);
        long size = file.length();
        return size;
    }

    /**
     * 文件大小的格式化
     *
     * @param size 文件大小，单位为byte
     * @return 文件大小格式化后的文本
     */
    public static String formatSize(long size) {
        DecimalFormat df = new DecimalFormat("####.00");
        if (size < 1024) // 小于1KB
        {
            return size + "Byte";
        } else if (size < 1024 * 1024) // 小于1MB
        {
            float kSize = size / 1024f;
            return df.format(kSize) + "KB";
        } else if (size < 1024 * 1024 * 1024) // 小于1GB
        {
            float mSize = size / 1024f / 1024f;
            return df.format(mSize) + "MB";
        } else if (size < 1024L * 1024L * 1024L * 1024L) // 小于1TB
        {
            float gSize = size / 1024f / 1024f / 1024f;
            return df.format(gSize) + "GB";
        } else {
            return "size: error";
        }
    }

    /**
     * 格式化文件最后修改时间字符串
     *
     * @param time
     * @return
     */
    public static String formatTime(long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss", Locale.getDefault());
        String formatedTime = sdf.format(date);
        return formatedTime;
    }

    /**
     * 获取某个路径下的文件列表
     *
     * @param path 文件路径
     * @return 文件列表File[] files
     */
    public static File[] getFileList(String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                return files;
            } else {
                return null;
            }
        } else {
            return null;
        }

    }


    /**
     * 获取某个目录下的文件列表
     *
     * @param directory 目录
     * @return 文件列表File[] files
     */
    public static File[] getFileList(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            return files;
        } else {
            return null;
        }
    }

    /**
     * 获得根目录文件列表
     *
     * @param showHidden
     * @param showHidden 是否显示隐藏文件
     * @return
     */
    public static List<File> getSDCardFileList(boolean showHidden) {
        List<File> list = new ArrayList<>();
        File files[];
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File extDir = Environment.getExternalStorageDirectory();
            files = extDir.listFiles();
            for (File file : files) {
                list.add(file);
            }
            if (showHidden) {
            } else {
                for (int i = 0; i < list.size(); i++) {
                    File file = list.get(i);
                    if (file.isHidden() || file.getName().startsWith(".")) {
                        list.remove(file);
                    }
                }
            }
        } else {
        }
        return list;
    }


    /**
     * 判断文件夹是否存在
     *无则创建
     */
    public static void judeDirExists(String path) {
        File file = new File(path);
        if(!file.exists()){
            file.mkdir();
        }
    }


    /**
     * 新建目录
     *
     * @param file
     * @return
     */
    public static boolean createFolder(File file) {
        boolean isCreated = file.mkdir();
        return isCreated;
    }



    /**
     * 删除文件夹及其包含的所有文件
     *
     * @param file
     * @return
     */
    public static boolean deleteFolder(File file) {
        boolean flag = false;
        File files[] = file.listFiles();
        if (files != null && files.length >= 0) // 目录下存在文件列表
        {
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                if (f.isFile()) {
                    // 删除子文件
                    flag = deleteFile(f);
                    if (flag == false) {
                        return flag;
                    }
                } else {
                    // 删除子目录
                    flag = deleteFolder(f);
                    if (flag == false) {
                        return flag;
                    }
                }
            }
        }
        flag = file.delete();
        if (flag == false) {
            return flag;
        } else {
            return true;
        }
    }

    /**
     * 复制目录
     *
     * @param srcPath 源文件夹路径
     *                <p>
     *                <p>
     *                目标文件夹所在目录
     * @return 复制成功则返回true
     */
    public static boolean copyFolder(String srcPath, String destDir) {
        boolean flag = false;
        File srcFile = new File(srcPath);
        if (!srcFile.exists()) {
            // 源文件夹不存在
            return false;
        }
        String dirName = getDirName(srcPath);
        // 获得待复制的文件夹的名字，比如待复制的文件夹为"E://dir"则获取的名字为"dir"
        String destPath = destDir + File.separator + dirName; // 目标文件夹的完整路径
        // Util.toast("目标文件夹的完整路径为：" + destPath);
        if (destPath.equals(srcPath)) {
            return false;
        }
        File destDirFile = new File(destPath);
        if (destDirFile.exists()) {
            // 目标位置有一个同名文件夹
            return false;
        }
        destDirFile.mkdirs(); // 生成目录
        File[] files = srcFile.listFiles(); // 获取源文件夹下的子文件和子文件夹
        if (files.length == 0) {
            // 如果源文件夹为空目录则直接设置flag为true，这一步非常隐蔽，debug了很久
            flag = true;
        } else {
            for (File temp : files) {
                if (temp.isFile()) {
                    // 文件
                    flag = copyFile(temp.getAbsolutePath(), destPath);
                } else if (temp.isDirectory()) {
                    // 文件夹
                    flag = copyFolder(temp.getAbsolutePath(), destPath);
                }
                if (!flag) {
                    break;
                }
            }
        }
        if (flag) {
        }
        return flag;
    }

    /**
     * 获取待复制文件夹的文件夹名
     *
     * @param dir
     * @return String
     */
    public static String getDirName(String dir) {
        if (dir.endsWith(File.separator)) {
            // 如果文件夹路径以"//"结尾，则先去除末尾的"//"
            dir = dir.substring(0, dir.lastIndexOf(File.separator));
        }
        return dir.substring(dir.lastIndexOf(File.separator) + 1);
    }

    /**
     * 计算某个目录的大小
     *
     * @return
     */
    public static long getFolderSize(File directory) {
        File[] files = directory.listFiles();
        if (files != null && files.length >= 0) {
            long size = 0;
            for (File f : files) {
                if (f.isFile()) {
                    // 获得子文件的大小
                    size = size + getFileSize(f);
                } else {
                    // 获得子目录的大小
                    size = size + getFolderSize(f);
                }
            }
            return size;
        }
        return -1;
    }

    /**
     * 获得某个文件或目录的大小
     *
     * @param file
     * @return
     */
    public static long getFileOrFolderSize(File file) {
        long size = 0;
        if (file.isDirectory()) {
            size = getFolderSize(file);
        } else {
            size = getFileSize(file);
        }
        return size;
    }

    public static boolean isHasKeyGuid(Context context,String name){
        File file=new File(getAbsolutePath(context)+"/key");
        if(!file.exists()){
            file.mkdirs();
        }
        List<String> list= Arrays.asList(file.list());
        if(list.contains(name)){
            return true;
        }
        return false;
    }

    public static String getKeuGuidPath(Context context,String keyGuid){
        return FileUtil.getAbsolutePath(context)+"/key/"+keyGuid+".xml";
    }

    public static String getSrcFilePath(Context context,String appName){
        return FileUtil.getAbsolutePath(context)+"/"+appName+".zip";
    }

    public static String getWeexZipFilePath(String srcPath,String appName){
        return srcPath+"/"+appName+"/"+appName+".zip";
    }

    public static String getWeexSignFilePath(String srcPath,String appName){
        return srcPath+"/"+appName+"/"+"singFile.json";
    }


    public static String getDatafromFile(String Path) {
        BufferedReader reader = null;
        String laststr = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }

    public static boolean fileIsExists(String strFile)
    {
        try
        {
            File f=new File(strFile);
            if(!f.exists())
            {
                return false;
            }
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }

    public static boolean createNewFileForAudio(String path){
        File file = new File(path);
        File fileParent = file.getParentFile();
        if (!fileParent.exists()) {
            fileParent.mkdirs();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void writeText(String filePath, String fileName, String string) throws IOException {
        File file = new File(filePath);

        // 首先判断文件夹是否存在
        if (!file.exists()) {
            if (!file.mkdirs()) {   // 文件夹不存在则创建文件
                sLog.i(null, "文件夹创建失败");
            }
        } else {
            File fileWrite = new File(filePath + File.separator + fileName);

            // 首先判断文件是否存在
            if (!fileWrite.exists()) {
                if (!fileWrite.createNewFile()) {   // 文件不存在则创建文件
                    sLog.i(null, "文件创建失败");
                    return;
                }
            }
            // 实例化对象：文件输出流
            FileOutputStream fileOutputStream = new FileOutputStream(fileWrite);

            // 写入文件
            fileOutputStream.write(string.getBytes());

            // 清空输出流缓存
            fileOutputStream.flush();

            // 关闭输出流
            fileOutputStream.close();
        }
    }

}
