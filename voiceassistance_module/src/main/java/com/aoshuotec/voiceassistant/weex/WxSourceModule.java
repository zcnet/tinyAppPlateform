package com.aoshuotec.voiceassistant.weex;

import com.aoshuotec.voiceassistant.utils.Logg;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import SourceService.Proxy.IMediaPlayerHandler;
import SourceService.Proxy.IMediaPlayerMgr;
import tinyrpc.RpcError;
import tinyrpc.TRPCStream;

/**
 * Created by sun on 2019/3/8
 */

public class WxSourceModule extends WXModule {

    private static final String TAG = "sunWxSourceModule";

    @JSMethod(uiThread = false)
    public void changeSource(String strName, JSCallback callback) {
        Logg.i(TAG, "changeSource: name -> " + strName);
        try {

            if(strName.equals(IMediaPlayerMgr.Inst().GetActiveSource().strSrcDescriptor)){
                callback.invokeAndKeepAlive(true);
                return;
            }
            ISourceHandler handler = new ISourceHandler();
            handler.setData(strName, callback);
            IMediaPlayerMgr.Inst().AddListener(strName,handler);
            IMediaPlayerMgr.Inst().Activate(strName, true);
        } catch (RpcError rpcError) {
            callback.invokeAndKeepAlive(false);
            Logg.e(TAG, "changeSource error -> ", rpcError);
        }
    }

    class ISourceHandler extends IMediaPlayerHandler{

        private String name;
        private JSCallback callback;

        private ISourceHandler() throws RpcError {
        }

        private void setData(String str ,JSCallback jsCallback){
            name = str;
            callback= jsCallback;
        }

        @Override
        public void OnSourceActivated(String strSrcName, boolean bActiveHMI, TRPCStream SrcData) throws RpcError {
            super.OnSourceActivated(strSrcName, bActiveHMI, SrcData);
            Logg.e(TAG,"OnSourceActivated  strName - > "+strSrcName+" local -> "+name);
            if(name.equals(strSrcName)){
//                String nowSourceName = IMediaPlayerMgr.Inst().GetActiveSource().strSrcDescriptor;
//                Logg.e(TAG,"OnSourceActivated  nowSourceName -> "+nowSourceName);
                callback.invokeAndKeepAlive(true);
                IMediaPlayerMgr.Inst().RemoveListener(name);
            }
        }
    }

}
