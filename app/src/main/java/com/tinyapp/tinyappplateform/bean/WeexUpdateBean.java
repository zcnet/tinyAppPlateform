package com.tinyapp.tinyappplateform.bean;

import com.z.tinyapp.network.BaseJsonBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhengfei on 2018/8/21.
 */

public class WeexUpdateBean extends BaseJsonBean {
    public List<WeexUpdateBean.AppListBean> appList;

    public class AppListBean implements Serializable {
        public long id;
        public String name;
        public String guid;
        public String appVersion;
        public int packageSize;
        public int decompressedSize;
        public String showPackageURL;//应用显示卡包访问链接
        public String packageURL;//应用访问链接
        public String keyGuid;//秘钥唯一标识
        public String mainpathfile;//主文件路径
        public String maincardfile;//主文件卡片路径
        public String maininfofile;//主文件信息路径
        public String voicefile;//语音文件路径
        public String beforeNote;//更新前告示
        public String afterNote;//更新后告示
    }
}
