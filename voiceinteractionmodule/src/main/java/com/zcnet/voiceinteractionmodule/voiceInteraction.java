package com.zcnet.voiceinteractionmodule;

import com.google.gson.Gson;
import com.z.tinyapp.utils.common.TextUtil;
import com.zcnet.voiceinteractionmodule.InterData.voiceInterfaceBean_Dialog;
import com.zcnet.voiceinteractionmodule.common.Constants;
import com.zcnet.voiceinteractionmodule.common.Intent;
import com.zcnet.voiceinteractionmodule.common.Slot;
import com.zcnet.voiceinteractionmodule.common.SlotSnapshot;
import com.zcnet.voiceinteractionmodule.jsonEntity.unit2.Result;
import com.zcnet.voiceinteractionmodule.jsonEntity.unit2.SchemaSlots;
import com.zcnet.voiceinteractionmodule.jsonEntity.unit2.Unit2JsonRootBean;
import com.zcnet.voiceinteractionmodule.parseJsonEntity.IntentBaiduAI;
import com.zcnet.voiceinteractionmodule.parseJsonEntity.ScenarioBaiduAI;
import com.zcnet.voiceinteractionmodule.parseJsonEntity.SlotBaiduAIJsonEntity;
import com.zcnet.voiceinteractionmodule.util.CheckUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

