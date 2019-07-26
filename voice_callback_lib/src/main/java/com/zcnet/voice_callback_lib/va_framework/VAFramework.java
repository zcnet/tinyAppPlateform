package com.zcnet.voice_callback_lib.va_framework;

import com.z.tinyapp.utils.logs.sLog;
import com.zcnet.voice_callback_lib.Utilities;
import com.zcnet.voice_callback_lib.VoiceCallback2BO;
import com.zcnet.voice_callback_lib.va_3rd_biz.VA3rdBiz;
import com.zcnet.voice_callback_lib.voice_data.VAActionEntity;
import com.zcnet.voice_callback_lib.voice_data.VAActionEnum;
import com.zcnet.voice_callback_lib.voice_data.VABaseIntent;
import com.zcnet.voice_callback_lib.voice_data.VABaseIntentSlot;
import com.zcnet.voice_callback_lib.voice_data.VABaseVoiceData;
import com.zcnet.voice_callback_lib.voice_data.VABuActionEntity;
import com.zcnet.voice_callback_lib.voice_data.VAErrorTextEntity;
import com.zcnet.voice_callback_lib.voice_data.VAExpectEnum;
import com.zcnet.voice_callback_lib.voice_data.VAExpectedIntent;
import com.zcnet.voice_callback_lib.voice_data.VAIntent;
import com.zcnet.voice_callback_lib.voice_data.VAText;

import org.json.JSONArray;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// VAFramework 处理的主要对象是 VABaseVoiceData。
public class VAFramework {
    private static final VAFramework Instance = new VAFramework();

    public static VAFramework getInstance() {
        return Instance;
    }

    private VAFramework() {
        sLog.i(null, "VAFramework create");
    }

