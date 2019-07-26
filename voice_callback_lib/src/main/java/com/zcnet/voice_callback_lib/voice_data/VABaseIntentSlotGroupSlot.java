package com.zcnet.voice_callback_lib.voice_data;

public class VABaseIntentSlotGroupSlot {
    private String slotName;
    private String requiredSlot;

    public String getSlotName() {return slotName;}
    public void setSlotName(String slotName) {this.slotName = slotName;}

    public String getRequiredSlot() {return requiredSlot;}
    public void setRequiredSlot(String requiredSlot) {this.requiredSlot = requiredSlot;}

    public String toString() {
        return "VABaseIntentSlotGroupSlot [slotName=" + slotName
                + ", requiredSlot=" + requiredSlot + "]";
    }
}