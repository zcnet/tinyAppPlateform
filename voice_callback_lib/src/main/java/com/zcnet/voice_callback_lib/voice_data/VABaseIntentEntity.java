package com.zcnet.voice_callback_lib.voice_data;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class VABaseIntentEntity {
    private String name;
    private Integer actionCount;
    private List<VABaseEntitySlots> slots;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<VABaseEntityGroup> slotGroups;

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public Integer getActionCount() {return actionCount;}
    public void setActionCount(Integer actionCount) {this.actionCount = actionCount;}

    public List<VABaseEntitySlots> getSlots() {return slots;}
    public void setSlots(List<VABaseEntitySlots> slots) {this.slots = slots;}

    public List<VABaseEntityGroup> getSlotGroups() {return slotGroups;}
    public void setSlotGroups(List<VABaseEntityGroup> slotGroups) {this.slotGroups = slotGroups;}
}
