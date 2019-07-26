package com.zcnet.voiceinteractionmodule.InterData;
import java.io.Serializable;

public class voiceInterfaceSlotBean implements Serializable{
    private String name;
    private String originalWord;
    private String normalizedWord;
    private String transformerWord;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalWord() {
        return originalWord;
    }

    public void setOriginalWord(String originalWord) {
        this.originalWord = originalWord;
    }

    public String getNormalizedWord() {
        return normalizedWord;
    }

    public void setNormalizedWord(String normalizedWord) {
        this.normalizedWord = normalizedWord;
    }

    public String getTransformerWord() {
        return transformerWord;
    }

    public void setTransformerWord(String transformerWord) {
        this.transformerWord = transformerWord;
    }
}
