package com.zcnet.voiceinteractionmodule.InterData;

import java.io.Serializable;
import java.util.List;

public class SlotGroupAIJsonEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String must;
    private String leastFillSlot;
    private List<SlotForSlotGroupJsonEntity> slots;

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

    public String getLeastFillSlot() {
        return leastFillSlot;
    }

    public void setLeastFillSlot(String leastFillSlot) {
        this.leastFillSlot = leastFillSlot;
    }

    public List<SlotForSlotGroupJsonEntity> getSlots() {
        return slots;
    }

    public void setSlots(List<SlotForSlotGroupJsonEntity> slots) {
        this.slots = slots;
    }
}
