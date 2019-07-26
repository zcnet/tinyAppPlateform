package com.contacts;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.z.tinyapp.utils.common.TextUtil;
import com.z.tinyapp.utils.logs.sLog;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by sun on 2018/8/20
 */
class PinYinUtils {

    private static final String TAG = "sunPinYinUtils";

    private static final String KEY_CONTACTS = "KEY_CONTACTS";

    private static boolean sDebug = true;

    /**
     * 通过名字搜索
     *
     * @param name       名字
     * @param moduleList 本地数据列表
     * @return 排序后列表
     */
    static ArrayList<PinYinModule> compareSortList(String name, ArrayList<PinYinModule> moduleList) {

        name = name.replaceAll(" ", "").replaceAll("-", "");

        if (isNumeric(name)) {
            //纯数字排序
            return compareNum(name, moduleList);
        } else {
            //纯姓名或者拼音排序
            return compareString(name, moduleList);
        }
    }

    /**
     * 通过名字搜索
     *
     * @param name       名字
     * @param moduleList 本地数据列表
     * @return 排序后列表
     */
    static ArrayList<PinYinModule> compareSortList(String name, String flag, ArrayList<PinYinModule> moduleList) {

        name = name.replaceAll(" ", "").replaceAll("-", "");

        if (isNumeric(name)) {
            //纯数字排序
            return compareNum(name, moduleList);
        } else {
            //纯姓名或者拼音排序
            return compareString(name, flag, moduleList);
        }
    }


    /**
     * 通过首字母排序
     *
     * @return 排序后列表
     */
    static ArrayList<PinYinModule> compareSortList(ArrayList<PinYinModule> list) {

        Collections.sort(list, new Comparator<PinYinModule>() {

            @Override
            public int compare(PinYinModule m1, PinYinModule m2) {

                if (m1.getFirstChar() != m2.getFirstChar()) {
                    return m1.getFirstChar() - m2.getFirstChar();
                }

                String name1 = m1.getName();
                String name2 = m2.getName();

                char c1;
                char c2;

                int index = name1.length() < name2.length() ? name1.length() : name2.length();

                for (int i = 0; i < index; i++) {

                    c1 = name1.charAt(i);
                    c2 = name2.charAt(i);
//                    if (c1 >= 97 && c1 <= 122 && c2 >= 97 && c2 <= 122) {
//                        //小写vs小写
//                        if (c1 - c2 != 0) {
//                            return c1 - c2;
//                        }
//                    } else if (c1 >= 97 && c1 <= 122 && c2 >= 65 && c2 <= 90) {
//                        //小写vs大写
//                        if (c1 - c2 != 0) {
//
//                            if (c1 - c2 - 32 == 0) {
//                                return c2 - c1;
//                            }
//
//                            return c1 - (c2 + 32);
//                        }
//                    } else if (c1 >= 65 && c1 <= 90 && c2 >= 97 && c2 <= 122) {
//                        //大写vs小写
//                        if (c1 - c2 != 0) {
//
//                            if (c2 - c1 - 32 == 0) {
//                                return c2 - c1;
//                            }
//
//                            return c2 - (c1 + 32);
//                        }
//                    } else if (c1 >= 65 && c1 <= 90 && c2 >= 65 && c2 <= 90) {
//                        //大写vs大写
//                        if (c1 - c2 != 0) {
//                            return c1 - c2;
//                        }
//                    } else {
//                        if (c1 - c2 != 0) {
//                            return c1 - c2;
//                        }
//                    }

                    if (c1 >= 97 && c1 <= 122 && c2 >= 65 && c2 <= 90) {
                        //小写vs大写
                        if (c1 - c2 != 0) {

                            if (c1 - c2 - 32 == 0) {
                                return c2 - c1;
                            }

                            return c1 - (c2 + 32);
                        }
                    } else if (c1 >= 65 && c1 <= 90 && c2 >= 97 && c2 <= 122) {
                        //大写vs小写
                        if (c1 - c2 != 0) {

                            if (c2 - c1 - 32 == 0) {
                                return c2 - c1;
                            }

                            return c2 - (c1 + 32);
                        }
                    } else {
                        if (c1 - c2 != 0) {
                            return c1 - c2;
                        }
                    }
                }

                return name1.length() - name2.length();
            }
        });

        for (int i = 0; i < list.size(); i++) {
            sLog.e(TAG, "compareSortList: -->" + list.get(i).toString());
        }

        return list;
    }

