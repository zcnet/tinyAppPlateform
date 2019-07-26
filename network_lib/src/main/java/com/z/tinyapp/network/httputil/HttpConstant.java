package com.z.tinyapp.network.httputil;

/**
 * Created by zhengfei on 2018/8/13.
 */

public class HttpConstant {
    private static final String URL = "http://47.97.73.53/AppUpdateAPI/v1/app/";
    private static final String VER="?appVer=1.0";

    private static final String URL2 = "http://116.62.45.159:8080/VoiceAPI/v1/dialog";
    private static final String VER2="?appVer=1.0";

    private static final String URL3 = "http://www.zeninte.com/LoginAPI/v1/user/";
    private static final String VER3="?appVer=1.0";

    /**
     * 取得应用app的信息列表
     * assocation(0  - 未授权; 1 - 已授权)
     */
    public static final String GETWEEXLIST=URL+"list/get"+VER;

    /**
     * 获取key文件
     */
    public static final String GETKEYFILE=URL+"secret/key/get"+VER;

    /**
     * 获取主信息文件(更新信息文件)
     */
    public static final String GETMAINFILE=URL+"get"+VER;

    /**
     * 用户关联应用
     */
    public static final String USERASSOCIATION=URL+"user/association"+VER;

    /**
     * 调整用户位置
     */
    public static final String UPDATEPOS=URL+"user/position/update"+VER;

    /**
     * 调用后台的ASR识别
     */
    public static final String ASRBOREC=URL2+""+VER2;

    /**
     * 临时token 获取
     */
    public static final String ACCESSTOKENGAIN=URL3+"ssologin"+VER3;
}
