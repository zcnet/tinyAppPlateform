package com.zcnet.voice_callback_lib.voice_data;

import java.io.Serializable;

public class VABaseIntentSlot implements Serializable {
    private String name;
    private String originalWord;
    private String normalizedWord;
    private String transformerWord;
    private String must;
    private String resetWhenIntentRecognized;
    private String clarify;

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getOriginalWord() {return originalWord;}
    public void setOriginalWord(String originalWord) {this.originalWord = originalWord;}

    public String getNormalizedWord() {return normalizedWord;}
    public void setNormalizedWord(String normalizedWord) {this.normalizedWord = normalizedWord;}

    public String getTransformerWord() {return transformerWord;}
    public void setTransformerWord(String transformerWord) {this.transformerWord = transformerWord;}

    public String getRequiredSlot() {return must;}
    public void setRequiredSlot(String requiredSlot) {this.must = requiredSlot;}

    public String getResetWhenIntentRecognized() {return resetWhenIntentRecognized;}
    public void setResetWhenIntentRecognized(String resetWhenIntentRecognized) {
        this.resetWhenIntentRecognized = resetWhenIntentRecognized;
    }

    public String getClarify() {return clarify;}
    public void setClarify(String clarify) {this.clarify = clarify;}

    @Override
    public String toString() {
        return "VABaseIntentSlot [name=" + name + ", originalWord=" + originalWord
                + ", resetWhenIntentRecognized=" + resetWhenIntentRecognized
                + ", clarify=" + clarify + "]";
    }
}
