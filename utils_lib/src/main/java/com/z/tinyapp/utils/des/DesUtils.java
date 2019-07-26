package com.z.tinyapp.utils.des;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

/**
 * Created by zhengfei on 2018/8/17.
 */

public class DesUtils {
    /**
     * @param srcFileName  解密后的文件
     * @param destFileName 加密后的文件
     * @param keyPath 公钥的路径
     * @throws Exception
     */
    public synchronized static void decryptFile(String destFileName, String srcFileName,String keyPath) throws Exception {
        OutputStream outputWriter = null;
        FileInputStream inputReader = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", new org.bouncycastle.jce.provider.BouncyCastleProvider());
            byte[] buf = new byte[128];
            int buf1;
//            cipher.init(Cipher.DECRYPT_MODE, getKeyPair().getPrivate());
            cipher.init(Cipher.DECRYPT_MODE, getPublicKey(keyPath));
            outputWriter = new FileOutputStream(srcFileName);
            inputReader = new FileInputStream(destFileName);
            while ((buf1 = inputReader.read(buf)) != -1) {
                byte[] encText = null;
                byte[] newArr = null;
                if (buf.length == buf1) {
                    newArr = buf;
                } else {
                    newArr = new byte[buf1];
                    for (int i = 0; i < buf1; i++) {
                        newArr[i] = (byte) buf[i];
                    }
                }
                encText = cipher.doFinal(newArr);
                outputWriter.write(encText);
            }
            outputWriter.flush();
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (outputWriter != null) {
                    outputWriter.close();
                }
                if (inputReader != null) {
                    inputReader.close();
                }
            } catch (Exception e) {

            }
        }
    }

    //获取公钥
    public static String getPublicKeyWithFile(String keyPath) throws Exception {
        String publicKey = null;
        try {
            String fileName = keyPath;
            FileInputStream is = new FileInputStream(fileName);
            ObjectInputStream oos = new ObjectInputStream(is);
            publicKey = (String) oos.readObject();
            oos.close();
        } catch (Exception e) {
            throw new Exception("读取加密文件出错.", e);
        }
        return publicKey;

    }

    //获取公钥
    public static PublicKey getPublicKey(String keyPath) throws Exception {
        KeyPair kp;
        try {
            PrivateKey privateKey = null;
            PublicKey publicKey = null;

            String fileName = keyPath;
            FileInputStream is = new FileInputStream(fileName);
            ObjectInputStream oos = new ObjectInputStream(is);
            publicKey = (PublicKey) oos.readObject();
            oos.close();

            kp = new KeyPair(publicKey,privateKey);
        } catch (Exception e) {
            throw new Exception("读取加密文件出错.", e);
        }
        return kp.getPublic();
    }
}
