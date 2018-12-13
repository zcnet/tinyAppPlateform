package plateform.tinyapp.com.tinyappplateform;

import com.tinyapp.common.base.BaseApplication;
import com.tinyapp.utils.common.PrefernceUtil;
import com.sun.weexandroid_module.WxAndroidApplication;

/**
 * Created by zhengfei on 2018/8/9.
 */

public class TapApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        PrefernceUtil.initContext(this);

//        initalWeex();
    }
    public void initalWeex(){
        WxAndroidApplication.initWx(this);
    }

}