    /**
     * 通过姓名搜索
     *
     * @param name       输入的姓名
     * @param moduleList 本地数据列表
     * @return 排序后的数据列表
     */
    private static ArrayList<PinYinModule> compareString(String name, ArrayList<PinYinModule> moduleList) {

        PinYinModule voiceModule = new PinYinModule(name);

        ArrayList<PinYinModule> list = new Gson().fromJson(new Gson().toJson(moduleList), new TypeToken<List<PinYinModule>>(){}.getType());

        //匹配度 degree
        int degree = 0;
        boolean isAllMatch = false;

        Iterator itr = list.iterator();
        PinYinModule contactModule;

        int maxSize = 0;

        while (itr.hasNext()) {

            contactModule = ((PinYinModule) itr.next());

            degree = isNameSame(contactModule, voiceModule);

            contactModule.setDegree(degree);

            maxSize = (degree / 1000000) > maxSize ? (degree / 1000000) : maxSize;

            if (contactModule.getName().equals(voiceModule.getName())) {
                isAllMatch = true;
            }

        }

        if(maxSize==0){
            return new ArrayList<>();
        }

        itr = list.iterator();

        while (itr.hasNext()) {

            contactModule = ((PinYinModule) itr.next());

            if (contactModule.getDegree() <= (1000000 * maxSize)) {
                contactModule.setDegree(-1);
                itr.remove();
            }

        }

        Collections.sort(list);

        if (list.size() == 0) {
            showLogError(TAG, "--compareString list size 0 --");
            return list;
        }

        //如果有同字数完全匹配 则过滤掉字数不同的人名
        if (isAllMatch) {
            itr = list.iterator();
            while (itr.hasNext()) {

                Object obj = itr.next();
                if (obj == null) {
                    continue;
                }

                if (!((PinYinModule) obj).getName().equals(name)) {
                    itr.remove();
                }
            }
        }

        return list;
    }

    /**
     * 通过姓名和flag搜索
     *
     * @param name       输入的姓名
     * @param moduleList 本地数据列表
     * @return 排序后的数据列表
     */
    private static ArrayList<PinYinModule> compareString(String name, String flag, ArrayList<PinYinModule> moduleList) {

        PinYinModule voiceModule = new PinYinModule(name);
        PinYinFlagBean flagBean = new Gson().fromJson(flag, PinYinFlagBean.class);
        ArrayList<PinYinModule> list = new Gson().fromJson(new Gson().toJson(moduleList), new TypeToken<List<PinYinModule>>(){}.getType());

        //匹配度 degree
        int degree;
        boolean isAllMatch = false;

        Iterator itr = list.iterator();
        PinYinModule contactModule;

        int maxSize = 0;

        while (itr.hasNext()) {

            contactModule = ((PinYinModule) itr.next());

            degree = isNameSame(contactModule, voiceModule);

            contactModule.setDegree(degree);

            maxSize = (degree / 1000000) > maxSize ? (degree / 1000000) : maxSize;

            if (contactModule.getName().equals(voiceModule.getName())) {
                isAllMatch = true;
            }

        }

        if(maxSize==0){
            return new ArrayList<>();
        }

        itr = list.iterator();

        PinYinModule.PhoneNumInfo phoneNumInfo;
        while (itr.hasNext()) {

            contactModule = ((PinYinModule) itr.next());

            if (contactModule.getDegree() <= (1000000 * maxSize)) {
                contactModule.setDegree(-1);
                itr.remove();
            } else {

                Iterator<PinYinModule.PhoneNumInfo> iterator = contactModule.getNumInfo().iterator();

                while (iterator.hasNext()) {
                    phoneNumInfo = iterator.next();

                    if (flagBean.getType() == -1) {
                        if (!phoneNumInfo.getCarrierOperator().contains(flagBean.getCarrierOperator())) {
                            iterator.remove();
                            continue;
                        }
                    }

                    if (TextUtil.isEmpty(flagBean.getCarrierOperator())) {
                        if (phoneNumInfo.getType() != flagBean.getType()) {
                            iterator.remove();
                            continue;
                        }
                    }

                    if ((!phoneNumInfo.getCarrierOperator().contains(flagBean.getCarrierOperator())
                            && (phoneNumInfo.getType() != flagBean.getType()))) {
                        iterator.remove();
                    }
                }
            }
        }

        Collections.sort(list);

        if (list.size() == 0) {
            showLogError(TAG, "--compareString list size 0 --");
            return list;
        }


        itr = list.iterator();
        while (itr.hasNext()) {

            contactModule = ((PinYinModule) itr.next());

            if(contactModule.getNumInfo()==null||contactModule.getNumInfo().size()==0){
                itr.remove();
            }
        }


        //如果有同字数完全匹配 则过滤掉字数不同的人名
        if (isAllMatch) {
            itr = list.iterator();
            while (itr.hasNext()) {

                Object obj = itr.next();
                if (obj == null) {
                    continue;
                }

                if (!((PinYinModule) obj).getName().equals(name)) {
                    itr.remove();
                }
            }
        }

        return list;
    }

