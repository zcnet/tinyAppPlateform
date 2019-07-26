/**
 *  Zeninte - Connected Car Operation Platform (CCOP)
 *  Copyright(C) 2016, 2017, 2018, Zeninte Development Team
 *  All Right Reserved.
 */
package com.zcnet.voiceinteractionmodule.usered;

import java.io.Serializable;
import java.util.List;


/**
 * Descriptions
 *
 * @version 2018年7月19日
 * @author Zeninte
 * @since JDK1.6
 *
 */
public class BaseIntentEntity implements Serializable {
	/** */
	private static final long serialVersionUID = 1L;
	private String name;
	private Integer actionCount;
	private List<BaseEntitySlots> slots;
	private List<BaseEntityGroup> slotGroups;

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
	 * @return the actionCount
	 */
	public Integer getActionCount() {
		return actionCount;
	}

	/**
	 * @param actionCount
	 *            the actionCount to set
	 */
	public void setActionCount(Integer actionCount) {
		this.actionCount = actionCount;
	}

	/**
	 * @return the slots
	 */
	public List<BaseEntitySlots> getSlots() {
		return slots;
	}

	/**
	 * @param slots
	 *            the slots to set
	 */
	public void setSlots(List<BaseEntitySlots> slots) {
		this.slots = slots;
	}

	/**
	 * @return the slotGroups
	 */
	public List<BaseEntityGroup> getSlotGroups() {
		return slotGroups;
	}

	/**
	 * @param slotGroups
	 *            the slotGroups to set
	 */
	public void setSlotGroups(List<BaseEntityGroup> slotGroups) {
		this.slotGroups = slotGroups;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BaseIntentEntity [name=" + name + ", actionCount=" + actionCount + ", slots=" + slots + ", slotGroups="
				+ slotGroups + "]";
	}

}
