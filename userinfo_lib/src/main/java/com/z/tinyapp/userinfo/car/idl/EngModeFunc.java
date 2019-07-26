package com.z.tinyapp.userinfo.car.idl;

import MEngModeService.ProductInfo;
import MEngModeService.Proxy.IEngModeService;
import tinyrpc.RpcError;

/**
 * 获取车辆版本号（电动？混合？）
 * Created by GongDongdong on 2018/6/14.
 */

public class EngModeFunc {

    IEngModeService engModeService;

    public EngModeFunc(){
        try {
            IDLBasicFuncSet.activeTheTRPCClinetMgr();
            engModeService = IEngModeService.Inst();
        } catch (RpcError rpcError) {
            rpcError.printStackTrace();
        }
    }

    public String getProductInfor(){
        String productInfoStr = "";
        MEngModeService.ProductInfo productInfo;
        productInfo = new ProductInfo();
        try {
            engModeService.GetProductInfo(productInfo);
            productInfoStr = productInfo.IntegrationVer;
        } catch (RpcError rpcError) {
            rpcError.printStackTrace();
        }
        return productInfoStr;
    }
}
