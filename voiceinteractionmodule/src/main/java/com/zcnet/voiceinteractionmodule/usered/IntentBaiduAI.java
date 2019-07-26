/**
 * Copyright 2018 bejson.com 
 */
package com.zcnet.voiceinteractionmodule.usered;

import java.io.Serializable;
import java.util.List;

/**
 * Descriptions
 * 
 * @version Jul 3, 2018
 * @author Zeninte
 * @since JDK1.6
 * 
 */
public class IntentBaiduAI implements Serializable {

	/** */
	private static final long serialVersionUID = 1L;
	private String name;
	private String actionable;
	private String action;

	//remove by sunhy
//	private String backtrackAction;
//	private String onStatusDecide;

	private String onSlotFill;
	private String onStatusPending;
	private String onSlotFillComplete;
	private String onStatusComplete;
	private String onDuplicate;
	private String reEnterable;
	private List<SlotBaiduAIJsonEntity> slots;

	//remove by sunhy
//	private List<SlotGroupBaiduAIJsonEntity> slotGroups;

	private String onComplete;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the actionable
	 */
	public String getActionable() {
		return actionable;
	}
	
	/**
	 * @param actionable
	 *            the actionable to set
	 */
	public void setActionable(String actionable) {
		this.actionable = actionable;
	}
	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}
	
	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	//remove by sunhy
//	/**
//	 * @return the backtrackAction
//	 */
//	public String getBacktrackAction() {
//		return backtrackAction;
//	}
//
//	/**
//	 * @param backtrackAction
//	 *            the backtrackAction to set
//	 */
//	public void setBacktrackAction(String backtrackAction) {
//		this.backtrackAction = backtrackAction;
//	}
//
//	/**
//	 * @return the onStatusDecide
//	 */
//	public String getOnStatusDecide() {
//		return onStatusDecide;
//	}

//	/**
//	 * @param onStatusDecide
//	 *            the onStatusDecide to set
//	 */
//	public void setOnStatusDecide(String onStatusDecide) {
//		this.onStatusDecide = onStatusDecide;
//	}

	
	/**
	 * @return the onSlotFill
	 */
	public String getOnSlotFill() {
		return onSlotFill;
	}
	
	/**
	 * @param onSlotFill
	 *            the onSlotFill to set
	 */
	public void setOnSlotFill(String onSlotFill) {
		this.onSlotFill = onSlotFill;
	}
	
	/**
	 * @return the onStatusPending
	 */
	public String getOnStatusPending() {
		return onStatusPending;
	}
	
	/**
	 * @param onStatusPending
	 *            the onStatusPending to set
	 */
	public void setOnStatusPending(String onStatusPending) {
		this.onStatusPending = onStatusPending;
	}
	

	/**
	 * @return the onSlotFillComplete
	 */
	public String getOnSlotFillComplete() {
		return onSlotFillComplete;
	}

	/**
	 * @param onSlotFillComplete
	 *            the onSlotFillComplete to set
	 */
	public void setOnSlotFillComplete(String onSlotFillComplete) {
		this.onSlotFillComplete = onSlotFillComplete;
	}

	/**
	 * @return the onDuplicate
	 */
	public String getOnDuplicate() {
		return onDuplicate;
	}
	
	/**
	 * @param onDuplicate
	 *            the onDuplicate to set
	 */
	public void setOnDuplicate(String onDuplicate) {
		this.onDuplicate = onDuplicate;
	}
	
	/**
	 * @return the reEnterable
	 */
	public String getReEnterable() {
		return reEnterable;
	}
	
	/**
	 * @param reEnterable
	 *            the reEnterable to set
	 */
	public void setReEnterable(String reEnterable) {
		this.reEnterable = reEnterable;
	}

	/**
	 * @return the slots
	 */
	public List<SlotBaiduAIJsonEntity> getSlots() {
		return slots;
	}

	/**
	 * @param slots
	 *            the slots to set
	 */
	public void setSlots(List<SlotBaiduAIJsonEntity> slots) {
		this.slots = slots;
	}

	//remove by sunhy
//	/**
//	 * @return the slotGroups
//	 */
//	public List<SlotGroupBaiduAIJsonEntity> getSlotGroups() {
//		return slotGroups;
//	}
//
//	/**
//	 * @param slotGroups
//	 *            the slotGroups to set
//	 */
//	public void setSlotGroups(List<SlotGroupBaiduAIJsonEntity> slotGroups) {
//		this.slotGroups = slotGroups;
//	}

	/**
	 * @return the onStatusComplete
	 */
	public String getOnStatusComplete() {
		return onStatusComplete;
	}

	/**
	 * @param onStatusComplete
	 *            the onStatusComplete to set
	 */
	public void setOnStatusComplete(String onStatusComplete) {
		this.onStatusComplete = onStatusComplete;
	}

	/**
	 * @return the onComplete
	 */
	public String getOnComplete() {
		return onComplete;
	}

	/**
	 * @param onComplete
	 *            the onComplete to set
	 */
	public void setOnComplete(String onComplete) {
		this.onComplete = onComplete;
	}


	//remove by sunhy
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
//	@Override
//	public String toString() {
//		return "IntentBaiduAI [name=" + name + ", actionable=" + actionable + ", action=" + action
//				+ ", backtrackAction=" + backtrackAction + ", onStatusDecide=" + onStatusDecide + ", onSlotFill="
//				+ onSlotFill + ", onStatusPending=" + onStatusPending + ", onSlotFillComplete=" + onSlotFillComplete
//				+ ", onStatusComplete=" + onStatusComplete + ", onDuplicate=" + onDuplicate + ", reEnterable="
//				+ reEnterable + ", slots=" + slots + ", slotGroups=" + slotGroups + ", onComplete=" + onComplete + "]";
//	}

}
