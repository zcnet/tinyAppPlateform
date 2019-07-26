package com.z.tinyapp.utils.des;

import com.boring.boringsslutil.BoringSSLUtil;

/**
 * Created by zhengfei on 2018/10/15.
 */

public class BoringsslUtils {
    public static boolean decryptFile(String publicKey,byte[] sign, String content) throws Exception{
        if(publicKey==null||sign==null||content==null){
            return false;
        }
        int verify = BoringSSLUtil.RSAverify(publicKey, sign, content);
        return verify==0?true:false;
    }
}
