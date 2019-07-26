package com.zcnet.voiceinteractionmodule.InterData;
import java.io.Serializable;

public class SlotAIJsonEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String must;
    private String type;
    private String resetWhenIntentRecognized;
    private String transformer;
    private String clarify;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMust() {
        return must;
    }

    public void setMust(String must) {
        this.must = must;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResetWhenIntentRecognized() {
        return resetWhenIntentRecognized;
    }

    public void setResetWhenIntentRecognized(String resetWhenIntentRecognized) {
        this.resetWhenIntentRecognized = resetWhenIntentRecognized;
    }

    public String getTransformer() {
        return transformer;
    }

    public void setTransformer(String transformer) {
        this.transformer = transformer;
    }

    public String getClarify() {
        return clarify;
    }

    public void setClarify(String clarify) {
        this.clarify = clarify;
    }
}
