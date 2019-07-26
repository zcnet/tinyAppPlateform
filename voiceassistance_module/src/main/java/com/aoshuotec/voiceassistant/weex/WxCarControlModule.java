package com.aoshuotec.voiceassistant.weex;

import com.z.tinyapp.utils.logs.sLog;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import MCANService.ENMAirCondition;
import MCANService.ENMBlowerMode;
import MCANService.ENMNavigationMapRequestHeadLamp;
import MCANService.ENMVRRearSunshadeRequestType;
import MCANService.ENMVRWiperRequestType;
import MCANService.ENMVehicleAreaBlowerMode;
import MCANService.ENMVehicleAreaTemperature;
import MCANService.Proxy.ICANService;
import MCommon.EResult;
import tinyrpc.RpcError;

/**
 * Created by sun on 2019/3/7
 */
@SuppressWarnings("unused")
public class WxCarControlModule extends WXModule {

    private static final String TAG = "sunWxCarControlModule";

    @JSMethod(uiThread = false)
    public void setTemp(int area, int temp, JSCallback callback) {
        sLog.i(TAG, "setTemp: area - > " + area + " temp -> " + temp);
        try {
            EResult result = ICANService.Inst().SetAbsoluteTemperature(ENMVehicleAreaTemperature.valueOf(area), temp);
            if (result.value() == EResult.ESuccess.value()) {
                callback.invokeAndKeepAlive(true);
            } else {
                callback.invokeAndKeepAlive(false);
            }
        } catch (RpcError rpcError) {
            sLog.i(TAG, "setTemp: ", rpcError);
            callback.invokeAndKeepAlive(false);
        }
    }

    @JSMethod(uiThread = false)
    public void setTempLeft(int area, int temp, JSCallback callback) {
        sLog.i(TAG, "setTempLeft: ");
        try {
            EResult result;
            if (area == 0) {
                result = ICANService.Inst().SetAbsoluteTemperature(ENMVehicleAreaTemperature.VehicleAreaTemperature_FrontLeft, temp);
            } else if (area == 2) {
                result = ICANService.Inst().SetAbsoluteTemperature(ENMVehicleAreaTemperature.VehicleAreaTemperature_RearLeft, temp);
            } else {
                callback.invokeAndKeepAlive(false);
                return;
            }
            if (result.value() == EResult.ESuccess.value()) {
                callback.invokeAndKeepAlive(true);
            } else {
                callback.invokeAndKeepAlive(false);
            }
        } catch (RpcError rpcError) {
            sLog.i(TAG, "setTempLeft: ", rpcError);
            callback.invokeAndKeepAlive(false);
        }
    }

    @JSMethod(uiThread = false)
    public void setTempRight(int area, int temp, JSCallback callback) {
        sLog.i(TAG, "setTempRight: ");
        try {
            EResult result;
            if (area == 1) {
                result = ICANService.Inst().SetAbsoluteTemperature(ENMVehicleAreaTemperature.VehicleAreaTemperature_FrontRight, temp);
            } else if (area == 3) {
                result = ICANService.Inst().SetAbsoluteTemperature(ENMVehicleAreaTemperature.VehicleAreaTemperature_RearRight, temp);
            } else {
                callback.invokeAndKeepAlive(false);
                return;
            }
            if (result.value() == EResult.ESuccess.value()) {
                callback.invokeAndKeepAlive(true);
            } else {
                callback.invokeAndKeepAlive(false);
            }
        } catch (RpcError rpcError) {
            sLog.i(TAG, "setTempRight: ", rpcError);
            callback.invokeAndKeepAlive(false);
        }
    }

    @JSMethod(uiThread = false)
    public void adjustTemp(JSCallback callback) {
        callback.invokeAndKeepAlive(true);
    }

    @JSMethod(uiThread = false)
    public void adjustTempLeft(JSCallback callback) {
        callback.invokeAndKeepAlive(true);
    }

    @JSMethod(uiThread = false)
    public void adjustTempRight(JSCallback callback) {
        callback.invokeAndKeepAlive(true);
    }

    @JSMethod(uiThread = false)
    public void turnOnAirConditioner(JSCallback callback) {
        sLog.i(TAG, "turnOnAirConditioner: ");
        try {
            EResult result = ICANService.Inst().SetAirCondition(ENMAirCondition.AirCondition_COMP_ON);
            if (result.value() == EResult.ESuccess.value()) {
                callback.invokeAndKeepAlive(true);
            } else {
                callback.invokeAndKeepAlive(false);
            }
        } catch (RpcError rpcError) {
            sLog.i(TAG, "turnOnAirConditioner: ", rpcError);
            callback.invokeAndKeepAlive(false);
        }
    }

    @JSMethod(uiThread = false)
    public void turnOffAirConditioner(JSCallback callback) {
        sLog.i(TAG, "turnOffAirConditioner: ");
        try {
            EResult result = ICANService.Inst().SetAirCondition(ENMAirCondition.AirCondition_COMP_OFF);
            if (result.value() == EResult.ESuccess.value()) {
                callback.invokeAndKeepAlive(true);
            } else {
                callback.invokeAndKeepAlive(false);
            }
        } catch (RpcError rpcError) {
            sLog.i(TAG, "turnOffAirConditioner: ", rpcError);
            callback.invokeAndKeepAlive(false);
        }
    }

