package com.zcnet.voice_callback_lib.va_3rd_biz;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.contacts.Contact;
import com.contacts.TelNumber;
import com.z.tinyapp.utils.logs.sLog;
import com.contacts.CallLogUtils;
import com.contacts.PinYinService;
import com.zcnet.voice_callback_lib.GeneralContext;
import com.zcnet.voice_callback_lib.R;
import com.zcnet.voice_callback_lib.Utilities;
import com.zcnet.voice_callback_lib.va_3rd_biz.PhoneCallData.ContactInfo;
import com.zcnet.voice_callback_lib.va_3rd_biz.PhoneCallData.PhoneNumPrevCalled;
import com.zcnet.voice_callback_lib.va_3rd_biz.PhoneCallData.PhoneNumPrevCalledEntry;
import com.zcnet.voice_callback_lib.va_3rd_biz.PhoneCallData.PhoneNumber;
import com.zcnet.voice_callback_lib.va_framework.VAActionEntityListEnum;
import com.zcnet.voice_callback_lib.va_framework.VAFramework;
import com.zcnet.voice_callback_lib.voice_data.VAActionEntity;
import com.zcnet.voice_callback_lib.voice_data.VABaseOnCompleteReqPostData;
import com.zcnet.voice_callback_lib.voice_data.VABuActionEntity;
import com.zcnet.voice_callback_lib.voice_data.VAErrorTextEntity;
import com.zcnet.voice_callback_lib.voice_data.VAIntent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import MBluetoothService.RecentCallInfo;

public class PhoneCall {
    private static final String LOG_TAG = "_VA_PHONECALL";
    VAFramework vaFramework = VAFramework.getInstance();
    VAIntent vaIntent = vaFramework.getVoiceData().getVaIntents().get(0);
    VABaseOnCompleteReqPostData postData = vaFramework.getVoiceData().getOnCompleteReqPostData();

