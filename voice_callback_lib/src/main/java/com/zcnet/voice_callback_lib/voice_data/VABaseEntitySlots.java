package com.zcnet.voice_callback_lib.voice_data;

public class VABaseEntitySlots {
    private String name;
    private String originalWord;
    private String normalizedWord;
    private String transformerWord;
    private String must;
    private String type;
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

    public String getMust() {return must;}
    public void setMust(String must) {this.must = must;}

    public String getType() {return type;}
    public void setType(String type) {this.type = type;}

    public String getResetWhenIntentRecognized() {return resetWhenIntentRecognized;}
    public void setResetWhenIntentRecognized(String resetWhenIntentRecognized) {
        this.resetWhenIntentRecognized = resetWhenIntentRecognized;
    }

    public String getClarify() {return clarify;}
    public void setClarify(String clarify) {this.clarify = clarify;}

    @Override
    public String toString() {
        return "BaseEntitySlots [name=" + name + ", originalWord=" + originalWord
                + ", normalizedWord=" + normalizedWord + ", must=" + must + ", type=" + type
                + ", resetWhenIntentRecognized=" + resetWhenIntentRecognized
                + ", clarify=" + clarify + "]";
    }
}
