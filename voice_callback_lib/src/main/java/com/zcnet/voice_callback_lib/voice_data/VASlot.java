package com.zcnet.voice_callback_lib.voice_data;

import java.io.Serializable;

public class VASlot implements Serializable {
    private static final long serialVersionUID = 1L;
    private String slotType;
    private String originalWord;
    private String normalizedWord;
    private String transformerWord;

    public String getSlotType() {return slotType;}
    public void setSlotType(String slotType) {this.slotType = slotType;}

    public String getOriginalWord() {return originalWord;}
    public void setOriginalWord(String originalWord) {this.originalWord = originalWord;}

    public String getNormalizedWord() {return normalizedWord;}
    public void setNormalizedWord(String normalizedWord) {this.normalizedWord = normalizedWord;}

    public String getTransformerWord() {return transformerWord;}
    public void setTransformerWord(String transformerWord) {this.transformerWord = transformerWord;}
}