    /**
     * 通过电话号码搜索
     *
     * @param tel        输入的电话号码
     * @param moduleList 本地数据列表
     * @return 排序后的数据列表
     */
    private static ArrayList<PinYinModule> compareNum(String tel, ArrayList<PinYinModule> moduleList) {

        ArrayList<PinYinModule> list = new Gson().fromJson(new Gson().toJson(moduleList), new TypeToken<List<PinYinModule>>(){}.getType());

        Iterator itr = list.iterator();
        PinYinModule tempModule;
        int degree;

        while (itr.hasNext()) {

            tempModule = (PinYinModule) itr.next();

            degree = isTelSame(tempModule, tel);

            if (degree == 10086) {
                itr.remove();
            } else {
                tempModule.setDegree(degree);
            }
        }

        Collections.sort(list, new Comparator<PinYinModule>() {
            @Override
            public int compare(PinYinModule module, PinYinModule t1) {
                return ((int) (module.getSameTel() - t1.getSameTel()));
            }
        });

        return list;
    }

    /**
     * 查询两个名字匹配度
     *
     * @param phoneName 通讯录人名
     * @param voiceName 语音返回的人名
     * @return 如果小于0则不匹配 等于0完美匹配 大于等于1返回的是匹配度
     * <p>
     * 匹配度说明:
     * <p>
     * 1000000为完整匹配一个字
     * 10000为相似匹配一个字
     * 100为连续匹配音节
     * 1为匹配音节
     */
    private static int isNameSame(PinYinModule phoneName, PinYinModule voiceName) {

//        ArrayList<NameNumber> phoneNameList = phoneName.getNameNumber();
//        ArrayList<NameNumber> voiceNameList = voiceName.getNameNumber();
//
//        //记录所有单位查询后的差值
//        int allMatchingCount = 0;
//        //上一个声母/韵母是否匹配
//        boolean isMatch = false;
//        //最大连续匹配声母/韵母数
//        int maxDegreeCount = 0;
//        //当前连续匹配声母韵母数量
//        int nowDegreeCount = 0;
//
//        NameNumber p;
//        NameNumber v;
//
//        leftName:
//        for (int i = 0; i < phoneNameList.size(); i++) {
//            p = phoneNameList.get(i);
//            p.clear();
//            for (int j = 0; j < voiceNameList.size(); j++) {
//                v = voiceNameList.get(j);
//
//                if(p.isSame||v.isSame){
//                    continue;
//                }
//
//                if ((p.s == v.s && p.y == v.y)||p.x==v.x) {
//                    p.isSame=true;
//                    v.isSame=true;
//                    allMatchingCount += 1000000;
//
//                    if (isMatch) {
//                        nowDegreeCount += 100;
//                    } else {
//                        isMatch = true;
//                        nowDegreeCount += 100;
//                    }
//                    if (nowDegreeCount > maxDegreeCount) {
//                        maxDegreeCount = nowDegreeCount;
//                        phoneName.setIndex(i - maxDegreeCount / 100);
//                    }
//
//                    continue leftName;
//                } else if ((p.s / 5 == v.s / 5 && p.y / 5 == v.y / 5)) {
//                    p.isSame=true;
//                    v.isSame=true;
//                    allMatchingCount += 10000;
//                    if (isMatch) {
//                        nowDegreeCount += 100;
//                    } else {
//                        isMatch = true;
//                        nowDegreeCount += 100;
//                    }
//                    if (nowDegreeCount > maxDegreeCount) {
//                        maxDegreeCount = nowDegreeCount;
//                        phoneName.setIndex(i - maxDegreeCount / 100);
//                    }
//
//                    continue leftName;
//                } else {
//                    if (nowDegreeCount > maxDegreeCount) {
//                        maxDegreeCount = nowDegreeCount;
//                        phoneName.setIndex(i - maxDegreeCount / 100);
//                    }
//                    nowDegreeCount = 0;
//                    isMatch = false;
//                }
//
//                if (p.s / 5 == v.s / 5) {
//                    allMatchingCount += 1;
//                }
//
//                if (p.y / 5 == v.y / 5) {
//                    allMatchingCount += 1;
//                }
//            }
//        }
//
//        allMatchingCount += maxDegreeCount;
//
//        for (NameNumber nameNumber : voiceNameList) {
//            nameNumber.clear();
//        }
//        return allMatchingCount;

        ArrayList<NameNumber> phoneNameList = phoneName.getNameNumber();
        ArrayList<NameNumber> voiceNameList = voiceName.getNameNumber();
        //牛大伟
        //记录所有单位查询后的差值
        int allMatchingCount = 0;
        //上一个声母/韵母是否匹配
        boolean isMatch = false;
        //最大连续匹配声母/韵母数
        int maxDegreeCount = 0;
        //当前连续匹配声母韵母数量
        int nowDegreeCount = 0;

        NameNumber p;
        NameNumber v;

        int phoneNameIndex = 0;
        int voiceNameIndex = 0;

        int phoneNameSize = phoneNameList.size();
        int voiceNameSize = voiceNameList.size();

        leftName:
        for (int i = phoneNameIndex; i < phoneNameSize; i++) {
            p = phoneNameList.get(i);
            for (int j = voiceNameIndex; j < voiceNameSize; j++) {
                v = voiceNameList.get(j);
                if (p.s == v.s && p.y == v.y) {
                    allMatchingCount += 1000000;
                    voiceNameIndex = ((1 + j) < voiceNameSize) ? 1 + j : voiceNameSize;

                    if (isMatch) {
                        nowDegreeCount += 100;
                    } else {
                        isMatch = true;
                        nowDegreeCount += 100;
                    }
                    if (nowDegreeCount > maxDegreeCount) {
                        maxDegreeCount = nowDegreeCount;
                        phoneName.setIndex(i - maxDegreeCount / 100);
                    }

                    continue leftName;
                } else if (p.s / 5 == v.s / 5 && p.y / 5 == v.y / 5) {
                    allMatchingCount += 10000;
                    voiceNameIndex = ((1 + j) < voiceNameSize) ? 1 + j : voiceNameSize;
                    if (isMatch) {
                        nowDegreeCount += 100;
                    } else {
                        isMatch = true;
                        nowDegreeCount += 100;
                    }
                    if (nowDegreeCount > maxDegreeCount) {
                        maxDegreeCount = nowDegreeCount;
                        phoneName.setIndex(i - maxDegreeCount / 100);
                    }

                    continue leftName;
                } else {
                    if (nowDegreeCount > maxDegreeCount) {
                        maxDegreeCount = nowDegreeCount;
                        phoneName.setIndex(i - maxDegreeCount / 100);
                    }
                    nowDegreeCount = 0;
                    isMatch = false;
                }

                if (p.s / 5 == v.s / 5) {
                    allMatchingCount += 1;
                }

                if (p.y / 5 == v.y / 5) {
                    allMatchingCount += 1;
                }
            }
        }

        allMatchingCount += maxDegreeCount;

        return allMatchingCount;
    }