    @JSMethod(uiThread = false)
    public void turnOnAirLeftConditionerWithDirect(JSCallback callback) {
        callback.invokeAndKeepAlive(true);
    }

    @JSMethod(uiThread = false)
    public void turnOffAirConditionerWithDirect(JSCallback callback) {
        callback.invokeAndKeepAlive(true);
    }

    @JSMethod(uiThread = false)
    public void turnOnAirConditionerWithAirVolume(JSCallback callback) {
        callback.invokeAndKeepAlive(true);
    }

    @JSMethod(uiThread = false)
    public void maxAirVolume(JSCallback callback) {
        callback.invokeAndKeepAlive(true);
    }

    @JSMethod(uiThread = false)
    public void minAirVolume(JSCallback callback) {
        callback.invokeAndKeepAlive(true);
    }

    @JSMethod(uiThread = false)
    public void setMode(int mode, JSCallback callback) {
        sLog.i(TAG, "setMode: ");
        try {
            EResult result = ICANService.Inst().SetAirCondition(ENMAirCondition.valueOf(mode));
            if (result.value() == EResult.ESuccess.value()) {
                callback.invokeAndKeepAlive(true);
            } else {
                callback.invokeAndKeepAlive(false);
            }
        } catch (RpcError rpcError) {
            sLog.i(TAG, "setMode: ", rpcError);
            callback.invokeAndKeepAlive(false);
        }
    }

    @JSMethod(uiThread = false)
    public void setCircleMode(int mode, JSCallback callback) {
        sLog.i(TAG, "setCircleMode: ");
        try {
            EResult result = ICANService.Inst().SetHVACBlowerMode(ENMVehicleAreaBlowerMode.valueOf(mode), ENMBlowerMode.BlowerMode_Front_AC);
            if (result.value() == EResult.ESuccess.value()) {
                callback.invokeAndKeepAlive(true);
            } else {
                callback.invokeAndKeepAlive(false);
            }
        } catch (RpcError rpcError) {
            sLog.i(TAG, "setCircleMode: ", rpcError);
            callback.invokeAndKeepAlive(false);
        }
    }

    @JSMethod(uiThread = false)
    public void setWindDirect(JSCallback callback) {
        callback.invokeAndKeepAlive(true);
    }

    @JSMethod(uiThread = false)
    public void brightnessScreen(JSCallback callback) {
        callback.invokeAndKeepAlive(true);
    }

    @JSMethod(uiThread = false)
    public void dimmingScreen(JSCallback callback) {
        callback.invokeAndKeepAlive(true);
    }

    @JSMethod(uiThread = false)
    public void screenBrightest(JSCallback callback) {
        callback.invokeAndKeepAlive(true);
    }

    @JSMethod(uiThread = false)
    public void screenDarkest(JSCallback callback) {
        callback.invokeAndKeepAlive(true);
    }

    @JSMethod(uiThread = false)
    public void openScuttle(JSCallback callback) {
        callback.invokeAndKeepAlive(true);
    }

    @JSMethod(uiThread = false)
    public void closeScuttle(JSCallback callback) {
        callback.invokeAndKeepAlive(true);
    }

    @JSMethod(uiThread = false)
    public void openWiper(JSCallback callback) {
        sLog.i(TAG, "openWiper: ");
        try {
            EResult result = ICANService.Inst().SetVRWiperRequestType(ENMVRWiperRequestType.ENMVRWiperRequestType_Trigger);
            if (result.value() == EResult.ESuccess.value()) {
                callback.invokeAndKeepAlive(true);
            } else {
                callback.invokeAndKeepAlive(false);
            }
        } catch (RpcError rpcError) {
            sLog.i(TAG, "openWiper: ", rpcError);
            callback.invokeAndKeepAlive(false);
        }
    }

    @JSMethod(uiThread = false)
    public void closeWiper(JSCallback callback) {
        sLog.i(TAG, "closeWiper: ");
        try {
            EResult result = ICANService.Inst().SetVRWiperRequestType(ENMVRWiperRequestType.ENMVRWiperRequestType_No_Action);
            if (result.value() == EResult.ESuccess.value()) {
                callback.invokeAndKeepAlive(true);
            } else {
                callback.invokeAndKeepAlive(false);
            }
        } catch (RpcError rpcError) {
            sLog.i(TAG, "closeWiper: ", rpcError);
            callback.invokeAndKeepAlive(false);
        }
    }

    @JSMethod(uiThread = false)
    public void openSunShade(JSCallback callback) {
        sLog.i(TAG, "openSunShade: ");
        try {
            EResult result = ICANService.Inst().SetVRRearSunshadeRequestType(ENMVRRearSunshadeRequestType.ENMVRRearSunshadeRequestType_Comfort_Open);
            if (result.value() == EResult.ESuccess.value()) {
                callback.invokeAndKeepAlive(true);
            } else {
                callback.invokeAndKeepAlive(false);
            }
        } catch (RpcError rpcError) {
            sLog.i(TAG, "openSunShade: ", rpcError);
            callback.invokeAndKeepAlive(false);
        }
    }

