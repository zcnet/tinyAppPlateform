package com.zcnet.voiceinteractionmodule.InterData;
import java.io.Serializable;

public class SlotForSlotGroupJsonEntity implements Serializable  {
    private static final long serialVersionUID = 1L;
    private String name;
    private String must;

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
}