    // MAKE_CALL
    public void makeCall() throws Exception {
        // reset_session = false;
        vaFramework.getVoiceData().getVaIntents().get(0).setResetAiSession(false);
        freshData();
        // START L0.1 //////////////////////////////////////////////////////////////////////////////
        String onstar = vaFramework.getNormalizedWordSlot(vaIntent, "user_onstar");
        if (!onstar.isEmpty()){
            vaFramework.setIsUseOnStar(true);
        }
        if (!(isBtConnected()) && !(vaFramework.isUseOnStar())) {

            sLog.i(LOG_TAG, "makeCall: !isBtConnected.");
            sLog.i(LOG_TAG, "makeCall: !isUseOnStar.");
            vaFramework.getVoiceData().clearExpectedIntent();
            vaFramework.addExpectedIntent("MAKE_CALL");
            vaFramework.addExpectedIntent("STOP_CALL");
            vaFramework.addExpectedIntent("COMM_COMFIRM");
            vaFramework.addExpectedIntent("PHONE_NUM_MODIFY");
            vaFramework.getVoiceData().setPhoneNumber(vaFramework.getNormalizedWordSlot(vaIntent, "user_phone_num"));
            List<VAActionEntityListEnum> vaActionEntityListEnums = new ArrayList<VAActionEntityListEnum>();
            vaActionEntityListEnums.add(VAActionEntityListEnum.PLAY);
            vaActionEntityListEnums.add(VAActionEntityListEnum.DISPLAY);
            vaActionEntityListEnums.add(VAActionEntityListEnum.REC);
            List<VAActionEntity> vaActionEntities =
                    vaFramework.getVaActionListResult(getXmlString(R.array.blue_tooth_not_connect), vaActionEntityListEnums);

            VAIntent newVaIntent = vaFramework.botSayAndListen(
                    vaActionEntities, null, null);
            if (!Utilities.isNull(newVaIntent)) {
                freshData();
                if (vaIntent.getIntentName().equals("COMM_COMFIRM")) {
                    sLog.i(LOG_TAG, "makeCall: new intentName = COMM_COMFIRM.");
                    String normalizedWord = vaFramework.getNormalizedWordSlot(vaIntent,
                            "user_confirm_yesno");
                    if (normalizedWord.equals("否")) {
                        vaActionEntities.clear();
                        vaActionEntities.add(vaFramework.getPlayResult(
                                "", 0, 0,
                                0, 1, 0));
                        List<VAErrorTextEntity> vaErrorTextEntities =
                                vaFramework.getErrorTextListResult();
                        vaFramework.botSay(vaActionEntities, vaErrorTextEntities, null);
                        vaFramework.vaExit(vaIntent, 0.5);
                    } else if (normalizedWord.equals("是")) {
                        vaActionEntities.clear();
                        vaActionEntities.add(vaFramework.getPlayResult(getXmlString(R.array.blue_tooth_wait_connect), 0, 0,
                                0, 1, 0));
                        List<VAErrorTextEntity> vaErrorTextEntities =
                                vaFramework.getErrorTextListResult();
                        vaFramework.botSay(vaActionEntities, vaErrorTextEntities, null);
                        vaFramework.setIsUseOnStar(true);
                        vaFramework.vaExit(vaIntent, 0.5);
                    }
                } else {
                    handleExpectedIntent();
                    vaFramework.vaExit(vaIntent, 0.5);
                }
            } else {
                vaFramework.vaExit(vaIntent, 0.5);
            }
        }
        // END L0.1 ////////////////////////////////////////////////////////////////////////////////

        // START L0: 用户号码已填槽 //////////////////////////////////////////////////////////////////
        // 如果本次意图中对“电话号码”进行了填槽。
        if (vaFramework.isFillingSlot(vaIntent, "user_phone_num") && !vaFramework.isFillingSlot(vaIntent, "user_name")) {
            // 获取电话号码
            String phoneNumber = vaFramework.getNormalizedWordSlot(vaIntent, "user_phone_num");
            // START L1.1: 判断电话是否完整 //////////////////////////////////////////////////////////
            if (isPhoneNumComplete(phoneNumber)) {
                sLog.i(LOG_TAG, "makeCall: isPhoneNumComplete.");
                if (isPhoneNumPrevCalled(phoneNumber)) {
                    // START L1: 直接为用户拨打 //////////////////////////////////////////////////////
                    sLog.i(LOG_TAG, "makeCall: isPhoneNumPrevCalled.");
                    vaFramework.getVoiceData().clearExpectedIntent();
                    vaFramework.addExpectedIntent("STOP_CALL");
                    vaFramework.addExpectedIntent("MAKE_CALL");
                    vaFramework.addExpectedIntent("PHONE_NUM_MODIFY");
                    vaFramework.getVoiceData().setPhoneNumber(phoneNumber);
                    List<VAActionEntityListEnum> flagList = new ArrayList<VAActionEntityListEnum>();
                    flagList.add(VAActionEntityListEnum.PLAY);
                    flagList.add(VAActionEntityListEnum.DISPLAY);
                    flagList.add(VAActionEntityListEnum.REC);
                    flagList.add(VAActionEntityListEnum.EXECUTION);
                    String botSayContext =String.format(getXmlString(R.array.call_wait_close),phoneNumber);
                    List<VAActionEntity> vaActionEntities =
                            vaFramework.getVaActionListResult(botSayContext, flagList);
                    List<VABuActionEntity> vaBuActionEntities =
                            vaFramework.getVaBuActionListResult("{\"actionName\":\"拨打提示\",\"num\":\"" + phoneNumber + "\"}");
                    VAIntent newVaIntent = vaFramework.botSayAndListen(vaActionEntities, null, vaBuActionEntities);
                    if (!Utilities.isNull(newVaIntent)) {
                        freshData();
                        handleExpectedIntent();
                        vaFramework.vaExit(vaIntent, 0.5);
                    } else {
                        vaFramework.vaExit(vaIntent, 0.5);
                    }
                    // END L1 //////////////////////////////////////////////////////////////////////
                } else { // 这个电话以前没有被拨打过
                    sLog.i(LOG_TAG, "makeCall: !isPhoneNumPrevCalled.");
                    vaFramework.getVoiceData().clearExpectedIntent();
                    vaFramework.addExpectedIntent("COMM_COMFIRM");
                    vaFramework.addExpectedIntent("STOP_CALL");
                    vaFramework.addExpectedIntent("PHONE_NUM_MODIFY");
                    vaFramework.addExpectedIntent("MAKE_CALL");
                    vaFramework.addExpectedIntent("PHONE_PHONE_CLEAR");
                    vaFramework.getVoiceData().setPhoneNumber(phoneNumber);
                    List<VAActionEntityListEnum> flagList = new ArrayList<VAActionEntityListEnum>();
                    flagList.add(VAActionEntityListEnum.PLAY);
                    flagList.add(VAActionEntityListEnum.DISPLAY);
                    flagList.add(VAActionEntityListEnum.REC);
                    flagList.add(VAActionEntityListEnum.EXECUTION);
                    String botSayContext = String.format(getXmlString(R.array.ask_for_call),phoneNumber);
                    List<VAActionEntity> vaActionEntities =
                            vaFramework.getVaActionListResult(botSayContext, flagList);
                    List<VABuActionEntity> vaBuActionEntities =
                            vaFramework.getVaBuActionListResult("{\"actionName\":\"询问\",\"num\":\"" + phoneNumber + "\"}");
                    List<VAErrorTextEntity> vaErrorTextEntities = vaFramework.getErrorTextListResult();
                    VAIntent newVaIntent =
                            vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, vaBuActionEntities);
                    if (!Utilities.isNull(newVaIntent)) {
                        freshData();
                        if (vaIntent.getIntentName().equals("COMM_COMFIRM")) {
                            String normalizedWord = vaFramework.getNormalizedWordSlot(vaIntent, "user_confirm_yesno");
                            if (normalizedWord.equals("是")) {
                                gotoL1(phoneNumber);
                            } else if (normalizedWord.equals("否")) {
                                flagList.clear();
                                flagList.add(VAActionEntityListEnum.PLAY);
                                flagList.add(VAActionEntityListEnum.DISPLAY);
                                flagList.add(VAActionEntityListEnum.EXECUTION);
                                botSayContext = getXmlString(R.array.stop_call);
                                vaActionEntities = vaFramework.getVaActionListResult(botSayContext, flagList);
                                vaBuActionEntities = vaFramework.getVaBuActionListResult("{\"actionName\":\"挂断\"}");
                                vaErrorTextEntities = vaFramework.getErrorTextListResult();
                                vaFramework.botSay(vaActionEntities, vaErrorTextEntities, vaBuActionEntities);
                                vaFramework.vaExit(vaIntent, 0.5);
                            }
                        } else {
                            handleExpectedIntent();
                            vaFramework.vaExit(vaIntent, 0.5);
                        }
                        // TODO: 2/27/2019 slot_filling
                    } else {
                        addPhoneNum2PrevCalled(phoneNumber);
                        vaFramework.vaExit(vaIntent, 0.5);
                    }
                }
            } else { // 电话号码不符合系统设定的规范
                sLog.i(LOG_TAG, "makeCall: !isPhoneNumComplete.");
                vaFramework.getVoiceData().clearExpectedIntent();
                vaFramework.addExpectedIntent("COMM_COMFIRM");
                vaFramework.addExpectedIntent("STOP_CALL");
                vaFramework.addExpectedIntent("PHONE_NUM_MODIFY");
                vaFramework.addExpectedIntent("MAKE_CALL");
                vaFramework.getVoiceData().setPhoneNumber(phoneNumber);
                List<VAActionEntityListEnum> flagList = new ArrayList<VAActionEntityListEnum>();
                flagList.add(VAActionEntityListEnum.PLAY);
                flagList.add(VAActionEntityListEnum.DISPLAY);
                String botSayContext = getXmlString(R.array.say_number);
                List<VAActionEntity> vaActionEntities = vaFramework.getVaActionListResult(botSayContext, flagList);
                VAActionEntity vaActionEntity = vaFramework.getRecResult(1, 0);
                vaActionEntities.add(vaActionEntity);
                List<VAErrorTextEntity> vaErrorTextEntities = vaFramework.getErrorTextListResult();
                VAIntent newVaIntent = vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, null);
                if (!Utilities.isNull(newVaIntent)) {
                    freshData();
                    if (vaIntent.getIntentName().equals("COMM_COMFIRM")) {
                        String normalizedWord = vaFramework.getNormalizedWordSlot(vaIntent, "user_confirm_yesno");
                        if (normalizedWord.equals("是")) {
                            gotoL1(phoneNumber);
                        } else if (normalizedWord.equals("否")) {
                            flagList.clear();
                            flagList.add(VAActionEntityListEnum.PLAY);
                            flagList.add(VAActionEntityListEnum.DISPLAY);
                            flagList.add(VAActionEntityListEnum.EXECUTION);
                            vaActionEntities = vaFramework.getVaActionListResult(getXmlString(R.array.stop_call), flagList);
                            vaErrorTextEntities = vaFramework.getErrorTextListResult();
                            List<VABuActionEntity> vaBuActionEntities =
                                    vaFramework.getVaBuActionListResult("{\"actionName\":\"挂断\"}");
                            vaFramework.botSay(vaActionEntities, vaErrorTextEntities, vaBuActionEntities);
                            vaFramework.vaExit(vaIntent, 0.5);
                        }
                    } else {
                        handleExpectedIntent();
                        vaFramework.vaExit(vaIntent, 0.5);
                    }
                    // TODO: 2/27/2019 slot_filling
                } else {
                    vaFramework.vaExit(vaIntent, 0.5);
                }
            }
            // END L1.1 ////////////////////////////////////////////////////////////////////////////
        } else { // 如果本次意图没有中对“电话号码”进行了填槽。
            // START L2: 等待姓名槽位有值后拨打 ///////////////////////////////////////////////////////
            if (!vaFramework.isFillingSlot(vaIntent, "user_name")) { // 处理姓名相关的场景
                vaFramework.getVoiceData().clearExpectedIntent();
                vaFramework.addExpectedIntent("COMM_COMFIRM");
                vaFramework.addExpectedIntent("STOP_CALL");
                vaFramework.addExpectedIntent("MAKE_CALL");
                vaFramework.addExpectedIntent("COMM_SELECT");
                List<VAActionEntityListEnum> flagList = new ArrayList<VAActionEntityListEnum>();
                flagList.add(VAActionEntityListEnum.PLAY);
                flagList.add(VAActionEntityListEnum.DISPLAY);
                flagList.add(VAActionEntityListEnum.REC);
                flagList.add(VAActionEntityListEnum.EXECUTION);
                String botSayContext = getXmlString(R.array.say_contacts_name);
                List<VAActionEntity> vaActionEntities =
                        vaFramework.getVaActionListResult(botSayContext, flagList);
                List<VABuActionEntity> vaBuActionEntities =
                        vaFramework.getVaBuActionListResult("{\"actionName\":\"询问\"");
                List<VAErrorTextEntity> vaErrorTextEntities = vaFramework.getErrorTextListResult();
                VAIntent newVaIntent =
                        vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, vaBuActionEntities);
                if (!Utilities.isNull(newVaIntent)) {
                    freshData();
                    if (vaIntent.getIntentName().equals("COMM_COMFIRM")) {
                        String normalizedWord = vaFramework.getNormalizedWordSlot(vaIntent, "user_confirm_yesno");
                        if (normalizedWord.equals("是")) {
                            flagList.clear();
                            flagList.add(VAActionEntityListEnum.PLAY);
                            flagList.add(VAActionEntityListEnum.DISPLAY);
                            flagList.add(VAActionEntityListEnum.REC);
                            flagList.add(VAActionEntityListEnum.EXECUTION);
                            botSayContext = getXmlString(R.array.call_stop_no_name);
                            vaActionEntities = vaFramework.getVaActionListResult(botSayContext, flagList);
                            vaBuActionEntities = vaFramework.getVaBuActionListResult("{\"actionName\":\"询问\"");
                            vaErrorTextEntities = vaFramework.getErrorTextListResult();
                            newVaIntent = vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, vaBuActionEntities);
                            if (!Utilities.isNull(newVaIntent)) {
                                freshData();
                                gotoL2();
                            } else {
                                vaFramework.vaExit(vaIntent, 0.5);
                            }
                        } else if (normalizedWord.equals("否")) {
                            flagList.clear();
                            flagList.add(VAActionEntityListEnum.PLAY);
                            flagList.add(VAActionEntityListEnum.DISPLAY);
                            botSayContext = getXmlString(R.array.stop_session);
                            vaActionEntities = vaFramework.getVaActionListResult(botSayContext, flagList);
                            vaFramework.botSay(vaActionEntities, null, null);
                            vaFramework.vaExit(vaIntent, 0.5);
                        }
                    } else {
                        handleExpectedIntent();
                        vaFramework.vaExit(vaIntent, 0.5);
                    }
                } else {
                    flagList.clear();
                    flagList.add(VAActionEntityListEnum.PLAY);
                    flagList.add(VAActionEntityListEnum.DISPLAY);
                    flagList.add(VAActionEntityListEnum.REC);
                    flagList.add(VAActionEntityListEnum.EXECUTION);
                    botSayContext = getXmlString(R.array.call_need_contacts_name);
                    vaActionEntities = vaFramework.getVaActionListResult(botSayContext, flagList);
                    vaBuActionEntities = vaFramework.getVaBuActionListResult("{\"actionName\":\"询问\"");
                    vaErrorTextEntities = vaFramework.getErrorTextListResult();
                    newVaIntent = vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, vaBuActionEntities);
                    if (!Utilities.isNull(newVaIntent)) {
                        freshData();
                        gotoL2();
                    } else {
                        vaFramework.vaExit(vaIntent, 0.5);
                    }
                }
            }// 此时 vaIntent 中已经对姓名进行了填槽。
            // END L2 //////////////////////////////////////////////////////////////////////////////
        }
        // END L0 //////////////////////////////////////////////////////////////////////////////////

        if (!isContactBookNotSync()) { // 通讯簿没有连接。
            sLog.i(LOG_TAG, "makeCall: !isContactBookNotSync.");
            List<VAActionEntityListEnum> flagList = new ArrayList<VAActionEntityListEnum>();
            flagList.add(VAActionEntityListEnum.PLAY);
            flagList.add(VAActionEntityListEnum.DISPLAY);
            String botSayContext = getXmlString(R.array.contacts_book_not_sync);
            List<VAActionEntity> vaActionEntities =
                    vaFramework.getVaActionListResult(botSayContext, flagList);
            List<VAErrorTextEntity> vaErrorTextEntities = vaFramework.getErrorTextListResult();
            vaFramework.botSay(vaActionEntities, vaErrorTextEntities, null);
            vaFramework.vaExit(vaIntent, 0.5);
        }
        else { // 通讯簿已经连接。
            // START L7: 处理姓名槽位 ////////////////////////////////////////////////////////////////
            sLog.i(LOG_TAG, "makeCall: isContactBookNotSync.");
            String contactName = vaFramework.getNormalizedWordSlot(vaIntent, "user_name");
            String type = vaFramework.getNormalizedWordSlot(vaIntent, "user_phone_type");
            String number = vaFramework.getNormalizedWordSlot(vaIntent, "user_phone_num");
            String location = vaFramework.getNormalizedWordSlot(vaIntent, "user_phone_loc");
            List<ContactInfo> contactInfos = searchContact(contactName, type, location, number);
            if (contactInfos.size() == 1) {
                sLog.i(LOG_TAG, "makeCall: contactInfos.size() == 1.");
                // START L6: 处理同一姓名下号码 ///////////////////////////////////////////////////////
                if (contactInfos.get(0).getPhoneNums().size() == 0) {
                    List<VAActionEntityListEnum> flagList = new ArrayList<VAActionEntityListEnum>();
                    flagList.add(VAActionEntityListEnum.PLAY);
                    flagList.add(VAActionEntityListEnum.REC);
                    String botSayContext = getXmlString(R.array.contacts_name_not_find);
                    List<VAActionEntity> vaActionEntities =
                            vaFramework.getVaActionListResult(botSayContext, flagList);
                    List<VAErrorTextEntity> vaErrorTextEntities = vaFramework.getErrorTextListResult();
                    VAIntent vaIntent =
                            vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, null);
                    if (!Utilities.isNull(vaIntent)) {
                        freshData();
                        gotoL2();
                    } else {
                        vaFramework.vaExit(vaIntent, 0.5);
                    }
                } else if (contactInfos.get(0).getPhoneNums().size() == 1) {
                    // START L3: 为用户直接拨打号码 ///////////////////////////////////////////////////
                    // 为用户直接拨打号码。
                    vaFramework.getVoiceData().clearExpectedIntent();
                    vaFramework.addExpectedIntent("STOP_CALL");
                    vaFramework.addExpectedIntent("MAKE_CALL");
                    vaFramework.addExpectedIntent("COMM_COMFIRM");
                    List<VAActionEntityListEnum> flagList = new ArrayList<VAActionEntityListEnum>();
                    flagList.add(VAActionEntityListEnum.PLAY);
                    flagList.add(VAActionEntityListEnum.DISPLAY);
                    flagList.add(VAActionEntityListEnum.REC);
                    flagList.add(VAActionEntityListEnum.EXECUTION);
                    String botSayContext =
                            String.format(getXmlString(R.array.call_start),contactInfos.get(0).getPhoneNums().get(0).getPhoneNumber());
                    List<VAActionEntity> vaActionEntities =
                            vaFramework.getVaActionListResult(botSayContext, flagList);
                    List<VABuActionEntity> vaBuActionEntities =
                            vaFramework.getVaBuActionListResult("{\"actionName\":\"拨打提示\",\"num\":\""
                                    + contactInfos.get(0).getPhoneNums().get(0).getPhoneNumber() + "\"}");
                    VAIntent newVaIntent =
                            vaFramework.botSayAndListen(vaActionEntities, null, vaBuActionEntities);
                    if (!Utilities.isNull(newVaIntent)) {
                        freshData();
                        if (vaIntent.getIntentName().equals("COMM_COMFIRM")) {
                            String normalizedWord =
                                    vaFramework.getNormalizedWordSlot(vaIntent, "user_confirm_yesno");
                            if (normalizedWord.equals("否")) {
                                flagList.clear();
                                flagList.add(VAActionEntityListEnum.PLAY);
                                flagList.add(VAActionEntityListEnum.DISPLAY);
                                flagList.add(VAActionEntityListEnum.EXECUTION);
                                vaActionEntities = vaFramework.getVaActionListResult(getXmlString(R.array.stop_call), flagList);
                                List<VAErrorTextEntity> vaErrorTextEntities = vaFramework.getErrorTextListResult();
                                vaBuActionEntities =
                                        vaFramework.getVaBuActionListResult("{\"actionName\":\"挂断\"}");
                                vaFramework.botSay(vaActionEntities, vaErrorTextEntities, vaBuActionEntities);
                                vaFramework.vaExit(vaIntent, 0.5);
                            }
                        } else {
                            handleExpectedIntent();
                            vaFramework.vaExit(vaIntent, 0.5);
                        }
                    } else {
                        vaFramework.vaExit(vaIntent, 0.5);
                    }
                    // END L3 //////////////////////////////////////////////////////////////////////
                } else {
                    gotoL4(contactInfos.get(0));
                    return;
                }
                // END L6 //////////////////////////////////////////////////////////////////////////

                // 如果联系人电话号码数量大于1，并且电话类型没有被填槽
                // START L4.1: 判断是否进行电话类型词槽的填槽 //////////////////////////////////////////
                /*if (!vaFramework.isFillingSlot(vaIntent, "user_phone_type")) {
                    // START L4: 处理同一联系人电话选择 ///////////////////////////////////////////////
                    vaFramework.getVoiceData().clearExpectedIntent();
                    vaFramework.addExpectedIntent("STOP_CALL");
                    vaFramework.addExpectedIntent("MAKE_CALL");
                    vaFramework.addExpectedIntent("COMM_SELECT");
                    List<VAActionEntityListEnum> flagList = new ArrayList<VAActionEntityListEnum>();
                    flagList.add(VAActionEntityListEnum.PLAY);
                    flagList.add(VAActionEntityListEnum.DISPLAY);
                    String botSayContext = getXmlString(R.array.find_multi_phone);
                    List<VAActionEntity> vaActionEntities =
                            vaFramework.getVaActionListResult(botSayContext, flagList);
                    VAActionEntity vaActionEntity = vaFramework.getRecResult(1, 0);
                    vaActionEntities.add(vaActionEntity);
                    List<VAErrorTextEntity> vaErrorTextEntities = vaFramework.getErrorTextListResult();
                    VAIntent newVaIntent =
                            vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, null);
                    if (!Utilities.isNull(newVaIntent)) {
                        freshData();
                        if (vaIntent.getIntentName().equals("MAKE_CALL")) {
                            if (vaFramework.isFillingSlot(vaIntent, "user_phone_num")) {
                                String phoneNumFragment = vaFramework.getNormalizedWordSlot(vaIntent, "user_phone_num");
                                int phoneNumberCount = 0;
                                List<PhoneNumber> phoneNumberList = new ArrayList<PhoneNumber>();
                                for (PhoneNumber phoneNumber : contactInfos.get(0).getPhoneNums()) {
                                    if (phoneNumber.getPhoneNumber().indexOf(phoneNumFragment) != -1) {
                                        phoneNumberCount++;
                                        phoneNumberList.add(phoneNumber);
                                    }
                                }
                                ContactInfo contactInfo = new ContactInfo();
                                contactInfo.setName(contactInfos.get(0).getName());
                                contactInfo.setPhoneNums(phoneNumberList);
                                if (phoneNumberCount == 1) {
                                    gotoL3(contactInfo);
                                } else {
                                    gotoL4(contactInfo);
                                }
                            } else if (vaFramework.isFillingSlot(vaIntent, "user_phone_type")) {
                                String phoneISP =
                                        vaFramework.getNormalizedWordSlot(vaIntent, "user_phone_type");
                                int phoneNumberCount = 0;
                                List<PhoneNumber> phoneNumberList = new ArrayList<PhoneNumber>();
                                for (PhoneNumber phoneNumber : contactInfos.get(0).getPhoneNums()) {
                                    if (phoneNumber.getISP().indexOf(phoneISP) != -1) {
                                        phoneNumberCount++;
                                        phoneNumberList.add(phoneNumber);
                                    }
                                }
                                ContactInfo contactInfo = new ContactInfo();
                                contactInfo.setName(contactInfos.get(0).getName());
                                contactInfo.setPhoneNums(phoneNumberList);
                                if (phoneNumberCount == 1) {
                                    gotoL3(contactInfo);
                                } else {
                                    gotoL4(contactInfo);
                                }
                            }
                        } else if (vaIntent.getIntentName().equals("COMM_SELECT")) {
                            if (vaFramework.isFillingSlot(vaIntent, "user_item_no")) {
                                String strPhoneNumIndex =
                                        vaFramework.getNormalizedWordSlot(vaIntent, "user_item_no");
                                Integer phoneNumIndex = Integer.valueOf(strPhoneNumIndex);
                                if (phoneNumIndex > contactInfos.get(0).getPhoneNums().size()) {
                                    flagList.clear();
                                    flagList.add(VAActionEntityListEnum.PLAY);
                                    flagList.add(VAActionEntityListEnum.DISPLAY);
                                    botSayContext = getXmlString(R.array.index_not_in_list);
                                    vaActionEntities =
                                            vaFramework.getVaActionListResult(botSayContext, flagList);
                                    vaActionEntity = vaFramework.getRecResult(1, 0);
                                    vaActionEntities.add(vaActionEntity);
                                    vaErrorTextEntities = vaFramework.getErrorTextListResult();
                                    newVaIntent =
                                            vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, null);
                                    if (!Utilities.isNull(newVaIntent)) {
                                        freshData();
                                        gotoL4(contactInfos.get(0));
                                    } else {
                                        vaFramework.vaExit(vaIntent, 0.5);
                                    }
                                } else {
                                    ContactInfo contactInfo = new ContactInfo();
                                    contactInfo.setName(contactInfos.get(0).getName());
                                    List<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();
                                    PhoneNumber phoneNumber = new PhoneNumber();
                                    phoneNumber = contactInfos.get(0).getPhoneNums().get(phoneNumIndex);
                                    phoneNumbers.add(phoneNumber);
                                    contactInfo.setPhoneNums(phoneNumbers);
                                    gotoL3(contactInfo);
                                }
                            } else if (vaFramework.isFillingSlot(vaIntent, "user_page_no")) {
                                String strPhonePageIndex =
                                        vaFramework.getNormalizedWordSlot(vaIntent, "user_page_no");
                                Integer phonePageIndex = Integer.valueOf(strPhonePageIndex);
                                if (phonePageIndex > (contactInfos.get(0).getPhoneNums().size() / 3 + 1)) {
                                    flagList.clear();
                                    flagList.add(VAActionEntityListEnum.PLAY);
                                    flagList.add(VAActionEntityListEnum.DISPLAY);
                                    botSayContext = getXmlString(R.array.index_not_in_bound);
                                    vaActionEntities =
                                            vaFramework.getVaActionListResult(botSayContext, flagList);
                                    vaActionEntity = vaFramework.getRecResult(1, 0);
                                    vaActionEntities.add(vaActionEntity);
                                    vaErrorTextEntities = vaFramework.getErrorTextListResult();
                                    newVaIntent =
                                            vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, null);
                                    if (!Utilities.isNull(newVaIntent)) {
                                        freshData();
                                        gotoL4(contactInfos.get(0));
                                    } else {
                                        vaFramework.vaExit(vaIntent, 0.5);
                                    }
                                } else {
                                    ContactInfo contactInfo = new ContactInfo();
                                    contactInfo.setName(contactInfos.get(0).getName());
                                    Integer leftIndex = phonePageIndex * 3;
                                    Integer rightIndex =
                                            phonePageIndex * 3 < contactInfos.get(0).getPhoneNums().size()
                                                    ? phonePageIndex : contactInfos.get(0).getPhoneNums().size();
                                    List<PhoneNumber> phoneNumberList = new ArrayList<PhoneNumber>();
                                    for (int i = leftIndex; i < rightIndex; i++) {
                                        phoneNumberList.add(contactInfos.get(0).getPhoneNums().get(i));
                                    }
                                    contactInfo.setPhoneNums(phoneNumberList);
                                    gotoL4(contactInfo);
                                }
                            }
                            // TODO: 2/27/2019 slot_filling
                        } else {
                            handleExpectedIntent();
                            vaFramework.vaExit(vaIntent, 0.5);
                        }
                    } else {
                        vaFramework.vaExit(vaIntent, 0.5);
                    }
                    // END L4 //////////////////////////////////////////////////////////////////////
                }
                else if (vaFramework.isFillingSlot(vaIntent, "user_phone_type")) {
                    String phoneType = vaFramework.getNormalizedWordSlot(vaIntent, "user_phone_type");
                    List<PhoneNumber> phoneNumbers = contactInfoFilterByType(contactInfos.get(0), phoneType);
                    if (phoneNumbers.size() == 0) {
                        List<VAActionEntityListEnum> flagList = new ArrayList<VAActionEntityListEnum>();
                        flagList.add(VAActionEntityListEnum.PLAY);
                        flagList.add(VAActionEntityListEnum.DISPLAY);
                        flagList.add(VAActionEntityListEnum.REC);
                        String botSayContext = getXmlString(R.array.find_number_type_error);
                        List<VAActionEntity> vaActionEntities =
                                vaFramework.getVaActionListResult(botSayContext, flagList);
                        List<VAErrorTextEntity> vaErrorTextEntities = vaFramework.getErrorTextListResult();
                        VAIntent newVaIntent =
                                vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, null);
                        if (!Utilities.isNull(newVaIntent)) {
                            freshData();
                            gotoL4_1(contactInfos.get(0));
                        } else {
                            vaFramework.vaExit(vaIntent, 0.5);
                        }
                    } else if (phoneNumbers.size() == 1) {
                        ContactInfo contactInfo = new ContactInfo();
                        contactInfo.setName(contactInfos.get(0).getName());
                        contactInfo.setPhoneNums(phoneNumbers);
                        gotoL3(contactInfo);
                    } else if (phoneNumbers.size() > 1) { // 同一电话类型下面对应了多个电话号码，需要为用户展现列表，让用户选择。
                        vaFramework.getVoiceData().clearExpectedIntent();
                        vaFramework.addExpectedIntent("STOP_CALL");
                        vaFramework.addExpectedIntent("MAKE_CALL");
                        vaFramework.addExpectedIntent("COMM_SELECT");
                        List<VAActionEntityListEnum> flagList = new ArrayList<VAActionEntityListEnum>();
                        flagList.add(VAActionEntityListEnum.PLAY);
                        flagList.add(VAActionEntityListEnum.DISPLAY);
                        String botSayContext = getXmlString(R.array.find_number_multi);
                        List<VAActionEntity> vaActionEntities =
                                vaFramework.getVaActionListResult(botSayContext, flagList);
                        VAActionEntity vaActionEntity = vaFramework.getRecResult(1, 0);
                        vaActionEntities.add(vaActionEntity);
                        List<VAErrorTextEntity> vaErrorTextEntities = vaFramework.getErrorTextListResult();
                        VAIntent newVaIntent =
                                vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, null);
                        if (!Utilities.isNull(newVaIntent)) {
                            freshData();
                            if (vaIntent.getIntentName().equals("COMM_SELECT")) {
                                if (vaFramework.isFillingSlot(vaIntent, "user_item_no")) {
                                    String strPhoneNumberIndex =
                                            vaFramework.getNormalizedWordSlot(vaIntent, "user_item_no");
                                    Integer phoneNumberIndex = Integer.valueOf(strPhoneNumberIndex);
                                    if (phoneNumberIndex > phoneNumbers.size()) {
                                        flagList.clear();
                                        flagList.add(VAActionEntityListEnum.PLAY);
                                        flagList.add(VAActionEntityListEnum.DISPLAY);
                                        botSayContext = getXmlString(R.array.index_not_in_list);
                                        vaActionEntities =
                                                vaFramework.getVaActionListResult(botSayContext, flagList);
                                        vaActionEntity = vaFramework.getRecResult(1, 0);
                                        vaActionEntities.add(vaActionEntity);
                                        vaErrorTextEntities = vaFramework.getErrorTextListResult();
                                        newVaIntent =
                                                vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, null);
                                        if (!Utilities.isNull(newVaIntent)) {
                                            freshData();
                                            ContactInfo contactInfo = new ContactInfo();
                                            contactInfo.setName(contactInfos.get(0).getName());
                                            contactInfo.setPhoneNums(phoneNumbers);
                                            gotoL4(contactInfo);
                                        } else {
                                            vaFramework.vaExit(vaIntent, 0.5);
                                        }
                                    } else {
                                        ContactInfo contactInfo = new ContactInfo();
                                        contactInfo.setName(contactInfos.get(0).getName());
                                        List<PhoneNumber> phoneNumbersFilterByIndex = new ArrayList<PhoneNumber>();
                                        PhoneNumber phoneNumber = phoneNumbers.get(phoneNumberIndex);
                                        phoneNumbersFilterByIndex.add(phoneNumber);
                                        contactInfo.setPhoneNums(phoneNumbersFilterByIndex);
                                        gotoL3(contactInfo);
                                    }
                                } else if (vaFramework.isFillingSlot(vaIntent, "user_page_no")) {
                                    String strPhonePageIndex =
                                            vaFramework.getNormalizedWordSlot(vaIntent, "user_page_no");
                                    Integer phonePageIndex = Integer.valueOf(strPhonePageIndex);
                                    if (phonePageIndex > (phoneNumbers.size() / 3 + 1)) {
                                        flagList.clear();
                                        flagList.add(VAActionEntityListEnum.PLAY);
                                        flagList.add(VAActionEntityListEnum.DISPLAY);
                                        botSayContext = getXmlString(R.array.index_not_in_bound);
                                        vaActionEntities =
                                                vaFramework.getVaActionListResult(botSayContext, flagList);
                                        vaActionEntity = vaFramework.getRecResult(1, 0);
                                        vaActionEntities.add(vaActionEntity);
                                        vaErrorTextEntities = vaFramework.getErrorTextListResult();
                                        newVaIntent =
                                                vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, null);
                                        if (!Utilities.isNull(newVaIntent)) {
                                            freshData();
                                            ContactInfo contactInfo = new ContactInfo();
                                            contactInfo.setName(contactInfos.get(0).getName());
                                            contactInfo.setPhoneNums(phoneNumbers);
                                            gotoL4(contactInfo);
                                        } else {
                                            vaFramework.vaExit(vaIntent, 0.5);
                                        }
                                    } else {
                                        ContactInfo contactInfo = new ContactInfo();
                                        contactInfo.setName(contactInfos.get(0).getName());
                                        Integer leftIndex = phonePageIndex * 3;
                                        Integer rightIndex =
                                                phonePageIndex * 3 < contactInfos.get(0).getPhoneNums().size()
                                                        ? phonePageIndex : contactInfos.get(0).getPhoneNums().size();
                                        List<PhoneNumber> phoneNumberList = new ArrayList<PhoneNumber>();
                                        for (int i = leftIndex; i < rightIndex; i++) {
                                            phoneNumberList.add(contactInfos.get(0).getPhoneNums().get(i));
                                        }
                                        contactInfo.setPhoneNums(phoneNumberList);
                                        gotoL4(contactInfo);
                                    }
                                }
                            } else if (vaIntent.getIntentName().equals("MAKE_CALL")) {
                                if (vaFramework.isFillingSlot(vaIntent, "user_phone_num")) {
                                    String phoneNumFragment = vaFramework.getNormalizedWordSlot(vaIntent, "user_phone_num");
                                    int phoneNumberCount = 0;
                                    List<PhoneNumber> phoneNumberList = new ArrayList<PhoneNumber>();
                                    for (PhoneNumber phoneNumber : phoneNumbers) {
                                        if (phoneNumber.getPhoneNumber().indexOf(phoneNumFragment) != -1) {
                                            phoneNumberCount++;
                                            phoneNumberList.add(phoneNumber);
                                        }
                                    }
                                    ContactInfo contactInfo = new ContactInfo();
                                    contactInfo.setName(contactInfos.get(0).getName());
                                    contactInfo.setPhoneNums(phoneNumberList);
                                    if (phoneNumberCount == 1) {
                                        gotoL3(contactInfo);
                                    } else {
                                        gotoL4(contactInfo);
                                    }
                                }
                            } else {
                                handleExpectedIntent();
                                vaFramework.vaExit(vaIntent, 0.5);
                            }
                            // TODO: 2/27/2019 slot_filling
                        }
                    }
                }*/
                // END L4.1 ////////////////////////////////////////////////////////////////////////
            }
            else if (contactInfos.size() > 1) {
                // START L5: 处理相同姓名的多匹配 /////////////////////////////////////////////////////
                vaFramework.getVoiceData().clearExpectedIntent();
                vaFramework.addExpectedIntent("STOP_CALL");
                vaFramework.addExpectedIntent("CONFIRM_CALL");
                vaFramework.addExpectedIntent("MAKE_CALL");
                vaFramework.addExpectedIntent("COMM_SELECT");// PHONE_SELECT_ITEM
                List<VAActionEntityListEnum> flagList = new ArrayList<VAActionEntityListEnum>();
                flagList.add(VAActionEntityListEnum.PLAY);
                flagList.add(VAActionEntityListEnum.DISPLAY);
                String botSayContext = getXmlString(R.array.find_number_multi_in_one_name);
                List<VAActionEntity> vaActionEntities =
                        vaFramework.getVaActionListResult(botSayContext, flagList);
                VAActionEntity vaActionEntity = vaFramework.getRecResult(1, 0);
                vaActionEntities.add(vaActionEntity);
                List<VAErrorTextEntity> vaErrorTextEntities = vaFramework.getErrorTextListResult();

                JSONArray texts = new JSONArray();
                List<String> pickuplist = new ArrayList<>();
                for (ContactInfo ci : contactInfos) {
                    JSONObject o = new JSONObject();
                    o.put("name", ci.getName());
                    o.put("number", ci.getPhoneNums().get(0).getPhoneNumber());
                    texts.put(o);
                    pickuplist.add(ci.getName());
                }
                List<VABuActionEntity> vaBuActionEntitiesx =
                        vaFramework.getVaBuActionListResult(texts.toString());
                vaBuActionEntitiesx.get(0).setAppName("showContacts");
                vaFramework.setPickuplist(pickuplist);
                VAIntent newVaIntent =
                        vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, vaBuActionEntitiesx);
                JSONObject pickupInfo = vaFramework.getVoiceData().getPickupInfo();
                if (pickupInfo != null){
                    String text = pickupInfo.optString("original_text");
                    if (!text.isEmpty()){
                        int idx = pickupInfo.optInt("item_index");
                        if (idx > contactInfos.size() || idx < 0) {
                            flagList.clear();
                            flagList.add(VAActionEntityListEnum.PLAY);
                            flagList.add(VAActionEntityListEnum.DISPLAY);
                            botSayContext = getXmlString(R.array.not_find_contacts_index);
                            vaActionEntities =
                                    vaFramework.getVaActionListResult(botSayContext, flagList);
                            vaActionEntity = vaFramework.getRecResult(1, 0);
                            vaActionEntities.add(vaActionEntity);
                            vaErrorTextEntities = vaFramework.getErrorTextListResult();
                            newVaIntent =
                                    vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, null);
                            if (!Utilities.isNull(newVaIntent)) {
                                freshData();
                                gotoL5(contactInfos);
                            } else {
                                vaFramework.vaExit(vaIntent, 0.5);
                            }
                        } else {
                            ContactInfo contactInfo = contactInfos.get(idx);
                            if (contactInfo.getPhoneNums().size() == 1) {
                                gotoL3(contactInfo);
                            } else if (contactInfo.getPhoneNums().size() > 1) {
                                gotoL4(contactInfo);
                            } else {
                                vaFramework.vaExit(vaIntent, 0.5);
                            }

                        }
                    }
                } else if (!Utilities.isNull(newVaIntent)) {
                    freshData();
                    if (vaIntent.getIntentName().equals("CONFIRM_CALL")) {
                        if (vaFramework.isFillingSlot(vaIntent, "user_confirm_yesno")) {
                            String normalizedWord =
                                    vaFramework.getNormalizedWordSlot(vaIntent, "user_confirm_yesno");
                            if (normalizedWord.equals("是")) {
                                flagList.clear();
                                flagList.add(VAActionEntityListEnum.PLAY);
                                botSayContext = getXmlString(R.array.contacts_not_clear);
                                vaActionEntities =
                                        vaFramework.getVaActionListResult(botSayContext, flagList);
                                vaActionEntity = vaFramework.getRecResult(1, 0);
                                vaActionEntities.add(vaActionEntity);
                                vaErrorTextEntities = vaFramework.getErrorTextListResult();
                                newVaIntent =
                                        vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, null);
                                if (!Utilities.isNull(newVaIntent)) {
                                    freshData();
                                    gotoL5(contactInfos);
                                } else {
                                    vaFramework.vaExit(vaIntent, 0.5);
                                }
                            } else if (normalizedWord.equals("否")) {
                                flagList.clear();
                                flagList.add(VAActionEntityListEnum.PLAY);
                                flagList.add(VAActionEntityListEnum.DISPLAY);
                                flagList.add(VAActionEntityListEnum.EXECUTION);
                                botSayContext = getXmlString(R.array.stop_call);
                                vaActionEntities = vaFramework.getVaActionListResult(botSayContext, flagList);
                                List<VABuActionEntity> vaBuActionEntities =
                                        vaFramework.getVaBuActionListResult("{\"actionName\":\"挂断\"}");
                                vaErrorTextEntities = vaFramework.getErrorTextListResult();
                                vaFramework.botSay(vaActionEntities, vaErrorTextEntities, vaBuActionEntities);
                                vaFramework.vaExit(vaIntent, 0.5);
                            }
                        }
                    } else if (vaIntent.getIntentName().equals("COMM_SELECT")) {
                        String strContactIndex = vaFramework.getNormalizedWordSlot(vaIntent, "user_num");

                        Integer contactIndex = toIndex(strContactIndex);
                        if (contactIndex > contactInfos.size()) {
                            flagList.clear();
                            flagList.add(VAActionEntityListEnum.PLAY);
                            flagList.add(VAActionEntityListEnum.DISPLAY);
                            botSayContext = getXmlString(R.array.not_find_contacts_index);
                            vaActionEntities =
                                    vaFramework.getVaActionListResult(botSayContext, flagList);
                            vaActionEntity = vaFramework.getRecResult(1, 0);
                            vaActionEntities.add(vaActionEntity);
                            vaErrorTextEntities = vaFramework.getErrorTextListResult();
                            newVaIntent =
                                    vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, null);
                            if (!Utilities.isNull(newVaIntent)) {
                                freshData();
                                gotoL5(contactInfos);
                            } else {
                                vaFramework.vaExit(vaIntent, 0.5);
                            }
                        } else {
                            ContactInfo contactInfo = contactInfos.get(contactIndex);
                            if (contactInfo.getPhoneNums().size() == 1) {
                                gotoL3(contactInfo);
                            } else if (contactInfo.getPhoneNums().size() > 1) {
                                gotoL4(contactInfo);
                            } else {
                                vaFramework.vaExit(vaIntent, 0.5);
                            }
                        }
                    } else {
                        handleExpectedIntent();
                        vaFramework.vaExit(vaIntent, 0.5);
                    }
                } else {
                    vaFramework.vaExit(vaIntent, 0.5);
                }
                // END L5 //////////////////////////////////////////////////////////////////////////
            }
            else {
                sLog.i(LOG_TAG, "联系人为 0");
                List<VAActionEntityListEnum> flagList = new ArrayList<VAActionEntityListEnum>();
                flagList.add(VAActionEntityListEnum.PLAY);
                flagList.add(VAActionEntityListEnum.DISPLAY);
                flagList.add(VAActionEntityListEnum.REC);
                String botSayContext = getXmlString(R.array.contacts_name_not_find);
                List<VAActionEntity> vaActionEntities =
                        vaFramework.getVaActionListResult(botSayContext, flagList);
                List<VAErrorTextEntity> vaErrorTextEntities = vaFramework.getErrorTextListResult();
                List<VABuActionEntity> vaBuActionEntities =
                        vaFramework.getVaBuActionListResult("{\"actionName\":\"挂断\"}");
                vaFramework.botSay(vaActionEntities, vaErrorTextEntities, vaBuActionEntities);
                vaFramework.vaExit(vaIntent, 0.5);
            }
            // TODO: 2/28/2019 联系人搜索结果为零的情况不考虑。
            // END L7 //////////////////////////////////////////////////////////////////////////////
        }
    }

    public void gotoL0() throws Exception {
        // 如果本次意图中对“电话号码”进行了填槽。
        if (vaFramework.isFillingSlot(vaIntent, "user_phone_num")) {
            // 获取电话号码
            String phoneNumber = vaFramework.getNormalizedWordSlot(vaIntent, "user_phone_num");
            // START L1.1: 判断电话是否完整 //////////////////////////////////////////////////////////
            if (isPhoneNumComplete(phoneNumber)) {
                sLog.i(LOG_TAG, "makeCall: isPhoneNumComplete.");
                // TODO: 2/21/2019 实现本地数据库。
                if (isPhoneNumPrevCalled(phoneNumber)) {
                    // START L1: 直接为用户拨打 //////////////////////////////////////////////////////
                    gotoL1(phoneNumber);
                    // END L1 //////////////////////////////////////////////////////////////////////
                } else { // 这个电话以前没有被拨打过
                    sLog.i(LOG_TAG, "makeCall: !isPhoneNumPrevCalled.");
                    vaFramework.getVoiceData().clearExpectedIntent();
                    vaFramework.addExpectedIntent("COMM_COMFIRM");
                    vaFramework.addExpectedIntent("STOP_CALL");
                    vaFramework.addExpectedIntent("PHONE_NUM_MODIFY");
                    vaFramework.addExpectedIntent("MAKE_CALL");
                    vaFramework.getVoiceData().setPhoneNumber(phoneNumber);
                    List<VAActionEntityListEnum> flagList = new ArrayList<VAActionEntityListEnum>();
                    flagList.add(VAActionEntityListEnum.PLAY);
                    flagList.add(VAActionEntityListEnum.DISPLAY);
                    flagList.add(VAActionEntityListEnum.REC);
                    flagList.add(VAActionEntityListEnum.EXECUTION);
                    String botSayContext = String.format(getXmlString(R.array.ask_for_call),phoneNumber);
                    List<VAActionEntity> vaActionEntities =
                            vaFramework.getVaActionListResult(botSayContext, flagList);
                    List<VABuActionEntity> vaBuActionEntities =
                            vaFramework.getVaBuActionListResult("{\"actionName\":\"询问\",\"num\":\"" + phoneNumber + "\"}");
                    List<VAErrorTextEntity> vaErrorTextEntities = vaFramework.getErrorTextListResult();
                    VAIntent newVaIntent =
                            vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, vaBuActionEntities);
                    if (!Utilities.isNull(newVaIntent)) {
                        freshData();
                        if (vaIntent.getIntentName().equals("COMM_COMFIRM")) {
                            String normalizedWord = vaFramework.getNormalizedWordSlot(vaIntent, "user_confirm_yesno");
                            if (normalizedWord.equals("是")) {
                                gotoL1(phoneNumber);
                            } else if (normalizedWord.equals("否")) {
                                flagList.clear();
                                flagList.add(VAActionEntityListEnum.PLAY);
                                flagList.add(VAActionEntityListEnum.DISPLAY);
                                flagList.add(VAActionEntityListEnum.EXECUTION);
                                botSayContext = getXmlString(R.array.stop_call);
                                vaActionEntities = vaFramework.getVaActionListResult(botSayContext, flagList);
                                vaBuActionEntities = vaFramework.getVaBuActionListResult("{\"actionName\":\"挂断\"}");
                                vaErrorTextEntities = vaFramework.getErrorTextListResult();
                                vaFramework.botSay(vaActionEntities, vaErrorTextEntities, vaBuActionEntities);
                                vaFramework.vaExit(vaIntent, 0.5);
                            }
                        } else {
                            handleExpectedIntent();
                            vaFramework.vaExit(vaIntent, 0.5);
                        }
                        // TODO: 2/27/2019 slot_filling
                    } else {
                        addPhoneNum2PrevCalled(phoneNumber);
                        vaFramework.vaExit(vaIntent, 0.5);
                    }
                }
            } else { // 电话号码不符合系统设定的规范
                sLog.i(LOG_TAG, "makeCall: !isPhoneNumComplete.");
                vaFramework.getVoiceData().clearExpectedIntent();
                vaFramework.addExpectedIntent("COMM_COMFIRM");
                vaFramework.addExpectedIntent("STOP_CALL");
                vaFramework.addExpectedIntent("PHONE_NUM_MODIFY");
                vaFramework.addExpectedIntent("MAKE_CALL");
                vaFramework.getVoiceData().setPhoneNumber(phoneNumber);
                List<VAActionEntityListEnum> flagList = new ArrayList<VAActionEntityListEnum>();
                flagList.add(VAActionEntityListEnum.PLAY);
                flagList.add(VAActionEntityListEnum.DISPLAY);
                String botSayContext = getXmlString(R.array.say_number);
                List<VAActionEntity> vaActionEntities = vaFramework.getVaActionListResult(botSayContext, flagList);
                VAActionEntity vaActionEntity = vaFramework.getRecResult(1, 0);
                vaActionEntities.add(vaActionEntity);
                List<VAErrorTextEntity> vaErrorTextEntities = vaFramework.getErrorTextListResult();
                VAIntent newVaIntent = vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, null);
                if (!Utilities.isNull(newVaIntent)) {
                    freshData();
                    if (vaIntent.getIntentName().equals("COMM_COMFIRM")) {
                        String normalizedWord = vaFramework.getNormalizedWordSlot(vaIntent, "user_confirm_yesno");
                        if (normalizedWord.equals("是")) {
                            gotoL1(phoneNumber);
                        } else if (normalizedWord.equals("否")) {
                            flagList.clear();
                            flagList.add(VAActionEntityListEnum.PLAY);
                            flagList.add(VAActionEntityListEnum.DISPLAY);
                            flagList.add(VAActionEntityListEnum.EXECUTION);
                            vaActionEntities = vaFramework.getVaActionListResult(getXmlString(R.array.stop_call), flagList);
                            vaErrorTextEntities = vaFramework.getErrorTextListResult();
                            List<VABuActionEntity> vaBuActionEntities =
                                    vaFramework.getVaBuActionListResult("{\"actionName\":\"挂断\"}");
                            vaFramework.botSay(vaActionEntities, vaErrorTextEntities, vaBuActionEntities);
                            vaFramework.vaExit(vaIntent, 0.5);
                        }
                    } else {
                        handleExpectedIntent();
                        vaFramework.vaExit(vaIntent, 0.5);
                    }
                    // TODO: 2/27/2019 slot_filling
                } else {
                    vaFramework.vaExit(vaIntent, 0.5);
                }
            }
            // END L1.1 ////////////////////////////////////////////////////////////////////////////
        } else { // 如果本次意图没有中对“电话号码”进行了填槽。
            // START L2: 等待姓名槽位有值后拨打 ///////////////////////////////////////////////////////
            gotoL2();
            // END L2 //////////////////////////////////////////////////////////////////////////////
        }
    }

    public void gotoL1(String phoneNumber) throws Exception {
        sLog.i(LOG_TAG, "L1: phoneNumber: " + phoneNumber);
        sLog.i(LOG_TAG, "makeCall: isPhoneNumPrevCalled.");
        vaFramework.getVoiceData().clearExpectedIntent();
        vaFramework.addExpectedIntent("STOP_CALL");
        vaFramework.addExpectedIntent("MAKE_CALL");
        vaFramework.addExpectedIntent("PHONE_NUM_MODIFY");
        vaFramework.getVoiceData().setPhoneNumber(phoneNumber);
        List<VAActionEntityListEnum> flagList = new ArrayList<VAActionEntityListEnum>();
        flagList.add(VAActionEntityListEnum.PLAY);
        flagList.add(VAActionEntityListEnum.DISPLAY);
        flagList.add(VAActionEntityListEnum.REC);
        flagList.add(VAActionEntityListEnum.EXECUTION);
        String botSayContext = String.format(getXmlString(R.array.call_wait_close),phoneNumber);
        List<VAActionEntity> vaActionEntities =
                vaFramework.getVaActionListResult(botSayContext, flagList);
        List<VABuActionEntity> vaBuActionEntities =
                vaFramework.getVaBuActionListResult("{\"actionName\":\"拨打提示\",\"num\":\"" + phoneNumber + "\"}");
        VAIntent newVaIntent = vaFramework.botSayAndListen(vaActionEntities, null, vaBuActionEntities);
        if (!Utilities.isNull(newVaIntent)) {
            freshData();
            handleExpectedIntent();
            vaFramework.vaExit(vaIntent, 0.5);
        } else {
            vaFramework.vaExit(vaIntent, 0.5);
        }
    }

    public void gotoL2() throws Exception {
        if (!vaFramework.isFillingSlot(vaIntent, "user_name")) { // 处理姓名相关的场景
            vaFramework.getVoiceData().clearExpectedIntent();
            vaFramework.addExpectedIntent("COMM_COMFIRM");
            vaFramework.addExpectedIntent("STOP_CALL");
            vaFramework.addExpectedIntent("MAKE_CALL");
            List<VAActionEntityListEnum> flagList = new ArrayList<VAActionEntityListEnum>();
            flagList.add(VAActionEntityListEnum.PLAY);
            flagList.add(VAActionEntityListEnum.DISPLAY);
            flagList.add(VAActionEntityListEnum.REC);
            flagList.add(VAActionEntityListEnum.EXECUTION);
            String botSayContext = getXmlString(R.array.say_contacts_name);
            List<VAActionEntity> vaActionEntities =
                    vaFramework.getVaActionListResult(botSayContext, flagList);
            List<VABuActionEntity> vaBuActionEntities =
                    vaFramework.getVaBuActionListResult("{\"actionName\":\"询问\"");
            List<VAErrorTextEntity> vaErrorTextEntities = vaFramework.getErrorTextListResult();
            VAIntent newVaIntent =
                    vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, vaBuActionEntities);
            if (!Utilities.isNull(newVaIntent)) {
                freshData();
                if (vaIntent.getIntentName().equals("COMM_COMFIRM")) {
                    String normalizedWord = vaFramework.getNormalizedWordSlot(vaIntent, "user_confirm_yesno");
                    if (normalizedWord.equals("是")) {
                        flagList.clear();
                        flagList.add(VAActionEntityListEnum.PLAY);
                        flagList.add(VAActionEntityListEnum.DISPLAY);
                        flagList.add(VAActionEntityListEnum.REC);
                        flagList.add(VAActionEntityListEnum.EXECUTION);
                        botSayContext = getXmlString(R.array.call_stop_no_name);
                        vaActionEntities = vaFramework.getVaActionListResult(botSayContext, flagList);
                        vaBuActionEntities = vaFramework.getVaBuActionListResult("{\"actionName\":\"询问\"");
                        vaErrorTextEntities = vaFramework.getErrorTextListResult();
                        newVaIntent = vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, vaBuActionEntities);
                        if (!Utilities.isNull(newVaIntent)) {
                            freshData();
                            gotoL2();
                        } else {
                            vaFramework.vaExit(vaIntent, 0.5);
                        }
                    } else if (normalizedWord.equals("否")) {
                        flagList.clear();
                        flagList.add(VAActionEntityListEnum.PLAY);
                        flagList.add(VAActionEntityListEnum.DISPLAY);
                        botSayContext = getXmlString(R.array.stop_session);
                        vaActionEntities = vaFramework.getVaActionListResult(botSayContext, flagList);
                        vaFramework.botSay(vaActionEntities, null, null);
                        vaFramework.vaExit(vaIntent, 0.5);
                    }
                } else {
                    handleExpectedIntent();
                    vaFramework.vaExit(vaIntent, 0.5);
                }
            } else {
                flagList.clear();
                flagList.add(VAActionEntityListEnum.PLAY);
                flagList.add(VAActionEntityListEnum.DISPLAY);
                flagList.add(VAActionEntityListEnum.REC);
                flagList.add(VAActionEntityListEnum.EXECUTION);
                botSayContext = getXmlString(R.array.call_need_contacts_name);
                vaActionEntities = vaFramework.getVaActionListResult(botSayContext, flagList);
                vaBuActionEntities = vaFramework.getVaBuActionListResult("{\"actionName\":\"询问\"");
                vaErrorTextEntities = vaFramework.getErrorTextListResult();
                newVaIntent = vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, vaBuActionEntities);
                if (!Utilities.isNull(newVaIntent)) {
                    freshData();
                    gotoL2();
                } else {
                    vaFramework.vaExit(vaIntent, 0.5);
                }
            }
        }// 此时 vaIntent 中已经对姓名进行了填槽。
    }

    public void gotoL3(ContactInfo contactInfo) throws Exception {
        // 为用户直接拨打号码。
        vaFramework.getVoiceData().clearExpectedIntent();
        vaFramework.addExpectedIntent("STOP_CALL");
        vaFramework.addExpectedIntent("MAKE_CALL");
        vaFramework.addExpectedIntent("COMM_COMFIRM");
        List<VAActionEntityListEnum> flagList = new ArrayList<VAActionEntityListEnum>();
        flagList.add(VAActionEntityListEnum.PLAY);
        flagList.add(VAActionEntityListEnum.DISPLAY);
        flagList.add(VAActionEntityListEnum.REC);
        flagList.add(VAActionEntityListEnum.EXECUTION);
        String botSayContext = String.format(getXmlString(R.array.call_start),contactInfo.getPhoneNums().get(0).getPhoneNumber());
        List<VAActionEntity> vaActionEntities =
                vaFramework.getVaActionListResult(botSayContext, flagList);
        List<VABuActionEntity> vaBuActionEntities =
                vaFramework.getVaBuActionListResult("{\"actionName\":\"拨打提示\",\"num\":\""
                        + contactInfo.getPhoneNums().get(0).getPhoneNumber() + "\"}");
        VAIntent newVaIntent =
                vaFramework.botSayAndListen(vaActionEntities, null, vaBuActionEntities);
        if (!Utilities.isNull(newVaIntent)) {
            freshData();
            if (vaIntent.getIntentName().equals("COMM_COMFIRM")) {
                String normalizedWord =
                        vaFramework.getNormalizedWordSlot(vaIntent, "user_confirm_yesno");
                if (normalizedWord.equals("否")) {
                    flagList.clear();
                    flagList.add(VAActionEntityListEnum.PLAY);
                    flagList.add(VAActionEntityListEnum.DISPLAY);
                    flagList.add(VAActionEntityListEnum.EXECUTION);
                    vaActionEntities = vaFramework.getVaActionListResult(getXmlString(R.array.stop_call), flagList);
                    List<VAErrorTextEntity> vaErrorTextEntities = vaFramework.getErrorTextListResult();
                    vaBuActionEntities =
                            vaFramework.getVaBuActionListResult("{\"actionName\":\"挂断\"}");
                    vaFramework.botSay(vaActionEntities, vaErrorTextEntities, vaBuActionEntities);
                    vaFramework.vaExit(vaIntent, 0.5);
                }
            } else {
                handleExpectedIntent();
                vaFramework.vaExit(vaIntent, 0.5);
            }
        } else {
            vaFramework.vaExit(vaIntent, 0.5);
        }
    }

    public void gotoL4(ContactInfo contactInfoIn) throws Exception {
        vaFramework.getVoiceData().clearExpectedIntent();
        vaFramework.addExpectedIntent("STOP_CALL");
        vaFramework.addExpectedIntent("MAKE_CALL");
        vaFramework.addExpectedIntent("COMM_SELECT");
        List<VAActionEntityListEnum> flagList = new ArrayList<VAActionEntityListEnum>();
        flagList.add(VAActionEntityListEnum.PLAY);
        flagList.add(VAActionEntityListEnum.DISPLAY);
        String botSayContext = getXmlString(R.array.find_multi_phone);
        List<VAActionEntity> vaActionEntities =
                vaFramework.getVaActionListResult(botSayContext, flagList);
        VAActionEntity vaActionEntity = vaFramework.getRecResult(1, 0);
        vaActionEntities.add(vaActionEntity);
        List<VAErrorTextEntity> vaErrorTextEntities = vaFramework.getErrorTextListResult();
        List<String> pickuplist = new ArrayList<>();
        JSONArray texts = new JSONArray();
        for (PhoneNumber pn : contactInfoIn.getPhoneNums()) {
            JSONObject o = new JSONObject();
            o.put("name", pn.getPhoneNumber());
            //o.put("number", ci.getPhoneNums().get(0).getPhoneNumber());
            texts.put(o);
            pickuplist.add(pn.getPhoneNumber());
        }
        List<VABuActionEntity> vaBuActionEntitiesx =
                vaFramework.getVaBuActionListResult(texts.toString());
        vaBuActionEntitiesx.get(0).setAppName("showContacts");
        vaFramework.setPickuplist(pickuplist);
        VAIntent newVaIntent =
                vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, vaBuActionEntitiesx);
        JSONObject pickupInfo = vaFramework.getVoiceData().getPickupInfo();
        if (pickupInfo != null){
            String text = pickupInfo.optString("original_text");
            if (!text.isEmpty()){
                int idx = pickupInfo.optInt("item_index");
                if (idx > contactInfoIn.getPhoneNums().size() || idx < 0) {
                    flagList.clear();
                    flagList.add(VAActionEntityListEnum.PLAY);
                    flagList.add(VAActionEntityListEnum.DISPLAY);
                    botSayContext = getXmlString(R.array.not_find_contacts_index);
                    vaActionEntities =
                            vaFramework.getVaActionListResult(botSayContext, flagList);
                    vaActionEntity = vaFramework.getRecResult(1, 0);
                    vaActionEntities.add(vaActionEntity);
                    vaErrorTextEntities = vaFramework.getErrorTextListResult();
                    newVaIntent =
                            vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, null);
                    if (!Utilities.isNull(newVaIntent)) {
                        freshData();
                        gotoL4(contactInfoIn);
                    } else {
                        vaFramework.vaExit(vaIntent, 0.5);
                    }
                } else {
                    ContactInfo contactInfo = new ContactInfo();
                    contactInfo.setName(contactInfoIn.getName());
                    List<PhoneNumber> phoneNumberList = new ArrayList<PhoneNumber>();
                    phoneNumberList.add(contactInfoIn.getPhoneNums().get(idx));
                    contactInfo.setPhoneNums(phoneNumberList);
                    gotoL3(contactInfo);
                }
            }
        } else if (!Utilities.isNull(newVaIntent)) {
            freshData();
            if (vaIntent.getIntentName().equals("MAKE_CALL")) {
                if (vaFramework.isFillingSlot(vaIntent, "user_phone_num")) {
                    String phoneNumFragment = vaFramework.getNormalizedWordSlot(vaIntent, "user_phone_num");
                    int phoneNumberCount = 0;
                    List<PhoneNumber> phoneNumberList = new ArrayList<PhoneNumber>();
                    for (PhoneNumber phoneNumber : contactInfoIn.getPhoneNums()) {
                        if (phoneNumber.getPhoneNumber().indexOf(phoneNumFragment) != -1) {
                            phoneNumberCount++;
                            phoneNumberList.add(phoneNumber);
                        }
                    }
                    ContactInfo contactInfo = new ContactInfo();
                    contactInfo.setName(contactInfoIn.getName());
                    contactInfo.setPhoneNums(phoneNumberList);
                    if (phoneNumberCount == 1) {
                        gotoL3(contactInfo);
                    } else {
                        gotoL4(contactInfo);
                    }
                } else if (vaFramework.isFillingSlot(vaIntent, "user_phone_type")) {
                    String phoneISP =
                            vaFramework.getNormalizedWordSlot(vaIntent, "user_phone_type");
                    int phoneNumberCount = 0;
                    List<PhoneNumber> phoneNumberList = new ArrayList<PhoneNumber>();
                    for (PhoneNumber phoneNumber : contactInfoIn.getPhoneNums()) {
                        if (phoneNumber.getISP().indexOf(phoneISP) != -1) {
                            phoneNumberCount++;
                            phoneNumberList.add(phoneNumber);
                        }
                    }
                    ContactInfo contactInfo = new ContactInfo();
                    contactInfo.setName(contactInfoIn.getName());
                    contactInfo.setPhoneNums(phoneNumberList);
                    if (phoneNumberCount == 1) {
                        gotoL3(contactInfo);
                    } else {
                        gotoL4(contactInfo);
                    }
                }
            }
            /*else if (vaIntent.getIntentName().equals("COMM_SELECT")) {
                if (vaFramework.isFillingSlot(vaIntent, "user_item_no")) {
                    String strPhoneNumIndex =
                            vaFramework.getNormalizedWordSlot(vaIntent, "user_item_no");
                    Integer phoneNumIndex = Integer.valueOf(strPhoneNumIndex);
                    if (phoneNumIndex > contactInfoIn.getPhoneNums().size()) {
                        flagList.clear();
                        flagList.add(VAActionEntityListEnum.PLAY);
                        flagList.add(VAActionEntityListEnum.DISPLAY);
                        botSayContext = getXmlString(R.array.index_not_in_list);
                        vaActionEntities =
                                vaFramework.getVaActionListResult(botSayContext, flagList);
                        vaActionEntity = vaFramework.getRecResult(1, 0);
                        vaActionEntities.add(vaActionEntity);
                        vaErrorTextEntities = vaFramework.getErrorTextListResult();
                        newVaIntent =
                                vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, null);
                        if (!Utilities.isNull(newVaIntent)) {
                            freshData();
                            gotoL4(contactInfoIn);
                        } else {
                            vaFramework.vaExit(vaIntent, 0.5);
                        }
                    } else {
                        ContactInfo contactInfo = new ContactInfo();
                        contactInfo.setName(contactInfoIn.getName());
                        List<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();
                        PhoneNumber phoneNumber = new PhoneNumber();
                        phoneNumber = contactInfoIn.getPhoneNums().get(phoneNumIndex);
                        phoneNumbers.add(phoneNumber);
                        contactInfo.setPhoneNums(phoneNumbers);
                        gotoL3(contactInfo);
                    }
                } else if (vaFramework.isFillingSlot(vaIntent, "user_page_no")) {
                    String strPhonePageIndex =
                            vaFramework.getNormalizedWordSlot(vaIntent, "user_page_no");
                    Integer phonePageIndex = Integer.valueOf(strPhonePageIndex);
                    if (phonePageIndex > (contactInfoIn.getPhoneNums().size() / 3 + 1)) {
                        flagList.clear();
                        flagList.add(VAActionEntityListEnum.PLAY);
                        flagList.add(VAActionEntityListEnum.DISPLAY);
                        botSayContext = getXmlString(R.array.index_not_in_bound);
                        vaActionEntities =
                                vaFramework.getVaActionListResult(botSayContext, flagList);
                        vaActionEntity = vaFramework.getRecResult(1, 0);
                        vaActionEntities.add(vaActionEntity);
                        vaErrorTextEntities = vaFramework.getErrorTextListResult();
                        newVaIntent =
                                vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, null);
                        if (!Utilities.isNull(newVaIntent)) {
                            freshData();
                            gotoL4(contactInfoIn);
                        } else {
                            vaFramework.vaExit(vaIntent, 0.5);
                        }
                    } else {
                        ContactInfo contactInfo = new ContactInfo();
                        contactInfo.setName(contactInfoIn.getName());
                        Integer leftIndex = phonePageIndex * 3;
                        Integer rightIndex =
                                phonePageIndex * 3 < contactInfoIn.getPhoneNums().size()
                                        ? phonePageIndex : contactInfoIn.getPhoneNums().size();
                        List<PhoneNumber> phoneNumberList = new ArrayList<PhoneNumber>();
                        for (int i = leftIndex; i < rightIndex; i++) {
                            phoneNumberList.add(contactInfoIn.getPhoneNums().get(i));
                        }
                        contactInfo.setPhoneNums(phoneNumberList);
                        gotoL4(contactInfo);
                    }
                }
                // TODO: 2/27/2019 slot_filling
            }  */
            else if (vaIntent.getIntentName().equals("COMM_SELECT")) {
                String strPhoneNumIndex = vaFramework.getNormalizedWordSlot(vaIntent, "user_num");

                Integer phoneNumIndex = toIndex(strPhoneNumIndex);
                if (phoneNumIndex > contactInfoIn.getPhoneNums().size()) {
                    flagList.clear();
                    flagList.add(VAActionEntityListEnum.PLAY);
                    flagList.add(VAActionEntityListEnum.DISPLAY);
                    botSayContext = getXmlString(R.array.index_not_in_bound);
                    vaActionEntities =
                            vaFramework.getVaActionListResult(botSayContext, flagList);
                    vaActionEntity = vaFramework.getRecResult(1, 0);
                    vaActionEntities.add(vaActionEntity);
                    vaErrorTextEntities = vaFramework.getErrorTextListResult();
                    newVaIntent =
                            vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, null);
                    if (!Utilities.isNull(newVaIntent)) {
                        freshData();
                        gotoL4(contactInfoIn);
                    } else {
                        vaFramework.vaExit(vaIntent, 0.5);
                    }
                } else {
                    ContactInfo contactInfo = new ContactInfo();
                    contactInfo.setName(contactInfoIn.getName());
                    List<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();
                    PhoneNumber phoneNumber = new PhoneNumber();
                    phoneNumber = contactInfoIn.getPhoneNums().get(phoneNumIndex);
                    phoneNumbers.add(phoneNumber);
                    contactInfo.setPhoneNums(phoneNumbers);
                    gotoL3(contactInfo);
                }
            } else {
                handleExpectedIntent();
                vaFramework.vaExit(vaIntent, 0.5);
            }
        } else {
            vaFramework.vaExit(vaIntent, 0.5);
        }
    }

    public void gotoL4_1(ContactInfo contactInfoIn) throws Exception {
        if (!vaFramework.isFillingSlot(vaIntent, "user_phone_type")) {
            // START L4: 处理同一联系人电话选择 ///////////////////////////////////////////////
            gotoL4(contactInfoIn);
            // END L4 //////////////////////////////////////////////////////////////////////
        } else if (vaFramework.isFillingSlot(vaIntent, "user_phone_type")) {
            String phoneType = vaFramework.getNormalizedWordSlot(vaIntent, "user_phone_type");
            List<PhoneNumber> phoneNumbers = contactInfoFilterByType(contactInfoIn, phoneType);
            if (phoneNumbers.size() == 0) {
                List<VAActionEntityListEnum> flagList = new ArrayList<VAActionEntityListEnum>();
                flagList.add(VAActionEntityListEnum.PLAY);
                flagList.add(VAActionEntityListEnum.DISPLAY);
                flagList.add(VAActionEntityListEnum.REC);
                String botSayContext = getXmlString(R.array.find_number_type_error);
                List<VAActionEntity> vaActionEntities =
                        vaFramework.getVaActionListResult(botSayContext, flagList);
                List<VAErrorTextEntity> vaErrorTextEntities = vaFramework.getErrorTextListResult();
                VAIntent newVaIntent =
                        vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, null);
                if (!Utilities.isNull(newVaIntent)) {
                    freshData();
                    gotoL4_1(contactInfoIn);
                } else {
                    vaFramework.vaExit(vaIntent, 0.5);
                }
            } else if (phoneNumbers.size() == 1) {
                ContactInfo contactInfo = new ContactInfo();
                contactInfo.setName(contactInfoIn.getName());
                contactInfo.setPhoneNums(phoneNumbers);
                gotoL3(contactInfo);
            } else if (phoneNumbers.size() > 1) { // 同一电话类型下面对应了多个电话号码，需要为用户展现列表，让用户选择。
                vaFramework.getVoiceData().clearExpectedIntent();
                vaFramework.addExpectedIntent("STOP_CALL");
                vaFramework.addExpectedIntent("MAKE_CALL");
                vaFramework.addExpectedIntent("COMM_SELECT");
                List<VAActionEntityListEnum> flagList = new ArrayList<VAActionEntityListEnum>();
                flagList.add(VAActionEntityListEnum.PLAY);
                flagList.add(VAActionEntityListEnum.DISPLAY);
                String botSayContext = getXmlString(R.array.find_number_multi);
                List<VAActionEntity> vaActionEntities =
                        vaFramework.getVaActionListResult(botSayContext, flagList);
                VAActionEntity vaActionEntity = vaFramework.getRecResult(1, 0);
                vaActionEntities.add(vaActionEntity);
                List<VAErrorTextEntity> vaErrorTextEntities = vaFramework.getErrorTextListResult();
                VAIntent newVaIntent =
                        vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, null);
                if (!Utilities.isNull(newVaIntent)) {
                    freshData();
                    if (vaIntent.getIntentName().equals("COMM_SELECT")) {
                        if (vaFramework.isFillingSlot(vaIntent, "user_item_no")) {
                            String strPhoneNumberIndex =
                                    vaFramework.getNormalizedWordSlot(vaIntent, "user_item_no");
                            Integer phoneNumberIndex = Integer.valueOf(strPhoneNumberIndex);
                            if (phoneNumberIndex > phoneNumbers.size()) {
                                flagList.clear();
                                flagList.add(VAActionEntityListEnum.PLAY);
                                flagList.add(VAActionEntityListEnum.DISPLAY);
                                botSayContext = getXmlString(R.array.index_not_in_list);
                                vaActionEntities =
                                        vaFramework.getVaActionListResult(botSayContext, flagList);
                                vaActionEntity = vaFramework.getRecResult(1, 0);
                                vaActionEntities.add(vaActionEntity);
                                vaErrorTextEntities = vaFramework.getErrorTextListResult();
                                newVaIntent =
                                        vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, null);
                                if (!Utilities.isNull(newVaIntent)) {
                                    freshData();
                                    ContactInfo contactInfo = new ContactInfo();
                                    contactInfo.setName(contactInfoIn.getName());
                                    contactInfo.setPhoneNums(phoneNumbers);
                                    gotoL4(contactInfo);
                                } else {
                                    vaFramework.vaExit(vaIntent, 0.5);
                                }
                            } else {
                                ContactInfo contactInfo = new ContactInfo();
                                contactInfo.setName(contactInfoIn.getName());
                                List<PhoneNumber> phoneNumbersFilterByIndex = new ArrayList<PhoneNumber>();
                                PhoneNumber phoneNumber = phoneNumbers.get(phoneNumberIndex);
                                phoneNumbersFilterByIndex.add(phoneNumber);
                                contactInfo.setPhoneNums(phoneNumbersFilterByIndex);
                                gotoL3(contactInfo);
                            }
                        } else if (vaFramework.isFillingSlot(vaIntent, "user_page_no")) {
                            String strPhonePageIndex =
                                    vaFramework.getNormalizedWordSlot(vaIntent, "user_page_no");
                            Integer phonePageIndex = Integer.valueOf(strPhonePageIndex);
                            if (phonePageIndex > (phoneNumbers.size() / 3 + 1)) {
                                flagList.clear();
                                flagList.add(VAActionEntityListEnum.PLAY);
                                flagList.add(VAActionEntityListEnum.DISPLAY);
                                botSayContext = getXmlString(R.array.index_not_in_bound);
                                vaActionEntities =
                                        vaFramework.getVaActionListResult(botSayContext, flagList);
                                vaActionEntity = vaFramework.getRecResult(1, 0);
                                vaActionEntities.add(vaActionEntity);
                                vaErrorTextEntities = vaFramework.getErrorTextListResult();
                                newVaIntent =
                                        vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, null);
                                if (!Utilities.isNull(newVaIntent)) {
                                    freshData();
                                    ContactInfo contactInfo = new ContactInfo();
                                    contactInfo.setName(contactInfoIn.getName());
                                    contactInfo.setPhoneNums(phoneNumbers);
                                    gotoL4(contactInfo);
                                } else {
                                    vaFramework.vaExit(vaIntent, 0.5);
                                }
                            } else {
                                ContactInfo contactInfo = new ContactInfo();
                                contactInfo.setName(contactInfoIn.getName());
                                Integer leftIndex = phonePageIndex * 3;
                                Integer rightIndex =
                                        phonePageIndex * 3 < contactInfoIn.getPhoneNums().size()
                                                ? phonePageIndex : contactInfoIn.getPhoneNums().size();
                                List<PhoneNumber> phoneNumberList = new ArrayList<PhoneNumber>();
                                for (int i = leftIndex; i < rightIndex; i++) {
                                    phoneNumberList.add(contactInfoIn.getPhoneNums().get(i));
                                }
                                contactInfo.setPhoneNums(phoneNumberList);
                                gotoL4(contactInfo);
                            }
                        }
                    } else if (vaIntent.getIntentName().equals("MAKE_CALL")) {
                        if (vaFramework.isFillingSlot(vaIntent, "user_phone_num")) {
                            String phoneNumFragment = vaFramework.getNormalizedWordSlot(vaIntent, "user_phone_num");
                            int phoneNumberCount = 0;
                            List<PhoneNumber> phoneNumberList = new ArrayList<PhoneNumber>();
                            for (PhoneNumber phoneNumber : phoneNumbers) {
                                if (phoneNumber.getPhoneNumber().indexOf(phoneNumFragment) != -1) {
                                    phoneNumberCount++;
                                    phoneNumberList.add(phoneNumber);
                                }
                            }
                            ContactInfo contactInfo = new ContactInfo();
                            contactInfo.setName(contactInfoIn.getName());
                            contactInfo.setPhoneNums(phoneNumberList);
                            if (phoneNumberCount == 1) {
                                gotoL3(contactInfo);
                            } else {
                                gotoL4(contactInfo);
                            }
                        }
                    } else {
                        handleExpectedIntent();
                        vaFramework.vaExit(vaIntent, 0.5);
                    }
                    // TODO: 2/27/2019 slot_filling
                }
            }
        }
    }

    public void gotoL5(List<ContactInfo> contactInfos) throws Exception {
        vaFramework.getVoiceData().clearExpectedIntent();
        vaFramework.addExpectedIntent("STOP_CALL");
        vaFramework.addExpectedIntent("CONFIRM_CALL");
        vaFramework.addExpectedIntent("MAKE_CALL");
        vaFramework.addExpectedIntent("COMM_SELECT");
        List<VAActionEntityListEnum> flagList = new ArrayList<VAActionEntityListEnum>();
        flagList.add(VAActionEntityListEnum.PLAY);
        String botSayContext = getXmlString(R.array.find_number_multi_in_one_name);
        List<VAActionEntity> vaActionEntities =
                vaFramework.getVaActionListResult(botSayContext, flagList);
        VAActionEntity vaActionEntity = vaFramework.getRecResult(1, 0);
        vaActionEntities.add(vaActionEntity);
        List<VAErrorTextEntity> vaErrorTextEntities = vaFramework.getErrorTextListResult();
        VAIntent newVaIntent =
                vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, null);
        if (!Utilities.isNull(newVaIntent)) {
            freshData();
            if (vaIntent.getIntentName().equals("CONFIRM_CALL")) {
                if (vaFramework.isFillingSlot(vaIntent, "user_confirm_yesno")) {
                    String normalizedWord =
                            vaFramework.getNormalizedWordSlot(vaIntent, "user_confirm_yesno");
                    if (normalizedWord.equals("是")) {
                        flagList.clear();
                        flagList.add(VAActionEntityListEnum.PLAY);
                        botSayContext = getXmlString(R.array.contacts_not_clear);
                        vaActionEntities =
                                vaFramework.getVaActionListResult(botSayContext, flagList);
                        vaActionEntity = vaFramework.getRecResult(1, 0);
                        vaActionEntities.add(vaActionEntity);
                        vaErrorTextEntities = vaFramework.getErrorTextListResult();
                        newVaIntent =
                                vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, null);
                        if (!Utilities.isNull(newVaIntent)) {
                            freshData();
                            gotoL5(contactInfos);
                        } else {
                            vaFramework.vaExit(vaIntent, 0.5);
                        }
                    } else if (normalizedWord.equals("否")) {
                        flagList.clear();
                        flagList.add(VAActionEntityListEnum.PLAY);
                        flagList.add(VAActionEntityListEnum.DISPLAY);
                        flagList.add(VAActionEntityListEnum.EXECUTION);
                        botSayContext = getXmlString(R.array.stop_call);
                        vaActionEntities = vaFramework.getVaActionListResult(botSayContext, flagList);
                        List<VABuActionEntity> vaBuActionEntities =
                                vaFramework.getVaBuActionListResult("{\"actionName\":\"挂断\"}");
                        vaErrorTextEntities = vaFramework.getErrorTextListResult();
                        vaFramework.botSay(vaActionEntities, vaErrorTextEntities, vaBuActionEntities);
                        vaFramework.vaExit(vaIntent, 0.5);
                    }
                }
            } else if (vaIntent.getIntentName().equals("COMM_SELECT")) {
                String strContactIndex = vaFramework.getNormalizedWordSlot(vaIntent, "user_item_no");
                Integer contactIndex = Integer.valueOf(strContactIndex);
                if (contactIndex > contactInfos.size()) {
                    flagList.clear();
                    flagList.add(VAActionEntityListEnum.PLAY);
                    flagList.add(VAActionEntityListEnum.DISPLAY);
                    botSayContext = getXmlString(R.array.not_find_contacts_index);
                    vaActionEntities =
                            vaFramework.getVaActionListResult(botSayContext, flagList);
                    vaActionEntity = vaFramework.getRecResult(1, 0);
                    vaActionEntities.add(vaActionEntity);
                    vaErrorTextEntities = vaFramework.getErrorTextListResult();
                    newVaIntent =
                            vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, null);
                    if (!Utilities.isNull(newVaIntent)) {
                        freshData();
                        gotoL5(contactInfos);
                    } else {
                        vaFramework.vaExit(vaIntent, 0.5);
                    }
                } else {
                    ContactInfo contactInfo = contactInfos.get(contactIndex);
                    gotoL6(contactInfo);
                }
            } else {
                handleExpectedIntent();
                vaFramework.vaExit(vaIntent, 0.5);
            }
        } else {
            vaFramework.vaExit(vaIntent, 0.5);
        }
    }

    public void gotoL6(ContactInfo contactInfoIn) throws Exception {
        if (contactInfoIn.getPhoneNums().size() == 0) {
            List<VAActionEntityListEnum> flagList = new ArrayList<VAActionEntityListEnum>();
            flagList.add(VAActionEntityListEnum.PLAY);
            flagList.add(VAActionEntityListEnum.REC);
            String botSayContext = getXmlString(R.array.contacts_name_not_find);
            List<VAActionEntity> vaActionEntities =
                    vaFramework.getVaActionListResult(botSayContext, flagList);
            List<VAErrorTextEntity> vaErrorTextEntities = vaFramework.getErrorTextListResult();
            VAIntent vaIntent =
                    vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, null);
            if (!Utilities.isNull(vaIntent)) {
                freshData();
                gotoL2();
            } else {
                vaFramework.vaExit(vaIntent, 0.5);
            }
        } else if (contactInfoIn.getPhoneNums().size() == 1) {
            // START L3: 为用户直接拨打号码 ///////////////////////////////////////////////////
            gotoL3(contactInfoIn);
            // END L3 //////////////////////////////////////////////////////////////////////
        }
    }

    // STOP_CALL
    public void stopCall() throws Exception {
        // reset_session = true;
        vaFramework.getVoiceData().getVaIntents().get(0).setResetAiSession(true);
        freshData();
        List<VAActionEntityListEnum> flagList = new ArrayList<VAActionEntityListEnum>();
        flagList.add(VAActionEntityListEnum.PLAY);
        flagList.add(VAActionEntityListEnum.DISPLAY);
        flagList.add(VAActionEntityListEnum.EXECUTION);
        String botSayContext = getXmlString(R.array.ok);
        List<VAActionEntity> vaActionEntities =
                vaFramework.getVaActionListResult(botSayContext, flagList);
        List<VABuActionEntity> vaBuActionEntities =
                vaFramework.getVaBuActionListResult("{\"actionName\":\"挂断\"}");
        List<VAErrorTextEntity> vaErrorTextEntities = vaFramework.getErrorTextListResult();
        vaFramework.botSay(vaActionEntities, vaErrorTextEntities, vaBuActionEntities);
        vaFramework.vaExit(vaIntent, 0.5);
    }

    // PHONE_NUM_MODIFY
    public void phoneNumModify() throws Exception {
        String prevPhoneNum = vaFramework.getVoiceData().getPhoneNumber();
        String currPhoneNum = "";
        vaFramework.getVoiceData().getVaIntents().get(0).setResetAiSession(true);
        freshData();
        if (!prevPhoneNum.isEmpty() && vaFramework.isFillingSlot(vaIntent, "user_curr_fragment") &&
                vaFramework.isFillingSlot(vaIntent, "user_prev_fragment")) {
            String del = vaFramework.getNormalizedWordSlot(vaIntent, "user_curr_fragment");
            String mod = vaFramework.getNormalizedWordSlot(vaIntent, "user_prev_fragment");
            currPhoneNum = prevPhoneNum.replace(mod, del);
        } else {
            if (vaFramework.isFillingSlot(vaIntent, "user_curr_fragment")) {
                currPhoneNum = vaFramework.getNormalizedWordSlot(vaIntent, "user_curr_fragment");
            }
        }

        if (currPhoneNum.isEmpty()){
            vaFramework.vaExit(vaIntent, 0.5);
            return;
        }

        sLog.i(LOG_TAG, "makeCall: isPhoneNumPrevCalled.");
        vaFramework.getVoiceData().clearExpectedIntent();
        vaFramework.addExpectedIntent("STOP_CALL");
        vaFramework.addExpectedIntent("MAKE_CALL");
        vaFramework.addExpectedIntent("PHONE_NUM_MODIFY");
        vaFramework.addExpectedIntent("COMM_COMFIRM");
        vaFramework.getVoiceData().setPhoneNumber(currPhoneNum);
        List<VAActionEntityListEnum> flagList = new ArrayList<VAActionEntityListEnum>();
        flagList.add(VAActionEntityListEnum.PLAY);
        flagList.add(VAActionEntityListEnum.DISPLAY);
        flagList.add(VAActionEntityListEnum.REC);
        flagList.add(VAActionEntityListEnum.EXECUTION);
        String botSayContext = String.format(getXmlString(R.array.call_start_with_close),currPhoneNum);
        List<VAActionEntity> vaActionEntities =
                vaFramework.getVaActionListResult(botSayContext, flagList);
        List<VABuActionEntity> vaBuActionEntities =
                vaFramework.getVaBuActionListResult("{\"actionName\":\"拨打提示\",\"num\":\"" + currPhoneNum + "\"}");
        VAIntent newVaIntent = vaFramework.botSayAndListen(vaActionEntities, null, vaBuActionEntities);
        if (!Utilities.isNull(newVaIntent)) {
            freshData();
            if (vaIntent.getIntentName().equals("COMM_COMFIRM")) {
                String normalizedWord =
                        vaFramework.getNormalizedWordSlot(vaIntent, "user_confirm_yesno");
                if (normalizedWord.equals("否")) {
                    vaFramework.getVoiceData().clearExpectedIntent();
                    vaFramework.addExpectedIntent("COMM_COMFIRM");
                    vaFramework.addExpectedIntent("STOP_CALL");
                    vaFramework.addExpectedIntent("MAKE_CALL");
                    vaFramework.getVoiceData().setPhoneNumber(currPhoneNum);
                    flagList.clear();
                    flagList.add(VAActionEntityListEnum.PLAY);
                    flagList.add(VAActionEntityListEnum.DISPLAY);
                    flagList.add(VAActionEntityListEnum.REC);
                    botSayContext = getXmlString(R.array.say_contacts_number);
                    vaActionEntities =
                            vaFramework.getVaActionListResult(botSayContext, flagList);
                    VAActionEntity vaActionEntity = vaFramework.getRecResult(1, 0);
                    vaActionEntities.add(vaActionEntity);
                    List<VAErrorTextEntity> vaErrorTextEntities = vaFramework.getErrorTextListResult();
                    newVaIntent =
                            vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, null);
                    // START L_确认号码 /////////////////////////////////////////////////////////////
                    if (!Utilities.isNull(newVaIntent)) {
                        freshData();
                        if (vaIntent.getIntentName().equals("COMM_COMFIRM")) {
                            normalizedWord = vaFramework.getNormalizedWordSlot(vaIntent, "user_confirm_yesno");
                            if (normalizedWord.equals("是")) {
                                flagList.clear();
                                flagList.add(VAActionEntityListEnum.PLAY);
                                flagList.add(VAActionEntityListEnum.DISPLAY);
                                botSayContext = getXmlString(R.array.get_current_number);
                                vaActionEntities = vaFramework.getVaActionListResult(botSayContext, flagList);
                                vaActionEntity = vaFramework.getRecResult(1, 0);
                                vaActionEntities.add(vaActionEntity);
                                vaErrorTextEntities = vaFramework.getErrorTextListResult();
                                newVaIntent =
                                        vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, null);
                                gotoL_confirmNum(newVaIntent);
                            } else if (normalizedWord.equals("否")) {
                                vaFramework.vaExit(vaIntent, 0.5);
                            }
                        } else {
                            handleExpectedIntent();
                            vaFramework.vaExit(vaIntent, 0.5);
                        }
                    } else {
                        handleExpectedIntent();
                        vaFramework.vaExit(vaIntent, 0.5);
                    }
                    // END L_确认号码 ///////////////////////////////////////////////////////////////
                }
            } else {
                handleExpectedIntent();
                vaFramework.vaExit(vaIntent, 0.5);
            }
        } else {
            vaFramework.vaExit(vaIntent, 0.5);
        }
    }

    public void gotoL_confirmNum(VAIntent newVaIntent) throws Exception {
        if (!Utilities.isNull(newVaIntent)) {
            freshData();
            if (vaIntent.getIntentName().equals("COMM_COMFIRM")) {
                String normalizedWord = vaFramework.getNormalizedWordSlot(vaIntent, "user_confirm_yesno");
                if (normalizedWord.equals("是")) {
                    List<VAActionEntityListEnum> flagList = new ArrayList<VAActionEntityListEnum>();
                    flagList.add(VAActionEntityListEnum.PLAY);
                    flagList.add(VAActionEntityListEnum.DISPLAY);
                    String botSayContext = getXmlString(R.array.get_current_number);
                    List<VAActionEntity> vaActionEntities = vaFramework.getVaActionListResult(botSayContext, flagList);
                    VAActionEntity vaActionEntity = vaFramework.getRecResult(1, 0);
                    vaActionEntities.add(vaActionEntity);
                    List<VAErrorTextEntity> vaErrorTextEntities = vaFramework.getErrorTextListResult();
                    newVaIntent =
                            vaFramework.botSayAndListen(vaActionEntities, vaErrorTextEntities, null);
                    gotoL_confirmNum(newVaIntent);
                } else if (normalizedWord.equals("否")) {
                    vaFramework.vaExit(vaIntent, 0.5);
                }
            } else {
                handleExpectedIntent();
                vaFramework.vaExit(vaIntent, 0.5);
            }
        } else {
            handleExpectedIntent();
            vaFramework.vaExit(vaIntent, 0.5);
        }
    }



    public void checkMissingCalls() throws Exception {
        // reset_session = true;
        vaFramework.getVoiceData().getVaIntents().get(0).setResetAiSession(true);
        freshData();
        List<ContactInfo> contactInfos = getMissingCalls();
        if (contactInfos.size() == 0) {
            List<VAActionEntityListEnum> flagList = new ArrayList<VAActionEntityListEnum>();
            flagList.add(VAActionEntityListEnum.PLAY);
            flagList.add(VAActionEntityListEnum.DISPLAY);
            String botSayContext = getXmlString(R.array.no_miss_call);
            List<VAActionEntity> vaActionEntities =
                    vaFramework.getVaActionListResult(botSayContext, flagList);
            vaFramework.botSay(vaActionEntities, null, null);
            vaFramework.vaExit(vaIntent, 0.5);
        } else {
            List<VAActionEntityListEnum> flagList = new ArrayList<VAActionEntityListEnum>();
            flagList.add(VAActionEntityListEnum.PLAY);
            flagList.add(VAActionEntityListEnum.DISPLAY);
            String botSayContext = getXmlString(R.array.show_missing_call);
            List<VAActionEntity> vaActionEntities =
                    vaFramework.getVaActionListResult(botSayContext, flagList);
            vaFramework.botSay(vaActionEntities, null, null);
            vaFramework.vaExit(vaIntent, 0.5);
        }
    }

    public void redialCall() throws Exception {
        // reset_session = true;
        vaFramework.getVoiceData().getVaIntents().get(0).setResetAiSession(true);
        freshData();
        List<ContactInfo> contactInfos = getRecordList();
        if (contactInfos.size() == 0) {
            List<VAActionEntityListEnum> flagList = new ArrayList<VAActionEntityListEnum>();
            flagList.add(VAActionEntityListEnum.PLAY);
            flagList.add(VAActionEntityListEnum.DISPLAY);
            String botSayContext = getXmlString(R.array.no_redial_num);
            List<VAActionEntity> vaActionEntities =
                    vaFramework.getVaActionListResult(botSayContext, flagList);
            vaFramework.botSay(vaActionEntities, null, null);
            vaFramework.vaExit(vaIntent, 0.5);
        } else {
            String phoneNumber = contactInfos.get(0).getPhoneNums().get(0).getPhoneNumber();
            vaFramework.getVoiceData().clearExpectedIntent();
            vaFramework.addExpectedIntent("PHONE_NUM_MODIFY");
            vaFramework.addExpectedIntent("STOP_CALL");
            vaFramework.addExpectedIntent("MAKE_CALL");
            vaFramework.getVoiceData().setPhoneNumber(phoneNumber);
            List<VAActionEntityListEnum> flagList = new ArrayList<VAActionEntityListEnum>();
            flagList.add(VAActionEntityListEnum.PLAY);
            flagList.add(VAActionEntityListEnum.DISPLAY);
            flagList.add(VAActionEntityListEnum.REC);
            flagList.add(VAActionEntityListEnum.EXECUTION);
            String botSayContext = String.format(getXmlString(R.array.redial_start),phoneNumber);
            List<VAActionEntity> vaActionEntities =
                    vaFramework.getVaActionListResult(botSayContext, flagList);
            List<VAErrorTextEntity> vaErrorTextEntities = vaFramework.getErrorTextListResult();
            List<VABuActionEntity> vaBuActionEntities =
                    vaFramework.getVaBuActionListResult("{\"actionName\":\"重拨提示\",\"num\":" + phoneNumber + "\"}");
            VAIntent newVaIntent =
                    vaFramework.botSayAndListen(vaActionEntities, null, vaBuActionEntities);
            if (!Utilities.isNull(newVaIntent)) {
                freshData();
                handleExpectedIntent();
                vaFramework.vaExit(vaIntent, 0.5);
            } else {
                vaFramework.vaExit(vaIntent, 0.5);
            }
        }
    }

    public void phoneNumClear() throws Exception {

        sLog.i(LOG_TAG, "makeCall: clear phone number.");
        vaFramework.getVoiceData().clearExpectedIntent();
        vaFramework.addExpectedIntent("STOP_CALL");
        vaFramework.addExpectedIntent("MAKE_CALL");
        vaFramework.addExpectedIntent("PHONE_NUM_MODIFY");
        vaFramework.addExpectedIntent("COMM_COMFIRM");
        vaFramework.getVoiceData().setPhoneNumber("");
        List<VAActionEntityListEnum> flagList = new ArrayList<VAActionEntityListEnum>();
        flagList.add(VAActionEntityListEnum.PLAY);
        flagList.add(VAActionEntityListEnum.DISPLAY);
        flagList.add(VAActionEntityListEnum.REC);
        flagList.add(VAActionEntityListEnum.EXECUTION);
        String botSayContext = getXmlString(R.array.get_current_number);
        List<VAActionEntity> vaActionEntities =
                vaFramework.getVaActionListResult(botSayContext, flagList);
        List<VABuActionEntity> vaBuActionEntities =
                vaFramework.getVaBuActionListResult("{\"actionName\":\"挂断\"}");
        VAIntent newVaIntent =
                vaFramework.botSayAndListen(vaActionEntities, null, vaBuActionEntities);
        if (!Utilities.isNull(newVaIntent)) {
            freshData();
            if (vaIntent.getIntentName().equals("MAKE_CALL")) {
                String phoneNumber = vaFramework.getNormalizedWordSlot(vaIntent, "user_phone_num");
                if (!phoneNumber.isEmpty() && !"none".equals(phoneNumber)) {
                    gotoL1(phoneNumber);
                }
            }
        }
    }

    private void freshData() {
        vaIntent = vaFramework.getVoiceData().getVaIntents().get(0);
        postData = vaFramework.getVoiceData().getOnCompleteReqPostData();
    }

    private boolean isBtConnected() throws Exception {
        if (vaFramework.getVoiceData().getJsonObject().getInt("btConnected") == 1) {
            return true;
        } else if (vaFramework.getVoiceData().getJsonObject().getInt("btConnected") == 0) {
            return false;
        }
        return false;
    }

    private boolean isPhoneNumComplete(String phoneNumber) {
        sLog.i(LOG_TAG, "isPhoneNumComplete: phoneNumber: " + phoneNumber);
        if (phoneNumber.equals("110") || phoneNumber.equals("120") || phoneNumber.equals("119") || phoneNumber.startsWith("100")) {
            return true;
        } else if(phoneNumber.startsWith("1") && phoneNumber.length() >= 8|| !phoneNumber.startsWith("1") && phoneNumber.length() >= 6){
            return true;
        } else {
            return false;
        }
    }

    private boolean isContactBookNotSync() throws Exception {
        if (vaFramework.getVoiceData().getJsonObject().getInt("contactBookSync") == 1) {
            return true;
        } else if (vaFramework.getVoiceData().getJsonObject().getInt("contactBookSync") == 0) {
            return false;
        }
        return false;
    }

    private boolean isPhoneNumPrevCalled(String phoneNumber) throws Exception {
        PhoneNumPrevCalled dbHelper = new PhoneNumPrevCalled(GeneralContext.getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
        };
        // Filter results WHERE "PhoneNum" = phoneNumber;
        String selection = PhoneNumPrevCalledEntry.PhoneNumEntry.PHONE_NUM + " = ?";
        String[] selectionArgs = {phoneNumber};

        Cursor cursor = db.query(PhoneNumPrevCalledEntry.PhoneNumEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);

        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    private void addPhoneNum2PrevCalled(String phoneNumber) throws Exception {
        PhoneNumPrevCalled dbHelper = new PhoneNumPrevCalled(GeneralContext.getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(PhoneNumPrevCalledEntry.PhoneNumEntry.PHONE_NUM, phoneNumber);
        long newRowId = db.insert(PhoneNumPrevCalledEntry.PhoneNumEntry.TABLE_NAME, null, values);
    }

    private List<ContactInfo> searchContact(String name, String userPhoneType, String userPhoneLoc, String userSubNum) {
        List<ContactInfo> contactInfos = new ArrayList<ContactInfo>();
        List<Contact> pinYinModules = PinYinService.searchContacts(name, userPhoneType, userPhoneLoc, userSubNum);
        if (pinYinModules.size() > 0) {
            for (Contact pinYinModule : pinYinModules) {
                ContactInfo contactInfo = new ContactInfo();
                contactInfo.setName(pinYinModule.getContactNameFirst()+pinYinModule.getContactNameLast());
                List<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();
                for (TelNumber phoneNumInfo : pinYinModule.getTelNums()) {
                    PhoneNumber phoneNumber = new PhoneNumber();
                    phoneNumber.setPhoneNumber(phoneNumInfo.getNumber());
                    //changed by sunhy
                    phoneNumber.setISP(phoneNumInfo.getCarrierOperator());
//                    phoneNumber.setPhoneNumber(phoneNumInfo.getCarrierOperator());
                    phoneNumbers.add(phoneNumber);
                }
                contactInfo.setPhoneNums(phoneNumbers);
                contactInfos.add(contactInfo);
            }
        }
        return contactInfos;
    }

    private List<ContactInfo> getMissingCalls() {
        Map<Integer, RecentCallInfo> contactInfoMap = CallLogUtils.filterCallLogType(3);
        List<ContactInfo> contactInfos = new ArrayList<ContactInfo>();
        if (!Utilities.isNull(contactInfoMap)) {
            Set<Integer> infoIterator = contactInfoMap.keySet();
            for (Integer integer : infoIterator) {
                RecentCallInfo recentCallInfo = contactInfoMap.get(integer);
                ContactInfo contactInfo = new ContactInfo();
                contactInfo.setName(recentCallInfo.contactNameLast + recentCallInfo.contactNameFirst);
                List<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();
                PhoneNumber phoneNumber = new PhoneNumber();
                phoneNumber.setPhoneNumber(recentCallInfo.telNum);
                //remove by sunhy
//                phoneNumber.setPhoneNumber("");
                phoneNumbers.add(phoneNumber);
                contactInfo.setPhoneNums(phoneNumbers);
                contactInfos.add(contactInfo);
            }
        }
        return contactInfos;
    }

    private List<ContactInfo> getRecordList() {
        Map<Integer, RecentCallInfo> contactInfoMap = CallLogUtils.filterCallLogType(2);
        List<ContactInfo> contactInfos = new ArrayList<ContactInfo>();
        if (!Utilities.isNull(contactInfoMap)) {
            Set<Integer> infoIterator = contactInfoMap.keySet();
            for (Integer integer : infoIterator) {
                RecentCallInfo recentCallInfo = contactInfoMap.get(integer);
                ContactInfo contactInfo = new ContactInfo();
                contactInfo.setName(recentCallInfo.contactNameLast + recentCallInfo.contactNameFirst);
                List<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();
                PhoneNumber phoneNumber = new PhoneNumber();
                phoneNumber.setPhoneNumber(recentCallInfo.telNum);
                phoneNumber.setPhoneNumber("");
                phoneNumbers.add(phoneNumber);
                contactInfo.setPhoneNums(phoneNumbers);
                contactInfos.add(contactInfo);
            }
        }
        return contactInfos;
    }

    // 处理新意图，不包含 COMM_COMFIRM。
    private void handleExpectedIntent() throws Exception {
        if (vaIntent.getIntentName().equals("MAKE_CALL")) {
            sLog.i(LOG_TAG, "handleExpectedIntent: new intentName = MAKE_CALL.");
            vaFramework.vaLeave();
        } else if (vaIntent.getIntentName().equals("STOP_CALL")) {
            sLog.i(LOG_TAG, "handleExpectedIntent: new intentName = STOP_CALL.");
            stopCall();
            //vaFramework.vaExit(vaIntent, 0.5);
        } else if (vaIntent.getIntentName().equals("PHONE_NUM_MODIFY")) {
            // 需要存储当前电话。
            sLog.i(LOG_TAG, "handleExpectedIntent: new intentName = PHONE_NUM_MODIFY.");
            vaFramework.vaLeave();
        } else {
            vaFramework.vaExit(vaIntent, 0.5);
        }
    }

    private List<PhoneNumber> contactInfoFilterByType(ContactInfo contactInfo, String phoneType) {
        sLog.i(LOG_TAG, "contactInfoFilterByType: contactInfo: " + contactInfo);
        sLog.i(LOG_TAG, "contactInfoFilterByType: phoneType: " + phoneType);
        List<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();
        for (PhoneNumber phoneNumber : contactInfo.getPhoneNums()) {
            if (phoneNumber.getISP().equals(phoneType)) {
                phoneNumbers.add(phoneNumber);
            }
        }
        return phoneNumbers;
    }

    private String getXmlString(int id){
        return GeneralContext.getContext().getResources().getStringArray(id)[GeneralContext.getBot()];
    }

    private int toIndex(String strIndex){
        switch (strIndex) {
            case "一": case "1":
                strIndex = "0";
                break;
            case "二": case "2":
                strIndex = "1";
                break;
            case "三": case "3":
                strIndex = "2";
                break;
            case "四": case "4":
                strIndex = "3";
                break;
            case "五": case "5":
                strIndex = "4";
                break;
            case "六": case "6":
                strIndex = "5";
                break;
            case "七": case "7":
                strIndex = "6";
                break;
            case "八": case "8":
                strIndex = "7";
                break;
            case "九": case "9":
                strIndex = "8";
                break;
            case "十": case "10":
                strIndex = "9";
                break;
            default:
                strIndex = "0";
                break;
        }
        Integer contactIndex = Integer.valueOf(strIndex);
        return contactIndex;
    }

}
