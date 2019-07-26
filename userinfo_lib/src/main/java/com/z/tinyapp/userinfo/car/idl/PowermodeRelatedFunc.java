package com.z.tinyapp.userinfo.car.idl;


import MPowerModeService.EPowerState;
import MPowerModeService.Proxy.IPowerModeService;
import MPowerModeService.Proxy.PowerStateChangeEvent;
import tinyrpc.RpcError;

/**
 * Created by GongDongdong on 2018/3/22.
 */

public class PowermodeRelatedFunc {
    IPowerModeService powerModeService;
    PowerStateChangeEvent powerevent = null;
    EPowerState currentPowerState = EPowerState.PWR_STATE_UNKNOW;
    public PowermodeRelatedFunc(){
        try {
            IDLBasicFuncSet.activeTheTRPCClinetMgr();
            powerModeService = IPowerModeService.Inst();
        } catch (RpcError rpcError) {
            rpcError.printStackTrace();
        }
    }

    public void registPowerStateEvent(){
        try {
            powerevent = new PowerStateChangeEvent(true){
                @Override
                public void OnPowerStateChangeEventTriggered(EPowerState value) {
                    super.OnPowerStateChangeEventTriggered(value);
                    currentPowerState = value;
                    if(currentPowerState.value() == EPowerState.PWR_STATE_HMI_INACTIVE.value()){
                        if(notifyState != null){//车辆熄火
                            notifyState.notifyState();
                        }
                    }
                }
            };
            powerevent.ForceTriggerLastEvent();
        } catch (RpcError rpcError) {
            rpcError.printStackTrace();
        }
    }

    public void unregistPowerStateEvent(){
        try {
            powerevent.EndListen();
        } catch (RpcError rpcError) {
            rpcError.printStackTrace();
        }
    }

    public int getPowerState(){
        return currentPowerState.value();
    }

    private notifyRunningStateChanged notifyState = null;
    public void setNotifyState(notifyRunningStateChanged one){
        this.notifyState = one;
    }
    public interface notifyRunningStateChanged{
        void notifyState();
    }

}
