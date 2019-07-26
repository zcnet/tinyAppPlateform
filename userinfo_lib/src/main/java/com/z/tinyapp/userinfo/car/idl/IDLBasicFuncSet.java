package com.z.tinyapp.userinfo.car.idl;

import tinyrpc.RpcError;
import tinyrpc.TRPCClientMgr;

/**
 * Created by GongDongdong on 2018/4/26.
 */

public class IDLBasicFuncSet {
    public static void activeTheTRPCClinetMgr(){
        try {
            TRPCClientMgr.Inst().SetServerIPAddress((10 << 24) | (255 << 16) | (255 << 8) | 100);
            TRPCClientMgr.Inst().SetServerPort(32769);
            TRPCClientMgr.Inst().SetAutoRecovery(true);
            TRPCClientMgr.Inst().Activate();
        } catch (RpcError rpcError) {
            rpcError.printStackTrace();
        }
    }
}
