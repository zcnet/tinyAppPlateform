package com.z.tinyapp.userinfo.car;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.RemoteException;
import android.sgm.CanSignalData;
import android.sgm.SocketChnlVDListener;
import android.sgm.VehicleDataSignals;
import android.sgm.VehicleManager;
import android.util.Log;

import java.util.List;

/**
 * Created by zhengfei on 2018/10/22.
 */

public class CarServiceManager {
    private INightBack iNightBack;
    public CarInfoBean carInfoBean;
    VehicleManager vehicleManager;
    SocketChnlVDListener listener;
    public void setiNightBack(INightBack iNightBack){
        this.iNightBack=iNightBack;
    }
    String[] signals = new String[]{
            VehicleDataSignals.DISP_NT_SCHM_ATV,//大灯信号
            VehicleDataSignals.VehOdo,//总里程
            VehicleDataSignals.TireLFPrs,//左前胎压
            VehicleDataSignals.TireLRPrs,//左后胎压
            VehicleDataSignals.TireRFPrs,//右前胎压
            VehicleDataSignals.TireRRPrs,//右后胎压
            VehicleDataSignals.FlLvlPct//燃油剩余比例
    };
    private static CarServiceManager carServiceManager;
    public static final CarServiceManager getInstance(Context context){
        if(carServiceManager==null){
            carServiceManager=new CarServiceManager(context);
        }
        return carServiceManager;
    }
    @SuppressLint("WrongConstant")
    private CarServiceManager(Context context){
        vehicleManager = (VehicleManager)context.getSystemService(Context.VEHICLE_SERVICE);
        carInfoBean=new CarInfoBean();
        inital();
    }

    public void inital(){
        listener = new SocketChnlVDListener.Stub(){
            @Override
            public void OnDataChanged(List< CanSignalData > list, String s) throws RemoteException {
                CanSignalData canSignalData=list.get(0);
                if(canSignalData.mName.equals(VehicleDataSignals.DISP_NT_SCHM_ATV)){
                    carInfoBean.disliglt= canSignalData.mBoolData;
                    if(iNightBack!=null){
                        iNightBack.nightChange(carInfoBean.disliglt);
                    }
                }else if(canSignalData.mName.equals(VehicleDataSignals.VehOdo)){
                    carInfoBean.vehodo=canSignalData.mFloatData;
                }
                else if(canSignalData.mName.equals(VehicleDataSignals.TireLFPrs)){
                    carInfoBean.tireLFPrs=canSignalData.mIntData;
                }
                else if(canSignalData.mName.equals(VehicleDataSignals.TireLRPrs)){
                    carInfoBean.tireLRPrs=canSignalData.mIntData;
                }
                else if(canSignalData.mName.equals(VehicleDataSignals.TireRFPrs)){
                    carInfoBean.tireRFPrs=canSignalData.mIntData;
                }
                else if(canSignalData.mName.equals(VehicleDataSignals.TireRRPrs)){
                    carInfoBean.tireRRPrs=canSignalData.mIntData;
                }
                else if(canSignalData.mName.equals(VehicleDataSignals.FlLvlPct)){
                    carInfoBean.flLvlPct=canSignalData.mFloatData;
                }
            }

            @Override
            public void OnError(List<CanSignalData> list, String s) throws RemoteException {

            }
        };
        vehicleManager.registerCarDataListener(listener, signals);
    }
    public void unRegister(){
        vehicleManager.unregisterCarData(listener,signals);
    }
    public interface INightBack{
        void nightChange(boolean isStart);
    }

    public void getCarData(){
        vehicleManager.getCarData(listener,signals);
    }
}
