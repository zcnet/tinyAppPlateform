package com.zcnet.voice_callback_lib.voice_data;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

public class VABaseOnResultReqPostData extends VABaseInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Boolean resetAiSession;
    private String frontSessionId;
    private String frontSessionRoundId;

    private List<VABaseIntent> intent;

    private Integer actionType;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<VAActionEntity> vaActionList;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<VAErrorTextEntity> errorTextList;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<VABuActionEntity> buActionList;

    public Boolean getResetAiSession() {return resetAiSession;}
    public void setResetAiSession(Boolean resetAiSession) {this.resetAiSession = resetAiSession;}

    public String getFrontSessionId() {return frontSessionId;}
    public void setFrontSessionId(String frontSessionId) {this.frontSessionId = frontSessionId;}

    public String getFrontSessionRoundId() {return frontSessionRoundId;}
    public void setFrontSessionRoundId(String frontSessionRoundId) {
        this.frontSessionRoundId = frontSessionRoundId;
    }

    public List<VABaseIntent> getIntent() {return intent;}
    public void setIntent(List<VABaseIntent> intent) {this.intent = intent;}

    public Integer getActionType() {return actionType;}
    public void setActionType(Integer actionType) {this.actionType = actionType;}

    public List<VAActionEntity> getVaActionList() {return vaActionList;}
    public void setVaActionList(List<VAActionEntity> vaActionList) {
        this.vaActionList = vaActionList;
    }

    public List<VAErrorTextEntity> getErrorTextList() {return errorTextList;}
    public void setErrorTextList(List<VAErrorTextEntity> errorTextList) {
        this.errorTextList = errorTextList;
    }

    public List<VABuActionEntity> getBuActionList() {return buActionList;}
    public void setBuActionList(List<VABuActionEntity> buActionList) {
        this.buActionList = buActionList;
    }

    @Override
    public String toString() {
        return "VABaseOnResultReqPostData [resetAiSession=" + resetAiSession
                + ", frontSessionId=" + frontSessionId
                + ", frontSessionRoundId=" + frontSessionRoundId + ", intent" + intent
                + ", actionType" + actionType + ", vaActionList=" + vaActionList
                + ", errorTextList=" + errorTextList + ", buActionList=" + buActionList + "]";
    }
}
