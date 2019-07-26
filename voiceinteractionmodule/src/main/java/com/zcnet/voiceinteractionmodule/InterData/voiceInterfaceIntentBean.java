package com.zcnet.voiceinteractionmodule.InterData;
import java.io.Serializable;
import java.util.List;

public class voiceInterfaceIntentBean implements Serializable {
    private String name;
    private List<voiceInterfaceSlotBean> slots;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<voiceInterfaceSlotBean> getSlots() {
        return slots;
    }

    public void setSlots(List<voiceInterfaceSlotBean> slots) {
        this.slots = slots;
    }
}
