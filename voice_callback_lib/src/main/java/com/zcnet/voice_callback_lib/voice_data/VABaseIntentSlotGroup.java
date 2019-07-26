package com.zcnet.voice_callback_lib.voice_data;

import java.util.List;

public class VABaseIntentSlotGroup {
    private String slotGroupName;
    private String requiredSlotGroup;
    private Integer leastFillSlot;
    private List<VABaseIntentSlotGroupSlot> slots;

    public String getSlotGroupName() {return slotGroupName;}
    public void setSlotGroupName(String slotGroupName) {this.slotGroupName = slotGroupName;}

    public String getRequiredSlotGroup() {return requiredSlotGroup;}
    public void setRequiredSlotGroup(String requiredSlotGroup) {
        this.requiredSlotGroup = requiredSlotGroup;
    }

    public Integer getLeastFillSlot() {return leastFillSlot;}
    public void setLeastFillSlot(Integer leastFillSlot) {this.leastFillSlot = leastFillSlot;}

    public List<VABaseIntentSlotGroupSlot> getSlots() {return slots;}
    public void setSlots(List<VABaseIntentSlotGroupSlot> slots) {this.slots = slots;}

    public String toString() {
        return "VABaseIntentSlotGroup [slotGroupName=" + slotGroupName
                + ", requiredSlotGroup=" + requiredSlotGroup
                + ", leastFillSlot=" + leastFillSlot + ", slots=" + slots + "]";
    }
}
