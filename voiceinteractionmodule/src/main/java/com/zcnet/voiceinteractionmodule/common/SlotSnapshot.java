package com.zcnet.voiceinteractionmodule.common;

import java.io.Serializable;

public class SlotSnapshot extends Entity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String type;
    private String originalWord;
    private String normalizedWord;
    private String transformerWord;
    private String aiSessionId;

    public SlotSnapshot() {
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

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOriginalWord() {
        return this.originalWord;
    }

    public void setOriginalWord(String originalWord) {
        this.originalWord = originalWord;
    }

    public String getNormalizedWord() {
        return this.normalizedWord;
    }

    public void setNormalizedWord(String normalizedWord) {
        this.normalizedWord = normalizedWord;
    }

    public String getTransformerWord() {
        return this.transformerWord;
    }

    public void setTransformerWord(String transformerWord) {
        this.transformerWord = transformerWord;
    }

    public String getAiSessionId() {
        return this.aiSessionId;
    }

    public void setAiSessionId(String aiSessionId) {
        this.aiSessionId = aiSessionId;
    }
}
