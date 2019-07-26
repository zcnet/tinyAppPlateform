package com.z.tinyapp.userinfo.car.idl;


import com.z.tinyapp.userinfo.car.PowerBean;


import MCANService.ENMPHEVPowerFlow;
import MCANService.ENMPHEVUsableStateOfChargeValidity;
import MCANService.Proxy.ICANService;
import MCANService.Proxy.PHEVPowerFlowChangeEvent;
import tinyrpc.ParamDouble;
import tinyrpc.RpcError;

/**
 * 电池电量状态监听
 * Created by GongDongdong on 2018/3/22.
 */

public class CANServicefunc {
    private static final String TAG = "tg_tag";
    ICANService canService;
    PHEVPowerFlowChangeEvent PHEVPowerflowchangedevent = null;
    ENMPHEVPowerFlow chargingstate = ENMPHEVPowerFlow.PHEVPowerFlow_AutoStop;
    public CANServicefunc(){
        try {
            IDLBasicFuncSet.activeTheTRPCClinetMgr();
            canService = ICANService.Inst();
        } catch (RpcError rpcError) {
            rpcError.printStackTrace();
        }
    }

    public PowerBean getVoltagePercent(){
        ParamDouble power = new ParamDouble(10);
        MCANService.ENMPHEVUsableStateOfChargeValidity.Ref validity =
                new MCANService.ENMPHEVUsableStateOfChargeValidity.Ref(
                        ENMPHEVUsableStateOfChargeValidity.PHEVUsableStateOfChargeValidity_Invalid
                );
        try {
            MCommon.EResult funcres = canService.GetPHEVUsableStateOfCharge(power, validity);
            PowerBean powerBean=new PowerBean();
            powerBean.power=power.getValue();
            powerBean.validity=validity.getValue();
            powerBean.funcres=funcres.value();
            return powerBean;
        } catch (RpcError rpcError) {
            rpcError.printStackTrace();
        }
        return null;
    }

    public void registPowerflowchangedEvent(){
        try {
            PHEVPowerflowchangedevent = new PHEVPowerFlowChangeEvent(true){
                @Override
                public void OnPHEVPowerFlowChangeEventTriggered(ENMPHEVPowerFlow powerFlow) {
                    super.OnPHEVPowerFlowChangeEventTriggered(powerFlow);
                    chargingstate = powerFlow;
                }
            };
            PHEVPowerflowchangedevent.ForceTriggerLastEvent();
        } catch (RpcError rpcError) {
            rpcError.printStackTrace();
        }
    }
    public void unregistPowerFlowChargeEvent(){
        try {
            PHEVPowerflowchangedevent.EndListen();
        } catch (RpcError rpcError) {
            rpcError.printStackTrace();
        }
    }

    public int getchargestate(){
        return chargingstate.value();
    }
}
