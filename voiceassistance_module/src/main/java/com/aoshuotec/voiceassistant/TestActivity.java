package com.aoshuotec.voiceassistant;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.aoshuotec.voiceassistant.constants.FloatWindowConstant;
import com.aoshuotec.voiceassistant.listener.IDataCallBack;
import com.aoshuotec.voiceassistant.models.BoBackBean;
import com.aoshuotec.voiceassistant.utils.Logg;
import com.google.gson.Gson;
import com.z.tinyapp.utils.common.WeexApps;
import com.z.tinyapp.utils.logs.sLog;
import com.sun.weexandroid_module.WxRvUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sun on 2018/9/29
 */

public class TestActivity extends AppCompatActivity implements IDataCallBack {

    private static final String TAG = "sunTestActivity";

    //    private FunctionManager.MBinder mBinder;
    private LinearLayout jsLayout;

    private Long id = -1L;

    List<BoBackBean> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jsLayout = (LinearLayout) findViewById(R.id.ll_all);

        Intent intent = new Intent();
        intent.setComponent(new ComponentName(this, FunctionManager.class));
        intent.setPackage("com.aoshuotec.voiceassistant");
        startService(intent);

        initData();

        findViewById(R.id.btn0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWeex();
            }
        });


    }

    private void initData() {
        //asr -> 拨打电话
        mList.add(new Gson().fromJson("{" +
                "\"code\": \"S00\"," +
                "\"botId\": 1," +
                "\"sessionId\": \"1cc43160dc474915be44c00596239e521\"," +
                "\"intent\": [{" +
                "\"name\": \"MAKE_CALL\"," +
                "\"actionCount\": 1," +
                "\"slots\": [{" +
                "\"name\": \"user_phone_num\"," +
                "\"originalWord\": \"原始词\"," +
                "\"normalizedWord\": \"规整词\"," +
                "\"transformerWord\": \"转换词\"," +
                "\"must\": \"yes\"," +
                "\"type\": \"userdefined\"," +
                "\"resetWhenIntentRecognized\": \"no\"," +
                "\"clarify\": \"ask_for_phone_num\"" +
                "}]," +
                "\"slotGroups\": [{" +
                "\"name\": \"make_call\"," +
                "\"must\": \"yes\"," +
                "\"least_fill_slot\": 1," +
                "\"slots\": [{" +
                "\"name\": \"user_contact_name\"," +
                "\"must\": \"no\"" +
                "}, {" +
                "\"name\": \"user_phone_num\"," +
                "\"must\": \"no\"" +
                "}]" +
                "}]" +
                "}]," +
                "\"vaActionList\": [{" +
                "\"name\": \"play:播放文字\"," +
                "\"priority\": 0," +
                "\"interval\": 0," +
                "\"retry\": 0," +
                "\"doActionNow\": 1," +
                "\"textList\": [{" +
                "\"text\": \"请说联系人姓名\"," +
                "\"interval\": 0" +
                "}]" +
                "}, {" +
                "\"name\": \"rec:录制音频\"," +
                "\"priority\": 0," +
                "\"interval\": 0" +
                "}," +
                "{" +
                "\"name\": \"display:显示文字\"," +
                "\"priority\": 0," +
                "\"interval\": 0," +
                "\"textList\": [{" +
                "\"text\": \"请说联系人姓名\"," +
                "\"interval\": 0" +
                "}]" +
                "}]," +
                "\"errorTextList\": [{" +
                "\"errorHintNum\": 3," +
                "\"textList\": [{" +
                "\"text\": \"发生错误请重试第一次\"" +
                "}, {" +
                "\"text\": \"发生错误请重试第二次\"" +
                "}, {" +
                "\"text\": \"发生错误请重试最后一次\"" +
                "}]" +
                "}]," +
                "\"buActionList\": []" +
                "}", BoBackBean.class));
        //asr -> 张三
        mList.add(new Gson().fromJson("{" +
                "\"code\": \"S00\"," +
                "\"botId\": 1," +
                "\"sessionId\": \"1cc43160dc474915be44c00596239e521\"," +
                "\"intent\": [{" +
                "\"name\": \"MAKE_CALL\"," +
                "\"actionCount\": 1," +
                "\"slots\": [{" +
                "\"name\": \"user_phone_num\"," +
                "\"originalWord\": \"原始词\"," +
                "\"normalizedWord\": \"规整词\"," +
                "\"transformerWord\": \"转换词\"," +
                "\"must\": \"yes\"," +
                "\"type\": \"userdefined\"," +
                "\"resetWhenIntentRecognized\": \"no\"," +
                "\"clarify\": \"ask_for_phone_num\"" +
                "}]," +
                "\"slotGroups\": [{" +
                "\"name\": \"make_call\"," +
                "\"must\": \"yes\"," +
                "\"least_fill_slot\": 1," +
                "\"slots\": [{" +
                "\"name\": \"user_contact_name\"," +
                "\"must\": \"no\"" +
                "}, {" +
                "\"name\": \"user_phone_num\"," +
                "\"must\": \"no\"" +
                "}]" +
                "}]" +
                "}]," +
                "\"vaActionList\": [{" +
                "\"name\": \"play:播放文字\"," +
                "\"priority\": 0," +
                "\"interval\": 0," +
                "\"retry\": 0," +
                "\"doActionNow\": 1," +
                "\"textList\": [{" +
                "\"text\": \"正在为您查找彭万里的电话。\"," +
                "\"interval\": 0" +
                "}]" +
                "}, {" +
                "\"name\": \"display:显示文字\"," +
                "\"priority\": 0," +
                "\"interval\": 0," +
                "\"textList\": [{" +
                "\"text\": \"正在为您查找彭万里的电话。\"," +
                "\"interval\": 0" +
                "}]" +
                "}, {" +
                "\"name\": \"execaction:执行业务动作\"," +
                "\"priority\": 0," +
                "\"interval\": 0" +
                "}]," +
                "\"errorTextList\": [{" +
                "\"errorHintNum\": 3," +
                "\"textList\": [{" +
                "\"text\": \"发生错误请重试第一次\"" +
                "}, {" +
                "\"text\": \"发生错误请重试第二次\"" +
                "}, {" +
                "\"text\": \"发生错误请重试最后一次\"" +
                "}]" +
                "}]," +
                "\"buActionList\": [{" +
                "\"appName\": \"callCard\"," +
                "\"moduleName\": \"callModule\"," +
                "\"displayType\": 1," +
                "\"isReuse\": 0," +
                "\"content\": \"{\\\"actionName\\\":\\\"查找\\\",\\\"name\\\":\\\"彭万里\\\"}\"" +
                "}]" +
                "}", BoBackBean.class));
        //asr -> 上传联系人后，拨打第几个
        mList.add(new Gson().fromJson("{" +
                "\"code\": \"S00\"," +
                "\"botId\": 1," +
                "\"sessionId\": \"1cc43160dc474915be44c00596239e521\"," +
                "\"intent\": [{" +
                "\"name\": \"MAKE_CALL\"," +
                "\"actionCount\": 1," +
                "\"slots\": [{" +
                "\"name\": \"user_phone_num\"," +
                "\"originalWord\": \"原始词\"," +
                "\"normalizedWord\": \"规整词\"," +
                "\"transformerWord\": \"转换词\"," +
                "\"must\": \"yes\"," +
                "\"type\": \"userdefined\"," +
                "\"resetWhenIntentRecognized\": \"no\"," +
                "\"clarify\": \"ask_for_phone_num\"" +
                "}]," +
                "\"slotGroups\": [{" +
                "\"name\": \"make_call\"," +
                "\"must\": \"yes\"," +
                "\"least_fill_slot\": 1," +
                "\"slots\": [{" +
                "\"name\": \"user_contact_name\"," +
                "\"must\": \"no\"" +
                "}, {" +
                "\"name\": \"user_phone_num\"," +
                "\"must\": \"no\"" +
                "}]" +
                "}]" +
                "}]," +
                "\"vaActionList\": [{" +
                "\"name\": \"play:播放文字\"," +
                "\"priority\": 0," +
                "\"interval\": 0," +
                "\"retry\": 0," +
                "\"doActionNow\": 1," +
                "\"textList\": [{" +
                "\"text\": \"为您拨打彭万里的电话，您可以说挂断来终止当前拨号。\"," +
                "\"interval\": 0" +
                "}]" +
                "}, {" +
                "\"name\": \"display:显示文字\"," +
                "\"priority\": 0," +
                "\"interval\": 0," +
                "\"textList\": [{" +
                "\"text\": \"为您拨打彭万里的电话，您可以说挂断来终止当前拨号。\"," +
                "\"interval\": 0" +
                "}]" +
                "}, {" +
                "\"name\": \"rec:录制音频\"," +
                "\"priority\": 0," +
                "\"interval\": 0" +
                "}, {" +
                "\"name\": \"execaction:执行业务动作\"," +
                "\"priority\": 0," +
                "\"interval\": 0" +
                "}]," +
                "\"errorTextList\": [{" +
                "\"errorHintNum\": 3," +
                "\"textList\": [{" +
                "\"text\": \"发生错误请重试第一次\"" +
                "}, {" +
                "\"text\": \"发生错误请重试第二次\"" +
                "}, {" +
                "\"text\": \"发生错误请重试最后一次\"" +
                "}]" +
                "}]," +
                "\"buActionList\": [{" +
                "\"appName\": \"callCard\"," +
                "\"moduleName\": \"callModule\"," +
                "\"displayType\": 1," +
                "\"isReuse\": 0," +
                "\"content\": \"{\\\"actionName\\\":\\\"拨打提示\\\",\\\"num\\\":\\\"17383948493\\\",\\\"name\\\":\\\"彭万里\\\"}\"" +
                "}]" +
                "}", BoBackBean.class));
        //asr -> 三秒后 拨出
        mList.add(new Gson().fromJson("{" +
                "\"code\": \"S00\"," +
                "\"botId\": 1," +
                "\"sessionId\": \"1cc43160dc474915be44c00596239e521\"," +
                "\"intent\": [{" +
                "\"name\": \"MAKE_CALL\"," +
                "\"actionCount\": 1," +
                "\"slots\": [{" +
                "\"name\": \"user_phone_num\"," +
                "\"originalWord\": \"原始词\"," +
                "\"normalizedWord\": \"规整词\"," +
                "\"transformerWord\": \"转换词\"," +
                "\"must\": \"yes\"," +
                "\"type\": \"userdefined\"," +
                "\"resetWhenIntentRecognized\": \"no\"," +
                "\"clarify\": \"ask_for_phone_num\"" +
                "}]," +
                "\"slotGroups\": [{" +
                "\"name\": \"make_call\"," +
                "\"must\": \"yes\"," +
                "\"least_fill_slot\": 1," +
                "\"slots\": [{" +
                "\"name\": \"user_contact_name\"," +
                "\"must\": \"no\"" +
                "}, {" +
                "\"name\": \"user_phone_num\"," +
                "\"must\": \"no\"" +
                "}]" +
                "}]" +
                "}]," +
                "\"vaActionList\": [{" +
                "\"name\": \"play:播放文字\"," +
                "\"priority\": 0," +
                "\"interval\": 0," +
                "\"retry\": 0," +
                "\"doActionNow\": 1," +
                "\"textList\": [{" +
                "\"text\": \"呼叫中\"," +
                "\"interval\": 0" +
                "}]" +
                "}, {" +
                "\"name\": \"display:显示文字\"," +
                "\"priority\": 0," +
                "\"interval\": 0," +
                "\"textList\": [{" +
                "\"text\": \"呼叫中\"," +
                "\"interval\": 0" +
                "}]" +
                "}, {" +
                "\"name\": \"rec:录制音频\"," +
                "\"priority\": 0," +
                "\"interval\": 0" +
                "}" +
                ", {" +
                "\"name\": \"execaction:执行业务动作\"," +
                "\"priority\": 0," +
                "\"interval\": 0" +
                "}]," +
                "\"errorTextList\": [{" +
                "\"errorHintNum\": 3," +
                "\"textList\": [{" +
                "\"text\": \"发生错误请重试第一次\"" +
                "}, {" +
                "\"text\": \"发生错误请重试第二次\"" +
                "}, {" +
                "\"text\": \"发生错误请重试最后一次\"" +
                "}]" +
                "}]," +
                "\"buActionList\": [{" +
                "\"appName\": \"callCard\"," +
                "\"moduleName\": \"callModule\"," +
                "\"displayType\": 1," +
                "\"isReuse\": 0," +
                "\"content\": \"{\\\"actionName\\\":\\\"拨打\\\",\\\"num\\\":\\\"17383948493\\\",\\\"name\\\":\\\"彭万里\\\"}\"" +
                "}]" +
                "}", BoBackBean.class));

    }

    private void addWeex() {

        BoBackBean bean = mList.get(1);

        final Map<String, Object> map = new HashMap<>();

        List<BoBackBean.BuActionListBean> buActionListBeans = bean.getBuActionList();
        BoBackBean.BuActionListBean buActionListBean;
        if (buActionListBeans != null) {
            for (int i = 0; i < buActionListBeans.size(); i++) {
                buActionListBean = buActionListBeans.get(i);

                switch (buActionListBean.getAppName()) {
                    case "callCard":
                        map.put(FloatWindowConstant.MODULE_KEY_LIST_BO_CALL_BACK, buActionListBean.getContent());
                        break;
                }
            }
        }

        map.put("name", System.currentTimeMillis());

        Logg.i(TAG, "addWxView: bo add data ->" + new Gson().toJson(map));

        jsLayout.post(new Runnable() {
            @Override
            public void run() {
                String tel_searchname_path = WeexApps.getAppListMgr().findVRCardPath("liteapp_voiceassistant_offline", "tel_searchname");
                WxRvUtils.getFloatWindowView(jsLayout.getContext(), jsLayout, tel_searchname_path, map, new WxRvUtils.IJsLoadSuccessCallBack() {
                    @Override
                    public void onAdded() {
                        sLog.i(TAG, "onAdded: all over");
                    }
                });
            }
        });
    }

    @Override
    public void getJniDataBack(Bundle bundle) {

    }

    @Override
    public void getBoDataBack(Bundle bundle) {

    }

    @Override
    public void getWxCommonDataBack(Bundle bundle) {

    }

    @Override
    public void getWxContactDataBack(Bundle bundle) {

    }

    @Override
    public void getWxCallLogDataBack(Bundle bundle) {

    }


    @Override
    public void getFwDataBack(Bundle bundle) {

    }

    @Override
    public void getBaiDDataBack(Bundle bundle) {

    }

    @Override
    public void getPinYinDataBack(Bundle bundle) {

    }

    @Override
    public void getTTSDataBack(Bundle bundle) {

    }

    @Override
    public void getLocationDataBack(Bundle bundle) {

    }

}
