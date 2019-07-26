package com.tinyapp.tinyappplateform.weexapps;

import java.io.Serializable;

public class AppWrap  implements Serializable {
    public interface IAppWrapCB {
        void onSuccess();
    }
    //IAppWrapCB cb;
    public AppWrap(IAppWrapCB cb){
        //this.cb = cb;
    }
    public void callCB(){
        //cb.onSuccess();
    }
}
