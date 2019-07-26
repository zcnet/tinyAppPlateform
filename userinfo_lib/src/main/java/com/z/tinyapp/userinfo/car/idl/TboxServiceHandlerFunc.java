package com.z.tinyapp.userinfo.car.idl;

import TBoxService.Proxy.ITBoxServiceHandler;
import tinyrpc.RpcError;

/**
 * Created by GongDongdong on 2018/6/14.
 */

public class TboxServiceHandlerFunc {
    ITBoxServiceHandler itBoxServiceHandler = null;
    public TboxServiceHandlerFunc(){
        IDLBasicFuncSet.activeTheTRPCClinetMgr();
        try {
            itBoxServiceHandler = new ITBoxServiceHandler();
        } catch (RpcError rpcError) {
            rpcError.printStackTrace();
        }
    }

    public String getNotifInformation(){
        String aaa = "";
//        itBoxServiceHandler.NotifyTBoxInfomation(aaa,aaa,1);
        return aaa;
    }
}