//public class voiceInteraction implements Serializable {
//
//    //意图栈
//    public static Stack stack = new Stack<>();
//    //百度返回结果
//    Unit2JsonRootBean unit2JsonRootBeanCache = null;
//    private static final String YES = "yes";
//    private static final String NO = "no";
//    private static final String NONE = "none";
//    private static final String PREDEFINEDHISTORY = "predefined_history";
//
//    public String robotAIDialog(String strContent) throws Exception {
//        vaailog.logm("robotAIDialog", strContent);
//        String ret = "this is test";
//        Gson gson = new Gson();
//        voiceInterfaceBean_Dialog postData = gson.fromJson(strContent, voiceInterfaceBean_Dialog.class);
//        vaailog.logm("robotAIDialog", "javabean finished" + postData);
//        addAIProcess(postData);
//        return ret;
//    }
//
//
//    public String addAIProcess(voiceInterfaceBean_Dialog postData) throws Exception {
//
//        //得到前台数据
//        if (postData == null || TextUtil.isEmpty(postData.getContent())) {
//            //TODO:统一的参数错误处理
//            throw new RuntimeException("Error Data");
//        }
//        //TODO:请求百度unit获取intent
//        Intent vaIntent = new Intent();
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//        //如果请求有sessionId
//        boolean newSessionFlag = false;
//        if (CheckUtil.isNull(postData.sessionId)) {
//            newSessionFlag = true;
//        }
//
//        //百度判断是否为新的Intent
//        boolean newIntentFlag = false;
//
//        // 根据botId，初始化场景信息
//        // 场景信息 key: 场景ID; value: 场景配置信息
//        //TODO 获得所有场景
//        ScenarioJsonRootBean scenarioJsonRootBean = scenarioService.getScenariosJson(botId);
//        // 取得所有场景
//        Map<Long, ScenarioBaiduAI> scenarios = scenarioService.getScenariosJson(scenarioJsonRootBean);
//
//        // 第一次请求匹配的AI返回值
//        Unit2JsonRootBean matchedRootBean = null;
//        // AI返回时确定的意图，key: 意图 + ai会话ID， value: 匹配的场景对象
//        Map<String, ScenarioBaiduAI> secnariosCache = new HashMap<String, ScenarioBaiduAI>();
//        // 处理sessionId
//        String frontSessionId = postData.sessionId;
//        String frontSessionRoundId = "";
//        // 当前意图
//        Intent currIntent = null;
//        // 出栈意图
//        Intent outIntent = null;
//
//        String intentStrFromAIResp = matchedRootBean.getResult().getResponse().getSchema().getIntent();
//
//        String intentKey = intentStrFromAIResp
//                + getValueFromAIJsonStr(matchedRootBean.getResult().getBot_session(), "session_id");
//
//        ScenarioBaiduAI scenarioBaiduAI = secnariosCache.get(intentKey);
//
//        Result aiResult = matchedRootBean.getResult();
//        //当前会话不存在意图
//        if (newSessionFlag) {
//            if (!isActionIntent(matchedRootBean, scenarioBaiduAI)) {
//                // 执行error_action，error_action返回结果缓存下来（错误类型：无动作意图无法寄生）
//                throw new RuntimeException("无动作意图无法寄生");
//            } else {
//                // 将意图写入DB
//                Intent newIntent = new Intent();
//                newIntent.setAiSessionId(String.valueOf(getValueFromAIJsonStr(aiResult.getBot_session(), "session_id")));
//                newIntent.setAiSceneId(scenarioBaiduAI.getId());
//                newIntent.setIntent(aiResult.getResponse().getSchema().getIntent());
//                newIntent.setStatus(Constants.INTENT_STATUS_DECIDED);
//                newIntent.setActionCount(0L);
//                newIntent.setFrontSessionId(postData.sessionId);
//                // 意图入栈
//                stack.push(newIntent);
//                currIntent = newIntent;
//                // 填写词槽
//                List<Slot> slotList = addSlot(aiResult, newIntent, scenarioBaiduAI);
//                // 调用complete接口。
//                //TODO :on_complete
//                // 请求结果
//                result = new BotDialogPhase4APIResult();
//                result.setCode(WebApiErrorCodes.S_OK.code);
//                result.setFrontSessionRoundId(newIntent.getFrontSessionRoundId());
//            }
//        }//当前会话不存在意图END
//        else {
//            boolean extendSlotFlag = false;
//            // 词槽继承
//            List<SlotSnapshot> slotSnapInsertList = new ArrayList<SlotSnapshot>();
//            List<Slot> extendSlotInsertList = new ArrayList<Slot>();
//
//            if (!CheckUtil.isNull(outIntent) && currIntent.getIntent().equals(outIntent.getIntent())
//                    && currIntent.getFrontSessionId().equals(outIntent.getFrontSessionId())) {// 识别出的意图与当前栈顶意图相同
//// 0823添加会话ID相同
//                currIntent.setLastAiSessionId(getValueFromAIJsonStr(aiResult.getBot_session(), "session_id"));
//
//                // “仅填槽”
//                if (isAiRespContainCMD(aiResult)) {
//                    // 执行词槽填槽通知restful url 填槽URL为null时，无需调用
//// callbackService.onComplete(intentBaiduAI.getOnComplete(), null,
//// getIntentFromBaiduAI(scenarioBaiduAI, matchedRootBean, 0),
//// aiRespJson2NLPInfoEx(matchedRootBean), ActionEnum.ENTER, currIntent.getAiSessionId(),
//// currIntent.getAiSceneId().toString(), 0, userId, currIntent.getFrontSessionId(),
//// currIntent.getFrontSessionRoundId());
//
//                    // CALL 词槽填槽(意图)
//                    List<Slot> slotList = addSlot(aiResult, currIntent, userId, scenarioBaiduAI, intentBaiduAI,
//                            startDate);
//
//                    // 合并词槽
//                    Map<String, Slot> slotInsertListMap = new HashMap<String, Slot>();
//                    if (!CheckUtil.isNull(slotList)) {
//                        for (Slot entity : slotList) {
//                            slotInsertListMap.put(entity.getType(), entity);
//                        }
//                    }
//                    if (!CheckUtil.isNull(extendSlotInsertList)) {
//                        for (Slot entity : extendSlotInsertList) {
//                            if (!slotInsertListMap.containsKey(entity.getType())) {
//                                slotList.add(entity);
//                            }
//                        }
//                    }
//
//                    // 执行action，action返回结果返回给前台
//                    // 调用action接口。
//                    callbackService.onComplete(intentBaiduAI.getOnComplete(), null,
//                            getIntentsReqEntity(scenarioBaiduAI, currIntent, slotList, clarifySlot),
//                            aiRespJson2NLPInfoEx(matchedRootBean), ActionEnum.REMAIN, currIntent.getAiSessionId(),
//                            currIntent.getAiSceneId().toString(), DialogStatusEnum.FILLING, PriorityEnum.NO_DELAY,
//                            IntentSimilarEnum.SAME, userId, currIntent.getFrontSessionId(),
//                            currIntent.getFrontSessionRoundId(), postData.getFrontInfo());
//                    String dialogAiRespJsonBean = REDIS_CACHE_DIALOG_AI_RESP_JSON + frontSessionRoundId;
//                    redisCacheEx.putCacheWithExpireTime(dialogAiRespJsonBean, matchedRootBean,
//                            Constants.SECONDS_OF_24_HOURS);
//                    String frontSessionBotIdKey = REDIS_CACHE_DIALOG_BOT_ID + currIntent.getFrontSessionId();
//                    redisCacheEx.putCacheWithExpireTime(frontSessionBotIdKey, postData.getBotId(),
//                            Constants.SECONDS_OF_24_HOURS);
//                } else {// 不是"仅填槽"的场合，要判断是意图重入还是意图合并
//                    String reEnter = intentBaiduAI.getReEnterable();
//                    String onDuplicateUrl = intentBaiduAI.getOnDuplicate();
//                    if (!CheckUtil.isNull(reEnter) && reEnter.equals(YES) && !CheckUtil.isNull(onDuplicateUrl)
//                            && !onDuplicateUrl.equals(NONE)) {
//                        ScenarioBaiduAI outScenarioBaiduAI = scenarios.get(outIntent.getAiSceneId());
//                        ReEnterEnum reEnterRet = callbackService.onDuplicate(onDuplicateUrl,
//                                getIntentsReqEntity(outScenarioBaiduAI, outIntent, null, clarifySlot),
//                                getIntentFromBaiduAI(scenarioBaiduAI, matchedRootBean, 0));
//
//                        if (reEnterRet.equals(ReEnterEnum.YES)) {
//
//                            if (hasPredefinedPriority(matchedRootBean)) {// 优先级延后
//                                // 当前栈顶意图B暂存
//                                // 当前栈顶意图B出栈
//                                // 新意图C入栈
//                                // 新意图C填槽
//                                // 暂存的意图B入栈
//                                // 意图B执行action
//                                // CALL:action后处理(currIntent,action返回结果)
//                                Intent tempIntent = outIntent;
//                                outIntent = stack.pop();
//                                stack.push(currIntent);
//                                List<Slot> slotList = addSlot(matchedRootBean.getResult(), currIntent, userId,
//                                        scenarioBaiduAI, intentBaiduAI, startDate);
//                                stack.add(tempIntent);
//
//                                // 调用action接口。
//                                // A1(Curr=A1, Prev=null, REMAIN, FILLING, NO_DELAY, SAME)
//                                callbackService.onComplete(intentBaiduAI.getOnComplete(), null,
//                                        getIntentsReqEntity(scenarioBaiduAI, currIntent, slotList, clarifySlot),
//                                        aiRespJson2NLPInfoEx(matchedRootBean), ActionEnum.REMAIN,
//                                        currIntent.getAiSessionId(), currIntent.getAiSceneId().toString(),
//                                        DialogStatusEnum.FILLING, PriorityEnum.NO_DELAY, IntentSimilarEnum.SAME,
//                                        userId, currIntent.getFrontSessionId(), currIntent.getFrontSessionRoundId(),
//                                        postData.getFrontInfo());
//                            } else {// 优先级不延后
//                                if (!newIntentFlag) {
//                                    Intent newIntent = new Intent();
//                                    newIntent.setUser(new FKEntity<User>(userId, null));
//                                    newIntent.setAiSessionId(getValueFromAIJsonStr(aiResult.getBot_session(),
//                                            "session_id"));
//                                    newIntent.setAiSceneId(scenarioBaiduAI.getId());
//                                    newIntent.setIntent(aiResult.getResponse().getSchema().getIntent());
//                                    newIntent.setStatus(Constants.INTENT_STATUS_DECIDED);
//                                    newIntent.setActionCount(0L);
//                                    newIntent.setFrontSessionId(currIntent.getFrontSessionId());
//                                    newIntent.setFrontSessionRoundId(currIntent.getFrontSessionRoundId());
//                                    newIntent.setLastAiSessionId(getValueFromAIJsonStr(aiResult.getBot_session(),
//                                            "session_id"));
//
//                                    intentService.insert(newIntent);
//                                    currIntent = newIntent;
//                                    newIntentFlag = true;
//                                }
//                                stack.push(currIntent);
//
//                                // 填槽
//                                List<Slot> slotList = addSlot(aiResult, currIntent, userId, scenarioBaiduAI,
//                                        intentBaiduAI, startDate);
//
//                                // 踢出已添加的词槽
//                                Map<String, Slot> addedSlotMap = new HashMap<String, Slot>();
//                                for (Slot entity : slotList) {
//                                    addedSlotMap.put(entity.getType(), entity);
//                                }
//
//                                // 词槽继承
//                                List<Slot> slotInsertList = new ArrayList<Slot>();
//                                slotSnapInsertList.clear();
//                                slotInsertList.clear();
//                                if (!extendSlotFlag) {
//                                    Long extendSlotAiSessionId = getExtendIntentId(currIntent, newIntentFlag);
//                                    if (CheckUtil.isNull(extendSlotAiSessionId)) {
//                                        extendSlotAiSessionId = outIntent.getId();
//                                    }
//                                    extendSlot(scenarioBaiduAI, currIntent, extendSlotAiSessionId, slotSnapInsertList,
//                                            slotInsertList, addedSlotMap);
//                                }
//
//                                // 词槽重置
//                                resetSlot(scenarioBaiduAI, currIntent);
//
//                                for (Slot extendSlot : slotInsertList) {
//                                    if (addedSlotMap.containsKey(extendSlot.getType())) {
//                                        slotInsertList.remove(extendSlot);
//                                    }
//                                }
//
//                                for (SlotSnapshot extendSlotSnap : slotSnapInsertList) {
//                                    if (addedSlotMap.containsKey(extendSlotSnap.getType())) {
//                                        slotSnapInsertList.remove(extendSlotSnap);
//                                    }
//                                }
//
//                                // 添加关联关系
//                                slotService.insertList(slotInsertList);
//                                slotSnapshotService.insertList(slotSnapInsertList);
//                                addIntentSlotToDB(currIntent.getId(), slotInsertList);
//
//                                slotInsertList.addAll(slotList);
//
//                                // A1(Curr=A1, Prev=null, REMAIN, RE_ENTER, NO_DELAY, SAME)
//                                callbackService.onComplete(intentBaiduAI.getOnComplete(), null,
//                                        getIntentsReqEntity(scenarioBaiduAI, outIntent, null, clarifySlot),
//                                        aiRespJson2NLPInfoEx(matchedRootBean), ActionEnum.REMAIN,
//                                        currIntent.getAiSessionId(), currIntent.getAiSceneId().toString(),
//                                        DialogStatusEnum.RE_ENTER, PriorityEnum.NO_DELAY, IntentSimilarEnum.SAME,
//                                        userId, currIntent.getFrontSessionId(), currIntent.getFrontSessionRoundId(),
//                                        postData.getFrontInfo());
//
//                                // A2(Curr=A2, Prev=A1, ENTER, RE_ENTER, NO_DELAY, SAME)
//                                callbackService.onComplete(intentBaiduAI.getOnComplete(),
//                                        getIntentsReqEntity(scenarioBaiduAI, outIntent, null, clarifySlot),
//                                        getIntentsReqEntity(scenarioBaiduAI, currIntent, slotInsertList, clarifySlot),
//                                        aiRespJson2NLPInfoEx(matchedRootBean), ActionEnum.ENTER,
//                                        currIntent.getAiSessionId(), currIntent.getAiSceneId().toString(),
//                                        DialogStatusEnum.RE_ENTER, PriorityEnum.NO_DELAY, IntentSimilarEnum.SAME,
//                                        userId, currIntent.getFrontSessionId(), currIntent.getFrontSessionRoundId(),
//                                        postData.getFrontInfo());
//                            }
//                        }
//                        redisCacheEx.deleteCache(redisIntentKey);
//
//                        log.info("putCache(" + redisIntentKey + "): " + stack);
//                        redisCacheEx.putCache(redisIntentKey, stack);
//                        String baiduAIkey = REDIS_CACHE_BOT_SCENARIO_BADIDU_AI_JSON + currIntent.getFrontSessionId();
//                        redisCacheEx.putCacheWithExpireTime(baiduAIkey, scenarios, Constants.SECONDS_OF_24_HOURS);
//
//                        result.setCode(WebApiErrorCodes.S_OK.code);
//                        result.setFrontSessionRoundId(currIntent.getFrontSessionRoundId());
//                        return result;
//                    }
//                    // 意图合并
//                    // CALL 词槽填槽(意图)
//                    List<Slot> slotList = addSlot(aiResult, currIntent, userId, scenarioBaiduAI, intentBaiduAI,
//                            startDate);
//
//                    Map<String, Slot> slotInsertListMap = new HashMap<String, Slot>();
//                    if (!CheckUtil.isNull(slotList)) {
//                        for (Slot entity : slotList) {
//                            slotInsertListMap.put(entity.getType(), entity);
//                        }
//                    }
//                    if (!CheckUtil.isNull(extendSlotInsertList)) {
//                        for (Slot entity : extendSlotInsertList) {
//                            if (!slotInsertListMap.containsKey(entity.getType())) {
//                                slotList.add(entity);
//                            }
//                        }
//                    }
//
//                    // 执行action，action返回结果返回给前台
//                    // 调用action接口。
//                    IntentsReqEntity intentsReqEntity = null;
//
//// if (isUserHistory(matchedRootBean) || isCompleteSlot) {
//                    if (isUserHistory(matchedRootBean)) {
//                        intentsReqEntity = getIntentsReqEntity(scenarioBaiduAI, currIntent, slotList, clarifySlot);
//                    } else {
//                        intentsReqEntity = getIntentFromBaiduAIDiff(scenarioBaiduAI, matchedRootBean, 0);
//                    }
//
//                    callbackService.onComplete(intentBaiduAI.getOnComplete(), null,
//                            getIntentsReqEntity(scenarioBaiduAI, currIntent, slotList, clarifySlot),
//                            aiRespJson2NLPInfoEx(matchedRootBean), ActionEnum.REMAIN, currIntent.getAiSessionId(),
//                            currIntent.getAiSceneId().toString(), DialogStatusEnum.MERGE, PriorityEnum.NO_DELAY,
//                            IntentSimilarEnum.SAME, userId, currIntent.getFrontSessionId(),
//                            currIntent.getFrontSessionRoundId(), postData.getFrontInfo());
//                }
//            } else {// (识别出的意图与当前栈顶意图不同)
//                if (!isActionIntent(matchedRootBean, scenarioBaiduAI)) {// 无动作意图不入栈
//                    if (isContainAllSlots(currIntent, outIntent, scenarios)) { // 寄生状态
//
//                        List<Slot> slotInsertList = null;
//                        if (isAiRespContainCMD(aiResult)) {// "仅填槽"的场合
//                            slotInsertList = addSlot(aiResult, currIntent, userId, scenarioBaiduAI, intentBaiduAI,
//                                    startDate);
//                        } else {// 变成了寄生状态
//                            slotInsertList = addSlot(aiResult, currIntent, userId, scenarioBaiduAI, intentBaiduAI,
//                                    startDate);
//                        }
//
//                        Map<String, Slot> slotInsertListMap = new HashMap<String, Slot>();
//                        if (!CheckUtil.isNull(slotInsertList)) {
//                            for (Slot entity : slotInsertList) {
//                                slotInsertListMap.put(entity.getType(), entity);
//                            }
//                        }
//                        if (!CheckUtil.isNull(extendSlotInsertList)) {
//                            for (Slot entity : extendSlotInsertList) {
//                                if (!slotInsertListMap.containsKey(entity.getType())) {
//                                    slotInsertList.add(entity);
//                                }
//                            }
//                        }
//
//                        callbackService.onComplete(intentBaiduAI.getOnComplete(), null,
//                                getIntentsReqEntity(scenarioBaiduAI, currIntent, slotInsertList, clarifySlot),
//                                aiRespJson2NLPInfoEx(matchedRootBean), ActionEnum.REMAIN, currIntent.getAiSessionId(),
//                                currIntent.getAiSceneId().toString(), DialogStatusEnum.FILLING, PriorityEnum.NO_DELAY,
//                                IntentSimilarEnum.SAME, userId, currIntent.getFrontSessionId(),
//                                currIntent.getFrontSessionRoundId(), postData.getFrontInfo());
//
//                    } else {// “无动作意图”的“词槽”不含在当前栈顶“意图”中 --> 无法填槽和寄生，出错
//                        callbackService.onComplete(intentBaiduAI.getOnComplete(), null,
//                                getIntentsReqEntity(scenarioBaiduAI, currIntent, null, clarifySlot),
//                                aiRespJson2NLPInfoEx(matchedRootBean), ActionEnum.REMAIN, currIntent.getAiSessionId(),
//                                currIntent.getAiSceneId().toString(), DialogStatusEnum.FILLING, PriorityEnum.NO_DELAY,
//                                IntentSimilarEnum.SAME, userId, currIntent.getFrontSessionId(),
//                                currIntent.getFrontSessionRoundId(), postData.getFrontInfo());
//                    }
//                } else {// 识别出的意图是有动作意图
//
//                    if (isContainAllSlots(currIntent, outIntent, scenarios)) {
//                        // 词槽继承
//                        if (!extendSlotFlag) {
//                            Long extendSlotAiSessionId = getExtendIntentId(currIntent, newIntentFlag);
//
//                            if (CheckUtil.isNull(extendSlotAiSessionId)) {
//                                extendSlotAiSessionId = outIntent.getId();
//                            }
//                            extendSlot(scenarioBaiduAI, currIntent, extendSlotAiSessionId, slotSnapInsertList,
//                                    extendSlotInsertList, null);
//
//                            // 添加关联关系
//                            slotService.insertList(extendSlotInsertList);
//                            slotSnapshotService.insertList(slotSnapInsertList);
//                            addIntentSlotToDB(currIntent.getId(), extendSlotInsertList);
//                        }
//
//
//                        if (hasPredefinedPriority(matchedRootBean)) {// 优先级延后
//                            Intent tempIntent = outIntent;
//                            stack.push(currIntent);
//                            outIntent = currIntent;
//
//                            List<Slot> slotList = addSlot(matchedRootBean.getResult(), currIntent, userId,
//                                    scenarioBaiduAI, intentBaiduAI, startDate);
//                            stack.add(tempIntent);
//                            String respText = "好的，"
//                                    + matchedRootBean.getResult().getResponse().getQu_res().getRaw_query();
//
//                            String keyPre = REDIS_CACHE_DIALOG_ON_RESULT_PRE + frontSessionRoundId;
//
//                            redisCacheEx.putCacheWithExpireTime(keyPre, respText, Constants.SECONDS_OF_24_HOURS);
//
//                            currIntent = tempIntent;
//                            currIntent = intentService.getById(currIntent.getId());
//
//                            currIntent.setFrontSessionRoundId(frontSessionRoundId);
//                            intentService.update(currIntent);
//
//                            ScenarioBaiduAI scenarioBaiduAIPriority = scenarios.get(currIntent.getAiSceneId());
//
//                            IntentBaiduAI intentBaiduAIPriority = getScenarioBaiduAIByIntent(scenarioBaiduAIPriority,
//                                    currIntent.getIntent());
//
//                            // A(Curr=A, Prev=null, REMAIN, FILLING, NO_DELAY, SIMILAR)
//                            callbackService.onComplete(intentBaiduAIPriority.getOnComplete(), null,
//                                    getIntentsReqEntity(scenarioBaiduAIPriority, currIntent, null, clarifySlot),
//                                    aiRespJson2NLPInfoEx(matchedRootBean), ActionEnum.REMAIN,
//                                    currIntent.getAiSessionId(), currIntent.getAiSceneId().toString(),
//                                    DialogStatusEnum.FILLING, PriorityEnum.NO_DELAY, IntentSimilarEnum.SIMILAR, userId,
//                                    currIntent.getFrontSessionId(), currIntent.getFrontSessionRoundId(),
//                                    postData.getFrontInfo());
//
//                        } else { // 优先级不延后
//                            // 原意图不变，重新入栈，新意图入栈
//                            stack.push(currIntent);
//
//                            // 词槽重置
//                            resetSlot(scenarioBaiduAI, currIntent);
//
//                            // CALL 词槽填槽(新意图)
//                            List<Slot> slotInsertList = addSlot(aiResult, currIntent, userId, scenarioBaiduAI,
//                                    intentBaiduAI, startDate);
//                            Map<String, Slot> slotInsertListMap = new HashMap<String, Slot>();
//                            if (!CheckUtil.isNull(slotInsertList)) {
//                                for (Slot entity : slotInsertList) {
//                                    slotInsertListMap.put(entity.getType(), entity);
//                                }
//                            }
//                            if (!CheckUtil.isNull(extendSlotInsertList)) {
//                                for (Slot entity : extendSlotInsertList) {
//                                    if (!slotInsertListMap.containsKey(entity.getType())) {
//                                        slotInsertList.add(entity);
//                                    }
//                                }
//                            }
//
//                            // A(Curr=A, Prev=null, REMAIN, DECIDED, NO_DELAY, SIMILAR)
//                            callbackService.onComplete(intentBaiduAI.getOnComplete(), null,
//                                    getIntentsReqEntity(scenarioBaiduAI, outIntent, null, clarifySlot),
//                                    aiRespJson2NLPInfoEx(matchedRootBean), ActionEnum.REMAIN,
//                                    currIntent.getAiSessionId(), currIntent.getAiSceneId().toString(),
//                                    DialogStatusEnum.DECIDED, PriorityEnum.NO_DELAY, IntentSimilarEnum.SIMILAR, userId,
//                                    currIntent.getFrontSessionId(), currIntent.getFrontSessionRoundId(),
//                                    postData.getFrontInfo());
//
//                            // B(Curr=B, Prev=A, ENTER, DECIDED, NO_DELAY, SIMILAR)
//                            callbackService.onComplete(intentBaiduAI.getOnComplete(),
//                                    getIntentsReqEntity(scenarioBaiduAI, outIntent, null, clarifySlot),
//                                    getIntentsReqEntity(scenarioBaiduAI, currIntent, slotInsertList, clarifySlot),
//                                    aiRespJson2NLPInfoEx(matchedRootBean), ActionEnum.ENTER,
//                                    currIntent.getAiSessionId(), currIntent.getAiSceneId().toString(),
//                                    DialogStatusEnum.DECIDED, PriorityEnum.NO_DELAY, IntentSimilarEnum.SIMILAR, userId,
//                                    currIntent.getFrontSessionId(), currIntent.getFrontSessionRoundId(),
//                                    postData.getFrontInfo());
//                        }
//                    } else {// 意图不相似
//                        // 意图栈全部清空
//                        stack.clear();
//
//                        // 新意图入栈
//                        stack.push(currIntent);
//                        // CALL 词槽填槽(新意图)
//                        List<Slot> slotInsertList = addSlot(aiResult, currIntent, userId, scenarioBaiduAI,
//                                intentBaiduAI, startDate);
//
//                        Map<String, Slot> slotInsertListMap = new HashMap<String, Slot>();
//                        if (!CheckUtil.isNull(slotInsertList)) {
//                            for (Slot entity : slotInsertList) {
//                                slotInsertListMap.put(entity.getType(), entity);
//                            }
//                        }
//                        if (!CheckUtil.isNull(extendSlotInsertList)) {
//                            for (Slot entity : extendSlotInsertList) {
//                                if (!slotInsertListMap.containsKey(entity.getType())) {
//                                    slotInsertList.add(entity);
//                                }
//                            }
//                        }
//                        callbackService.onComplete(intentBaiduAI.getOnComplete(), null,
//                                getIntentsReqEntity(scenarioBaiduAI, currIntent, slotInsertList, clarifySlot),
//                                aiRespJson2NLPInfoEx(matchedRootBean), ActionEnum.ENTER, currIntent.getAiSessionId(),
//                                currIntent.getAiSceneId().toString(), DialogStatusEnum.DECIDED, PriorityEnum.NO_DELAY,
//                                IntentSimilarEnum.NOT_SIMILAR, userId, currIntent.getFrontSessionId(),
//                                currIntent.getFrontSessionRoundId(), postData.getFrontInfo());
//                    }
//                }
//            }
//        }
//
//        log.info("mapCache: " + mapCache);
//        if (!CheckUtil.isNull(aiBotId) && null != mapCache) {
//            while (mapCache.isEmpty()) {
//                continue;
//            }
//
//            for (String unit2JsonBeanKey : mapCache.keySet()) {
//                Unit2JsonRootBean unit2JsonRootBean = mapCache.get(unit2JsonBeanKey);
//
//                if (CheckUtil.isNull(unit2JsonRootBean)) {
//                    continue;
//                }
//                Schema schema = null;
//                try {
//                    schema = unit2JsonRootBean.getResult().getResponse().getSchema();
//                } catch (Exception e) {
//                }
//
//                validateNull(schema);
//
//                // (1) $.result.response.schema.intent_confidence、
//                // (2) $.result.response.qu_res.candidates[0].intent_confidence。
//                // 都大于阈值70的作为被识别出来的场景
//                if (schema.getIntent_confidence() > 70 && getQuResIntentConfidence(unit2JsonRootBean) > 70) {
//                    over70Counter = over70Counter + 1;
//                    matchedRootBean = unit2JsonRootBean;
//                    ScenarioBaiduAI entity = scenarios.get(Long.valueOf(unit2JsonRootBean.getResult().getBot_id()));
//                    secnariosCache.put(
//                            schema.getIntent()
//                                    + getValueFromAIJsonStr(unit2JsonRootBean.getResult().getBot_session(),
//                                    "session_id"), entity);
//
//                    redisCacheEx.putCacheWithExpireTime(dialogContentKey, unit2JsonRootBean, Constants.SECONDS_OF_24_HOURS);
//
//                    String dialogAiRespJsonBean = REDIS_CACHE_DIALOG_AI_RESP_JSON
//                            + currIntent.getFrontSessionRoundId();
//                    redisCacheEx.putCacheWithExpireTime(dialogAiRespJsonBean, unit2JsonRootBean,
//                            Constants.SECONDS_OF_24_HOURS);
//
//                    currIntent = intentService.getById(currIntent.getId());
//
//                    currIntent.setAiSessionId(getValueFromAIJsonStr(unit2JsonRootBean.getResult().getBot_session(),
//                            "session_id"));
//                    currIntent.setLastAiSessionId(getValueFromAIJsonStr(unit2JsonRootBean.getResult().getBot_session(),
//                            "session_id"));
//                    intentService.update(currIntent);
//                    log.info(matchedRootBean.getResult());
//                }
//
//            }
//        }
//
//        redisCacheEx.deleteCache(redisIntentKey);
//
//        log.info("putCache stack: " + stack);
//        redisCacheEx.putCache(redisIntentKey, stack);
//
//        String frontSessionBotIdKey = REDIS_CACHE_DIALOG_BOT_ID + currIntent.getFrontSessionId();
//        redisCacheEx.putCacheWithExpireTime(frontSessionBotIdKey, postData.getBotId(), Constants.SECONDS_OF_24_HOURS);
//
//        String baiduAIkey = REDIS_CACHE_BOT_SCENARIO_BADIDU_AI_JSON + currIntent.getFrontSessionId();
//        redisCacheEx.putCacheWithExpireTime(baiduAIkey, scenarios, Constants.SECONDS_OF_24_HOURS);
//        result.setCode(WebApiErrorCodes.S_OK.code);
//        result.setFrontSessionRoundId(currIntent.getFrontSessionRoundId());
//        return result;
//    }
//
//    private boolean isActionIntent(Unit2JsonRootBean aiRootBean, ScenarioBaiduAI scenarioBaiduAI) throws Exception {
//
//        if (!CheckUtil.isNull(aiRootBean) && !CheckUtil.isNull(scenarioBaiduAI)) {
//            String intent = aiRootBean.getResult().getResponse().getSchema().getIntent();
//            if (!CheckUtil.isNull(scenarioBaiduAI)) {
//                IntentBaiduAI intentBaiduAI = getScenarioBaiduAIByIntent(scenarioBaiduAI, intent);
//                if (!CheckUtil.isNull(intentBaiduAI)) {
//                    if (YES.equals(intentBaiduAI.getActionable())) {
//                        return true;
//                    } else {
//                        return false;
//                    }
//                }
//            }
//        }
//        return false;
//    }
//
//    private IntentBaiduAI getScenarioBaiduAIByIntent(ScenarioBaiduAI scenarioBaiduAI, String intent) {
//        IntentBaiduAI intentBaiduAI = null;
//        List<IntentBaiduAI> intents = scenarioBaiduAI.getIntents();
//        for (IntentBaiduAI entity : intents) {
//            if (entity.getName().equals(intent)) {
//                intentBaiduAI = entity;
//            }
//        }
//        return intentBaiduAI;
//    }
//
//    private String getValueFromAIJsonStr(String botSessionStr, String key) throws Exception {
//        Object result = "";
//        Gson gson = new Gson();
//        Map map = gson.fromJson(Arrays.toString(botSessionStr.getBytes()), Map.class);
//        result = map.get(key);
//        return String.valueOf(result);
//    }
//
//    public List<Slot> addSlot(Result resultFromAIResp, Intent intent, ScenarioBaiduAI scenarioBaiduAI) throws Exception {
//        List<SlotSnapshot> slotSnapInsertList = new ArrayList<SlotSnapshot>();
//        List<Slot> slotInsertList = new ArrayList<Slot>();
//        List<SchemaSlots> slotsFromAIResp = resultFromAIResp.getResponse().getSchema().getSlots();
//        if (!CheckUtil.isNull(slotsFromAIResp)) {
//
//            // 遍历百度识别的该意图的全部词槽
//            for (SchemaSlots slot : slotsFromAIResp) {
//
//                // 只写入当前这轮会话中引入的词槽
//                if (0 != slot.getSession_offset()) {
//                    continue;
//                }
//                // 稍后、刚刚不写入DB
//                if ("user_priority".equals(slot.getName())) {
//                    continue;
//                }
//                // 稍后、刚刚不写入DB
//                if ("user_history".equals(slot.getName())) {
//                    continue;
//                }
//                // 词槽的transformer != none
//                String transformer = getSlotTransformer(intent.getIntent(), slot.getName(), scenarioBaiduAI);
//                String transformerResult = "";
//                String normalizedWord = slot.getNormalized_word();
//                String originalWord = slot.getOriginal_word();
//
//                if (CheckUtil.isNull(normalizedWord)) {
//                    normalizedWord = slot.getOriginal_word();
//                }
//
//                if (!PREDEFINEDHISTORY.equals(slot.getName())) {// 词槽类型不是查询历史
//                    // 填槽
//                    Slot newEntity = fillSlot(slot, originalWord, normalizedWord, transformerResult);
//
//                    Slot slotDb = slotService.getByIntentIdType(intent.getId(), newEntity.getType());
//
//                    if (!CheckUtil.isNull(slotDb)) {
//                        if (!slotDb.getNormalizedWord().equals(newEntity.getNormalizedWord())) {
//                            slotInsertList.add(newEntity);
//                        }
//                    } else {
//                        slotInsertList.add(newEntity);
//                    }
//
//                    SlotSnapshot newSnapEntity = fillSlotSnapshot(slot, slot.getOriginal_word(),
//                            slot.getNormalized_word(), transformerResult);
//                    newSnapEntity.setAiSessionId(intent.getAiSessionId());
//
//                    SlotSnapshot slotSnapshotDb = slotSnapshotService.getByTypeSessionid(newSnapEntity.getType(),
//                            intent.getAiSessionId());
//
//                    if (CheckUtil.isNull(slotSnapshotDb)) {
//                        slotSnapInsertList.add(newSnapEntity);
//                    } else {
//                        if (!slotSnapshotDb.getOriginalWord().equals(newSnapEntity.getOriginalWord())) {
//                            slotSnapInsertList.add(newSnapEntity);
//                        }
//                    }
//                } else {// 词槽类型是查询历史
//                    if (isSlotResetWhenIntentRecognizedNo(intent.getIntent(), slot.getName(), scenarioBaiduAI)) {// 词槽不重置
//                        // 查询一定时间范围内的该词槽历史上最新的值来填槽
//                        List<Slot> slotHistoryList = slotService.getByIntentSlotTypeUID(intent.getIntent(), userId,
//                                startDate, new Date());
//                        if (!CheckUtil.isNull(slotHistoryList)) {
//                            for (Slot slotDb : slotHistoryList) {
//
//                                Slot newEntity = fillSlotByDb(slotDb, slotDb.getOriginalWord(),
//                                        slotDb.getNormalizedWord(), slotDb.getTransformerWord());
//                                Slot slotDb2 = slotService.getByIntentIdType(intent.getId(), newEntity.getType());
//
//                                if (!CheckUtil.isNull(slotDb2)) {
//                                    if (!slotDb2.getOriginalWord().equals(newEntity.getOriginalWord())) {
//                                        slotInsertList.add(newEntity);
//                                    }
//                                } else {
//                                    slotInsertList.add(newEntity);
//
//                                }
//
//                                SlotSnapshot newSnapEntity = fillSlotSnapshot(slotDb, slotDb.getOriginalWord(),
//                                        slotDb.getNormalizedWord(), slotDb.getTransformerWord());
//                                newSnapEntity.setAiSessionId(intent.getAiSessionId());
//
//                                SlotSnapshot slotSnapshotDb = slotSnapshotService.getByTypeSessionid(
//                                        newSnapEntity.getType(), intent.getAiSessionId());
//
//                                if (CheckUtil.isNull(slotSnapshotDb)) {
//                                    slotSnapInsertList.add(newSnapEntity);
//                                } else {
//                                    if (!slotSnapshotDb.getOriginalWord().equals(newSnapEntity.getOriginalWord())) {
//                                        slotSnapInsertList.add(newSnapEntity);
//                                    }
//                                }
//
//                                if (isSlotMustNo(intent.getIntent(), slot.getName(), scenarioBaiduAI)) {
//                                    break;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        // 添加关联关系
//        return slotInsertList;
//    }
//
//    private String getSlotTransformer(String intentStr, String slotName, ScenarioBaiduAI scenarioBaiduAI)
//            throws Exception {
//
//        if (!CheckUtil.isNull(scenarioBaiduAI)) {
//
//            List<IntentBaiduAI> intents = scenarioBaiduAI.getIntents();
//            for (IntentBaiduAI entity : intents) {
//                if (entity.getName().equals(intentStr)) {
//
//                    List<SlotBaiduAIJsonEntity> slots = entity.getSlots();
//                    for (SlotBaiduAIJsonEntity slotEntity : slots) {
//
//                        if (slotEntity.getName().equals(slotName)) {
//                            String transformer = slotEntity.getTransformer();
//                            if (!NONE.equals(transformer)) {
//                                return transformer;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return null;
//    }
//
//    private Slot fillSlot(SchemaSlots slot, String originalWord, String normalizedWord, String transformerWord) {
//        Slot newEntity = new Slot();
//        newEntity.setType(slot.getName());
//        newEntity.setOriginalWord(originalWord);
//        newEntity.setNormalizedWord(normalizedWord);
//        newEntity.setTransformerWord(transformerWord);
//        return newEntity;
//    }
//
//    private SlotSnapshot fillSlotSnapshot(SchemaSlots slot, String originalWord, String normalizedWord,
//                                          String transformerWord) {
//        SlotSnapshot newSnapEntity = new SlotSnapshot();
//        newSnapEntity.setType(slot.getName());
//        newSnapEntity.setOriginalWord(originalWord);
//        newSnapEntity.setNormalizedWord(normalizedWord);
//        newSnapEntity.setTransformerWord(transformerWord);
//        return newSnapEntity;
//    }
//
//    private SlotSnapshot fillSlotSnapshot(Slot slot, String originalWord, String normalizedWord, String transformerWord) {
//        SlotSnapshot newSnapEntity = new SlotSnapshot();
//        newSnapEntity.setType(slot.getType());
//        newSnapEntity.setOriginalWord(originalWord);
//        newSnapEntity.setNormalizedWord(normalizedWord);
//        newSnapEntity.setTransformerWord(transformerWord);
//        return newSnapEntity;
//    }
//
//
//    private boolean isSlotMustNo(String intentStr, String slotName, ScenarioBaiduAI scenarioBaiduAI) throws Exception {
//
//        if (!CheckUtil.isNull(scenarioBaiduAI)) {
//
//            List<IntentBaiduAI> intents = scenarioBaiduAI.getIntents();
//            for (IntentBaiduAI entity : intents) {
//                if (entity.getName().equals(intentStr)) {
//
//                    List<SlotBaiduAIJsonEntity> slots = entity.getSlots();
//                    for (SlotBaiduAIJsonEntity slotEntity : slots) {
//
//                        if (slotEntity.getName().equals(slotName)) {
//                            String must = slotEntity.getMust();
//                            if (NO.equals(must) && slotEntity.getType().equals("userdefined")) {
//                                return true;
//                            } else {
//                                return false;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return false;
//    }
//
//    private boolean isSlotResetWhenIntentRecognizedNo(String intentStr, String slotName, ScenarioBaiduAI scenarioBaiduAI)
//            throws Exception {
//
//        if (!CheckUtil.isNull(scenarioBaiduAI)) {
//
//            List<IntentBaiduAI> intents = scenarioBaiduAI.getIntents();
//            for (IntentBaiduAI entity : intents) {
//                if (entity.getName().equals(intentStr)) {
//
//                    List<SlotBaiduAIJsonEntity> slots = entity.getSlots();
//                    for (SlotBaiduAIJsonEntity slotEntity : slots) {
//
//                        if (slotEntity.getName().equals(slotName)) {
//                            String resetWhenIntentRecognized = slotEntity.getResetWhenIntentRecognized();
//                            if (NO.equals(resetWhenIntentRecognized)) {
//                                return true;
//                            } else {
//                                return false;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return false;
//    }
//
//
//    public String robotAIDialogIntent(String strContent) {
//        String ret = "this is test";
//        return ret;
//    }
//
//    public String getRobotAIResult(String strContent) {
//        String ret = "this is test";
//        return ret;
//    }
//
//    public boolean initRobotAI(voiceInteractionFeedback obj) {
//        return true;
//    }
//}