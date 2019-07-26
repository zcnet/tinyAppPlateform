package com.zcnet.voice_callback_lib.voice_data;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

public class VABaseOnCompleteReqPostData extends VABaseInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;              // 发起对话的用户 ID，不处理。
    private String frontSessionId;      // 前端会话 ID，不处理。
    private String frontSessionRoundId; // 对话唯一标识符，不处理。
    private String sessionId;           // 当前 AI 平台的会话 ID。
    private String sceneId;             // 当前 AI 平台的场景 ID。
    // 前端状态信息（透传）。
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String frontInfo;

    // 自然语言处理结果。
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<VABaseNlpInfo> nlpInfoEx;

    // 用于表征被切走或保持栈内状态的那个 intent 的详细信息，可能为空白。
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<VABaseIntent> prevIntent;
    // 用于表征当前被切入的那个 intent 的详细信息。
    private List<VABaseIntent> currIntent;
    // 新意图是否相似于上一个意图：
    // EMPTY：栈空。
    // SAME：相同。
    // SIMILAR：相似。
    // NOT SIMILAR：不相似。
    // TODO: ENUM
    private Integer intentSimilar;

    // 动作类型：
    // EXIT: 代表切出。
    // ENTER: 代表切入。
    // REMAIN: 代表保持。
    // TODO: ENUM
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer action;
    // 对话状态：确认、合并、重入、仅填槽、填槽完成。
    // TODO: ENUM
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer dialogStatus;
    // 优先级延后 FLAG：不延后、延后
    // TODO: ENUM
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer priority;

    public String getUserId() {return userId;}
    public void setUserId(String userId) {this.userId = userId;}

    public String getFrontSessionId() {return frontSessionId;}
    public void setFrontSessionId(String frontSessionId) {this.frontSessionId = frontSessionId;}

    public String getFrontSessionRoundId() {return frontSessionRoundId;}
    public void setFrontSessionRoundId(String frontSessionRoundId) {
        this.frontSessionRoundId = frontSessionRoundId;
    }

    public String getSessionId() {return sessionId;}
    public void setSessionId(String sessionId) {this.sessionId = sessionId;}

    public String getSceneId() {return sceneId;}
    public void setSceneId(String sceneId) {this.sceneId = sceneId;}

    public String getFrontInfo() {return frontInfo;}
    public void setFrontInfo(String frontInfo) {this.frontInfo = frontInfo;}

    public List<VABaseNlpInfo> getNlpInfoEx() {return nlpInfoEx;}
    public void setNlpInfoEx(List<VABaseNlpInfo> nlpInfoEx) {this.nlpInfoEx = nlpInfoEx;}

    public List<VABaseIntent> getPrevIntent() {return prevIntent;}
    public void setPrevIntent(List<VABaseIntent> prevIntent) {this.prevIntent = prevIntent;}

    public List<VABaseIntent> getCurrIntent() {return currIntent;}
    public void setCurrIntent(List<VABaseIntent> currIntent) {this.currIntent = currIntent;}

    public Integer getIntentSimilar() {return intentSimilar;}
    public void setIntentSimilar(Integer intentSimilar) {this.intentSimilar = intentSimilar;}

    public Integer getAction() {return action;}
    public void setAction(Integer action) {this.action = action;}

    public Integer getDialogStatus() {return dialogStatus;}
    public void setDialogStatus(Integer dialogStatus) {this.dialogStatus = dialogStatus;}

    public Integer getPriority() {return priority;}
    public void setPriority(Integer priority) {this.priority = priority;}

    @Override
    public String toString() {
        return "VABaseOnCompleteReqPostData [userId=" + userId
                + ", frontSessionId=" + frontSessionId
                + ", frontSessionRoundId=" + frontSessionRoundId + ", sessionId=" + sessionId
                + ", sceneId=" + sceneId + ", frontInfo=" + frontInfo
                + ", nlpInfoEx=" + nlpInfoEx + ", prevIntent=" + prevIntent
                + ", currIntent=" + currIntent + ", intentSimilar=" + intentSimilar
                + ", action=" + action + ", dialogStatus=" + dialogStatus
                + ", priority=" + priority + "]";
    }
}
