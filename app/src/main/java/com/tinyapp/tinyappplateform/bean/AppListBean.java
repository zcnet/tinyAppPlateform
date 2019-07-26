package com.tinyapp.tinyappplateform.bean;

import java.io.Serializable;

/**
 * Created by zhengfei on 2018/8/31.
 */

public class AppListBean implements Serializable {
    public long id;
    public String name;
    public String guid;
    public String version;
    public String icon;
    public String intro;//应用说明
    public String showPackageURL;//应用显示卡包访问链接
    public String keyGuid;//秘钥唯一标识
    public int isAssociate;//是否已关联(0 - 未关联; 1- 已关联)
    public int isInstall;//是否已安装
    public int status=0;//0 正常  1正在更新  2已更新未加载 3 即将删除 4卡片正在下载，5 卡片下载完成，主weex未下载    6正在下载主Packet  7已授权未下载卡片
    public String mainpathfile;//主文件路径
    public String maincardfile;//主文件卡片路径
    public String maininfofile;//主文件信息路径
    public String voicefile;//语音文件路径
    public boolean isUsed=true;
}
