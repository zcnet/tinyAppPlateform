package com.zcnet.voice_callback_lib.voice_data;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class VABaseIntent {
    private String name;                      // 意图名称。
    private Integer actionCount;                    // 动作计数器。
    private List<VABaseIntentSlot> slots;           // 词槽。
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<VABaseIntentSlotGroup> slotGroups;     // 词槽组。

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public Integer getActionCount() {return actionCount;}
    public void setActionCount(Integer actionCount) {this.actionCount = actionCount;}

    public List<VABaseIntentSlot> getSlots() {return slots;}
    public void setSlots(List<VABaseIntentSlot> slots) {this.slots = slots;}

    public List<VABaseIntentSlotGroup> getSlotGroups() {return slotGroups;}
    public void setSlotGroups(List<VABaseIntentSlotGroup> slotGroups) {
        this.slotGroups = slotGroups;
    }

    public String toString() {
        return "VABaseIntent [name=" + name + ", actionCount=" + actionCount
                + ", slots=" + slots + ", slotGroups=" + slotGroups + "]";
    }
}
