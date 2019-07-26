package com.zcnet.voiceinteractionmodule;


import com.google.gson.Gson;
import com.z.tinyapp.utils.logs.sLog;
import com.zcnet.voice_callback_lib.IVoiceDataCallBack;
import com.zcnet.voice_callback_lib.VoiceCallback2BO;
import com.zcnet.voice_callback_lib.voice_data.VABaseOnCompleteReqPostData;
import com.zcnet.voiceinteractionmodule.Enum.ActionEnum;
import com.zcnet.voiceinteractionmodule.Enum.ActionTypeEnum;
import com.zcnet.voiceinteractionmodule.Enum.DialogStatusEnum;
import com.zcnet.voiceinteractionmodule.Enum.EmotionEnum;
import com.zcnet.voiceinteractionmodule.Enum.IntentSimilarEnum;
import com.zcnet.voiceinteractionmodule.Enum.IsPoliteEnum;
import com.zcnet.voiceinteractionmodule.Enum.IsQuestionEnum;
import com.zcnet.voiceinteractionmodule.Enum.PriorityEnum;
import com.zcnet.voiceinteractionmodule.Enum.ReEnterEnum;
import com.zcnet.voiceinteractionmodule.Enum.ResetAiSessionEnum;
import com.zcnet.voiceinteractionmodule.InterData.voiceInterfaceBean_Dialog;
import com.zcnet.voiceinteractionmodule.common.Constants;
import com.zcnet.voiceinteractionmodule.common.Intent;
import com.zcnet.voiceinteractionmodule.common.Slot;
import com.zcnet.voiceinteractionmodule.common.SlotSnapshot;
import com.zcnet.voiceinteractionmodule.common.Text;
import com.zcnet.voiceinteractionmodule.jsonEntity.unit2.Response;
import com.zcnet.voiceinteractionmodule.jsonEntity.unit2.Result;
import com.zcnet.voiceinteractionmodule.jsonEntity.unit2.Unit2JsonRootBean;
import com.zcnet.voiceinteractionmodule.parseJsonEntity.BotJsonRootBean;
import com.zcnet.voiceinteractionmodule.parseJsonEntity.Dialog_string_table;
import com.zcnet.voiceinteractionmodule.parseJsonEntity.IntentsReqEntity;
import com.zcnet.voiceinteractionmodule.parseJsonEntity.NLPInfoEx;
import com.zcnet.voiceinteractionmodule.parseJsonEntity.Slots;
import com.zcnet.voiceinteractionmodule.service.IntentSlotService;
import com.zcnet.voiceinteractionmodule.usered.APIResult;
import com.zcnet.voiceinteractionmodule.usered.BaseEntitySlots;
import com.zcnet.voiceinteractionmodule.usered.BaseIntentEntity;
import com.zcnet.voiceinteractionmodule.usered.BaseOnResultReqPostData;
import com.zcnet.voiceinteractionmodule.usered.BotDialogResult;
import com.zcnet.voiceinteractionmodule.usered.BotDialogResultGetPhase6APIResult;
import com.zcnet.voiceinteractionmodule.usered.IntentBaiduAI;
import com.zcnet.voiceinteractionmodule.usered.ScenarioBaiduAI;
import com.zcnet.voiceinteractionmodule.usered.ScenarioJsonRootBean;
import com.zcnet.voiceinteractionmodule.usered.SlotBaiduAIJsonEntity;
import com.zcnet.voiceinteractionmodule.util.CheckUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class voiceAPI {
    private static final String CMD_UPDATE = "CMD_UPDATE";
    private static final String YES = "yes";
    private static final String NONE = "none";
    private static final String PREDEFINEDHISTORY = "predefined_history";
    private static final String NO = "no";
    private static final String S_OK = "S00";
    private static final  String TAG = "sunVoiceApi";

    private IntentSlotService intentSlotService = new IntentSlotService();

    //俺就是来顶个包解决编译问题滴
    private Long userId = new Long(111);

    //意图栈
    private static Stack<Intent> stack = new Stack<Intent>();

    //意图快照
    private static LinkedList<SlotSnapshot> slotSnapshot = new LinkedList<>();

    private IBoDataCallBack mCallBack;

    //场景配置文件
    private String mConfigStr = "";

    public voiceAPI(IBoDataCallBack callBack) {
        mCallBack = callBack;
        VoiceCallback2BO.getInstance().setPickuplistCallback(new IVoiceDataCallBack() {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONArray array = new JSONArray(data);
                    int len = array.length();
                    for (int i = 0; i < len; ++ i) {
                        SessionInstance.getInstance().addToPickuplist(array.getString(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected APIResult updateOnResult(String strContent)
            throws Exception {

        Gson gson = new Gson();
        BaseOnResultReqPostData postData = gson.fromJson(strContent, BaseOnResultReqPostData.class);

        APIResult result = new APIResult();
        result.setCode(S_OK);

        Intent currIntent = null;
        // 词槽transformer更新
        updateTransformerPhase6(postData, currIntent);


        // 2:识别完成
        if (postData.getActionType().equals(ActionTypeEnum.RECOGNITION_COMPLETE.getValue())) {
            // update record
//            currIntent = intentService.getById(currIntent.getId());
//            currIntent.setActionCount(currIntent.getActionCount() + 1);
//            currIntent.setStatus(Constants.INTENT_STATUS_COMPLETE);
//            intentService.update(currIntent);

            int stackSize = stack.size();

            // 识别完成时，栈顶意图出栈
            stack.pop();

            // 当第三方发来actionType为：识别完成 && 意图栈中仅存在一个意图的场合，把应答结果消息推送到前端。
            // 仅做消息推送，不保存到redis.
            if (1 == stackSize) {

                //  TODO 推送消息
//                pushTinyappplateformMessagePhase6(postData, currIntent, deviceCode);
            }

            return result;
        }

        Intent outIntent = null;
        if (!stack.isEmpty()) {
            outIntent = stack.peek();
//            outIntent = intentService.getById(outIntent.getId());
        }


        if (ResetAiSessionEnum.YES.value == postData.getResetAiSession().intValue()) {
            if (!stack.isEmpty()) {
                // 会话重置时，清空意图ai_session_id与last_ai_session_id字段
                currIntent.setAiSessionId("");
                currIntent.setLastAiSessionId("");
//                intentService.update(currIntent);
            }
        }

// Text textFromPostData = postData.getFrontActionList().get(0).getTextList().get(0);
        // TODO if id != null 方言处理
//        IntentBaiduAI intentBaiduAI = getScenarioBaiduAIByIntent(scenarioBaiduAI, currIntent.getIntent());

        //TODO 用来回溯
//        String textStr = actionResultPhase6(postData, intentBaiduAI, userId, currIntent.getId(), botJsonRootBean);
//        String text = actionBackProcessPhase6(outIntent, currIntent, textStr, userId, scenarioBaiduAI, aiRespJsonBean,
//                stack, scenarios, startDate, postData);


        BotDialogResultGetPhase6APIResult realResult = new BotDialogResultGetPhase6APIResult();
        realResult.setCode(S_OK);
        realResult.setSessionId(currIntent.getFrontSessionId());

        List<BaseIntentEntity> baseIntentList = new ArrayList<BaseIntentEntity>();
        BaseIntentEntity baseIntent = new BaseIntentEntity();
        baseIntent.setName(currIntent.getIntent());
        baseIntent.setActionCount(currIntent.getActionCount().intValue());

        // TODO:intent.slots的转换(Intent -> BaseIntentEntity)

        baseIntentList.add(baseIntent);
        realResult.setIntent(baseIntentList);

        // TODO 要对应每个场景的postData
        realResult.setVaActionList(postData.getVaActionList());
        realResult.setErrorTextList(postData.getErrorTextList());
        realResult.setBuActionList(postData.getBuActionList());


        realResult.setCode(S_OK);

        // TODO 推送消息
//        pushTinyappplateformMessagePhase6(postData, currIntent, deviceCode);

        return result;
    }

    /**
     * function action返回结果返回给前台 语言风格处理(phase6)
     */
    public String actionResultPhase6(BaseOnResultReqPostData callbackResult, IntentBaiduAI intentBaiduAI,
                                     Long userId, Long intentId, BotJsonRootBean botJsonEntity) throws Exception {


        if (CheckUtil.isNull(callbackResult.getVaActionList())
                || CheckUtil.isNull(callbackResult.getVaActionList().get(0).getTextList())) {
            return null;
        }

        Text textFromPostData = callbackResult.getVaActionList().get(0).getTextList().get(0);

        if (!CheckUtil.isNull(callbackResult.getActionType())
                && callbackResult.getActionType().equals(ActionTypeEnum.FILLSLOT)) {

            if (!CheckUtil.isNull(textFromPostData.getText())) {
                return textFromPostData.getText();
            } else if (!CheckUtil.isNull(textFromPostData.getId())) {
                // clarify_slot66
                String clarifySlotX = textFromPostData.getId();

                Dialog_string_table tableEntity = botJsonEntity.getDialog_string_table();
                Gson gson = new Gson();
                String table = gson.toJson(tableEntity);
                Map<String, Object> map = gson.fromJson(table, Map.class);

                if (!CheckUtil.isNull(map.get(clarifySlotX))) {
                    String contentResult = getReturnString(map, clarifySlotX, userId);
                    if (!CheckUtil.isNull(contentResult)) {
                        return contentResult;
                    }
                } else {
                    // 此处intent的词槽是 需要从配置文件读取的数据
                    List<SlotBaiduAIJsonEntity> slotsConfig = intentBaiduAI.getSlots();
                    for (SlotBaiduAIJsonEntity entity : slotsConfig) {
//                        Slot slot = slotService.getByIntentIdType(intentId, entity.getName());
//                        if (CheckUtil.isNull(slot)) {
                        String clarify = entity.getClarify();
                        String contentResult = getReturnString(map, clarify, userId);
                        if (!CheckUtil.isNull(contentResult)) {
                            return contentResult;
                        }
                        //remove by sunhy 存疑
//                        break;
//                        }
                    }
                }
            }
        }

        return textFromPostData.getText();
    }

    public BotDialogResult addAIProcess(String strContent)
            throws Exception {

        //解析postData
        Gson gson = new Gson();
        voiceInterfaceBean_Dialog postData = gson.fromJson(strContent, voiceInterfaceBean_Dialog.class);

        boolean newIntentFlag = false;

        // 初始化场景信息
        // 场景信息 key: 场景ID; value: 场景配置信息
        ScenarioJsonRootBean scenarioJsonRootBean = getScenarioByJSON();

        // 取得所有场景
        Map<Long, ScenarioBaiduAI> scenarios = getScenariosJson(scenarioJsonRootBean);

        // 统计AI返回置信度大于70的请求
        int over70Counter = 0;

        // 第一次请求匹配的AI返回值
        Unit2JsonRootBean matchedRootBean = null;

        // AI返回时确定的意图，key: 意图 + ai会话ID， value: 匹配的场景对象
        Map<String, ScenarioBaiduAI> secnariosCache = new HashMap<String, ScenarioBaiduAI>();

        // 处理sessionId
        String frontSessionId = postData.sessionId;


        // 当前意图
        Intent currIntent = null;
        // 出栈意图
        Intent outIntent = null;
        if (!stack.isEmpty()) {
            outIntent = stack.peek();
        }
        ConcurrentMap<String, Unit2JsonRootBean> mapCache = null;
        ConcurrentMap<String, Unit2JsonRootBean> mapAi = new ConcurrentHashMap<String, Unit2JsonRootBean>();
        boolean newSessionFlag = false;
        if (CheckUtil.isNull(frontSessionId)) {
            newSessionFlag = true;
        } else {
            currIntent = stack.pop();
        }

        //test
        ScenarioBaiduAI scenarioBaiduAI = null;
        //TODO 请求百度unit获得请求结果
//        Unit2JsonRootBean unit2RootBean = VITest.getBaiduRespone();
        Unit2JsonRootBean unit2RootBean = VITest.getZNLPResponse(postData.getContent());
        if ("bot".equals(unit2RootBean.getAction())){
            // (1) $.result.response.schema.intent_confidence、
            // (2) $.result.response.qu_res.candidates[0].intent_confidence。
            // 都大于阈值70的作为被识别出来的场景
            for (Response response : unit2RootBean.getResult().getResponse_list()) {
                Response.SchemaBean schema = response.getSchema();
                //change by sunhy
    //            if (schema.getConfidence() > 70 && getQuResIntentConfidence(response) > 70) {
                if (schema.getConfidence() > 0.7) {
                    unit2RootBean.getResult().setResponse(response);
                    over70Counter = over70Counter + 1;
                    matchedRootBean = unit2RootBean;
                    if (currIntent == null || !currIntent.getIntent().equals(schema.getIntent())) {
                        newIntentFlag = true;
                    }

                    //remove by sunhy
                    scenarioBaiduAI = scenarios.get(Long.valueOf("14509"));
                }
            }

            // 唯一超阈值意图识别失败
            if (over70Counter != 1) {
                // 执行error_action，error_action返回结果缓存下来 （错误类型：识别意图次数超过阈值）
                //TODO:return errorList
                mCallBack.onFailed();
                return null;
            }
            return addAIProcessBot(postData, scenarios, matchedRootBean, scenarioBaiduAI, unit2RootBean,
                    currIntent, outIntent, frontSessionId, newSessionFlag, newIntentFlag);

        } else {
            return  addAIProcessPickuplist(unit2RootBean);
        }

    }

    public BotDialogResult addAIProcessPickuplist(Unit2JsonRootBean unit2RootBean){
        String data = new Gson().toJson(unit2RootBean.getResult().getResponse_list().get(0).getSchema());

        VoiceCallback2BO.getInstance().onCompletePickuplist(data, new IVoiceDataCallBack() {
            @Override
            public void onSuccess(String data) {
                //mCallBack.onDataBack(data);
            }
        });
        return new BotDialogResult();
    }

    public BotDialogResult addAIProcessBot(voiceInterfaceBean_Dialog postData,
                                           Map<Long, ScenarioBaiduAI> scenarios,
                                           Unit2JsonRootBean matchedRootBean,
                                           ScenarioBaiduAI scenarioBaiduAI,
                                           Unit2JsonRootBean unit2RootBean,
                                           Intent currIntent,
                                           Intent outIntent,
                                           String frontSessionId,
                                           boolean newSessionFlag,
                                           boolean newIntentFlag)
            throws Exception {

        BotDialogResult result = new BotDialogResult();
        String frontSessionRoundId = "";

        String intentStrFromAIResp = matchedRootBean.getResult().getResponse().getSchema().getIntent();
        IntentBaiduAI intentBaiduAI = getScenarioBaiduAIByIntent(scenarioBaiduAI, intentStrFromAIResp);
        Result aiResult = matchedRootBean.getResult();
        String clarifySlot = getClarifySlot(matchedRootBean);
        if (newSessionFlag) {
            sLog.i(null, "newSessionFlag");
            if (!isActionIntent(matchedRootBean, scenarioBaiduAI)) {
                // 执行error_action，error_action返回结果缓存下来（错误类型：无动作意图无法寄生）
                throw new RuntimeException("无动作意图无法寄生");
            } else {
                // 从百度结果中构造意图对象
                Intent newIntent = new Intent();
                newIntent.setAiSessionId(unit2RootBean.getResult().getBot_session());
                //remove by sunhy
                newIntent.setAiSceneId(scenarioBaiduAI.getId());
                newIntent.setIntent(aiResult.getResponse().getSchema().getIntent());
                newIntent.setStatus(Constants.INTENT_STATUS_DECIDED);
                newIntent.setActionCount(0L);
                newIntent.setFrontSessionId(frontSessionId);
                newIntent.setFrontSessionRoundId(getFrontSessionRoundId(frontSessionId,
                        newIntent.getFrontSessionRoundId()));
//                newIntent.setLastAiSessionId(getValueFromAIJsonStr(aiResult.getBot_session(), "session_id"));

                // 意图入栈
                stack.push(newIntent);

                currIntent = newIntent;

                // 填写词槽
                List<Slot> slotList = addSlot(aiResult, newIntent, scenarioBaiduAI);
                sLog.i(null, "onComplete start");
                onComplete(
//                        null,
                        //changed by sunhy
                        intentBaiduAI.getOnComplete(),
                        null,
                        getIntentFromBaiduAI(scenarioBaiduAI, matchedRootBean, 0),
                        aiRespJson2NLPInfoEx(matchedRootBean),
                        ActionEnum.ENTER,
                        currIntent.getAiSessionId(),
                        //changed by sunhy
//                        currIntent.getAiSceneId().toString(),
                        "",
                        DialogStatusEnum.DECIDED,
                        PriorityEnum.NO_DELAY,
                        IntentSimilarEnum.STACK_EMPTY,
                        userId,
                        currIntent.getFrontSessionId(),
                        currIntent.getFrontSessionRoundId(),
                        postData.frontInfo);

                // 请求结果
                result = new BotDialogResult();
                result.setCode(S_OK);
                result.setFrontSessionRoundId(newIntent.getFrontSessionRoundId());
            }
        } else {
            boolean extendSlotFlag = false;
            // 词槽继承
            List<SlotSnapshot> slotSnapInsertList = slotSnapshot;
            List<Slot> extendSlotInsertList = new ArrayList<Slot>();
            if (newIntentFlag) {

                Intent newIntent = new Intent();

                newIntent.setAiSessionId(getValueFromAIJsonStr(aiResult.getBot_session(), "session_id"));
                newIntent.setAiSceneId(scenarioBaiduAI.getId());
                newIntent.setIntent(aiResult.getResponse().getSchema().getIntent());
                newIntent.setStatus(Constants.INTENT_STATUS_DECIDED);
                newIntent.setActionCount(0L);
                newIntent.setFrontSessionId(frontSessionId);
                newIntent.setFrontSessionRoundId(frontSessionRoundId);
                newIntent.setLastAiSessionId(getValueFromAIJsonStr(aiResult.getBot_session(), "session_id"));
                currIntent = newIntent;
//                Long extendSlotAiSessionId = getExtendIntentId(currIntent, newIntentFlag);
//                if (!CheckUtil.isNull(extendSlotAiSessionId)) {
//                    extendSlot(scenarioBaiduAI, currIntent, extendSlotAiSessionId, slotSnapInsertList,
//                            extendSlotInsertList, null);
//                }
                // 添加关联关系
//                slotService.insertList(extendSlotInsertList);
//                slotSnapshotService.insertList(slotSnapInsertList);
//                addIntentSlotToDB(currIntent.getId(), extendSlotInsertList);
                extendSlotFlag = true;
                // stack.push(currIntent);
            }

// validateNull(outIntent);


            if (!CheckUtil.isNull(outIntent) && currIntent.getIntent().equals(outIntent.getIntent())
                    && currIntent.getFrontSessionId().equals(outIntent.getFrontSessionId())) {// 识别出的意图与当前栈顶意图相同
// 0823添加会话ID相同
                currIntent.setLastAiSessionId(getValueFromAIJsonStr(aiResult.getBot_session(), "session_id"));

                // “仅填槽”
                if (isAiRespContainCMD(aiResult)) {
                    // 执行词槽填槽通知restful url 填槽URL为null时，无需调用

                    // CALL 词槽填槽(意图)
                    List<Slot> slotList = addSlot(aiResult, currIntent, scenarioBaiduAI);

                    // 合并词槽
                    Map<String, Slot> slotInsertListMap = new HashMap<String, Slot>();
                    if (!CheckUtil.isNull(slotList)) {
                        for (Slot entity : slotList) {
                            slotInsertListMap.put(entity.getType(), entity);
                        }
                    }
                    if (!CheckUtil.isNull(extendSlotInsertList)) {
                        for (Slot entity : extendSlotInsertList) {
                            if (!slotInsertListMap.containsKey(entity.getType())) {
                                slotList.add(entity);
                            }
                        }
                    }

                    // 执行action，action返回结果返回给前台
                    // 调用action接口。
                    onComplete(intentBaiduAI.getOnComplete(), null,
                            getIntentsReqEntity(scenarioBaiduAI, currIntent, slotList, clarifySlot),
                            aiRespJson2NLPInfoEx(matchedRootBean), ActionEnum.REMAIN, currIntent.getAiSessionId(),
                            currIntent.getAiSceneId().toString(), DialogStatusEnum.FILLING, PriorityEnum.NO_DELAY,
                            IntentSimilarEnum.SAME, userId, currIntent.getFrontSessionId(),
                            currIntent.getFrontSessionRoundId(), postData.frontInfo);
                } else {// 不是"仅填槽"的场合，要判断是意图重入还是意图合并
                    String reEnter = intentBaiduAI.getReEnterable();
                    String onDuplicateUrl = intentBaiduAI.getOnDuplicate();
                    if (!CheckUtil.isNull(reEnter) && reEnter.equals(YES) && !CheckUtil.isNull(onDuplicateUrl)
                            && !onDuplicateUrl.equals(NONE)) {
                        ScenarioBaiduAI outScenarioBaiduAI = scenarios.get(outIntent.getAiSceneId());
                        //TODO 询问第三方业务是否重入
                        ReEnterEnum reEnterRet = onDuplicate();

                        if (reEnterRet.equals(ReEnterEnum.YES)) {

                            if (hasPredefinedPriority(matchedRootBean)) {// 优先级延后
                                // 当前栈顶意图B暂存
                                // 当前栈顶意图B出栈
                                // 新意图C入栈
                                // 新意图C填槽
                                // 暂存的意图B入栈
                                // 意图B执行action
                                // CALL:action后处理(currIntent,action返回结果)
                                Intent tempIntent = outIntent;
                                outIntent = stack.pop();
                                stack.push(currIntent);
                                List<Slot> slotList = addSlot(matchedRootBean.getResult(), currIntent, scenarioBaiduAI);
                                stack.add(tempIntent);

                                // 调用action接口。
                                // A1(Curr=A1, Prev=null, REMAIN, FILLING, NO_DELAY, SAME)
                                onComplete(intentBaiduAI.getOnComplete(), null,
                                        getIntentsReqEntity(scenarioBaiduAI, currIntent, slotList, clarifySlot),
                                        aiRespJson2NLPInfoEx(matchedRootBean), ActionEnum.REMAIN,
                                        currIntent.getAiSessionId(), currIntent.getAiSceneId().toString(),
                                        DialogStatusEnum.FILLING, PriorityEnum.NO_DELAY, IntentSimilarEnum.SAME,
                                        userId, currIntent.getFrontSessionId(), currIntent.getFrontSessionRoundId(),
                                        postData.frontInfo);
                            } else {// 优先级不延后
                                if (!newIntentFlag) {
                                    Intent newIntent = new Intent();
                                    newIntent.setAiSessionId(getValueFromAIJsonStr(aiResult.getBot_session(),
                                            "session_id"));
                                    newIntent.setAiSceneId(scenarioBaiduAI.getId());
                                    newIntent.setIntent(aiResult.getResponse().getSchema().getIntent());
                                    newIntent.setStatus(Constants.INTENT_STATUS_DECIDED);
                                    newIntent.setActionCount(0L);
                                    newIntent.setFrontSessionId(currIntent.getFrontSessionId());
                                    newIntent.setFrontSessionRoundId(currIntent.getFrontSessionRoundId());
                                    newIntent.setLastAiSessionId(getValueFromAIJsonStr(aiResult.getBot_session(),
                                            "session_id"));
                                    currIntent = newIntent;
                                    newIntentFlag = true;
                                }
                                stack.push(currIntent);

                                // 填槽
                                List<Slot> slotList = addSlot(aiResult, currIntent, scenarioBaiduAI);

                                // 踢出已添加的词槽
                                Map<String, Slot> addedSlotMap = new HashMap<String, Slot>();
                                for (Slot entity : slotList) {
                                    addedSlotMap.put(entity.getType(), entity);
                                }

                                // 词槽继承
                                List<Slot> slotInsertList = new ArrayList<Slot>();
                                slotSnapInsertList.clear();
                                slotInsertList.clear();
                                if (!extendSlotFlag) {
//                                    Long extendSlotAiSessionId = getExtendIntentId(currIntent, newIntentFlag);
//                                    if (CheckUtil.isNull(extendSlotAiSessionId)) {
//                                        extendSlotAiSessionId = outIntent.getId();
//                                    }
//                                    extendSlot(scenarioBaiduAI, currIntent, extendSlotAiSessionId, slotSnapInsertList,
//                                            slotInsertList, addedSlotMap);
                                }

                                // 词槽重置
//                                resetSlot(scenarioBaiduAI, currIntent);

                                for (Slot extendSlot : slotInsertList) {
                                    if (addedSlotMap.containsKey(extendSlot.getType())) {
                                        slotInsertList.remove(extendSlot);
                                    }
                                }

                                for (SlotSnapshot extendSlotSnap : slotSnapInsertList) {
                                    if (addedSlotMap.containsKey(extendSlotSnap.getType())) {
                                        slotSnapInsertList.remove(extendSlotSnap);
                                    }
                                }

//                                addIntentSlotToDB(currIntent.getId(), slotInsertList);

                                slotInsertList.addAll(slotList);

                                // A1(Curr=A1, Prev=null, REMAIN, RE_ENTER, NO_DELAY, SAME)
                                onComplete(intentBaiduAI.getOnComplete(), null,
                                        getIntentsReqEntity(scenarioBaiduAI, outIntent, null, clarifySlot),
                                        aiRespJson2NLPInfoEx(matchedRootBean), ActionEnum.REMAIN,
                                        currIntent.getAiSessionId(), currIntent.getAiSceneId().toString(),
                                        DialogStatusEnum.RE_ENTER, PriorityEnum.NO_DELAY, IntentSimilarEnum.SAME,
                                        userId, currIntent.getFrontSessionId(), currIntent.getFrontSessionRoundId(),
                                        postData.frontInfo);

                                // A2(Curr=A2, Prev=A1, ENTER, RE_ENTER, NO_DELAY, SAME)
                                onComplete(intentBaiduAI.getOnComplete(),
                                        getIntentsReqEntity(scenarioBaiduAI, outIntent, null, clarifySlot),
                                        getIntentsReqEntity(scenarioBaiduAI, currIntent, slotInsertList, clarifySlot),
                                        aiRespJson2NLPInfoEx(matchedRootBean), ActionEnum.ENTER,
                                        currIntent.getAiSessionId(), currIntent.getAiSceneId().toString(),
                                        DialogStatusEnum.RE_ENTER, PriorityEnum.NO_DELAY, IntentSimilarEnum.SAME,
                                        userId, currIntent.getFrontSessionId(), currIntent.getFrontSessionRoundId(),
                                        postData.frontInfo);
                            }
                        }
                        result.setCode(S_OK);
                        result.setFrontSessionRoundId(currIntent.getFrontSessionRoundId());
                        return result;
                    }
                    // 意图合并
                    // CALL 词槽填槽(意图)
                    List<Slot> slotList = addSlot(aiResult, currIntent, scenarioBaiduAI);

                    Map<String, Slot> slotInsertListMap = new HashMap<String, Slot>();
                    if (!CheckUtil.isNull(slotList)) {
                        for (Slot entity : slotList) {
                            slotInsertListMap.put(entity.getType(), entity);
                        }
                    }
                    if (!CheckUtil.isNull(extendSlotInsertList)) {
                        for (Slot entity : extendSlotInsertList) {
                            if (!slotInsertListMap.containsKey(entity.getType())) {
                                slotList.add(entity);
                            }
                        }
                    }

                    // 执行action，action返回结果返回给前台
                    // 调用action接口。
                    IntentsReqEntity intentsReqEntity = null;

                    if (isUserHistory(matchedRootBean)) {
//                        intentsReqEntity = getIntentsReqEntity(scenarioBaiduAI, currIntent, slotList, clarifySlot);
                    } else {
//                        intentsReqEntity = getIntentFromBaiduAIDiff(scenarioBaiduAI, matchedRootBean, 0);
                    }

                    onComplete(intentBaiduAI.getOnComplete(), null,
                            getIntentsReqEntity(scenarioBaiduAI, currIntent, slotList, clarifySlot),
                            aiRespJson2NLPInfoEx(matchedRootBean), ActionEnum.REMAIN, currIntent.getAiSessionId(),
                            currIntent.getAiSceneId().toString(), DialogStatusEnum.MERGE, PriorityEnum.NO_DELAY,
                            IntentSimilarEnum.SAME, userId, currIntent.getFrontSessionId(),
                            currIntent.getFrontSessionRoundId(), postData.frontInfo);
                }
            } else {// (识别出的意图与当前栈顶意图不同)
                if (!isActionIntent(matchedRootBean, scenarioBaiduAI)) {// 无动作意图不入栈
                    if (isContainAllSlots(currIntent, outIntent, scenarios)) { // 寄生状态

                        List<Slot> slotInsertList = null;
                        if (isAiRespContainCMD(aiResult)) {// "仅填槽"的场合
                            slotInsertList = addSlot(aiResult, currIntent, scenarioBaiduAI);
                        } else {// 变成了寄生状态
                            slotInsertList = addSlot(aiResult, currIntent, scenarioBaiduAI);
                        }

                        Map<String, Slot> slotInsertListMap = new HashMap<String, Slot>();
                        if (!CheckUtil.isNull(slotInsertList)) {
                            for (Slot entity : slotInsertList) {
                                slotInsertListMap.put(entity.getType(), entity);
                            }
                        }
                        if (!CheckUtil.isNull(extendSlotInsertList)) {
                            for (Slot entity : extendSlotInsertList) {
                                if (!slotInsertListMap.containsKey(entity.getType())) {
                                    slotInsertList.add(entity);
                                }
                            }
                        }

                        onComplete(intentBaiduAI.getOnComplete(), null,
                                getIntentsReqEntity(scenarioBaiduAI, currIntent, slotInsertList, clarifySlot),
                                aiRespJson2NLPInfoEx(matchedRootBean), ActionEnum.REMAIN, currIntent.getAiSessionId(),
                                currIntent.getAiSceneId().toString(), DialogStatusEnum.FILLING, PriorityEnum.NO_DELAY,
                                IntentSimilarEnum.SAME, userId, currIntent.getFrontSessionId(),
                                currIntent.getFrontSessionRoundId(), postData.frontInfo);

                    } else {// “无动作意图”的“词槽”不含在当前栈顶“意图”中 --> 无法填槽和寄生，出错
                        onComplete(intentBaiduAI.getOnComplete(), null,
                                getIntentsReqEntity(scenarioBaiduAI, currIntent, null, clarifySlot),
                                aiRespJson2NLPInfoEx(matchedRootBean), ActionEnum.REMAIN, currIntent.getAiSessionId(),
                                currIntent.getAiSceneId().toString(), DialogStatusEnum.FILLING, PriorityEnum.NO_DELAY,
                                IntentSimilarEnum.SAME, userId, currIntent.getFrontSessionId(),
                                currIntent.getFrontSessionRoundId(), postData.frontInfo);
                    }
                } else {// 识别出的意图是有动作意图

                    if (isContainAllSlots(currIntent, outIntent, scenarios)) {
                        // 词槽继承
                        if (!extendSlotFlag) {
//                            Long extendSlotAiSessionId = getExtendIntentId(currIntent, newIntentFlag);

//                            if (CheckUtil.isNull(extendSlotAiSessionId)) {
//                                extendSlotAiSessionId = outIntent.getId();
//                            }
//                            extendSlot(scenarioBaiduAI, currIntent, extendSlotAiSessionId, slotSnapInsertList,
//                                    extendSlotInsertList, null);
//
//                            addIntentSlotToDB(currIntent.getId(), extendSlotInsertList);
                        }


                        if (hasPredefinedPriority(matchedRootBean)) {// 优先级延后
                            Intent tempIntent = outIntent;
                            stack.push(currIntent);
                            outIntent = currIntent;

                            List<Slot> slotList = addSlot(matchedRootBean.getResult(), currIntent, scenarioBaiduAI);
                            stack.add(tempIntent);

                            //remove by sunhy
//                            String respText = "好的，"
//                                    + matchedRootBean.getResult().getResponse().getQu_res().getRaw_query();


                            currIntent = tempIntent;

                            currIntent.setFrontSessionRoundId(frontSessionRoundId);

                            ScenarioBaiduAI scenarioBaiduAIPriority = scenarios.get(currIntent.getAiSceneId());

                            IntentBaiduAI intentBaiduAIPriority = getScenarioBaiduAIByIntent(scenarioBaiduAIPriority,
                                    currIntent.getIntent());

                            // A(Curr=A, Prev=null, REMAIN, FILLING, NO_DELAY, SIMILAR)
                            onComplete(intentBaiduAIPriority.getOnComplete(), null,
                                    getIntentsReqEntity(scenarioBaiduAIPriority, currIntent, null, clarifySlot),
                                    aiRespJson2NLPInfoEx(matchedRootBean), ActionEnum.REMAIN,
                                    currIntent.getAiSessionId(), currIntent.getAiSceneId().toString(),
                                    DialogStatusEnum.FILLING, PriorityEnum.NO_DELAY, IntentSimilarEnum.SIMILAR, userId,
                                    currIntent.getFrontSessionId(), currIntent.getFrontSessionRoundId(),
                                    postData.frontInfo);


                        } else { // 优先级不延后
                            // 原意图不变，重新入栈，新意图入栈
                            stack.push(currIntent);

                            // 词槽重置
//                            resetSlot(scenarioBaiduAI, currIntent);

                            // CALL 词槽填槽(新意图)
                            List<Slot> slotInsertList = addSlot(aiResult, currIntent, scenarioBaiduAI);
                            Map<String, Slot> slotInsertListMap = new HashMap<String, Slot>();
                            if (!CheckUtil.isNull(slotInsertList)) {
                                for (Slot entity : slotInsertList) {
                                    slotInsertListMap.put(entity.getType(), entity);
                                }
                            }
                            if (!CheckUtil.isNull(extendSlotInsertList)) {
                                for (Slot entity : extendSlotInsertList) {
                                    if (!slotInsertListMap.containsKey(entity.getType())) {
                                        slotInsertList.add(entity);
                                    }
                                }
                            }

                            // A(Curr=A, Prev=null, REMAIN, DECIDED, NO_DELAY, SIMILAR)
                            onComplete(intentBaiduAI.getOnComplete(), null,
                                    getIntentsReqEntity(scenarioBaiduAI, outIntent, null, clarifySlot),
                                    aiRespJson2NLPInfoEx(matchedRootBean), ActionEnum.REMAIN,
                                    currIntent.getAiSessionId(), currIntent.getAiSceneId().toString(),
                                    DialogStatusEnum.DECIDED, PriorityEnum.NO_DELAY, IntentSimilarEnum.SIMILAR, userId,
                                    currIntent.getFrontSessionId(), currIntent.getFrontSessionRoundId(),
                                    postData.frontInfo);


                            // B(Curr=B, Prev=A, ENTER, DECIDED, NO_DELAY, SIMILAR)
                            onComplete(intentBaiduAI.getOnComplete(),
                                    getIntentsReqEntity(scenarioBaiduAI, outIntent, null, clarifySlot),
                                    getIntentsReqEntity(scenarioBaiduAI, currIntent, slotInsertList, clarifySlot),
                                    aiRespJson2NLPInfoEx(matchedRootBean), ActionEnum.ENTER,
                                    currIntent.getAiSessionId(), currIntent.getAiSceneId().toString(),
                                    DialogStatusEnum.DECIDED, PriorityEnum.NO_DELAY, IntentSimilarEnum.SIMILAR, userId,
                                    currIntent.getFrontSessionId(), currIntent.getFrontSessionRoundId(),
                                    postData.frontInfo);
                        }
                    } else {// 意图不相似
                        // 意图栈全部清空
                        stack.clear();

                        // 新意图入栈
                        stack.push(currIntent);
                        // CALL 词槽填槽(新意图)
                        List<Slot> slotInsertList = addSlot(aiResult, currIntent, scenarioBaiduAI);

                        Map<String, Slot> slotInsertListMap = new HashMap<String, Slot>();
                        if (!CheckUtil.isNull(slotInsertList)) {
                            for (Slot entity : slotInsertList) {
                                slotInsertListMap.put(entity.getType(), entity);
                            }
                        }
                        if (!CheckUtil.isNull(extendSlotInsertList)) {
                            for (Slot entity : extendSlotInsertList) {
                                if (!slotInsertListMap.containsKey(entity.getType())) {
                                    slotInsertList.add(entity);
                                }
                            }
                        }
                        onComplete(intentBaiduAI.getOnComplete(), null,
                                getIntentsReqEntity(scenarioBaiduAI, currIntent, slotInsertList, clarifySlot),
                                aiRespJson2NLPInfoEx(matchedRootBean), ActionEnum.ENTER, currIntent.getAiSessionId(),
                                currIntent.getAiSceneId().toString(), DialogStatusEnum.DECIDED, PriorityEnum.NO_DELAY,
                                IntentSimilarEnum.NOT_SIMILAR, userId, currIntent.getFrontSessionId(),
                                currIntent.getFrontSessionRoundId(), postData.frontInfo);
                    }
                }
            }
        }
        result.setCode(S_OK);
        result.setFrontSessionRoundId(currIntent.getFrontSessionRoundId());
        return result;
    }

    private ScenarioJsonRootBean getScenarioByJSON() throws IOException {
//        FileInputStream inputStream = new FileInputStream("json.scenario/scenario.json");
//        ByteArrayOutputStream result = new ByteArrayOutputStream();
//        byte[] buffer = new byte[1024];
//        int length;
//        while ((length = inputStream.read(buffer)) != -1) {
//            result.write(buffer, 0, length);
//        }
//        String str = result.toString("UTF-8");

        //remove by sunhy
        String str = "{\"bot_id\":7,\"scenario_name\":\"myScenario\",\"scenario_version\":\"0.0.0.7\",\"scenarios\":[{\"name\":\"电话场景\",\"id\":14509,\"intents\":[{\"name\":\"MAKE_CALL\",\"actionable\":\"yes\",\"on_duplicate\":\"none\",\"re_enterable\":\"no\",\"slots\":[{\"name\":\"user_name\",\"must\":\"no\",\"type\":\"userdefined\",\"reset_when_intent_recognized\":\"no\",\"clarify\":\"ask_for_name\"},{\"name\":\"user_phone_num\",\"must\":\"no\",\"type\":\"userdefined\",\"reset_when_intent_recognized\":\"no\",\"clarify\":\"ask_for_phone_num\"},{\"name\":\"user_phone_type\",\"must\":\"no\",\"type\":\"userdefined\",\"reset_when_intent_recognized\":\"no\",\"clarify\":\"ask_for_phone_type\"},{\"name\":\"user_phone_loc\",\"must\":\"no\",\"type\":\"userdefined\",\"reset_when_intent_recognized\":\"no\",\"clarify\":\"ask_for_phone_loc\"},{\"name\":\"user_use_onstar\",\"must\":\"no\",\"type\":\"predefined_history\",\"reset_when_intent_recognized\":\"no\",\"clarify\":\"ask_for_use_onstar\"}]},{\"name\":\"STOP_CALL\",\"actionable\":\"yes\",\"on_duplicate\":\"none\",\"re_enterable\":\"no\"},{\"name\":\"COMM_COMFIRM\",\"actionable\":\"yes\",\"on_duplicate\":\"none\",\"re_enterable\":\"no\",\"slots\":[{\"name\":\"user_confirm_yesno\",\"must\":\"yes\",\"type\":\"userdefined\",\"reset_when_intent_recognized\":\"no\",\"clarify\":\"ask_for_confirm_yesno\"}]},{\"name\":\"COMM_SELECT\",\"actionable\":\"yes\",\"on_duplicate\":\"none\",\"re_enterable\":\"no\",\"slots\":[{\"name\":\"user_num\",\"must\":\"no\",\"type\":\"userdefined\",\"reset_when_intent_recognized\":\"no\",\"clarify\":\"ask_for_item_no\"},{\"name\":\"user_unit\",\"must\":\"no\",\"type\":\"userdefined\",\"reset_when_intent_recognized\":\"no\",\"clarify\":\"ask_for_item_unit\"},{\"name\":\"user_order\",\"must\":\"no\",\"type\":\"userdefined\",\"reset_when_intent_recognized\":\"no\",\"clarify\":\"ask_for_order\"}]},{\"name\":\"PHONE_NUM_MODIFY\",\"actionable\":\"yes\",\"on_duplicate\":\"none\",\"re_enterable\":\"no\",\"slots\":[{\"name\":\"user_prev_fragment\",\"must\":\"no\",\"type\":\"userdefined\",\"reset_when_intent_recognized\":\"no\",\"clarify\":\"ask_for_prev_fragment\"},{\"name\":\"user_curr_fragment\",\"must\":\"no\",\"type\":\"userdefined\",\"reset_when_intent_recognized\":\"no\",\"clarify\":\"ask_for_curr_fragment\"}]},{\"name\":\"CHECK_MISSING_CALLS\",\"actionable\":\"yes\",\"on_duplicate\":\"none\",\"re_enterable\":\"no\"},{\"name\":\"PHONE_NUM_CLEAR\",\"actionable\":\"yes\",\"on_duplicate\":\"none\",\"re_enterable\":\"no\"},{\"name\":\"REDIAL_CALL\",\"actionable\":\"yes\",\"on_duplicate\":\"none\",\"re_enterable\":\"no\"}]}]}";
        return new Gson().fromJson(str, ScenarioJsonRootBean.class);

        /*if (TextUtils.isEmpty(mConfigStr)) {
            mConfigStr = InterActionApplication.getConfig();
        }
        sLog.e("sunVoiceApi", "getScenarioByJSON config - > " + mConfigStr.length());
        return new Gson().fromJson(mConfigStr, ScenarioJsonRootBean.class);*/
    }

//    private double getQuResIntentConfidence(Response response) {
//        double result = 0;
//        try {
//            Candidates candidates = response.getQu_res().getCandidates().get(0);
//            result = candidates.getIntent_confidence();
//        } catch (Exception e) {
//        }
//
//        return result;
//    }

    private boolean isUserHistory(Unit2JsonRootBean matchedRootBean) {
        try {
            return matchedRootBean.getResult().getResponse().getQu_res().getCandidates().get(0).getSlots().get(0)
                    .getName().equals("user_history");
        } catch (Exception e) {
        }

        return false;
    }

    /**
     * 百度json中$.result.qu_res.intent_candidates[0].intent == "CMD_UPDATE*"前缀
     *
     * @param aiResult
     * @return
     */
    private boolean isAiRespContainCMD(Result aiResult) {
        try {
            return aiResult.getResponse().getQu_res().getCandidates().get(0).getIntent().startsWith(CMD_UPDATE);
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * 判断两个意图是否相似
     * <p>
     * (“无动作意图”的所有“词槽”包含当前栈顶“意图”的所有“词槽”。 没有词槽的意图不相似于其他意图，其他意图不相似于没有词槽的意图。
     *
     * @param currIntent
     * @param outIntent
     * @return
     */
    private boolean isContainAllSlots(Intent currIntent, Intent outIntent, Map<Long, ScenarioBaiduAI> scenariosMap) {
        boolean result = false;
        if (CheckUtil.isNull(currIntent)) {
            return false;
        }
        if (CheckUtil.isNull(outIntent)) {
            return false;
        }

        String currIntentName = currIntent.getIntent();

        String outIntentName = outIntent.getIntent();

        Map<String, IntentBaiduAI> outIntentMap = new HashMap<String, IntentBaiduAI>();
        if (scenariosMap.containsKey(outIntent.getAiSceneId())) {
            List<IntentBaiduAI> scenarios = scenariosMap.get(outIntent.getAiSceneId()).getIntents();
            for (IntentBaiduAI entity : scenarios) {
                outIntentMap.put(entity.getName(), entity);
            }
        }

        Map<String, IntentBaiduAI> currIntentMap = new HashMap<String, IntentBaiduAI>();
        if (scenariosMap.containsKey(currIntent.getAiSceneId())) {
            List<IntentBaiduAI> scenarios = scenariosMap.get(currIntent.getAiSceneId()).getIntents();
            for (IntentBaiduAI entity : scenarios) {
                currIntentMap.put(entity.getName(), entity);
            }
        }

        Map<String, SlotBaiduAIJsonEntity> outIntentSlots = new HashMap<String, SlotBaiduAIJsonEntity>();

        if (outIntentMap.containsKey(outIntentName)) {
            IntentBaiduAI outIntentIntentBaiduAI = outIntentMap.get(outIntentName);
            if (!CheckUtil.isNull(outIntentIntentBaiduAI)) {
                List<SlotBaiduAIJsonEntity> slots = outIntentIntentBaiduAI.getSlots();
                if (CheckUtil.isNull(slots)) {
                    return false;
                }
                for (SlotBaiduAIJsonEntity entity : slots) {
                    outIntentSlots.put(entity.getName(), entity);
                }
            }
        }
        if (currIntentMap.containsKey(currIntentName)) {
            IntentBaiduAI currIntentBaiduAI = currIntentMap.get(currIntentName);
            if (!CheckUtil.isNull(currIntentBaiduAI)) {
                boolean isContain = true;
                List<SlotBaiduAIJsonEntity> slots = currIntentBaiduAI.getSlots();
                if (CheckUtil.isNull(slots)) {
                    return false;
                }
                for (SlotBaiduAIJsonEntity entity : slots) {
                    if (!outIntentSlots.containsKey(entity.getName())) {
                        isContain = false;
                        break;
                    }
                }
                result = isContain;
            }
        }

        return result;
    }


    private NLPInfoEx aiRespJson2NLPInfoEx(Unit2JsonRootBean matchedRootBean) {
        NLPInfoEx nLPInfoEx = new NLPInfoEx();
        if (hasUserPolite(matchedRootBean)) {
            nLPInfoEx.setIsPolite(IsPoliteEnum.YES.getValue());
        } else {
            nLPInfoEx.setIsPolite(IsPoliteEnum.NO.getValue());
        }
        if (hasUserQuestion(matchedRootBean)) {
            nLPInfoEx.setIsQuestion(IsQuestionEnum.YES.getValue());
        } else {
            nLPInfoEx.setIsQuestion(IsQuestionEnum.NO.getValue());
        }

        nLPInfoEx.setEmotion(EmotionEnum.UNKNOWN.getValue());

        return nLPInfoEx;
    }


    private boolean hasUserQuestion(Unit2JsonRootBean matchedRootBean) {
        boolean result = false;

        //remove by sunhy
//        try {
//            List<CandidatesSlots> slotsList = matchedRootBean.getResult().getResponse().getQu_res().getCandidates()
//                    .get(0).getSlots();
//            if (!CheckUtil.isNull(slotsList)) {
//                for (CandidatesSlots entity : slotsList) {
//                    if ("user_question".equals(entity.getName())) {
//                        result = true;
//                        break;
//                    }
//                }
//            }
//        } catch (Exception e) {
//            return false;
//        }

        return result;
    }

    /**
     * @param matchedRootBean
     * @return
     */
    private boolean hasUserPolite(Unit2JsonRootBean matchedRootBean) {
        boolean result = false;

        //remove by sunhy
//        try {
//            List<CandidatesSlots> slotsList = matchedRootBean.getResult().getResponse().getQu_res().getCandidates()
//                    .get(0).getSlots();
//
//            if (!CheckUtil.isNull(slotsList)) {
//                for (CandidatesSlots entity : slotsList) {
//                    if ("user_polite".equals(entity.getName())) {
//                        result = true;
//                        break;
//                    }
//                }
//            }
//        } catch (Exception e) {
//            return false;
//        }

        return result;

    }

    /**
     * @param matchedRootBean
     * @return
     */
    private boolean hasPredefinedPriority(Unit2JsonRootBean matchedRootBean) {
        boolean result = false;

        //remove by sunhy
//        try {
//            List<CandidatesSlots> slotsList = matchedRootBean.getResult().getResponse().getQu_res().getCandidates()
//                    .get(0).getSlots();
//
//            if (!CheckUtil.isNull(slotsList)) {
//                for (CandidatesSlots entity : slotsList) {
//                    if ("user_priority".equals(entity.getName())) {
//                        result = true;
//                        break;
//                    }
//                }
//            }
//        } catch (Exception e) {
//            return false;
//        }

        return result;

    }

    /**
     * 判断是否为动作意图
     * <p>
     * secnariosCache'key: schema.getIntent() + rootBean.getResult().getBot_session()
     * <p>
     * 根据actionable字段判断
     *
     * @param aiRootBean
     * @return
     * @throws Exception
     */
    private boolean isActionIntent(Unit2JsonRootBean aiRootBean, ScenarioBaiduAI scenarioBaiduAI) throws Exception {

        if (!CheckUtil.isNull(aiRootBean) && !CheckUtil.isNull(scenarioBaiduAI)) {
            String intent = aiRootBean.getResult().getResponse().getSchema().getIntent();
            if (!CheckUtil.isNull(scenarioBaiduAI)) {
                IntentBaiduAI intentBaiduAI = getScenarioBaiduAIByIntent(scenarioBaiduAI, intent);
                if (!CheckUtil.isNull(intentBaiduAI)) {
                    if (YES.equals(intentBaiduAI.getActionable())) {
                        return true;
                    } else {
                        //changed by sunhy
                        return true;
//                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 词槽填槽(意图)
     *
     * @param intent
     */
    public List<Slot> addSlot(Result resultFromAIResp, Intent intent, ScenarioBaiduAI scenarioBaiduAI) throws Exception {
        List<SlotSnapshot> slotSnapInsertList = new ArrayList<SlotSnapshot>();
        List<Slot> slotInsertList = new ArrayList<Slot>();

        //changed by sunhy
//        List<SchemaSlots> slotsFromAIResp = resultFromAIResp.getResponse().getSchema().getSlots();
        List<Response.SchemaBean.SlotsBeanX> slotsFromAIResp = resultFromAIResp.getResponse().getSchema().getSlots();
// List<com.zeninte.server.voiceapi.unit.entity.Slots> slotsFromAIResp = resultFromAIResp.getQu_res()
// .getIntent_candidates().get(0).getSlots();

        if (!CheckUtil.isNull(slotsFromAIResp)) {

            // 遍历百度识别的该意图的全部词槽
            for (Response.SchemaBean.SlotsBeanX slot : slotsFromAIResp) {

                // 只写入当前这轮会话中引入的词槽
                //remove by sunhy
//                if (0 != slot.getSession_offset()) {
//                    continue;
//                }

                // 稍后、刚刚不写入DB
                if ("user_priority".equals(slot.getName())) {
                    continue;
                }

                // 稍后、刚刚不写入DB
                if ("user_history".equals(slot.getName())) {
                    continue;
                }

                // 词槽的transformer != none
                String transformer = getSlotTransformer(intent.getIntent(), slot.getName(), scenarioBaiduAI);
                String transformerResult = "";
                String normalizedWord = slot.getNormalized_word();
                String originalWord = slot.getOriginal_word();

                if (CheckUtil.isNull(normalizedWord)) {
                    normalizedWord = slot.getOriginal_word();
                }

                if (!PREDEFINEDHISTORY.equals(slot.getName())) {// 词槽类型不是查询历史
                    // 填槽
                    Slot newEntity = fillSlot(slot, originalWord, normalizedWord, transformerResult);

                    slotInsertList.add(newEntity);

                    SlotSnapshot newSnapEntity = fillSlotSnapshot(slot, slot.getOriginal_word(),
                            slot.getNormalized_word(), transformerResult);
                    newSnapEntity.setAiSessionId(intent.getAiSessionId());

                    slotSnapInsertList.add(newSnapEntity);
                } else {// 词槽类型是查询历史
                    if (isSlotResetWhenIntentRecognizedNo(intent.getIntent(), slot.getName(), scenarioBaiduAI)) {// 词槽不重置
                        // TODO 查询一定时间范围内的该词槽历史上最新的值来填槽   目前起一个Map(slot_type,slot_value)记录
                    }
                }
            }
        }


//        addIntentSlotToDB(intent.getId(), slotInsertList);

        return slotInsertList;

    }


    private SlotSnapshot fillSlotSnapshot(Response.SchemaBean.SlotsBeanX slot, String originalWord, String normalizedWord,
                                          String transformerWord) {
        SlotSnapshot newSnapEntity = new SlotSnapshot();
        newSnapEntity.setType(slot.getName());
        newSnapEntity.setOriginalWord(originalWord);
        newSnapEntity.setNormalizedWord(normalizedWord);
        newSnapEntity.setTransformerWord(transformerWord);
        return newSnapEntity;
    }

    private SlotSnapshot fillSlotSnapshot(Slot slot, String originalWord, String normalizedWord, String transformerWord) {
        SlotSnapshot newSnapEntity = new SlotSnapshot();
        newSnapEntity.setType(slot.getType());
        newSnapEntity.setOriginalWord(originalWord);
        newSnapEntity.setNormalizedWord(normalizedWord);
        newSnapEntity.setTransformerWord(transformerWord);
        return newSnapEntity;
    }

    /**
     * @param slot
     * @param originalWord
     * @return
     */
    //changed by sunhy
//    private Slot fillSlot(SchemaSlots slot, String originalWord, String normalizedWord, String transformerWord) {
    private Slot fillSlot(Response.SchemaBean.SlotsBeanX slot, String originalWord, String normalizedWord, String transformerWord) {
        Slot newEntity = new Slot();
        newEntity.setType(slot.getName());
        newEntity.setOriginalWord(originalWord);
        newEntity.setNormalizedWord(normalizedWord);
        newEntity.setTransformerWord(transformerWord);
        return newEntity;
    }

    /**
     * @param slot
     * @param originalWord
     * @return
     */
    private Slot fillSlotByDb(Slot slot, String originalWord, String normalizedWord, String transformerWord) {
        Slot newEntity = new Slot();
        newEntity.setType(slot.getType());
        newEntity.setOriginalWord(originalWord);
        newEntity.setNormalizedWord(normalizedWord);
        newEntity.setTransformerWord(transformerWord);
        return newEntity;
    }

    /**
     * 获取意图配置文件
     *
     * @param scenarioBaiduAI
     * @param intent
     */
    private IntentBaiduAI getScenarioBaiduAIByIntent(ScenarioBaiduAI scenarioBaiduAI, String intent) {
        IntentBaiduAI intentBaiduAI = null;

        //changed by sunhy
        if (scenarioBaiduAI == null) {
            return null;
        }
        List<IntentBaiduAI> intents = scenarioBaiduAI.getIntents();
        for (IntentBaiduAI entity : intents) {
            if (entity.getName().equals(intent)) {
                intentBaiduAI = entity;
            }
        }

        return intentBaiduAI;
    }

    /**
     * 判断 词槽的transformer != none
     *
     * @param intentStr
     * @param slotName
     * @return
     * @throws Exception
     */
    private String getSlotTransformer(String intentStr, String slotName, ScenarioBaiduAI scenarioBaiduAI)
            throws Exception {

        if (!CheckUtil.isNull(scenarioBaiduAI)) {

            List<IntentBaiduAI> intents = scenarioBaiduAI.getIntents();
            for (IntentBaiduAI entity : intents) {
                if (entity.getName().equals(intentStr)) {

                    List<SlotBaiduAIJsonEntity> slots = entity.getSlots();
                    if(slots!=null&&slots.size()!=0){
                        for (SlotBaiduAIJsonEntity slotEntity : slots) {

                            if (slotEntity.getName().equals(slotName)) {
                                String transformer = slotEntity.getTransformer();
                                if (!NONE.equals(transformer)) {
                                    return transformer;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 判断 词槽的type != predefined_history
     *
     * @param intentStr
     * @param slotName
     * @return
     * @throws Exception
     */
    private boolean isSlotTypePredefinedHistory(String intentStr, String slotName, ScenarioBaiduAI scenarioBaiduAI)
            throws Exception {

        if (!CheckUtil.isNull(scenarioBaiduAI)) {

            List<IntentBaiduAI> intents = scenarioBaiduAI.getIntents();
            for (IntentBaiduAI entity : intents) {
                if (entity.getName().equals(intentStr)) {

                    List<SlotBaiduAIJsonEntity> slots = entity.getSlots();
                    for (SlotBaiduAIJsonEntity slotEntity : slots) {

                        if (slotEntity.getName().equals(slotName)) {
                            String type = slotEntity.getType();
                            if (!PREDEFINEDHISTORY.equals(type)) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断 词槽的type != predefined_history
     *
     * @param intentStr
     * @param slotName
     * @return
     * @throws Exception
     */
    private boolean isSlotMustNo(String intentStr, String slotName, ScenarioBaiduAI scenarioBaiduAI) throws Exception {

        if (!CheckUtil.isNull(scenarioBaiduAI)) {

            List<IntentBaiduAI> intents = scenarioBaiduAI.getIntents();
            for (IntentBaiduAI entity : intents) {
                if (entity.getName().equals(intentStr)) {

                    List<SlotBaiduAIJsonEntity> slots = entity.getSlots();
                    for (SlotBaiduAIJsonEntity slotEntity : slots) {

                        if (slotEntity.getName().equals(slotName)) {
                            String must = slotEntity.getMust();
                            if (NO.equals(must) && slotEntity.getType().equals("userdefined")) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断 词槽的reset_when_intent_recognized == no
     *
     * @param intentStr
     * @param slotName
     * @return
     * @throws Exception
     */
    private boolean isSlotResetWhenIntentRecognizedNo(String intentStr, String slotName, ScenarioBaiduAI scenarioBaiduAI)
            throws Exception {

        if (!CheckUtil.isNull(scenarioBaiduAI)) {

            List<IntentBaiduAI> intents = scenarioBaiduAI.getIntents();
            for (IntentBaiduAI entity : intents) {
                if (entity.getName().equals(intentStr)) {

                    List<SlotBaiduAIJsonEntity> slots = entity.getSlots();
                    for (SlotBaiduAIJsonEntity slotEntity : slots) {

                        if (slotEntity.getName().equals(slotName)) {
                            String resetWhenIntentRecognized = slotEntity.getResetWhenIntentRecognized();
                            if (NO.equals(resetWhenIntentRecognized)) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 添加意图词槽关联关系
     *
     * @param intentId
     * @param slotList
     * @throws Exception
     */
    private void addIntentSlotToDB(Long intentId, List<Slot> slotList) throws Exception {
        intentSlotService.insertList(intentId, slotList);
    }

    /**
     * 组织回调函数请求参数 IntentsReqEntity
     *
     * @param scenarioBaiduAI
     * @param intent
     * @return
     * @throws Exception
     */
    private IntentsReqEntity getIntentsReqEntity(ScenarioBaiduAI scenarioBaiduAI, Intent intent, List<Slot> slotList,
                                                 String clarifySlot) throws Exception {

        if (null == intent) {
            return null;
        }

        IntentsReqEntity entity = null;

        // 获取该会话已保存的词槽
        Map<String, Slot> slotMap = new HashMap<String, Slot>();
        List<Slot> intentSlots = intentSlotService.getIntentSlots(intent.getId());
        for (Slot intentSlot : intentSlots) {
            slotMap.put(intentSlot.getType(), intentSlot);
        }


        boolean clearFlag = false;
        if (!CheckUtil.isNull(slotList)) {
            for (Slot slotEntity : slotList) {
                // slot's must property no and type is userdefined need clear slotMap
                if (isSlotMustNo(intent.getIntent(), slotEntity.getType(), scenarioBaiduAI) && !clearFlag) {
                    slotMap.clear();
                    clearFlag = true;
                }
                slotMap.put(slotEntity.getType(), slotEntity);
            }
        }

        if (!CheckUtil.isNull(scenarioBaiduAI)) {
            IntentBaiduAI intentBaiduAI = getScenarioBaiduAIByIntent(scenarioBaiduAI, intent.getIntent());

            if (!CheckUtil.isNull(intentBaiduAI)) {
                entity = new IntentsReqEntity();
                entity.setName(intentBaiduAI.getName());
                entity.setActionCount(intent.getActionCount().intValue());

                List<Slots> slotsList = new ArrayList<Slots>();

                List<SlotBaiduAIJsonEntity> slotBaiduAIJsonEntityList = intentBaiduAI.getSlots();
                for (SlotBaiduAIJsonEntity slotBaiduAIJsonEntity : slotBaiduAIJsonEntityList) {
                    Slots slotsEntity = new Slots();
                    slotsEntity.setName(slotBaiduAIJsonEntity.getName());
                    slotsEntity.setClarify(slotBaiduAIJsonEntity.getClarify());
                    slotsEntity.setMust(slotBaiduAIJsonEntity.getMust());
                    Slot slotDb = slotMap.get(slotBaiduAIJsonEntity.getName());
                    if (!CheckUtil.isNull(slotDb)) {
                        String normalizedWord = slotDb.getNormalizedWord();

                        if (!CheckUtil.isNull(normalizedWord)) {
                            slotsEntity.setNormalizedWord(normalizedWord);
                        } else {
                            slotsEntity.setNormalizedWord(NONE);
                        }

                        String originalWord = slotDb.getOriginalWord();
                        if (!CheckUtil.isNull(originalWord)) {
                            slotsEntity.setOriginalWord(originalWord);
                        } else {
                            slotsEntity.setOriginalWord(NONE);
                        }

                        String transformerWord = slotDb.getTransformerWord();
                        if (!CheckUtil.isNull(transformerWord)) {
                            slotsEntity.setTransformerWord(transformerWord);
                        } else {
                            slotsEntity.setTransformerWord(NONE);
                        }

                    } else {
                        slotsEntity.setNormalizedWord(NONE);
                        slotsEntity.setOriginalWord(NONE);
                        slotsEntity.setTransformerWord(NONE);
                    }

                    slotsEntity.setResetWhenIntentRecognized(slotBaiduAIJsonEntity.getResetWhenIntentRecognized());
                    slotsEntity.setType(slotBaiduAIJsonEntity.getType());
                    if (CheckUtil.isNull(slotBaiduAIJsonEntity.getClarify())
                            || "null".equals(slotBaiduAIJsonEntity.getClarify())) {
                        slotsEntity.setClarify(NONE);
                    } else {
                        slotsEntity.setClarify(slotBaiduAIJsonEntity.getClarify());
                    }

                    if (!CheckUtil.isNull(clarifySlot) && slotsEntity.getName().equals(clarifySlot)) {
                        slotsEntity.setClarify("clarify");
                    }
                    slotsList.add(slotsEntity);

                }

                entity.setSlots(slotsList);


                //remove by sunhy
//                List<SlotGroupBaiduAIJsonEntity> slotGroupBaiduAIJsonEntityList = intentBaiduAI.getSlotGroups();
//                List<SlotGroups> slotGroups = new ArrayList<SlotGroups>();
//                for (SlotGroupBaiduAIJsonEntity slotGroupBaiduAIJsonEntity : slotGroupBaiduAIJsonEntityList) {
//                    SlotGroups slotGroup = new SlotGroups();
//                    slotGroup.setMust(slotGroupBaiduAIJsonEntity.getMust());
//                    slotGroup.setName(slotGroupBaiduAIJsonEntity.getName());
//                    slotGroup.setLeastFillSlot(Integer.parseInt(slotGroupBaiduAIJsonEntity.getLeastFillSlot()));
//
//                    List<SlotForSlotGroupJsonEntity> slotForSlotGroupJsonEntityList = slotGroupBaiduAIJsonEntity
//                            .getSlots();
//                    List<Slots> slotsFromGroup = new ArrayList<Slots>();
//                    for (SlotForSlotGroupJsonEntity slotForSlotGroupJsonEntity : slotForSlotGroupJsonEntityList) {
//                        Slots slotFromGroup = new Slots();
//                        slotFromGroup.setName(slotForSlotGroupJsonEntity.getName());
//                        slotFromGroup.setMust(slotForSlotGroupJsonEntity.getMust());
//                        slotsFromGroup.add(slotFromGroup);
//                    }
//                    slotGroup.setSlots(slotsFromGroup);
//                }
//
//                entity.setSlotGroups(slotGroups);

            }
        }

        return entity;
    }


    /**
     * 继承词槽
     *
     * @param matchedScenario    dbIntent           原会话意图
     * @param newIntent          新的会话意图
     * @param slotSnapInsertList 将继承的词槽放入快照表
     * @param slotInsertList     将继承的词槽放入词槽表
     * @throws Exception
     */
//    private void extendSlot(ScenarioBaiduAI matchedScenario, Intent newIntent, Long extendIntentId,
//                            List<SlotSnapshot> slotSnapInsertList, List<Slot> slotInsertList, Map<String, Slot> addedSlotMap)
//            throws Exception {
//
//        if (CheckUtil.isNull(addedSlotMap)) {
//            addedSlotMap = new HashMap<String, Slot>();
//        }
//
//        List<IntentSlot> intentSlotList = intentSlotService.getIntentSlots(extendIntentId);
//
//        // 获取该会话已保存的词槽
//        Map<String, Slot> slotMap = new HashMap<String, Slot>();
//        for (IntentSlot entity : intentSlotList) {
//            Slot slotEntity = slotService.getById((Long) entity.getSlot().getFk());
//            if (!CheckUtil.isNull(slotEntity) && !slotMap.containsKey(slotEntity.getType())) {
//                slotMap.put(slotEntity.getType(), slotEntity);
//            }
//        }
//
//        slotInsertList.clear();
//        slotSnapInsertList.clear();
//        for (IntentBaiduAI jsonItent : matchedScenario.getIntents()) {
//            if (newIntent.getIntent().equals(jsonItent.getName())) {
//                for (SlotBaiduAIJsonEntity jsonSlots : jsonItent.getSlots()) {
//                    String intentType = jsonSlots.getName();
//                    if (slotMap.containsKey(intentType)) {
//                        // 将继承的词槽写入词槽和词槽快照
//                        Slot slotLastEntity = slotMap.get(intentType);
//                        SlotSnapshot newSnapEntity = new SlotSnapshot();
//                        newSnapEntity.setAiSessionId(newIntent.getAiSessionId());
//                        newSnapEntity.setType(slotLastEntity.getType());
//                        newSnapEntity.setOriginalWord(slotLastEntity.getOriginalWord());
//                        newSnapEntity.setNormalizedWord(slotLastEntity.getNormalizedWord());
//                        newSnapEntity.setTransformerWord(slotLastEntity.getTransformerWord());
//
//                        SlotSnapshot dbSlotSnapshot = slotSnapshotService.getByTypeSessionid(intentType,
//                                newIntent.getAiSessionId());
//                        if (!CheckUtil.isNull(dbSlotSnapshot)) {
//                            if (!dbSlotSnapshot.getOriginalWord().equals(newSnapEntity.getOriginalWord())) {
//                                if (!addedSlotMap.containsKey(newSnapEntity.getType())) {
//                                    slotSnapInsertList.add(newSnapEntity);
//                                }
//                            }
//                        }
//
//                        Slot slotByType = slotService.getByIntentIdType(newIntent.getId(), intentType);
//
//                        if (null != slotByType) {
//                            slotService.delete(slotByType);
//                            IntentSlot intentSlot = intentSlotService.getByIntentIdAndSlotId(newIntent.getId(),
//                                    slotByType.getId());
//                            if (!CheckUtil.isNull(intentSlot)) {
//                                intentSlotService.delete(intentSlot);
//                            }
//                        }
//                        Slot newEntity = new Slot();
//
//                        newEntity.setType(slotLastEntity.getType());
//                        newEntity.setOriginalWord(slotLastEntity.getOriginalWord());
//                        newEntity.setNormalizedWord(slotLastEntity.getNormalizedWord());
//                        newEntity.setTransformerWord(slotLastEntity.getTransformerWord());
//                        if (!addedSlotMap.containsKey(newSnapEntity.getType())) {
//                            slotInsertList.add(newEntity);
//                        }
//                    }
//                }
//            }
//        }
//    }

    /**
     * 获取意图词槽map
     * <p>
     * key slot.name value SlotBaiduAIJsonEntity
     *
     * @param scenarioBaiduAI
     * @param intent
     * @return
     * @throws Exception
     */
    private Map<String, SlotBaiduAIJsonEntity> getSlotsMap(ScenarioBaiduAI scenarioBaiduAI, String intent)
            throws Exception {
        Map<String, SlotBaiduAIJsonEntity> slotsMap = new HashMap<String, SlotBaiduAIJsonEntity>();
        if (!CheckUtil.isNull(scenarioBaiduAI)) {
            // 获取意图
            List<IntentBaiduAI> intents = scenarioBaiduAI.getIntents();
            for (IntentBaiduAI intentBaiduAI : intents) {
                if (intentBaiduAI.getName().equals(intent)) {
                    List<SlotBaiduAIJsonEntity> slots = intentBaiduAI.getSlots();
                    for (SlotBaiduAIJsonEntity slotBaiduAIJsonEntity : slots) {
                        slotsMap.put(slotBaiduAIJsonEntity.getName(), slotBaiduAIJsonEntity);
                    }
                }
            }
        }
        return slotsMap;

    }

//    /**
//     * 重置词槽
//     *
//     * @param matchedScenario 场景对象
//     * @param newIntent       意图
//     */
//    private void resetSlot(ScenarioBaiduAI matchedScenario, Intent newIntent) throws Exception {
//        // json配置文件中的词槽信息
//        Map<String, SlotBaiduAIJsonEntity> map = new HashMap<String, SlotBaiduAIJsonEntity>();
//        for (IntentBaiduAI jsonItent : matchedScenario.getIntents()) {
//            if (newIntent.getIntent().equals(jsonItent.getName())) {
//                for (SlotBaiduAIJsonEntity jsonSlots : jsonItent.getSlots()) {
//                    map.put(jsonSlots.getName(), jsonSlots);
//                }
//            }
//        }
//
//        // 获取意图关联词槽newIntent tb_intent_slot tb_slot
//        // 遍历词槽DB
//        // 查看词槽是否需要重置reset_when_intent_recognized
//        // 重置DB
//        if (!CheckUtil.isNull(newIntent)) {
//            List<IntentSlot> intentSlots = intentSlotService.getIntentSlots(newIntent.getId());
//
//            if (!CheckUtil.isNull(intentSlots)) {
//                for (IntentSlot intentSlot : intentSlots) {
//                    Slot slot = slotService.getById((Long) intentSlot.getSlot().getFk());
//                    if (!CheckUtil.isNull(slot)) {
//                        SlotBaiduAIJsonEntity jsonSlots = map.get(slot.getType());
//                        if ("yes".equals(jsonSlots.getResetWhenIntentRecognized())) {
//                            // 更新tb_slot
//                            slotService.delete(slot);
//                            // 更新意图词槽绑定 tb_intent_slot
//                            intentSlotService.delete(intentSlot);
//                            // 更新快照表 tb_slot_snapshot
//                            SlotSnapshot slotSnapshot = slotSnapshotService.getByTypeSessionid(slot.getType(),
//                                    newIntent.getAiSessionId());
//                            slotSnapshotService.delete(slotSnapshot);
//                        }
//                    }
//                }
//            }
//        }
//    }
//

    /**
     * 从AI返回值中获取IntentsReqEntity对象
     */
    private IntentsReqEntity getIntentFromBaiduAI(ScenarioBaiduAI scenarioBaiduAI, Unit2JsonRootBean matchedRootBean,
                                                  int count) throws Exception {
        IntentsReqEntity entity = null;

        String intentNameAI = matchedRootBean.getResult().getResponse().getSchema().getIntent();
        IntentBaiduAI intentBaiduAI = getScenarioBaiduAIByIntent(scenarioBaiduAI, intentNameAI);

        if (!CheckUtil.isNull(intentBaiduAI)) {

            // 获取从百度AI返回的词槽信息
            //changed by sunhy
            Map<String, Response.SchemaBean.SlotsBeanX> slotMapAI = new HashMap<>();

            //changed by sunhy
//            List<SchemaSlots> botMergedSlotsList = matchedRootBean.getResult().getResponse().getSchema().getSlots();
            List<Response.SchemaBean.SlotsBeanX> botMergedSlotsList = matchedRootBean.getResult().getResponse().getSchema().getSlots();

            if (!CheckUtil.isNull(botMergedSlotsList)) {

                //changed by sunhy
//                for (SchemaSlots slotEentity : botMergedSlotsList) {
                for (Response.SchemaBean.SlotsBeanX slotEentity : botMergedSlotsList) {
                    slotMapAI.put(slotEentity.getName(), slotEentity);
                }
            }

            entity = new IntentsReqEntity();
            entity.setName(intentBaiduAI.getName());
            entity.setActionCount(count);

            List<Slots> slotsList = new ArrayList<Slots>();
            String clarifySlot = getClarifySlot(matchedRootBean);
            List<SlotBaiduAIJsonEntity> slotBaiduAIJsonEntityList = intentBaiduAI.getSlots();
            //add by sunhy
            if(slotBaiduAIJsonEntityList!=null&&slotBaiduAIJsonEntityList.size()!=0){
                for (SlotBaiduAIJsonEntity slotBaiduAIJsonEntity : slotBaiduAIJsonEntityList) {
                    Slots slotsEntity = new Slots();
                    slotsEntity.setName(slotBaiduAIJsonEntity.getName());
                    slotsEntity.setClarify(slotBaiduAIJsonEntity.getClarify());
                    slotsEntity.setMust(slotBaiduAIJsonEntity.getMust());

                    //changed by sunhy
                    Response.SchemaBean.SlotsBeanX slotFromAI = slotMapAI.get(slotBaiduAIJsonEntity.getName());
//                SchemaSlots slotFromAI = null;
                    if (!CheckUtil.isNull(slotFromAI)) {

                        String normalizedWord = slotFromAI.getNormalized_word();
                        if (!CheckUtil.isNull(normalizedWord)) {
                            slotsEntity.setNormalizedWord(normalizedWord);
                        } else {
                            slotsEntity.setNormalizedWord(NONE);
                        }

                        String originalWord = slotFromAI.getOriginal_word();
                        if (!CheckUtil.isNull(originalWord)) {
                            slotsEntity.setOriginalWord(originalWord);
                        } else {
                            slotsEntity.setOriginalWord(NONE);
                        }

                        if (NONE.equals(slotsEntity.getNormalizedWord())) {
                            slotsEntity.setNormalizedWord(slotsEntity.getOriginalWord());
                        }

                        slotsEntity.setTransformerWord(NONE);

                    } else {
                        slotsEntity.setNormalizedWord(NONE);
                        slotsEntity.setOriginalWord(NONE);
                        slotsEntity.setTransformerWord(NONE);
                    }
                    slotsEntity.setResetWhenIntentRecognized(slotBaiduAIJsonEntity.getResetWhenIntentRecognized());
                    slotsEntity.setType(slotBaiduAIJsonEntity.getType());
                    if (CheckUtil.isNull(slotBaiduAIJsonEntity.getClarify())
                            || "null".equals(slotBaiduAIJsonEntity.getClarify())) {
                        slotsEntity.setClarify(NONE);
                    } else {
                        slotsEntity.setClarify(slotBaiduAIJsonEntity.getClarify());
                    }

                    if (!CheckUtil.isNull(clarifySlot) && slotsEntity.getName().equals(clarifySlot)) {
                        slotsEntity.setClarify("clarify");
                    }

                    slotsList.add(slotsEntity);

                }
            }

            entity.setSlots(slotsList);


            //remove by sunhy
//            List<SlotGroupBaiduAIJsonEntity> slotGroupBaiduAIJsonEntityList = intentBaiduAI.getSlotGroups();
//            List<SlotGroups> slotGroups = new ArrayList<SlotGroups>();
//            if (slotGroupBaiduAIJsonEntityList != null) {
//                for (SlotGroupBaiduAIJsonEntity slotGroupBaiduAIJsonEntity : slotGroupBaiduAIJsonEntityList) {
//                    SlotGroups slotGroup = new SlotGroups();
//                    slotGroup.setMust(slotGroupBaiduAIJsonEntity.getMust());
//                    slotGroup.setName(slotGroupBaiduAIJsonEntity.getName());
//                    slotGroup.setLeastFillSlot(Integer.parseInt(slotGroupBaiduAIJsonEntity.getLeastFillSlot()));
//
//                    List<SlotForSlotGroupJsonEntity> slotForSlotGroupJsonEntityList = slotGroupBaiduAIJsonEntity.getSlots();
//                    List<Slots> slotsFromGroup = new ArrayList<Slots>();
//                    for (SlotForSlotGroupJsonEntity slotForSlotGroupJsonEntity : slotForSlotGroupJsonEntityList) {
//                        Slots slotFromGroup = new Slots();
//                        slotFromGroup.setName(slotForSlotGroupJsonEntity.getName());
//                        slotFromGroup.setMust(slotForSlotGroupJsonEntity.getMust());
//                        slotsFromGroup.add(slotFromGroup);
//                    }
//                    slotGroup.setSlots(slotsFromGroup);
//                }
//            }
//            entity.setSlotGroups(slotGroups);

        }

        return entity;
    }

    //
//    /**
//     * 从AI返回值中获取IntentsReqEntity对象
//     */
//    private IntentsReqEntity getIntentFromBaiduAIDiff(ScenarioBaiduAI scenarioBaiduAI,
//                                                      Unit2JsonRootBean matchedRootBean, int count) throws Exception {
//        IntentsReqEntity entity = null;
//
//        String intentNameAI = matchedRootBean.getResult().getResponse().getSchema().getIntent();
//        IntentBaiduAI intentBaiduAI = getScenarioBaiduAIByIntent(scenarioBaiduAI, intentNameAI);
//
//        if (!CheckUtil.isNull(intentBaiduAI)) {
//
//            // 获取从百度AI返回的词槽信息
//            Map<String, SchemaSlots> slotMapAI = new HashMap<String, SchemaSlots>();
//
//            List<SchemaSlots> botMergedSlotsList = matchedRootBean.getResult().getResponse().getSchema().getSlots();
//            if (!CheckUtil.isNull(botMergedSlotsList)) {
//                for (SchemaSlots slotEentity : botMergedSlotsList) {
//                    slotMapAI.put(slotEentity.getName(), slotEentity);
//                }
//            }
//
//            entity = new IntentsReqEntity();
//            entity.setName(intentBaiduAI.getName());
//            entity.setActionCount(count);
//
//            List<Slots> slotsList = new ArrayList<Slots>();
//            String clarifySlot = getClarifySlot(matchedRootBean);
//            List<SlotBaiduAIJsonEntity> slotBaiduAIJsonEntityList = intentBaiduAI.getSlots();
//            for (SlotBaiduAIJsonEntity slotBaiduAIJsonEntity : slotBaiduAIJsonEntityList) {
//                Slots slotsEntity = new Slots();
//                slotsEntity.setName(slotBaiduAIJsonEntity.getName());
//                slotsEntity.setClarify(slotBaiduAIJsonEntity.getClarify());
//                slotsEntity.setMust(slotBaiduAIJsonEntity.getMust());
//                SchemaSlots slotFromAI = slotMapAI.get(slotBaiduAIJsonEntity.getName());
//                if (!CheckUtil.isNull(slotFromAI)) {
//
//                    String normalizedWord = slotFromAI.getNormalized_word();
//                    if (!CheckUtil.isNull(normalizedWord)) {
//                        slotsEntity.setNormalizedWord(normalizedWord);
//                    } else {
//                        slotsEntity.setNormalizedWord(NONE);
//                    }
//
//                    String originalWord = slotFromAI.getOriginal_word();
//                    if (!CheckUtil.isNull(originalWord)) {
//                        slotsEntity.setOriginalWord(originalWord);
//                    } else {
//                        slotsEntity.setOriginalWord(NONE);
//                    }
//
//                    if (NONE.equals(slotsEntity.getNormalizedWord())) {
//                        slotsEntity.setNormalizedWord(slotsEntity.getOriginalWord());
//                    }
//                    slotsEntity.setTransformerWord(NONE);
//                } else {
//                    slotsEntity.setNormalizedWord(NONE);
//                    slotsEntity.setOriginalWord(NONE);
//                    slotsEntity.setTransformerWord(NONE);
//                }
//                slotsEntity.setResetWhenIntentRecognized(slotBaiduAIJsonEntity.getResetWhenIntentRecognized());
//                slotsEntity.setType(slotBaiduAIJsonEntity.getType());
//                if (CheckUtil.isNull(slotBaiduAIJsonEntity.getClarify())
//                        || "null".equals(slotBaiduAIJsonEntity.getClarify())) {
//                    slotsEntity.setClarify(NONE);
//                } else {
//                    slotsEntity.setClarify(slotBaiduAIJsonEntity.getClarify());
//                }
//
//                if (!CheckUtil.isNull(clarifySlot) && slotsEntity.getName().equals(clarifySlot)) {
//                    slotsEntity.setClarify("clarify");
//                }
//
//                slotsList.add(slotsEntity);
//
//            }
//
//            entity.setSlots(slotsList);
//
//            List<SlotGroupBaiduAIJsonEntity> slotGroupBaiduAIJsonEntityList = intentBaiduAI.getSlotGroups();
//            List<SlotGroups> slotGroups = new ArrayList<SlotGroups>();
//            for (SlotGroupBaiduAIJsonEntity slotGroupBaiduAIJsonEntity : slotGroupBaiduAIJsonEntityList) {
//                SlotGroups slotGroup = new SlotGroups();
//                slotGroup.setMust(slotGroupBaiduAIJsonEntity.getMust());
//                slotGroup.setName(slotGroupBaiduAIJsonEntity.getName());
//                slotGroup.setLeastFillSlot(Integer.parseInt(slotGroupBaiduAIJsonEntity.getLeastFillSlot()));
//
//                List<SlotForSlotGroupJsonEntity> slotForSlotGroupJsonEntityList = slotGroupBaiduAIJsonEntity.getSlots();
//                List<com.zeninte.server.voiceapi.json.entity.Slots> slotsFromGroup = new ArrayList<com.zeninte.server.voiceapi.json.entity.Slots>();
//                for (SlotForSlotGroupJsonEntity slotForSlotGroupJsonEntity : slotForSlotGroupJsonEntityList) {
//                    com.zeninte.server.voiceapi.json.entity.Slots slotFromGroup = new com.zeninte.server.voiceapi.json.entity.Slots();
//                    slotFromGroup.setName(slotForSlotGroupJsonEntity.getName());
//                    slotFromGroup.setMust(slotForSlotGroupJsonEntity.getMust());
//                    slotsFromGroup.add(slotFromGroup);
//                }
//                slotGroup.setSlots(slotsFromGroup);
//
//            }
//
//            entity.setSlotGroups(slotGroups);
//
//        }
//
//        return entity;
//    }
//
//    /**
//     * function error_action返回结果返回给前台
//     */
//    public BotDialogResult errorAction(TypeEnum type, Integer count, String url, Long userId, Long botId,
//                                       String frontSessionRoundId, BotJsonRootBean botJsonEntity) throws Exception {
//        BotDialogResult result = new BotDialogResult();
//        String key = REDIS_CACHE_DIALOG_ON_RESULT + frontSessionRoundId;
//
//        // 调用error_action接口。
//        CallbackResult callbackResult = callbackService.onErrorAction(url, type, count);
//        String text = "";
//        if (!CheckUtil.isNull(callbackResult.getText())) {
//            text = callbackResult.getText();
//        } else if (!CheckUtil.isNull(callbackResult.getId()) && callbackResult.getId().equals("clarify_error")) {
//            // 0:中规中矩,1:轻松活泼
//            Integer styleType = 0;
//            Style style = styleService.getByUserId(userId);
//            if (!CheckUtil.isNull(style)) {
//                styleType = style.getStyle();
//            }
//
//            if (0 == styleType) {
//                text = botJsonEntity.getDialog_string_table().getClarify_error().getNormal();
//            } else {
//                text = botJsonEntity.getDialog_string_table().getClarify_error().getLively();
//            }
//
//        }
//
//        redisCacheEx.putCacheWithExpireTime(key, text, Constants.SECONDS_OF_24_HOURS);
//
//        result.setFrontSessionRoundId(frontSessionRoundId);
//        result.setCode(S_OK);
//        return result;
//    }
//
//    /**
//     * function error_action返回结果返回给前台(phase4)
//     */
//    public BotDialogResult errorActionPhase4(TypeEnum type, Integer count, String url, Long userId,
//                                             Long botId, String frontSessionRoundId, BotJsonRootBean botJsonEntity) throws Exception {
//
//        BotDialogResult result = new BotDialogResult();
//        String key = REDIS_CACHE_DIALOG_ON_RESULT + frontSessionRoundId;
//        // 调用error_action接口。
//// CallbackResult callbackResult = callbackService.onErrorAction(url, type, count);
//        CallbackPhase4Result callbackResult = callbackService.onErrorActionPhase4(url, type, count);
//
//        List<FrontActionEntity> frontActionListRet = new ArrayList<FrontActionEntity>();
//
//        List<FrontActionEntity> frontActionList = callbackResult.getFrontActionList();
//        if (!CheckUtil.isNull(frontActionList)) {
//            for (FrontActionEntity entity : frontActionList) {
//                List<Text> textList = entity.getTextList();
//
//                if (!CheckUtil.isNull(textList)) {
//                    List<Text> textListRet = new ArrayList<Text>();
//                    for (Text textEntity : textList) {
//                        String text = textEntity.getText();
//                        String id = textEntity.getId();
//                        if (CheckUtil.isNull(text)) {
//                            if (!CheckUtil.isNull(id) && id.equals("clarify_error")) {
//                                // 0:中规中矩,1:轻松活泼
//                                Integer styleType = 0;
//                                Style style = styleService.getByUserId(userId);
//                                if (!CheckUtil.isNull(style)) {
//                                    styleType = style.getStyle();
//                                }
//
//                                if (0 == styleType) {
//                                    text = botJsonEntity.getDialog_string_table().getClarify_error().getNormal();
//                                } else {
//                                    text = botJsonEntity.getDialog_string_table().getClarify_error().getLively();
//                                }
//
//                                textEntity.setText(text);
//                            }
//                        }
//                        textListRet.add(textEntity);
//                    }
//                    entity.setTextList(textListRet);
//                }
//                frontActionListRet.add(entity);
//            }
//        }
//
//        redisCacheEx.putCacheWithExpireTime(key, frontActionListRet, Constants.SECONDS_OF_24_HOURS);
//
//        result.setFrontSessionRoundId(frontSessionRoundId);
//        result.setCode(S_OK);
//        return result;
//    }
//
//    /**
//     * function error_action返回结果返回给前台(phase6)
//     */
//    public ErrorActionPhase6APIResult errorActionPhase6(TypeEnum type, Integer count, String url, Long userId,
//                                                        Long botId, String frontSessionRoundId, BotJsonRootBean botJsonEntity) throws Exception {
//
//        // 调用error_action接口
//        CallbackPhase6Result callbackResult = callbackService.onErrorActionPhase6(url, type, count);
//        // error action的返回值
//        List<VAActionEntity> vaActionList = callbackResult.getVaActionList();
//        // 本函数的返回值
//        List<VAActionEntity> vaActionListRet = new ArrayList<VAActionEntity>();
//        // 把 对话文本语言集ID 转成 对话文本text
//        if (!CheckUtil.isNull(vaActionList)) {
//            for (VAActionEntity entity : vaActionList) {
//                List<Text> textList = entity.getTextList();
//
//                if (!CheckUtil.isNull(textList)) {
//                    List<Text> textListRet = new ArrayList<Text>();
//                    for (Text textEntity : textList) {
//                        String text = textEntity.getText();
//                        String id = textEntity.getId();
//                        if (CheckUtil.isNull(text)) {
//                            if (!CheckUtil.isNull(id) && id.equals("clarify_error")) {
//                                // 0:中规中矩,1:轻松活泼
//                                Integer styleType = 0;
//                                Style style = styleService.getByUserId(userId);
//                                if (!CheckUtil.isNull(style)) {
//                                    styleType = style.getStyle();
//                                }
//
//                                if (0 == styleType) {
//                                    text = botJsonEntity.getDialog_string_table().getClarify_error().getNormal();
//                                } else {
//                                    text = botJsonEntity.getDialog_string_table().getClarify_error().getLively();
//                                }
//
//                                textEntity.setText(text);
//                            }
//                        }
//                        textListRet.add(textEntity);
//                    }
//                    entity.setTextList(textListRet);
//                }
//                vaActionListRet.add(entity);
//            }
//        }
//
//        String key = REDIS_CACHE_DIALOG_ON_RESULT + frontSessionRoundId;
//        redisCacheEx.putCacheWithExpireTime(key, vaActionListRet, Constants.SECONDS_OF_24_HOURS);
//
//        ErrorActionPhase6APIResult result = new ErrorActionPhase6APIResult();
//        result.setVaActionList(vaActionListRet);
//        result.setBuActionList(callbackResult.getBuActionList());
//        result.setCode(S_OK);
//        return result;
//    }
//
    private String getReturnString(Map<String, Object> map, String entityName, Long userId) throws Exception {
        String result = "";

        // 0:中规中矩,1:轻松活泼
        Integer styleType = 0;
//        Style style = styleService.getByUserId(userId);
//        if (!CheckUtil.isNull(style)) {
//            styleType = style.getStyle();
//        }

        Gson gson = new Gson();
        Object tableObj = map.get(entityName);

        String tableString = gson.toJson(tableObj);

        Map<String, Object> mapContent = gson.fromJson(tableString, Map.class);
        if (0 == styleType) {
            result = mapContent.get("normal").toString();
        } else {
            result = mapContent.get("lively").toString();
        }

        return result;
    }
//
//    /**
//     * @param currIntent 当前意图
//     * @param \          用户ID
//     * @return
//     * @throws Exception
//     */
//    private Long getExtendIntentId(Intent currIntent, boolean newFlag) throws Exception {
//
//        List<Intent> intentList = intentService.getByUidFrontSessionIdUpdateTime((Long) currIntent.getUser().getFk(),
//                currIntent.getFrontSessionId());
//
//        if (!CheckUtil.isNull(intentList)) {
//            if (newFlag) {
//                if (intentList.size() > 1) {
//                    return intentList.get(1).getId();
//                }
//            } else {
//                return intentList.get(0).getId();
//            }
//        }
//        return null;
//    }


    /**
     * 获取对话唯一标识符
     *
     * @param frontSessionId
     * @param frontSessionRoundId
     * @return
     */
    public static String getFrontSessionRoundId(String frontSessionId, String frontSessionRoundId) {
        String result = "";
        Integer defaultRoundNo = 1;
        DecimalFormat df = new DecimalFormat("0000");
        if (CheckUtil.isNull(frontSessionRoundId)) {
            result = frontSessionId + "-" + df.format(defaultRoundNo);
        } else {
            try {
                String beforeRoundNo = frontSessionRoundId.split("-")[1];
                result = frontSessionId + "-" + df.format(Integer.parseInt(beforeRoundNo) + 1);
            } catch (Exception e) {
                result = frontSessionId + "-" + df.format(defaultRoundNo);
            }
        }
        return result;

    }

    private String getValueFromAIJsonStr(String botSessionStr, String key) throws Exception {
        String result = "";

        Gson gson = new Gson();
        Map<String, String> map = gson.fromJson(botSessionStr, Map.class);

        result = map.get(key);

        return result;
    }

    private String getSessionId(String sessionId) throws Exception {
        if (CheckUtil.isNull(sessionId)) {
            return "";
        }
        final String sessionTemplate = "{\"session_id\": \"$$$\"}";

        return sessionTemplate.replace("$$$", sessionId);

    }

    /**
     * 获取待澄清词槽
     *
     * @param matchedRootBean
     * @return
     * @throws Exception
     */
    private String getClarifySlot(Unit2JsonRootBean matchedRootBean) throws Exception {


        String result = "";
        try {
            //remove by sunhy
//            result = matchedRootBean.getResult().getResponse().getAction_list().get(0).getRefine_detail()
//                    .getOption_list().get(0).getInfo().getName();
        } catch (Exception e) {
        }

        return result;
    }


    /**
     * 词槽transformer更新(phase6)
     *
     * @throws Exception
     */
    public void updateTransformerPhase6(BaseOnResultReqPostData postData, Intent intent) throws Exception {

//        List<IntentSlot> intentSlots = intentSlotService.getIntentSlots(intent.getId());

        Map<String, BaseEntitySlots> reqSlotMap = new HashMap<String, BaseEntitySlots>();

        List<BaseEntitySlots> slots = postData.getIntent().get(0).getSlots();
        for (BaseEntitySlots slot : slots) {
            reqSlotMap.put(slot.getName(), slot);
        }

//        for (IntentSlot entity : intentSlots) {
//            Slot slot = slotService.getById((Long) entity.getSlot().getFk());
//            if (!CheckUtil.isNull(slot)) {
//                if (reqSlotMap.containsKey(slot.getType())) {
//                    BaseEntitySlotsPhase4 slotObj = reqSlotMap.get(slot.getType());
//                    String transformerWord = slotObj.getTransformerWord();
//                    if (!CheckUtil.isNull(transformerWord) && !NONE.equals(transformerWord)
//                            && CheckUtil.isNull(slot.getTransformerWord())) {
//                        slot.setTransformerWord(transformerWord);
//                        slotService.update(slot);
//                    }
//                }
//            }
//        }
    }

//    /**
//     * action后处理
//     *
//     * @param
//     * @param
//     * @return
//     */
//    private String actionBackProcessPhase6(Intent outIntent, Intent currIntent, String result, Long userId,
//                                           ScenarioBaiduAI scenarioBaiduAI, Unit2JsonRootBean jsonRootBean, Stack<Intent> stack,
//                                           Map<Long, ScenarioBaiduAI> scenarios, Date startDate, BaseOnResultReqPostData postData)
//            throws Exception {
//        // 不应该再执行出栈操作，栈内是该意图本身
//        if (null == currIntent) {
//            currIntent = outIntent;
//            outIntent = stack.peek();
//            if (null != outIntent) {
//                outIntent = intentService.getById(outIntent.getId());
//            }
//            return backtrackingPhase6(outIntent, currIntent, result, true, scenarioBaiduAI, jsonRootBean, stack,
//                    scenarios, startDate, postData);
//        } else if (postData.getActionType().intValue() == ActionTypeEnum.RECOGNITION_COMPLETE.value) {// 意图识别完成
//            if (stack.size() == 1) {
//                if (postData.getResetAiSession().equals(ResetAiSessionEnum.YES)) {
//                    stack.pop();
//                }
//                return result;
//            } else if (stack.isEmpty()) {
//                return result;
//            } else {
//                stack.pop();
//                outIntent = currIntent;
//                Intent currSecIntent = stack.peek();
//                // 从DB中获取最新状态
//                currSecIntent = intentService.getById(currSecIntent.getId());
//                if (null != currSecIntent && !(currSecIntent.getId().intValue() == outIntent.getId().intValue())
//                        && currSecIntent.getFrontSessionId().equals(outIntent.getFrontSessionId())
//                        && isContainAllSlots(currSecIntent, outIntent, scenarios)
//                        && !currSecIntent.getStatus().equals(Constants.INTENT_STATUS_COMPLETE)) {
//                    // update FrontSessionRoundId
//                    currSecIntent.setFrontSessionRoundId(currIntent.getFrontSessionRoundId());
//                    intentService.update(currSecIntent);
//                    return backtrackingPhase6(outIntent, currSecIntent, result, true, scenarioBaiduAI, jsonRootBean,
//                            stack, scenarios, startDate, postData);
//                }
//                return result;
//            }
//        } else {
//            return result;
//        }
//    }

//    /**
//     * 意图回溯
//     *
//     * @param outIntent
//     * @param currIntent
//     * @param result
//     * @param flag
//     * @throws Exception
//     */
//    private String backtrackingPhase6(Intent outIntent, Intent currIntent, String result, boolean flag,
//                                      ScenarioBaiduAI scenarioBaiduAI, Unit2JsonRootBean jsonRootBean, Stack<Intent> stack,
//                                      Map<Long, ScenarioBaiduAI> scenariosMap, Date startDate, BaseOnResultReqPostData postData)
//            throws Exception {
//        ScenarioBaiduAI outScenarioBaiduAI = scenariosMap.get(outIntent.getAiSceneId());
//        ScenarioBaiduAI currScenarioBaiduAI = scenariosMap.get(currIntent.getAiSceneId());
//        IntentBaiduAI intentBaiduAICurr = getScenarioBaiduAIByIntent(currScenarioBaiduAI, currIntent.getIntent());
//        if (!CheckUtil.isNull(intentBaiduAICurr.getBacktrackAction())
//                && !currIntent.getStatus().equals(Constants.INTENT_STATUS_COMPLETE)) {
//            String clarifySlot = getClarifySlot(jsonRootBean);
//            // 执行currIntent的回溯action url(outIntent,currIntent，NLPInfo)
//            CallbackPhase4Result callbackTrackingResult = callbackService.onBacktrackActionPhase4(
//                    intentBaiduAICurr.getBacktrackAction(),
//                    getIntentsReqEntity(outScenarioBaiduAI, outIntent, null, clarifySlot),
//                    getIntentsReqEntity(currScenarioBaiduAI, currIntent, null, clarifySlot), ActionEnum.ENTER,
//                    aiRespJson2NLPInfoEx(jsonRootBean));
//
//            if (BacktrackEnum.YES.value == callbackTrackingResult.getBacktrack().intValue()) {
//                List<SlotSnapshot> slotSnapInsertList = new ArrayList<SlotSnapshot>();
//                List<Slot> slotInsertList = new ArrayList<Slot>();
//
//                if (outIntent.getIntent().equals(currIntent.getIntent()) && flag) {// 意图重入场合
//                    // 沿用currIntent的词槽（词槽不继承）
//                    List<IntentSlot> intentSlots = intentSlotService.getIntentSlots(outIntent.getId());
//                    if (!CheckUtil.isNull(intentSlots)) {
//                        for (IntentSlot intentSlot : intentSlots) {
//                            Slot slotEntity = slotService.getById((Long) intentSlot.getSlot().getFk());
//
//                            if (!CheckUtil.isNull(slotEntity)) {
//                                // 过滤已存在的词槽
//                                SlotSnapshot dbSlotSnapshot = slotSnapshotService.getByTypeSessionid(
//                                        slotEntity.getType(), outIntent.getAiSessionId());
//                                if (CheckUtil.isNull(dbSlotSnapshot)) {
//                                    SlotSnapshot snapshotEntity = new SlotSnapshot();
//                                    snapshotEntity.setAiSessionId(currIntent.getAiSessionId());
//                                    snapshotEntity.setNormalizedWord(slotEntity.getNormalizedWord());
//                                    snapshotEntity.setOriginalWord(slotEntity.getOriginalWord());
//                                    snapshotEntity.setTransformerWord(slotEntity.getTransformerWord());
//                                    snapshotEntity.setType(slotEntity.getType());
//                                    slotSnapInsertList.add(snapshotEntity);
//                                }
//                            }
//                        }
//                        slotSnapshotService.insertList(slotSnapInsertList);
//                    }
//                } else {// 不是意图重入场合
//                    // currIntent词槽继承outIntent词槽
//                    Long extendIntentId = getExtendIntentId(currIntent, true);
//                    if (CheckUtil.isNull(extendIntentId)) {
//                        extendIntentId = outIntent.getId();
//                    }
//
//                    // 填槽
//                    List<Slot> slotList = addSlot(jsonRootBean.getResult(), currIntent, scenarioBaiduAI);
//
//                    // 踢出已添加的词槽
//                    Map<String, Slot> addedSlotMap = new HashMap<String, Slot>();
//                    for (Slot entity : slotList) {
//                        addedSlotMap.put(entity.getType(), entity);
//                    }
//                    extendSlot(currScenarioBaiduAI, currIntent, extendIntentId, slotSnapInsertList, slotInsertList,
//                            addedSlotMap);
//
//                    // 添加关联关系
//                    slotService.insertList(slotInsertList);
//                    slotSnapshotService.insertList(slotSnapInsertList);
//
//                    addIntentSlotToDB(currIntent.getId(), slotInsertList);
//
//                    // 词槽清空
//                    resetSlot(outScenarioBaiduAI, outIntent);
//
//                    // 合并已填槽的词槽，用于返回结果
//                    slotInsertList.addAll(slotList);
//                }
//
//                IntentsReqEntity reqEntity = getIntentsReqEntity(currScenarioBaiduAI, currIntent, slotInsertList,
//                        clarifySlot);
//
//                // TODO:此处FrontInfo应从缓存中取得，FrontInfo之前保存到了缓存中
//                callbackService.onComplete(intentBaiduAICurr.getOnComplete(), null, reqEntity,
//                        aiRespJson2NLPInfoEx(jsonRootBean), ActionEnum.REMAIN, currIntent.getAiSessionId(), currIntent
//                                .getAiSceneId().toString(), DialogStatusEnum.FILLING, PriorityEnum.NO_DELAY,
//                        IntentSimilarEnum.STACK_EMPTY, (Long) currIntent.getUser().getFk(), currIntent
//                                .getFrontSessionId(), currIntent.getFrontSessionRoundId(), null);
//            } else {// url返回："不要回溯" --> 清空先前的意图
//
//                if (stack.size() == 1) {
//                    if (postData.getResetAiSession().equals(ResetAiSessionEnum.YES)) {
//                        stack.pop();
//                    }
//                    return result;
//                } else {
//                    stack.pop();
//                    currIntent = stack.peek();
//
//                    result = backtrackingPhase6(outIntent, currIntent, result, false, currScenarioBaiduAI,
//                            jsonRootBean, stack, scenariosMap, startDate, postData);
//                }
//            }
//        } else {// 意图回溯action url == none
//
//            if (stack.size() == 1) {
//                if (postData.getResetAiSession().equals(ResetAiSessionEnum.YES)) {
//                    stack.pop();
//                }
//                return result;
//            } else {
//                stack.pop();
//                currIntent = stack.peek();
//                result = backtrackingPhase6(outIntent, currIntent, result, false, currScenarioBaiduAI, jsonRootBean,
//                        stack, scenariosMap, startDate, postData);
//            }
//        }
//
//        return result;
//    }


    private Map<Long, ScenarioBaiduAI> getScenariosJson(ScenarioJsonRootBean rootJson) throws Exception {
        Map<Long, ScenarioBaiduAI> resultMap = new HashMap<Long, ScenarioBaiduAI>();

        if (!CheckUtil.isNull(rootJson)) {
            List<ScenarioBaiduAI> scenarios = rootJson.getScenarios();
            for (ScenarioBaiduAI entity : scenarios) {
                resultMap.put(entity.getId(), entity);
            }
        }

        return resultMap;
    }


    public void onComplete(String url, IntentsReqEntity prevIntent, IntentsReqEntity currIntent, NLPInfoEx nlp,
                           ActionEnum action, String sessionId, String sceneId, DialogStatusEnum dialogStatus, PriorityEnum priority,
                           IntentSimilarEnum intentSimilar, Long userId, String frontSessionId, String frontSessionRoundId,
                           String frontInfo) {
        String params = getPostData(prevIntent, currIntent, nlp, action, sessionId, sceneId, dialogStatus,
                priority, intentSimilar, userId, frontSessionId, frontSessionRoundId, frontInfo);
        sLog.i("sunVoiceApi", "onComplete: params 0000--->" + params);


        VABaseOnCompleteReqPostData data = new Gson().fromJson(params, VABaseOnCompleteReqPostData.class);
        VoiceCallback2BO.getInstance().onComplete(data, new IVoiceDataCallBack() {
            @Override
            public void onSuccess(String data) {
                mCallBack.onDataBack(data);
            }
        });
    }

    private static String getPostData(IntentsReqEntity prevIntent, IntentsReqEntity currIntent, NLPInfoEx nlp,
                                      ActionEnum action, String sessionId, String sceneId, DialogStatusEnum dialogStatus, PriorityEnum priority,
                                      IntentSimilarEnum intentSimilar, Long userId, String frontSessionId, String frontSessionRoundId,
                                      String frontInfo) {
        Gson gson = new Gson();
        StringBuilder strBuild = new StringBuilder();
        strBuild.append("{");
        if (!CheckUtil.isNull(prevIntent)) {
            strBuild.append("\"prevIntent\":[").append(gson.toJson(prevIntent));
            strBuild.append("],");
        }
        if (!CheckUtil.isNull(currIntent)) {
            strBuild.append("\"currIntent\":[").append(gson.toJson(currIntent));
            strBuild.append("],");
        }
        if (!CheckUtil.isNull(action)) {
            strBuild.append("\"action\":").append(action.value);
            strBuild.append(",");
        }
        if (!CheckUtil.isNull(nlp)) {
            strBuild.append("\"nlpInfoEx\":[").append(gson.toJson(nlp));
            strBuild.append("],");
        }
        if (!CheckUtil.isNull(sessionId)) {
            strBuild.append("\"sessionId\":\"").append(sessionId);
            strBuild.append("\",");
        }
        if (!CheckUtil.isNull(sceneId)) {
            strBuild.append("\"sceneId\":\"").append(sceneId);
            strBuild.append("\",");
        }

        if (!CheckUtil.isNull(dialogStatus)) {
            strBuild.append("\"dialogStatus\":").append(dialogStatus.value);
            strBuild.append(",");
        }
        if (!CheckUtil.isNull(priority)) {
            strBuild.append("\"priority\":").append(priority.value);
            strBuild.append(",");
        }
        if (!CheckUtil.isNull(intentSimilar)) {
            strBuild.append("\"intentSimilar\":").append(intentSimilar.value);
            strBuild.append(",");
        }
        if (!CheckUtil.isNull(userId) && 0L != userId.longValue()) {
            strBuild.append("\"userId\":").append(userId.longValue());
            strBuild.append(",");
        }
        if (!CheckUtil.isNull(frontSessionId)) {
            strBuild.append("\"frontSessionId\":\"").append(frontSessionId);
            strBuild.append("\",");
        }
        if (!CheckUtil.isNull(frontSessionRoundId)) {
            strBuild.append("\"frontSessionRoundId\":\"").append(frontSessionRoundId);
            strBuild.append("\",");
        }

        int strLength = strBuild.length();
        String postData = strBuild.substring(0, strLength);

        if (!CheckUtil.isNull(frontInfo)) {
            frontInfo = frontInfo.replaceAll("\\\"", "\\\\\"");
            postData += "\"frontInfo\":\"" + frontInfo + "\"";
        }

        postData = postData.concat("}");
        return postData;
    }


    private ReEnterEnum onDuplicate() {
        return ReEnterEnum.NO;
    }
}
