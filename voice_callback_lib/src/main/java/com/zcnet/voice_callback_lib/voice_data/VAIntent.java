package com.zcnet.voice_callback_lib.voice_data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class VAIntent implements Serializable {
    private Long userId;                    // 发起对话的用户ID，可以废弃了。
    private Long createTime;                // 创建时间戳。
    private Boolean resetAiSession = false;         // AI 是否重置。
    private String frontSessionId;          // 前端会话 ID。
    private String frontSessionRoundId;     // 前端会话轮次 ID。
    private Integer dialogStatus;           // 语音交互平台对话状态。
    private String intentName;              // 意图名。
    private List<VABaseIntentSlot> vaSlots; // 词槽。

    public Long getUserId() {return userId;}
    public void setUserId(Long userId) {this.userId = userId;}

    public Long getCreateTime() {return createTime;}
    public void setCreateTime(Long createTime) {this.createTime = createTime;}

    public Boolean getResetAiSession() {return resetAiSession;}
    public void setResetAiSession(Boolean resetAiSession) {this.resetAiSession = resetAiSession;}

    public String getFrontSessionId() {return frontSessionId;}
    public void setFrontSessionId(String frontSessionId) {this.frontSessionId = frontSessionId;}

    public String getFrontSessionRoundId() {return frontSessionRoundId;}
    public void setFrontSessionRoundId(String frontSessionRoundId) {
        this.frontSessionRoundId = frontSessionRoundId;
    }

    public Integer getDialogStatus() {return dialogStatus;}
    public void setDialogStatus(Integer dialogStatus) {this.dialogStatus = dialogStatus;}

    public String getIntentName() {return intentName;}
    public void setIntentName(String intentName) {this.intentName = intentName;}

    public List<VABaseIntentSlot> getVaSlots() {return vaSlots;}
    public void setVaSlots(List<VABaseIntentSlot> vaSlots) {this.vaSlots = vaSlots;}

    @Override
    public String toString() {
        return "VAIntent [userId=" + userId + "createTime=" + createTime
                + "resetAiSession=" + resetAiSession + "frontSessionId=" + frontSessionId
                + "frontSessionRoundId=" + frontSessionRoundId + "dialogStatus=" + dialogStatus
                + "intentName=" + intentName + "vaSlots=" + vaSlots + "]";
    }
}
