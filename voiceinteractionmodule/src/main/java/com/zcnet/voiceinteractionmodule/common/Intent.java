//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.zcnet.voiceinteractionmodule.common;

import java.io.Serializable;

public class Intent extends Entity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String user;
    private String frontSessionId;
    private String frontSessionRoundId;
    private String aiSessionId;
    private Long aiSceneId;
    private String lastAiSessionId;
    private String intent;
    private Long status;
    private Long actionCount;

    public Intent() {
    }

    public Object getKey() {
        return this.getId();
    }

    public void setKey(Object key) {
        this.setId((Long)key);
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFrontSessionId() {
        return this.frontSessionId;
    }

    public void setFrontSessionId(String frontSessionId) {
        this.frontSessionId = frontSessionId;
    }

    public String getAiSessionId() {
        return this.aiSessionId;
    }

    public void setAiSessionId(String aiSessionId) {
        this.aiSessionId = aiSessionId;
    }

    public Long getAiSceneId() {
        return this.aiSceneId;
    }

    public void setAiSceneId(Long aiSceneId) {
        this.aiSceneId = aiSceneId;
    }

    public String getLastAiSessionId() {
        return this.lastAiSessionId;
    }

    public void setLastAiSessionId(String lastAiSessionId) {
        this.lastAiSessionId = lastAiSessionId;
    }

    public String getIntent() {
        return this.intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public Long getStatus() {
        return this.status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getActionCount() {
        return this.actionCount;
    }

    public void setActionCount(Long actionCount) {
        this.actionCount = actionCount;
    }

    public String getFrontSessionRoundId() {
        return this.frontSessionRoundId;
    }

    public void setFrontSessionRoundId(String frontSessionRoundId) {
        this.frontSessionRoundId = frontSessionRoundId;
    }
}
