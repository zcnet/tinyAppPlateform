package com.zcnet.voiceinteractionmodule.InterData;
import java.io.Serializable;
import java.util.List;

public class IntentAI implements Serializable{
    private static final long serialVersionUID = 1L;
    private String name;
    private String actionable;
    private String action;
    private String backtrackAction;
    private String onStatusDecide;
    private String onSlotFill;
    private String onStatusPending;
    private String onSlotFillComplete;
    private String onStatusComplete;
    private String onDuplicate;
    private String reEnterable;
    private List<SlotAIJsonEntity> slots;
    private List<SlotGroupAIJsonEntity> slotGroups;

    private String onComplete;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActionable() {
        return actionable;
    }

    public void setActionable(String actionable) {
        this.actionable = actionable;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getBacktrackAction() {
        return backtrackAction;
    }

    public void setBacktrackAction(String backtrackAction) {
        this.backtrackAction = backtrackAction;
    }

    public String getOnStatusDecide() {
        return onStatusDecide;
    }

    public void setOnStatusDecide(String onStatusDecide) {
        this.onStatusDecide = onStatusDecide;
    }

    public String getOnSlotFill() {
        return onSlotFill;
    }

    public void setOnSlotFill(String onSlotFill) {
        this.onSlotFill = onSlotFill;
    }

    public String getOnStatusPending() {
        return onStatusPending;
    }

    public void setOnStatusPending(String onStatusPending) {
        this.onStatusPending = onStatusPending;
    }

    public String getOnSlotFillComplete() {
        return onSlotFillComplete;
    }

    public void setOnSlotFillComplete(String onSlotFillComplete) {
        this.onSlotFillComplete = onSlotFillComplete;
    }

    public String getOnStatusComplete() {
        return onStatusComplete;
    }

    public void setOnStatusComplete(String onStatusComplete) {
        this.onStatusComplete = onStatusComplete;
    }

    public String getOnDuplicate() {
        return onDuplicate;
    }

    public void setOnDuplicate(String onDuplicate) {
        this.onDuplicate = onDuplicate;
    }

    public String getReEnterable() {
        return reEnterable;
    }

    public void setReEnterable(String reEnterable) {
        this.reEnterable = reEnterable;
    }

    public List<SlotAIJsonEntity> getSlots() {
        return slots;
    }

    public void setSlots(List<SlotAIJsonEntity> slots) {
        this.slots = slots;
    }

    public List<SlotGroupAIJsonEntity> getSlotGroups() {
        return slotGroups;
    }

    public void setSlotGroups(List<SlotGroupAIJsonEntity> slotGroups) {
        this.slotGroups = slotGroups;
    }

    public String getOnComplete() {
        return onComplete;
    }

    public void setOnComplete(String onComplete) {
        this.onComplete = onComplete;
    }
}
