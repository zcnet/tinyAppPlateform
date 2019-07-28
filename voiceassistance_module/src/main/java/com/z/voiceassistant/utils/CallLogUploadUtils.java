package com.z.voiceassistant.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import com.z.voiceassistant.models.ThirdUploadMissingBean;
import com.z.voiceassistant.models.ThirdUploadOutBean;
import com.z.voiceassistant.models.WxCallLogBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import MBluetoothService.RecentCallInfo;

/**
 * Created by sun on 2018/12/27
 */
public class CallLogUploadUtils{
    /**
     * 将Map转为Wx可接收的JavaBean格式
     */
    public static WxCallLogBean changeToWxCallLogBean(Map<Integer, RecentCallInfo> map) {
        WxCallLogBean bean = new WxCallLogBean();
        List<WxCallLogBean.ContentBean> list = new LinkedList<>();
        WxCallLogBean.ContentBean contentBean;
        RecentCallInfo info;
        int count = 0;
        Set<Integer> set = map.keySet();
        for (Integer integer : set) {
            info = map.get(integer);
            count++;
            contentBean = new WxCallLogBean.ContentBean();
            contentBean.setCallId(info.recentCallID);
            contentBean.setTelNum(info.telNum);
            contentBean.setNameFirst(info.contactNameFirst);
            contentBean.setNameLast(info.contactNameLast);
            contentBean.setCallTime(
                    info.callTime.hour + "/" + info.callTime.minute + "/" + info.callTime.second
            );
            contentBean.setCallDate(
                    info.callDate.year + "/" + info.callDate.month + "/" + info.callDate.day
            );
            list.add(contentBean);
        }
        bean.setCount(count);
        bean.setContent(list);
        return bean;
    }

    /**
     * 将通话记录Map转为可上传至第三方未接来电接口的JavaBean
     */
    public static ThirdUploadMissingBean changeToThirdMissingBean(Map<Integer, RecentCallInfo> map) {

        ThirdUploadMissingBean thirdCallLogBean = new ThirdUploadMissingBean();
        List<ThirdUploadMissingBean.MissingListBean> list = new LinkedList<>();

        ThirdUploadMissingBean.MissingListBean bean;
        Set<Integer> set = map.keySet();
        RecentCallInfo info;
        int index = 0;

        for (Integer integer : set) {
            bean = new ThirdUploadMissingBean.MissingListBean();
            info = map.get(integer);
            bean.setContractName(info.contactNameFirst + info.contactNameLast);
            bean.setNum(info.telNum);
            bean.setRecordNo(integer);
            bean.setType("");
            list.add(bean);
            index = integer;
        }

        thirdCallLogBean.setMissingList(list);
        thirdCallLogBean.setNumPerPage(index / 3);
        return thirdCallLogBean;
    }

    /**
     * 将通话记录Map转为可上传至第三方拨出电话接口的JavaBean
     */
    public static ThirdUploadOutBean changeToThirdOutBean(Map<Integer, RecentCallInfo> map) {

        ThirdUploadOutBean thirdCallLogBean = new ThirdUploadOutBean();
        List<ThirdUploadOutBean.CalloutListBean> list = new LinkedList<>();

        ThirdUploadOutBean.CalloutListBean bean;
        Set<Integer> set = map.keySet();
        RecentCallInfo info;
        int index = 0;

        for (Integer integer : set) {
            bean = new ThirdUploadOutBean.CalloutListBean();
            info = map.get(integer);
            bean.setContractName(info.contactNameFirst + info.contactNameLast);
            bean.setNum(info.telNum);
            bean.setRecordNo(integer);
            bean.setType("");
            list.add(bean);
        }

        thirdCallLogBean.setCalloutList(list);
        thirdCallLogBean.setNumPerPage(index / 3);
        return thirdCallLogBean;
    }
        }

