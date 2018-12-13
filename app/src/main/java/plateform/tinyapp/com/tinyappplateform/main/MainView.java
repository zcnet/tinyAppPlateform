package plateform.tinyapp.com.tinyappplateform.main;

import com.tinyapp.common.base.BaseView;
import com.tinyapp.tinyappplateform.bean.WeexBeanList;

/**
 * Created by zhengfei on 2018/7/20.
 */

public interface MainView extends BaseView {
    void showMianDate(WeexBeanList weexBeanList);
    void initalWeexBack(boolean isSuccess);
    void changedAdapter();
}
