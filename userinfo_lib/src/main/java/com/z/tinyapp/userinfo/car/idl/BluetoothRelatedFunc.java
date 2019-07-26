package com.z.tinyapp.userinfo.car.idl;

import android.annotation.SuppressLint;

import com.z.tinyapp.utils.logs.sLog;

import java.util.HashMap;
import java.util.Map;

import MBluetoothService.ContactListInfo;
import MBluetoothService.EBtCurrentStatus;
import MBluetoothService.EPbSyncStatus;
import MBluetoothService.EPhoneType;
import MBluetoothService.PhoneBookSyncProgress;
import MBluetoothService.PhoneDeviceInfo;
import MBluetoothService.Proxy.BTStatusChangedEvent;
import MBluetoothService.Proxy.IBluetooth;
import MBluetoothService.Proxy.ICbPhone;
import MBluetoothService.Proxy.PhonebookSyncProgressEvent;
import MBluetoothService.Proxy.RecentCallListUpdatedEvent;
import MBluetoothService.RecentCallInfo;
import MCommon.EResult;
import tinyrpc.ParamInt;
import tinyrpc.RpcError;

/**
 * 蓝牙连接状态监听
 * Created by GongDongdong on 2018/1/10.
 */

public class BluetoothRelatedFunc {

    private static final String TAG = "sunBluetoothFunc";

    //IDL蓝牙对象实例
    private IBluetooth btInst;
    //接口
    private IBluetoothStatusBack mStatusCallBack;
    private IContactsBack mContactsBack;
    private ICallLogBack mCallLogBack;
    private IGetPhoneIdBack mGetPhoneIdBack;
    //Event
    private IBTStatusChangedEvent mBtStatusChangedEvent = null;
    private IPhoneBookSyncProgressEvent mPhoneBookSyncEvent = null;
    private IRecentCallListUpdatedEvent mRecentCallEvent = null;
    private IICbPhoneEvent mICBEvent;

    //蓝牙回调
    public interface IBluetoothStatusBack {
        void callSuccess();

        void callFailed();
    }

    //联系人回调
    public interface IContactsBack {
        void backMap(Map<Integer, ContactListInfo> map);
    }

    //通话记录回调
    public interface ICallLogBack {
        void onCallLogUpdate(Map<Integer, RecentCallInfo> map);
    }

    //获取PhoneId
    public interface IGetPhoneIdBack {
        void onGetPhoneId(int id);
    }

    public BluetoothRelatedFunc() {
        try {
            IDLBasicFuncSet.activeTheTRPCClinetMgr();

            btInst = IBluetooth.Inst();

            mBtStatusChangedEvent = new IBTStatusChangedEvent(true);
            mPhoneBookSyncEvent = new IPhoneBookSyncProgressEvent(true);
            mRecentCallEvent = new IRecentCallListUpdatedEvent(true);
            mICBEvent = new IICbPhoneEvent();

            btInst.GetPhoneDevice(mICBEvent);
        } catch (RpcError rpcError) {
            rpcError.printStackTrace();
        }
    }

    public void setIContactsBack(IContactsBack iContactsBack) {//联系人列表获取成功
        this.mContactsBack = iContactsBack;
    }

    public void setIBluetoothStatusBack(IBluetoothStatusBack statusCallBack) {
        this.mStatusCallBack = statusCallBack;
    }

    public void setICallLogBack(ICallLogBack iCallLogBack) {
        this.mCallLogBack = iCallLogBack;
    }

    public void setIGetPhoneIdBack(IGetPhoneIdBack iGetPhoneIdBack) {
        this.mGetPhoneIdBack = iGetPhoneIdBack;
    }

    /**
     * 获取最新联系人
     */
    public void flushContactsMap() {
        try {
            mPhoneBookSyncEvent.ForceTriggerLastEvent();
        } catch (RpcError rpcError) {
            sLog.i(TAG, "flushContactsMap: ");
        }
    }

    /**
     * 获取最新蓝牙状态
     */
    public void flushBluetoothStatus() {
        try {
            mBtStatusChangedEvent.ForceTriggerLastEvent();
        } catch (RpcError rpcError) {
            sLog.i(TAG, "flushBluetoothStatus: ");
        }
    }

    /**
     * 获取最新通话记录
     */
    public void flushCallLogMap() {
        try {
            mRecentCallEvent.ForceTriggerLastEvent();
        } catch (RpcError rpcError) {
            sLog.i(TAG, "flushCallLogMap: ");
        }
    }

    public void clearEvent() {
        try {

            if (mPhoneBookSyncEvent != null) {
                mPhoneBookSyncEvent.EndListen();
                mPhoneBookSyncEvent = null;
            }
            if (mBtStatusChangedEvent != null) {
                mBtStatusChangedEvent.EndListen();
                mBtStatusChangedEvent = null;
            }
            if (mRecentCallEvent != null) {
                mRecentCallEvent.EndListen();
                mRecentCallEvent = null;
            }
            if (mICBEvent != null) {
                mICBEvent = null;
            }
        } catch (RpcError rpcError) {
            rpcError.printStackTrace();
        }
    }

