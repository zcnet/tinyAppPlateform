package com.z.tinyapp.userinfo.car.idl;

import TBoxService.Proxy.ITBoxService;
import TBoxService.Proxy.TBoxInfoChangeEvent;
import tinyrpc.ParamString;
import tinyrpc.RpcError;

/**
 * Created by GongDongdong on 2018/6/12.
 */

public class TBoxFunc {
    private static final String TAG = "tg_tag";
    ITBoxService itBoxService = null;
    TBoxInfoChangeEvent tBoxInfoChangeEvent = null;
    public TBoxFunc(){
        IDLBasicFuncSet.activeTheTRPCClinetMgr();
        try {
            itBoxService = ITBoxService.Inst();
            try {
                tBoxInfoChangeEvent = new TBoxInfoChangeEvent(true){
                    @Override
                    public void OnTBoxInfoChangeEventTriggered(String strTBoxInfo, String strTBoxVendor, int iProtocolVer) {
                        super.OnTBoxInfoChangeEventTriggered(strTBoxInfo, strTBoxVendor, iProtocolVer);
                        tboxId = strTBoxVendor;
                    }
                };
                tBoxInfoChangeEvent.ForceTriggerLastEvent();
            } catch (RpcError rpcError) {
                rpcError.printStackTrace();
            }
        } catch (RpcError rpcError) {
            rpcError.printStackTrace();
        }
    }

    public String getMyTboxNum(){
        String myTboxNum = "";
        try {
            itBoxService.ReqMyNumber();
            ParamString strMyNumber = new ParamString("init value!");
            itBoxService.GetMyNumber(strMyNumber);
            myTboxNum = strMyNumber.getValue();
        } catch (RpcError rpcError) {
            rpcError.printStackTrace();
        }
        return myTboxNum;
    }

    String tboxId = "";
    public String getTboxid(){
        return tboxId;
    }
}
