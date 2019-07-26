package com.contacts;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.github.promeg.pinyinhelper.Pinyin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sun on 2018/8/20
 */
public class PinYinModule implements Serializable, Comparable<PinYinModule> {

    //声母和韵母的List
    private ArrayList<NameNumber> nameNumberList = new ArrayList<>(4);
    //匹配度
    private int degree = -1;
    //索引
    private int index = 100;
    //姓名
    private String name = "";
    //电话列表
    private ArrayList<PhoneNumInfo> numInfo = new ArrayList<>();
    //最相似的电话
    private long sameTel = 0;
    //首字母
    private char firstChar = 0;

    private boolean isFirst;

    PinYinModule(String name) {
        this(name, null);
    }

    private PinYinModule(String name, List<PhoneNumInfo> list) {

        if (TextUtils.isEmpty(name)) {
            name = "暂无姓名";
        }

        if (list != null && list.size() != 0) {
            numInfo.addAll(list);
        }

        this.name = name;
        name = name.toLowerCase();

        firstChar = name.substring(0, 1).toUpperCase().charAt(0);

        if (firstChar <= 127) {
            if (!((firstChar >= 65 && firstChar <= 90) || (firstChar >= 97 && firstChar <= 122))) {
                firstChar = 35;
            }
        } else {
            String temp = Pinyin.toPinyin(firstChar);
            if (TextUtils.isEmpty(temp)) {
                firstChar = 35;
            } else {
                firstChar = temp.charAt(0);
            }
        }

        boolean isChinese = true;

        //判断是否全是中文
        for (char c : name.toCharArray()) {
            if (!Pinyin.isChinese(c)) {
                isChinese = false;
                break;
            }
        }

        if (!isChinese) {
            NameNumber nameNumber;
            name = Pinyin.toPinyin(name, "");
            int length = name.length();
            formatS:
            for (int i = 0; i < length; i++) {
                nameNumber = new NameNumber();

                if (name.charAt(i) >= 48 && name.charAt(i) <= 57) {
                    nameNumber.x = name.charAt(i);
                    nameNumberList.add(nameNumber);
                    continue;
                }

                PinYinUtils.formatS(nameNumber, name.substring(i, i + 1));

                if (nameNumber.s == 1000) {
                    if (i + 1 == length) {
                        continue;
                    }
                    PinYinUtils.formatS(nameNumber, name.substring(i, i + 2));
                    if (nameNumber.s != 1000) {
                        for (int j = 5; j > 0; j--) {
                            if (j + i + 2 <= length) {
                                PinYinUtils.formatY(nameNumber, name.substring(i + 2, i + j + 2));
                                if (nameNumber.y != 1000) {
                                    nameNumberList.add(nameNumber);
                                    i = i + j;
                                    continue formatS;
                                }
                            }
                        }
                    }
                } else {
                    for (int j = 5; j > 0; j--) {
                        if (j + i + 1 <= length) {
                            PinYinUtils.formatY(nameNumber, name.substring(i + 1, i + j + 1));
                            if (nameNumber.y != 1000) {
                                nameNumberList.add(nameNumber);
                                i = i + j;
                                continue formatS;
                            }
                        }
                    }
                }
            }

            return;
        }

        String[] str = new String[name.length()];

        for (int i = 0; i < name.length(); i++) {
            str[i] = Pinyin.toPinyin(name.charAt(i));
        }

        NameNumber nameNumber;

        for (String aStr : str) {

            aStr = aStr.toLowerCase();

            nameNumber = new NameNumber();

            if (aStr.length() <= 2) {
                PinYinUtils.formatS(nameNumber, aStr);
                PinYinUtils.formatY(nameNumber, aStr.substring(1, aStr.length()));
                nameNumberList.add(nameNumber);
                continue;
            }

            PinYinUtils.formatS(nameNumber, aStr);

            if (!(nameNumber.s == 70 || nameNumber.s == 75 || nameNumber.s == 80)) {
                PinYinUtils.formatY(nameNumber, aStr.substring(1, aStr.length()));
            } else {
                PinYinUtils.formatY(nameNumber, aStr.substring(2, aStr.length()));
            }

            nameNumberList.add(nameNumber);
        }

    }

    void addPhoneInfo(PhoneNumInfo info) {
        numInfo.add(info);
    }

    ArrayList<NameNumber> getNameNumber() {
        return nameNumberList == null ? new ArrayList<NameNumber>() : nameNumberList;
    }

    int getDegree() {
        return degree;
    }

    void setDegree(int degree) {
        this.degree = degree;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int mIndex) {
        this.index = mIndex;
    }

    long getSameTel() {
        return sameTel;
    }

    void setSameTel(long mSameTel) {
        this.sameTel = mSameTel;
    }

    public char getFirstChar() {
        return firstChar;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public ArrayList<PhoneNumInfo> getNumInfo() {
        return numInfo;
    }

    public void setNumInfo(ArrayList<PhoneNumInfo> numInfo) {
        this.numInfo = numInfo;
    }

    public static class PhoneNumInfo{
        @Override
        public String toString() {
            return "PhoneNumInfo{" +
                    "tel='" + tel + '\'' +
                    ", province='" + province + '\'' +
                    ", city='" + city + '\'' +
                    ", carrierOperator='" + carrierOperator + '\'' +
                    ", type=" + type +
                    '}';
        }

        private String tel;
        private String province = "";
        private String city= "";
        private String carrierOperator="";
        private int type;

        public int getType() {
            return type;
        }
        public String getCarrierOperator() {
            return carrierOperator;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

    }

    @Override
    public int compareTo(@NonNull PinYinModule module) {

        int i = -(degree - module.degree);

        if (i != 0) {
            return i;
        }
        if (module.index != index) {
            return index - module.index;
        }

        return getName().length() - module.getName().length();
    }

    @Override
    public String toString() {
        return "PinYinModule{" +
                "nameNumberList=" + nameNumberList +
                ", degree=" + degree +
                ", index=" + index +
                ", name='" + name + '\'' +
                ", numInfo=" + numInfo +
                ", sameTel=" + sameTel +
                ", firstChar=" + firstChar +
                ", isFirst=" + isFirst +
                '}';
    }

}