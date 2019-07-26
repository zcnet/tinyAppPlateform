package com.z.tinyapp.userinfo.car.idl;

import org.json.JSONObject;

import MPersistenceService.EModuleName;
import MPersistenceService.Proxy.IPersistenceService;
import tinyrpc.ParamString;
import tinyrpc.RpcError;

/**
 * Created by GongDongdong on 2018/3/22.
 */

public class PersistenceServicefunc {
    IPersistenceService persistenceService;
    public PersistenceServicefunc(){
        try {
            IDLBasicFuncSet.activeTheTRPCClinetMgr();
            persistenceService = IPersistenceService.Inst();
        } catch (RpcError rpcError) {
            rpcError.printStackTrace();
        }
    }

    public String get_gm_vehicle_electric_car_brand(){
        ParamString result = new ParamString("无");
        ParamString result2 = new ParamString("无");
        try {
            MCommon.EResult funcRes;
            funcRes = persistenceService.GetAllModuleCal(result);
            MPersistenceService.EModuleName name = EModuleName.EMN_BRAND;
            persistenceService.GetOneModuleCal(name, result2);
            return result.getValue();
        } catch (RpcError rpcError) {
            rpcError.printStackTrace();
        }
        return null;
    }

    public int get_gm_EMN_BRAND() {
        ParamString result2 = new ParamString("无");
        int car_brand=0;
        try {
            MPersistenceService.EModuleName name = EModuleName.EMN_BRAND;
            persistenceService.GetOneModuleCal(name, result2);
            JSONObject jsonObject = new JSONObject(result2.getValue());
            car_brand = jsonObject.getInt("GM_VEHICLE_ELECTRIC_CAR_BRAND");
        } catch (RpcError rpcError) {
            rpcError.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return car_brand;
    }
}