    class IPhoneBookSyncProgressEvent extends PhonebookSyncProgressEvent {

        private IPhoneBookSyncProgressEvent(boolean bRegist) throws RpcError {
            super(bRegist);

        }

        @Override
        public void OnPhonebookSyncProgressEventTriggered(PhoneBookSyncProgress syncProgress) {
            super.OnPhonebookSyncProgressEventTriggered(syncProgress);
            if (syncProgress.syncStatus == EPbSyncStatus.PB_SYNC_STATUS_SUCCESSFUL) {
                if (mContactsBack != null) {
                    try {
                        EResult.Ref refCountRes = new EResult.Ref(EResult.EFailure);
                        ParamInt listCount = new ParamInt(0);
                        btInst.GetContactListCount(refCountRes, listCount);
                        if (refCountRes.value() == EResult.ESuccess.value()) {
                            int listCounts = listCount.getValue();
                            refCountRes.setValue(EResult.EFailure);
                            @SuppressLint("UseSparseArrays")
                            Map<Integer, ContactListInfo> contactList = new HashMap<>();
                            try {
                                btInst.GetContactList(refCountRes, 0, listCounts - 1, contactList);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (refCountRes.value() == EResult.ESuccess.value()) {
                                mContactsBack.backMap(contactList);
                            }
                        }
                    } catch (RpcError rpcError) {
                        sLog.i(TAG, "OnPhonebookSyncProgressEventTriggered: ");
                    }
                }
            }
        }
    }

    class IBTStatusChangedEvent extends BTStatusChangedEvent {
        private IBTStatusChangedEvent(boolean bRegist) throws RpcError {
            super(bRegist);
        }

        @Override
        public void OnBTStatusChangedEventTriggered(EBtCurrentStatus status, String deviceMac, String deviceName) {
            if (status.name().equals("BT_CURRENT_STATUS_ENABLED")) {
                mStatusCallBack.callFailed();
                return;
            }
            switch (status.value()) {
                case 0://蓝牙开关开启
                    break;
                case 1://蓝牙关闭
                    break;
                case 13://已经连上
                    break;
                case 14://连接中

                    break;
                case 15://连接完成
                    if (mStatusCallBack != null) {
                        mStatusCallBack.callSuccess();
                    }
                    break;
                case 16://连接失败
                    if (mStatusCallBack != null) {
                        mStatusCallBack.callFailed();
                    }
                    break;
                case 17://正在连接断开
                    break;
                case 18://断开连接失败

                    break;
                case 19://断开了
                    if (mStatusCallBack != null) {
                        mStatusCallBack.callFailed();
                    }
                    break;
                case 20://重新连接中
                    if (mStatusCallBack != null) {
                        mStatusCallBack.callSuccess();
                    }
                    break;
                case 21://重新连接成功
                    if (mStatusCallBack != null) {
                        mStatusCallBack.callSuccess();
                    }
                    break;
                case 22://重新连接失败
                    if (mStatusCallBack != null) {
                        mStatusCallBack.callFailed();
                    }
                    break;
                case 23://移除成功
                    break;
            }
        }
    }

    class IRecentCallListUpdatedEvent extends RecentCallListUpdatedEvent {

        private IRecentCallListUpdatedEvent(boolean bRegist) throws RpcError {
            super(bRegist);
        }

        @Override
        public void OnRecentCallListUpdatedEventTriggered() {
            super.OnRecentCallListUpdatedEventTriggered();
            try {
                ParamInt paramInt = new ParamInt(0);
                EResult.Ref result = new EResult.Ref(EResult.ESuccess);
                IBluetooth.Inst().GetRecentCallListCount(result, paramInt);
                @SuppressLint("UseSparseArrays")
                Map<Integer, RecentCallInfo> map = new HashMap<>();
                IBluetooth.Inst().GetRecentCallList(result, 0, paramInt.getValue(), map);
                mCallLogBack.onCallLogUpdate(map);
            } catch (RpcError rpcError) {
                sLog.e(TAG, "OnRecentCallListUpdatedEventTriggered: ", rpcError);
            }
        }
    }

    class IICbPhoneEvent extends ICbPhone {

        private IICbPhoneEvent() throws RpcError {
        }

        @Override
        public void OnGetPhoneDeviceDone(EResult result, Map<Integer, PhoneDeviceInfo> device) throws RpcError {
            super.OnGetPhoneDeviceDone(result, device);
            for (int i = 0; i < device.size(); i++) {

                if (device.get(i).phoneType == EPhoneType.PHONETYPE_BLUETOOTH) {
                    mGetPhoneIdBack.onGetPhoneId(device.get(i).phoneID);
                }

                sLog.i(TAG, "OnGetPhoneDeviceDone: type--->" + device.get(i).phoneType);

            }

        }

    }

}