    /**
     * 查询两个电话号码是否可以匹配
     *
     * @param phoneModule 本地数据
     * @param voiceTel    输入的电话号码
     * @return 排序后的列表
     */
    private static int isTelSame(PinYinModule phoneModule, String voiceTel) {

        if (phoneModule.getNumInfo() == null || phoneModule.getNumInfo().size() == 0) {
            return 10086;
        }

        List<PinYinModule.PhoneNumInfo> arr = phoneModule.getNumInfo();
        long[] phoneTel = new long[arr.size()];
        String temp;

        for (int i = 0; i < arr.size(); i++) {
            temp = arr.get(i).getTel();
            if (!TextUtils.isEmpty(temp)) {
                temp = temp.replaceAll("-", "").replaceAll(" ", "");
                if (isNumeric(temp)) {
                    phoneTel[i] = Long.valueOf(temp);
                }
            }
        }


        String phoneTelStr;

        int finalIndex = 10086;
        int tempIndex;

        for (long tel : phoneTel) {

            phoneTelStr = String.valueOf(tel);

            if (phoneTelStr.contains(voiceTel)) {

                tempIndex = phoneTelStr.indexOf(voiceTel);

                if (finalIndex >= tempIndex) {
                    finalIndex = tempIndex;
                    phoneModule.setSameTel(tel);
                }
            }
        }

        return finalIndex;
    }