    private static final String LOG_TAG = "_VA_VAFRAMEWORK";
    private VABaseVoiceData voiceData;
    private Stack<VABaseVoiceData> voiceDataStack = new Stack<VABaseVoiceData>();
    private boolean isVoiceDataEmpty = true;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            List<VABaseIntent> currIntentList =
                    voiceData.getOnCompleteReqPostData().getCurrIntent();
            // 没有传入意图的场合，返回“无法识别”。
            if (Utilities.isNull(currIntentList) || Utilities.isNull(currIntentList.get(0))) {
                sLog.i(LOG_TAG, "run: currIntentList is NULL.");
                VAIntent vaIntent = voiceData.getVaIntents().get(0);
                vaIntent.setResetAiSession(true);
                sLog.i(LOG_TAG, "run: vaIntent: " + vaIntent.toString());
                vaDefaultOnResult(vaIntent);
                sLog.i(LOG_TAG, "run: After vaDefaultOnResult. This's an ERROR!");
            } else {
                try {
                    invokeIntent(voiceData.getVaIntents().get(0));
                } catch (Exception e) {
                    sLog.i(LOG_TAG, "run: Exception: ", e);
                }
            }
        }
    };
    boolean isUseOnStar = false;

    public VABaseVoiceData getVoiceData() {
        return voiceData;
    }

    public void setVoiceData(VABaseVoiceData voiceData) {
        this.voiceData = voiceData;
    }

    // START STACK ZONE ////////////////////////////////////////////////////////////////////////////
    public Stack<VABaseVoiceData> getVoiceDataStack() {
        return voiceDataStack;
    }

    // 将 item 压栈。
    public void stackPush(VABaseVoiceData voiceData) {
        this.voiceDataStack.push(voiceData);
    }

    // 查看栈顶元素，但不从堆栈中移除它。
    public VABaseVoiceData stackPeep() {
        return this.voiceDataStack.peek();
    }

    // 出栈，调用前需要判空。
    public VABaseVoiceData stackPop() {
        return this.voiceDataStack.pop();
    }

    // 判断栈是否为空，为空返回 true。
    public boolean isStackEmpty() {
        boolean isEmpty = false;
        if (this.voiceDataStack.isEmpty()) {
            isEmpty = true;
        }
        return isEmpty;
    }
    // END STACK ZONE //////////////////////////////////////////////////////////////////////////////

    public boolean getIsVoiceDataEmpty() {
        return isVoiceDataEmpty;
    }

    public void setIsVoiceDataEmpty(boolean isVoiceDataEmpty) {
        this.isVoiceDataEmpty = isVoiceDataEmpty;
    }

    // VAFramework 对 VoiceData 里线程的操作。
    public void runVoiceData() {
        sLog.i(LOG_TAG, "runVoiceData.");
        this.voiceData.setThread(this.runnable);
        this.voiceData.threadRun();
    }

    public boolean getIsUseOnStar() {
        return isUseOnStar;
    }

    public void setIsUseOnStar(boolean isUseOnStar) {
        this.isUseOnStar = isUseOnStar;
    }

    public boolean isUseOnStar() {
        return getIsUseOnStar();
    }

    // START VOICEDATA /////////////////////////////////////////////////////////////////////////////
    // 添加期望意图
    public void addExpectedIntent(String intentName, VAExpectEnum vaExpectEnum, VAActionEnum vaActionEnum) {
        VAExpectedIntent vaExpectedIntent = new VAExpectedIntent();
        vaExpectedIntent.setIntentName(intentName);
        vaExpectedIntent.setVaExpect(vaExpectEnum);
        vaExpectedIntent.setVaAction(vaActionEnum);
        this.voiceData.addExpectedIntent(vaExpectedIntent);
    }

    // 添加期望意图的快捷方法。
    public void addExpectedIntent(String intentName) {
        VAExpectedIntent vaExpectedIntent = new VAExpectedIntent();
        vaExpectedIntent.setIntentName(intentName);
        vaExpectedIntent.setVaExpect(VAExpectEnum.EXPECT);
        vaExpectedIntent.setVaAction(VAActionEnum.CUSTOMIZED);
        this.voiceData.addExpectedIntent(vaExpectedIntent);
    }

    // 从词槽中获取规整词。
    public String getNormalizedWordSlot(VAIntent vaIntent, String slotName) {
        String normalizedWordSlot = "";
        List<VABaseIntentSlot> vaSlots = vaIntent.getVaSlots();
        for (VABaseIntentSlot vaSlot : vaSlots) {
            if (vaSlot.getName().equals(slotName)) {
                normalizedWordSlot = vaSlot.getNormalizedWord();
                return normalizedWordSlot;
            }
        }
        return normalizedWordSlot;
    }

    public boolean isFillingSlot(VAIntent vaIntent, String slotName) {
        List<VABaseIntentSlot> vaSlots = vaIntent.getVaSlots();
        for (VABaseIntentSlot vaSlot : vaSlots) {

            //changed by sunhy
            if (vaSlot.getName().equals(slotName) && !"none".equals(vaSlot.getOriginalWord())) {
//            if (vaSlot.getName().equals(slotName)) {
                return true;
            }
        }
        return false;
    }
    // END VOICEDATA ///////////////////////////////////////////////////////////////////////////////

    // START VAACTIONLIST RESULT ///////////////////////////////////////////////////////////////////
    public VAActionEntity getPlayResult(String text, Integer priority, Integer interval,
                                        Integer retry, Integer doActionNow, Integer textInterval) {
        sLog.i(LOG_TAG, "getPlayResult: text: " + text);
        sLog.i(LOG_TAG, "getPlayResult: priority: " + priority);
        sLog.i(LOG_TAG, "getPlayResult: interval: " + interval);
        sLog.i(LOG_TAG, "getPlayResult: retry: " + retry);
        sLog.i(LOG_TAG, "getPlayResult: doActionNow: " + doActionNow);
        sLog.i(LOG_TAG, "getPlayResult: textInterval: " + textInterval);
        // [NON_EMPTY] List<VAActionEntity> vaActionList [{
        //    String name;  // play:播放文字
        //    [NON_EMPTY] Integer priority;
        //    [NON_EMPTY] Integer interval;
        //    [NON_EMPTY] Integer retry;
        //    [NON_EMPTY] Integer doActionNow;
        //    [NON_EMPTY] List<VAText> textList [{
        //        [NON_EMPTY] String text;
        //        [NON_EMPTY] Integer interval;
        //    }];
        // }];
        VAActionEntity vaActionEntity = new VAActionEntity();
        vaActionEntity.setName("play:播放文字");
        vaActionEntity.setPriority(priority);
        vaActionEntity.setInterval(interval);
        vaActionEntity.setRetry(retry);
        vaActionEntity.setDoActionNow(doActionNow);
        if (!Utilities.isNull(text)) {
            List<VAText> textList = new ArrayList<VAText>();
            VAText vaText = new VAText();

            vaText.setText(text);
            vaText.setInterval(textInterval);

            textList.add(vaText);
            vaActionEntity.setTextList(textList);
        }
        return vaActionEntity;
    }

    public VAActionEntity getDisplayResult(List<String> texts, Integer priority,
                                           Integer interval, Integer textInterval) {
        sLog.i(LOG_TAG, "getDisplayResult: texts count: " + texts.size());
        sLog.i(LOG_TAG, "getDisplayResult: priority: " + priority);
        sLog.i(LOG_TAG, "getDisplayResult: internal: " + interval);
        sLog.i(LOG_TAG, "getDisplayResult: textInterval: " + textInterval);
        // [NON_EMPTY] List<VAActionEntity> vaActionList [{
        //     String name; // display:显示文字
        //     [NON_EMPTY] Integer priority;
        //     [NON_EMPTY] Integer interval;
        //     [NON_EMPTY] List<VAText> textList [{
        //         static final long serialVersionUID = 1L;
        //         [NON_EMPTY] String text;
        //         [NON_EMPTY] Integer interval;
        //     }];
        // }];
        VAActionEntity vaActionEntity = new VAActionEntity();
        vaActionEntity.setName("display:显示文字");
        vaActionEntity.setPriority(priority);
        vaActionEntity.setInterval(interval);
        if (!Utilities.isNull(texts)) {
            List<VAText> textList = new ArrayList<VAText>();
            for (String text: texts) {
                VAText vaText = new VAText();
                vaText.setText(text);
                vaText.setInterval(textInterval);
                textList.add(vaText);
            }

            vaActionEntity.setTextList(textList);
        }
        return vaActionEntity;
    }

    public VAActionEntity getDisplayResult(String text, Integer priority,
                                           Integer interval, Integer textInterval) {
        sLog.i(LOG_TAG, "getDisplayResult: text: " + text);
        sLog.i(LOG_TAG, "getDisplayResult: priority: " + priority);
        sLog.i(LOG_TAG, "getDisplayResult: internal: " + interval);
        sLog.i(LOG_TAG, "getDisplayResult: textInterval: " + textInterval);
        // [NON_EMPTY] List<VAActionEntity> vaActionList [{
        //     String name; // display:显示文字
        //     [NON_EMPTY] Integer priority;
        //     [NON_EMPTY] Integer interval;
        //     [NON_EMPTY] List<VAText> textList [{
        //         static final long serialVersionUID = 1L;
        //         [NON_EMPTY] String text;
        //         [NON_EMPTY] Integer interval;
        //     }];
        // }];
        VAActionEntity vaActionEntity = new VAActionEntity();
        vaActionEntity.setName("display:显示文字");
        vaActionEntity.setPriority(priority);
        vaActionEntity.setInterval(interval);
        if (!Utilities.isNull(text)) {
            List<VAText> textList = new ArrayList<VAText>();
            VAText vaText = new VAText();

            vaText.setText(text);
            vaText.setInterval(textInterval);

            textList.add(vaText);
            vaActionEntity.setTextList(textList);
        }
        return vaActionEntity;
    }

    public VAActionEntity getRecResult(Integer priority, Integer interval) {
        sLog.i(LOG_TAG, "getRecResult: priority: " + priority);
        sLog.i(LOG_TAG, "getRecResult: interval: " + interval);
        // [NON_EMPTY] List<VAActionEntity> vaActionList [{
        //     String name; // rec:录制音频
        //     [NON_EMPTY] Integer priority;
        //     [NON_EMPTY] Integer interval;
        // }];
        VAActionEntity vaActionEntity = new VAActionEntity();
        vaActionEntity.setName("rec:录制音频");
        vaActionEntity.setPriority(priority);
        vaActionEntity.setInterval(interval);
        return vaActionEntity;
    }

    public VAActionEntity getExecutionResult(Integer priority, Integer interval) {
        sLog.i(LOG_TAG, "getExecutionResult: priority: " + priority);
        sLog.i(LOG_TAG, "getExecutionResult: interval: " + interval);
        // [NON_EMPTY] List<VAActionEntity> vaActionList [{
        //     String name; // execution:执行业务动作
        //     [NON_EMPTY] Integer priority;
        //     [NON_EMPTY] Integer interval;
        // }];
        VAActionEntity vaActionEntity = new VAActionEntity();
        vaActionEntity.setName("execution:执行业务动作");
        vaActionEntity.setPriority(priority);
        vaActionEntity.setInterval(interval);
        return vaActionEntity;
    }

    public VAActionEntity getExitResult(Integer prority, Integer interval) {
        sLog.i(LOG_TAG, "getExitResult: prority: " + prority);
        sLog.i(LOG_TAG, "getExitResult: interval: " + interval);
        // [NON_EMPTY] List<VAActionEntity> vaActionList [{
        //     String name;
        //     [NON_EMPTY] Integer priority;
        //     [NON_EMPTY] Integer interval;
        // }];
        VAActionEntity vaActionEntity = new VAActionEntity();
        vaActionEntity.setName("exit:退出");
        vaActionEntity.setPriority(prority);
        vaActionEntity.setInterval(interval);
        return vaActionEntity;
    }

    public List<VAActionEntity> getVaActionListResult(String text,
                                                      List<VAActionEntityListEnum> flagList) {
        sLog.i(LOG_TAG, "getVaActionListResult: text: " + text);
        sLog.i(LOG_TAG, "getVaActionListResult: flagList: " + flagList);
        List<VAActionEntity> vaActionList = new ArrayList<VAActionEntity>();
        if (!Utilities.isNull(flagList)) {
            for (VAActionEntityListEnum flag : flagList) {
                if (flag.equals(VAActionEntityListEnum.PLAY)) {
                    vaActionList.add(getPlayResult(text, 0, 0,
                            0, 1, 0));
                }
                if (flag.equals(VAActionEntityListEnum.DISPLAY)) {
                    vaActionList.add(getDisplayResult(text, 0, 0, 0));
                }
                if (flag.equals(VAActionEntityListEnum.REC)) {
                    vaActionList.add(getRecResult(0, 0));
                }
                if (flag.equals(VAActionEntityListEnum.EXECUTION)) {
                    vaActionList.add(getExecutionResult(0, 0));
                }
            }
        }
        return vaActionList;
    }
    // END VAACTIONLIST RESULT /////////////////////////////////////////////////////////////////////

    // START ERRORTEXTLIST RESULT //////////////////////////////////////////////////////////////////
    public List<VAErrorTextEntity> getErrorTextListResult(List<String> botSayErrorTextList) {
        sLog.i(LOG_TAG, "getErrorTextListResult: botSayErrorTextList: " + botSayErrorTextList);
        if (Utilities.isNull(botSayErrorTextList)) {
            return null;
        } else {
            // [NON_EMPTY] List<VAErrorTextEntity> errorTextList [{
            //     [NON_EMPTY] Integer errorHintNum;
            //     [NON_EMPTY] List<VAText> textList [{
            //         static final long serialVersionUID = 1L;
            //         [NON_EMPTY] String text;
            //         [NON_EMPTY] String id;
            //         [NON_EMPTY] Integer interval;
            //         [NON_EMPTY] Integer isShow;
            //     }];
            // }];
            List<VAErrorTextEntity> vaErrorTextEntityList = new ArrayList<VAErrorTextEntity>();
            VAErrorTextEntity vaErrorTextEntity = new VAErrorTextEntity();
            vaErrorTextEntity.setErrorHintNum(botSayErrorTextList.size());

            List<VAText> vaTexts = new ArrayList<VAText>();
            for (String errorText : botSayErrorTextList) {
                VAText vaText = new VAText();
                vaText.setText(errorText);
                vaTexts.add(vaText);
            }
            vaErrorTextEntity.setTextList(vaTexts);

            vaErrorTextEntityList.add(vaErrorTextEntity);
            return vaErrorTextEntityList;
        }
    }

    public List<VAErrorTextEntity> getErrorTextListResult() {
        List<String> botSayErrorTextList = new ArrayList<String>();
        botSayErrorTextList.add("发生错误请重试第一次");
        botSayErrorTextList.add("发生错误请重试第二次");
        botSayErrorTextList.add("发生错误请重试最后一次");
        return getErrorTextListResult(botSayErrorTextList);
    }
    // END ERRORTEXTLIST RESULT ////////////////////////////////////////////////////////////////////

    // START BUACTIONLIST RESULT ///////////////////////////////////////////////////////////////////
    public List<VABuActionEntity> getVaBuActionListResult(String content) {
        sLog.i(LOG_TAG, "getVaBuActionListResult: content: " + content);
        List<VABuActionEntity> vaBuActionEntities = new ArrayList<VABuActionEntity>();
        VABuActionEntity vaBuActionEntity = new VABuActionEntity();
        vaBuActionEntity.setAppName("callCard");
        vaBuActionEntity.setModuleName("callModule");
        vaBuActionEntity.setDisplayType(1);
        vaBuActionEntity.setIsReuse(0);
        vaBuActionEntity.setContent(content);
        vaBuActionEntities.add(vaBuActionEntity);
        return vaBuActionEntities;
    }
    // END BUACTIONLIST RESULT /////////////////////////////////////////////////////////////////////

    // START ONRESULT //////////////////////////////////////////////////////////////////////////////
    public void vaDoAction(List<VAActionEntity> vaActionList,
                           List<VAErrorTextEntity> vaErrorTextList,
                           List<VABuActionEntity> vaBuActionList, VAIntent intent) {
        sLog.i(LOG_TAG, "vaDoAction: vaActionList: " + vaActionList);
        sLog.i(LOG_TAG, "vaDoAction: vaErrorTextList: " + vaErrorTextList);
        sLog.i(LOG_TAG, "vaDoAction: vaBuActionList: " + vaBuActionList);
        sLog.i(LOG_TAG, "vaDoAction: intent: " + intent);
        // VABaseOnResultReqPostData {
        //     Boolean resetAiSession;
        //     String frontSessionId;
        //     String frontSessionRoundId;
        //     List<VABaseIntent> intent;
        //     Integer actionType;
        //     [NON_EMPTY] List<VAActionEntity> vaActionList ;
        //     [NON_EMPTY] List<VAErrorTextEntity> errorTextList;
        //     [NON_EMPTY] List<VABuActionEntity> buActionList;
        // }
        try {
            // 给 VoiceData 里的 onResultReqPostData 存入数据。
            if (intent.getResetAiSession()) {
                this.voiceData.getOnResultReqPostData().setResetAiSession(true);
            } else {
                this.voiceData.getOnResultReqPostData().setResetAiSession(false);
            }
            this.voiceData.getOnResultReqPostData().setFrontSessionId(intent.getFrontSessionId());
            this.voiceData.getOnResultReqPostData().setFrontSessionRoundId(intent.getFrontSessionRoundId());

            // private List<VABaseIntent> intent;
            List<VABaseIntent> vaBaseIntents = new ArrayList<VABaseIntent>();
            VABaseIntent vaBaseIntent = new VABaseIntent();
            vaBaseIntent.setName(intent.getIntentName());
            // 少了一个 Integer actionCount;
            List<VABaseIntentSlot> vaSlotList = intent.getVaSlots();
            vaBaseIntent.setSlots(vaSlotList);
            vaBaseIntents.add(vaBaseIntent);
            this.voiceData.getOnResultReqPostData().setIntent(vaBaseIntents);

            this.voiceData.getOnResultReqPostData().setActionType(1);
            this.voiceData.getOnResultReqPostData().setVaActionList(vaActionList);
            this.voiceData.getOnResultReqPostData().setErrorTextList(vaErrorTextList);
            this.voiceData.getOnResultReqPostData().setBuActionList(vaBuActionList);

            // TODO: Convert Class to JSON.
            VoiceCallback2BO.getInstance().onResult(this.voiceData.getOnResultReqPostData());
        } catch (Exception e) {
            sLog.i(LOG_TAG, "vaDoAction: Exception: " ,e);
        }
    }

    public void botSay(List<VAActionEntity> vaActionList,
                       List<VAErrorTextEntity> vaErrorTextList,
                       List<VABuActionEntity> buActionList) {
        sLog.i(LOG_TAG, "botSay: vaActionList: " + vaActionList);
        sLog.i(LOG_TAG, "botSay: errorTextList: " + vaErrorTextList);
        sLog.i(LOG_TAG, "botSay: buActionEntityList: " + buActionList);
        try {
            VAIntent vaIntent = this.voiceData.getVaIntents().get(0);
            vaDoAction(vaActionList, vaErrorTextList, buActionList, vaIntent);
        } catch (Exception e) {
            sLog.i(LOG_TAG, "botSay: Exception: " + e);
        }
    }

    public VAIntent botSayAndListen(List<VAActionEntity> vaActionList,
                                    List<VAErrorTextEntity> vaErrorTextList,
                                    List<VABuActionEntity> buActionList) {
        sLog.i(LOG_TAG, "botSayAndBargeIn: vaActionList: " + vaActionList);
        sLog.i(LOG_TAG, "botSayAndBargeIn: errorTextList: " + vaErrorTextList);
        sLog.i(LOG_TAG, "botSayAndBargeIn: buActionList: " + buActionList);
        // 通过 onResult 接口发送结果给 BO。
        VAIntent vaIntent = this.voiceData.getVaIntents().get(0);
        vaDoAction(vaActionList, vaErrorTextList, buActionList, vaIntent);

        // 暂停线程，等待 BO 传过来下一个有效意图。
        this.voiceData.threadWait(30 * 1000);

        // 检查意图是否是新意图。
        if (this.voiceData.getIsNewIntent()) {
            this.voiceData.setIsNewIntent(false);    // 还原标志位。
            return this.voiceData.getVaIntents().get(0);
        } else {
            return null;
        }
    }

    private void vaDefaultOnResult(VAIntent intent) {
        try {
            List<VAActionEntityListEnum> flagList = new ArrayList<VAActionEntityListEnum>();
            flagList.add(VAActionEntityListEnum.PLAY);
            List<VAActionEntity> vaActionEntities =
                    getVaActionListResult("抱歉，我没有听清楚您的话，请再说一遍。", flagList);
            botSay(vaActionEntities, null, null);
            this.voiceData.threadInterrupt();
        } catch (Exception e) {
            sLog.i(LOG_TAG, "vaDefaultOnResult: Exception: " + e);
        }
    }

    private void sendSessionOver(Integer actionType, VAIntent intent, double second) {
        sLog.i(LOG_TAG, "sendSessionOver: actionType: " + actionType);
        sLog.i(LOG_TAG, "sendSessionOver: intent: " + intent);
        sLog.i(LOG_TAG, "sendSessionOver: second: " + second);
        // VABaseOnResultReqPostData {
        //     Boolean resetAiSession;
        //     String frontSessionId;
        //     String frontSessionRoundId;
        //     List<VABaseIntent> intent [{
        //         String intentName;                      // 意图名称
        //         Integer actionCount;                    // 动作计数器
        //         List<VABaseIntentSlot> slots [{         // 词槽
        //             String slotName;                    // 词槽名称
        //             String slotType;                    // 词槽类型
        //             String originalWord;                // 原始词
        //             String normalizedWord;              // 规整词
        //             String transformerWord;             // 转换词
        //             String requiredSlot;                // 必须填槽词槽
        //             String resetWhenIntentRecognized;   // 重置词槽
        //             String clarify;                     // 澄清语言集
        //         }];
        //         [NON_EMPTY] List<VABaseIntentSlotGroup> slotGroups [{   // 词槽组
        //             String slotGroupName;                               // 词槽组名称
        //             String requiredSlotGroup;                           // 必须填槽词槽组
        //             Integer leastFillSlot;                              // 最小填槽数
        //             List<VABaseIntentSlotGroupSlot> slots [{            // 词槽
        //                 String slotName;        // 词槽名称
        //                 String requiredSlot;    // 必须填槽词槽
        //             }];
        //         }];
        //     }];
        //     Integer actionType;
        //     [NON_EMPTY] List<VAActionEntity> vaActionList ;
        //     [NON_EMPTY] List<VAErrorTextEntity> errorTextList;
        //     [NON_EMPTY] List<VABuActionEntity> buActionList;
        // }
        try {
            this.voiceData.getOnResultReqPostData().setResetAiSession(intent.getResetAiSession());
            this.voiceData.getOnResultReqPostData().setFrontSessionId(intent.getFrontSessionId());
            this.voiceData.getOnResultReqPostData().setFrontSessionRoundId(intent.getFrontSessionRoundId());

            List<VABaseIntent> vaBaseIntents = new ArrayList<VABaseIntent>();
            VABaseIntent vaBaseIntent = new VABaseIntent();
            vaBaseIntent.setName(intent.getIntentName());
            // 没有 actionCount。
            List<VABaseIntentSlot> vaBaseIntentSlots = intent.getVaSlots();
            vaBaseIntent.setSlots(vaBaseIntentSlots);
            vaBaseIntents.add(vaBaseIntent);
            this.voiceData.getOnResultReqPostData().setIntent(vaBaseIntents);

            this.voiceData.getOnResultReqPostData().setActionType(actionType);
            List<VAActionEntity> vaActionEntities = new ArrayList<VAActionEntity>();
            DecimalFormat decimalFormat = new DecimalFormat("######0");
            VAActionEntity vaActionEntity = getExitResult(0,
                    Integer.parseInt(decimalFormat.format(second * 1000)));
            vaActionEntities.add(vaActionEntity);
            this.voiceData.getOnResultReqPostData().setVaActionList(vaActionEntities);

            // TODO: Convert Class to JSON.
            VoiceCallback2BO.getInstance().onResult(this.voiceData.getOnResultReqPostData());
        } catch (Exception e) {
            sLog.i(LOG_TAG, "sendSessionOver: Exception: " + e);
        }
    }
    // END ONRESULT ////////////////////////////////////////////////////////////////////////////////

    // 根据 VAIntent 调用对应的方法。
    // https://www.tutorialspoint.com/javareflect/javareflect_method_invoke.htm
    private void invokeIntent(VAIntent vaIntent) throws Exception {
        sLog.i(null, "invokeIntent: vaIntent: " + vaIntent);
        VA3rdBiz va3rdBiz = new VA3rdBiz();
        Method method = va3rdBiz.getClass().getMethod(vaIntent.getIntentName());
        method.invoke(va3rdBiz);
    }

    // START API FOR 3RD ZONE //////////////////////////////////////////////////////////////////////
    public void vaExit(VAIntent vaIntent, double second) throws Exception {
        sLog.i(LOG_TAG, "vaExit: vaIntent: " + vaIntent);
        sLog.i(LOG_TAG, "vaExit: second: " + second);
        sendSessionOver(2, vaIntent, second);
        // 按照设计，VA Framework 只有一个线程，处理一份 VoiceData，处理的 VoiceData 为 VA Framework 的成员
        // 变量。因此，只需要终止该线程即可，成员变量中的数据将在下一次 VA Framework 处理 VoiceData 时被新的数
        // 据覆盖。
        isVoiceDataEmpty = true;
        voiceData.threadInterrupt();
    }

    // 在原有的线程中处理新的 VAIntent。
    public void vaLeave() throws Exception {
        try {
            invokeIntent(this.getVoiceData().getVaIntents().get(0));
        } catch (Exception e) {
            sLog.i(LOG_TAG, "run: Exception: ", e);
        }
    }

    public void vaFinish() {
        isVoiceDataEmpty = true;
        voiceData.threadInterrupt();
    }

    public void setPickuplist(List<String> pickuplist) {
        JSONArray array = new JSONArray();
        for(String str:pickuplist){
            array.put(str);
        }

        VoiceCallback2BO.getInstance().onPickuplistResult( array.toString());

    }
    // END API FOR 3RD ZONE ////////////////////////////////////////////////////////////////////////
}