    @JSMethod(uiThread = false)
    public void closeSunShade(JSCallback callback) {
        sLog.i(TAG, "closeSunShade: ");
        try {
            EResult result = ICANService.Inst().SetVRRearSunshadeRequestType(ENMVRRearSunshadeRequestType.ENMVRRearSunshadeRequestType_Comfort_Close);
            if (result.value() == EResult.ESuccess.value()) {
                callback.invokeAndKeepAlive(true);
            } else {
                callback.invokeAndKeepAlive(false);
            }
        } catch (RpcError rpcError) {
            sLog.i(TAG, "closeSunShade: ", rpcError);
            callback.invokeAndKeepAlive(false);
        }
    }

    @JSMethod(uiThread = false)
    public void openWindows(JSCallback callback) {
        callback.invokeAndKeepAlive(true);
    }

    @JSMethod(uiThread = false)
    public void openWindow(JSCallback callback) {
        callback.invokeAndKeepAlive(true);
    }

    @JSMethod(uiThread = false)
    public void closeWindows(JSCallback callback) {
        callback.invokeAndKeepAlive(true);
    }

    @JSMethod(uiThread = false)
    public void closeWindow(JSCallback callback) {
        callback.invokeAndKeepAlive(true);
    }

    @JSMethod(uiThread = false)
    public void rearRowWindowUnlocking(JSCallback callback) {
        callback.invokeAndKeepAlive(true);
    }

    @JSMethod(uiThread = false)
    public void rearRowWindowLocking(JSCallback callback) {
        callback.invokeAndKeepAlive(true);
    }

    @JSMethod(uiThread = false)
    public void turnOnFogLamp(JSCallback callback) {
        callback.invokeAndKeepAlive(true);
    }

    @JSMethod(uiThread = false)
    public void turnOffFogLamp(JSCallback callback) {
        callback.invokeAndKeepAlive(true);
    }

    @JSMethod(uiThread = false)
    public void turnOnDippedHeadLight(JSCallback callback) {
        callback.invokeAndKeepAlive(true);
    }

    @JSMethod(uiThread = false)
    public void turnOffDippedHeadLight(JSCallback callback) {
        callback.invokeAndKeepAlive(true);
    }

    @JSMethod(uiThread = false)
    public void turnOnHighLight(JSCallback callback) {
        callback.invokeAndKeepAlive(true);
    }

    @JSMethod(uiThread = false)
    public void turnOffHighLight(JSCallback callback) {
        callback.invokeAndKeepAlive(true);
    }

    @JSMethod(uiThread = false)
    public void flashHighLight(JSCallback callback) {
        sLog.i(TAG, "flashHighLight: ");
        try {
            EResult result = ICANService.Inst().SetNavigationMapRequestHeadLamp(ENMNavigationMapRequestHeadLamp.NavigationMapRequestHeadLamp_MapLightUnknown);
            if (result.value() == EResult.ESuccess.value()) {
                callback.invokeAndKeepAlive(true);
            } else {
                callback.invokeAndKeepAlive(false);
            }
        } catch (RpcError rpcError) {
            sLog.i(TAG, "flashHighLight: ", rpcError);
            callback.invokeAndKeepAlive(false);
        }
    }

    @JSMethod(uiThread = false)
    public void turnOnDoubleFlashLight(JSCallback callback) {
        sLog.i(TAG, "turnOnDoubleFlashLight: ");
        try {
            EResult result = ICANService.Inst().SetNavigationMapRequestHeadLamp(ENMNavigationMapRequestHeadLamp.NavigationMapRequestHeadLamp_MapLightLightON);
            if (result.value() == EResult.ESuccess.value()) {
                callback.invokeAndKeepAlive(true);
            } else {
                callback.invokeAndKeepAlive(false);
            }
        } catch (RpcError rpcError) {
            sLog.i(TAG, "turnOnDoubleFlashLight: ", rpcError);
            callback.invokeAndKeepAlive(false);
        }
    }

    @JSMethod(uiThread = false)
    public void turnOffDoubleFlashLight(JSCallback callback) {
        sLog.i(TAG, "turnOffDoubleFlashLight: ");
        try {
            EResult result = ICANService.Inst().SetNavigationMapRequestHeadLamp(ENMNavigationMapRequestHeadLamp.NavigationMapRequestHeadLamp_MapLightLightOFF);
            if (result.value() == EResult.ESuccess.value()) {
                callback.invokeAndKeepAlive(true);
            } else {
                callback.invokeAndKeepAlive(false);
            }
        } catch (RpcError rpcError) {
            sLog.i(TAG, "turnOffDoubleFlashLight: ", rpcError);
            callback.invokeAndKeepAlive(false);
        }
    }

}
