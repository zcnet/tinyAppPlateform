package com.z.tinyapp.utils.des;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

public class FileMD5Summary {
    static String file1 = "D:\\code\\java code\\AppUpdateDemo\\file\\secret\\weex_app1.zip";
    static String file1_card = "D:\\code\\java code\\AppUpdateDemo\\file\\secret\\weex_app1_card.zip";
    static String file2 = "D:\\code\\java code\\AppUpdateDemo\\file\\secret\\weex_app2.zip";
    static String file2_card = "D:\\code\\java code\\AppUpdateDemo\\file\\secret\\weex_app2_card.zip";
    static String file3 = "D:\\code\\java code\\AppUpdateDemo\\file\\secret\\weex_app3.zip";
    static String file3_card = "D:\\code\\java code\\AppUpdateDemo\\file\\secret\\weex_app3_card.zip";
    static String file4 = "D:\\code\\java code\\AppUpdateDemo\\file\\secret\\weex_app4.zip";
    static String file4_card = "D:\\code\\java code\\AppUpdateDemo\\file\\secret\\weex_app4_card.zip";
    static String file5 = "D:\\code\\java code\\AppUpdateDemo\\file\\secret\\weex_app5.zip";
    static String file5_card = "D:\\code\\java code\\AppUpdateDemo\\file\\secret\\weex_app5_card.zip";
    static String file6 = "D:\\code\\java code\\AppUpdateDemo\\file\\secret\\weex_app6.zip";
    static String file6_card = "D:\\code\\java code\\AppUpdateDemo\\file\\secret\\weex_app6_card.zip";
    static String file7 = "D:\\code\\java code\\AppUpdateDemo\\file\\secret\\weex_app7.zip";
    static String file7_card = "D:\\code\\java code\\AppUpdateDemo\\file\\secret\\weex_app7_card.zip";
    static String file8 = "D:\\code\\java code\\AppUpdateDemo\\file\\secret\\weex_app8.zip";
    static String file8_card = "D:\\code\\java code\\AppUpdateDemo\\file\\secret\\weex_app8_card.zip";
    static String file9 = "D:\\code\\java code\\AppUpdateDemo\\file\\secret\\weex_app9.zip";
    static String file9_card = "D:\\code\\java code\\AppUpdateDemo\\file\\secret\\weex_app9_card.zip";

    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

    public static void main(String[] args) {
        File file = new File(file1);
        String result = getFileMD5(file);
        System.out.println(result);
        file = new File(file1_card);
        result = getFileMD5(file);
        System.out.println(result);

        file = new File(file2);
        result = getFileMD5(file);
        System.out.println(result);
        file = new File(file2_card);
        result = getFileMD5(file);
        System.out.println(result);

        file = new File(file3);
        result = getFileMD5(file);
        System.out.println(result);
        file = new File(file3_card);
        result = getFileMD5(file);
        System.out.println(result);

        file = new File(file4);
        result = getFileMD5(file);
        System.out.println(result);
        file = new File(file4_card);
        result = getFileMD5(file);
        System.out.println(result);

        file = new File(file5);
        result = getFileMD5(file);
        System.out.println(result);
        file = new File(file5_card);
        result = getFileMD5(file);
        System.out.println(result);

        file = new File(file6);
        result = getFileMD5(file);
        System.out.println(result);
        file = new File(file6_card);
        result = getFileMD5(file);
        System.out.println(result);

        file = new File(file7);
        result = getFileMD5(file);
        System.out.println(result);
        file = new File(file7_card);
        result = getFileMD5(file);
        System.out.println(result);

        file = new File(file8);
        result = getFileMD5(file);
        System.out.println(result);
        file = new File(file8_card);
        result = getFileMD5(file);
        System.out.println(result);

        file = new File(file9);
        result = getFileMD5(file);
        System.out.println(result);
        file = new File(file9_card);
        result = getFileMD5(file);
        System.out.println(result);
    }
}