    /**
     * 声母
     */
    static void formatS(NameNumber nameNumber, String str) {
        str = str.toLowerCase();
        if (str.length() >= 2) {
            switch (str.substring(0, 2)) {
                case "zh":
                    nameNumber.s = 70;
                    break;
                case "ch":
                    nameNumber.s = 75;
                    break;
                case "sh":
                    nameNumber.s = 80;
                    break;
                default:
                    nameNumber.s = 1000;
                    break;
            }

            if (nameNumber.s != 1000) {
                return;
            }
        }

        if (str.length() >= 1) {
            switch (str.substring(0, 1)) {
                case "b":
                    nameNumber.s = 5;
                    break;
                case "p":
                    nameNumber.s = 10;
                    break;
                case "m":
                    nameNumber.s = 15;
                    break;
                case "f":
                    nameNumber.s = 20;
                    break;
                case "d":
                    nameNumber.s = 25;
                    break;
                case "t":
                    nameNumber.s = 30;
                    break;
                case "n":
                    nameNumber.s = 35;
                    break;
                case "l":
                    nameNumber.s = 36;
                    break;
                case "g":
                    nameNumber.s = 40;
                    break;
                case "k":
                    nameNumber.s = 45;
                    break;
                case "h":
                    nameNumber.s = 50;
                    break;
                case "j":
                    nameNumber.s = 55;
                    break;
                case "q":
                    nameNumber.s = 60;
                    break;
                case "x":
                    nameNumber.s = 65;
                    break;
                case "r":
                    nameNumber.s = 85;
                    break;
                case "z":
                    nameNumber.s = 71;
                    break;
                case "c":
                    nameNumber.s = 76;
                    break;
                case "s":
                    nameNumber.s = 81;
                    break;
                case "y":
                    nameNumber.s = 90;
                    break;
                case "a":
                    nameNumber.s = 95;
                    break;
                case "e":
                    nameNumber.s = 105;
                    break;
                case "o":
                    nameNumber.s = 115;
                    break;
                case "w":
                    nameNumber.s = 120;
                    break;
                default:
                    nameNumber.s = 1000;
                    break;
            }
        }

    }

    /**
     * 韵母
     */
    static void formatY(NameNumber nameNumber, String str) {
        str = str.toLowerCase();
        switch (str) {
            case "u":
                nameNumber.y = 5;
                break;
            case "un":
                nameNumber.y = 6;
                break;
            case "ua":
                nameNumber.y = 10;
                break;
            case "uan":
                nameNumber.y = 11;
                break;
            case "uang":
                nameNumber.y = 12;
                break;
            case "uai":
                nameNumber.y = 15;
                break;
            case "ue":
                nameNumber.y = 20;
                break;
            case "uen":
                nameNumber.y = 21;
                break;
            case "ueng":
                nameNumber.y = 22;
                break;
            case "uei":
                nameNumber.y = 30;
                break;
            case "uo":
                nameNumber.y = 35;
                break;
            case "a":
                nameNumber.y = 40;
                break;
            case "ai":
                nameNumber.y = 45;
                break;
            case "ao":
                nameNumber.y = 50;
                break;
            case "an":
                nameNumber.y = 55;
                break;
            case "ang":
                nameNumber.y = 56;
                break;
            case "ia":
                nameNumber.y = 60;
                break;
            case "iao":
                nameNumber.y = 61;
                break;
            case "ian":
                nameNumber.y = 65;
                break;
            case "iang":
                nameNumber.y = 66;
                break;
            case "ie":
                nameNumber.y = 70;
                break;
            case "in":
                nameNumber.y = 75;
                break;
            case "ing":
                nameNumber.y = 76;
                break;
            case "iou":
                nameNumber.y = 80;
                break;
            case "iong":
                nameNumber.y = 85;
                break;
            case "o":
                nameNumber.y = 90;
                break;
            case "ou":
                nameNumber.y = 95;
                break;
            case "ong":
                nameNumber.y = 96;
                break;
            case "i":
                nameNumber.y = 100;
                break;
            case "e":
                nameNumber.y = 105;
                break;
            case "ei":
                nameNumber.y = 110;
                break;
            case "en":
                nameNumber.y = 115;
                break;
            case "eng":
                nameNumber.y = 116;
                break;
            case "er":
                nameNumber.y = 120;
                break;
            case "iu":
                nameNumber.y = 125;
                break;
            case "r":
                nameNumber.y = 130;
                break;
            case "n":
                nameNumber.y = 140;
                break;
            default:
                nameNumber.y = 1000;
                break;
        }

    }

