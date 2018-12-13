package plateform.tinyapp.com.tinyappplateform.weex;

import com.tinyapp.tinyappplateform.bean.AppListBean;
import com.tinyapp.tinyappplateform.bean.WeexBeanList;
import com.tinyapp.utils.common.JsonUtil;
import com.tinyapp.utils.common.PrefernceUtil;

/**
 * Created by zhengfei on 2018/8/8.
 */

public class WeexManager {
    public WeexBeanList weexList;
    public static WeexManager weexManager=null;
    public WeexManager(){}
    public static WeexManager getInstance(){
        if(weexManager==null){
            weexManager=new WeexManager();
        }
        return weexManager;
    }
    public AppListBean getBeanFromId(String id){
        for (AppListBean bean:weexList.appList) {
            if((bean.guid+"").equals(id)){
                return bean;
            }
        }
        return null;
    }

    public synchronized void setToPrefernce(){
        PrefernceUtil.putString("config_weex", JsonUtil.toJSONString(weexList));
    }
}