    static void write2Txt(String strcontent, File file) {
        try {
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(0);
            raf.write(strcontent.getBytes());
            raf.close();
        } catch (Exception e) {
            sLog.e("sunMain", "Error on write File:" + e);
        }
    }

    /**
     * 将文件打包入zip文件
     */
    static void zip(File srcFile, File desFile) throws IOException {
        ZipOutputStream zos = null;
        FileInputStream fis = null;
        try {
            //创建压缩输出流,将目标文件传入
            zos = new ZipOutputStream(new FileOutputStream(desFile));
            //创建文件输入流,将源文件传入
            fis = new FileInputStream(srcFile);
            byte[] buffer = new byte[1024];
            ZipEntry entry = new ZipEntry(srcFile.getName());
            zos.putNextEntry(entry);
            int len;
            //利用IO流写入写出的形式将源文件写入到目标文件中进行压缩
            while ((len = (fis.read(buffer))) != -1) {
                zos.write(buffer, 0, len);
            }
            sLog.e(TAG, "zip: 压缩完成");
        } finally {
            if (zos != null) {
                zos.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
    }

    /**
     * 将文件上传至服务器
     */
    static void upload(File uploadFile, File downFile) throws Exception {

        /*
         * 请求链接生成
         */
        String urlPath = "http://47.97.39.115/ThirdPartyAPI/v1/phone/home/get?appVer=1.0";

        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设置为POST情
        conn.setRequestMethod("POST");
        // 发送POST请求必须设置如下两行
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);

        // 换行符
        final String newLine = "\r\n";
        final String boundaryPrefix = "--";
        // 数据分割线 任意定义
        String BOUNDARY = "--uj_o0TLm-vs0RBIsC8p39r9XI3WsbKCRl";

        // 设置请求头参数
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("Charsert", "UTF-8");
        conn.setRequestProperty("Content-Type", "application/json; boundary=" + BOUNDARY);

        OutputStream out = new DataOutputStream(conn.getOutputStream());

        String sb = boundaryPrefix +
                BOUNDARY +
                newLine +
                "Content-Disposition: form-data;name=\"myfiles\";filename=\"" + uploadFile.getName() + "\"" + newLine +
                "Content-Type:application/octet-stream" + newLine +
                "Content-Transfer-Encoding: binary" + newLine +
                newLine +
                newLine;
        // 文件参数,photo参数名可以随意修改
        // 参数头设置完以后需要两个换行，然后才是参数内容

        // 将参数头的数据写入到输出流中
        out.write(sb.getBytes());

        // 数据输入流,用于读取文件数据
        DataInputStream in = new DataInputStream(new FileInputStream(
                uploadFile));
        byte[] bufferOut = new byte[1024];
        int bytes;
        // 每次读1KB数据,并且将文件数据写入到输出流中
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        // 最后添加换行
        out.write(newLine.getBytes());
        in.close();

        // 定义最后数据分隔线，即--加上BOUNDARY再加上--。
        byte[] end_data = (newLine + boundaryPrefix + BOUNDARY + boundaryPrefix + newLine)
                .getBytes();
        // 写上结尾标识
        out.write(end_data);
        out.flush();
        out.close();

        /*
         * 打印返回值
         */
        if (conn.getResponseCode() == 200) {
            FileOutputStream fileOutputStream = new FileOutputStream(downFile);
            InputStream inputStream = conn.getInputStream();
            byte[] b = new byte[1024];
            try {
                for (int n; (n = inputStream.read(b)) != -1; ) {
                    fileOutputStream.write(b, 0, n);
                }
            } catch (IOException e) {
                sLog.e(TAG, "upload: exception", e);
            }
        }

        conn.disconnect();
    }

    /**
     * 解压zip文件
     */
    static void unZipFolder(File zipFile, File dataBackFile) throws Exception {
        int BUFFER = 1024; //这里缓冲区我们使用4KB，
        try {
            BufferedOutputStream dest; //缓冲输出流
            FileInputStream fis = new FileInputStream(zipFile);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
            ZipEntry entry; //每个zip条目的实例

            while ((entry = zis.getNextEntry()) != null) {

                try {
                    sLog.i("Unzip: ", "=" + entry);
                    int count;
                    byte data[] = new byte[BUFFER];
                    FileOutputStream fos = new FileOutputStream(dataBackFile);
                    dest = new BufferedOutputStream(fos, BUFFER);
                    while ((count = zis.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, count);
                    }
                    dest.flush();
                    dest.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            zis.close();
        } catch (Exception cwj) {
            cwj.printStackTrace();
        }
    }

    /**
     * 转为JavaBean
     */
    static List<PinYinModule> readFromDataBack(File dataBackFile) throws IOException {

        StringBuilder builder = new StringBuilder("");

        byte[] bytes = new byte[1024];

        FileInputStream ins = new FileInputStream(dataBackFile);

        for (int n; (n = ins.read(bytes)) != -1; ) {
            builder.append(new String(bytes, 0, n));
        }
        return new Gson().fromJson(builder.toString(),
                new TypeToken<List<PinYinModule>>() {
                }.getType());
    }

    static String readFromDataStrBack(File dataBackFile) throws IOException {

        StringBuilder builder = new StringBuilder("");

        byte[] bytes = new byte[1024];

        FileInputStream ins = new FileInputStream(dataBackFile);

        for (int n; (n = ins.read(bytes)) != -1; ) {
            builder.append(new String(bytes, 0, n));
        }
        return builder.toString();
    }


    /**
     * 将通讯录List存储到SharePreference
     */
    @SuppressWarnings("unused")
    static void saveContactsToSp(Context ctx, ArrayList<PinYinModule> list) {
        SharedPreferences sp = ctx.getSharedPreferences(KEY_CONTACTS, Context.MODE_PRIVATE);
        sp.edit().putString(
                KEY_CONTACTS,
                new Gson().
                        toJson(list,
                                new TypeToken<
                                        ArrayList<PinYinModule>
                                        >() {
                                }.getType()))
                .apply();
    }

    /**
     * 从SharePreference中获取通讯录List
     */
    @SuppressWarnings("unused")
    static ArrayList<PinYinModule> getContactsFromSp(Context ctx) {
        return new Gson().fromJson(
                ctx.getSharedPreferences(KEY_CONTACTS, Context.MODE_PRIVATE).getString(KEY_CONTACTS, ""),
                new TypeToken<
                        ArrayList<PinYinModule>
                        >() {
                }.getType()
        );
    }

    /**
     * 判断字符串是否含有数字
     */
    @SuppressWarnings("unused")
    private static boolean hasNumber(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }

    /**
     * 判断是否为纯数字
     *
     * @param s 字符串
     */
    private static boolean isNumeric(String s) {
        return s != null && !"".equals(s.trim()) && s.matches("^[0-9]*$");
    }

    /**
     * 显示Log
     */
    static void showLogInfo(String tag, String str) {

        if (sDebug) {
            sLog.i(tag, str);
        }
    }

    /**
     * 显示Log
     */
    @SuppressWarnings("unused")
    private static void showLogError(String tag, String str, Exception err) {

        if (sDebug) {
            sLog.e(tag, str, err);
        }
    }

    /**
     * 显示Log
     */
    private static void showLogError(String tag, String str) {

        if (sDebug) {
            sLog.e(tag, str);
        }
    }

}
