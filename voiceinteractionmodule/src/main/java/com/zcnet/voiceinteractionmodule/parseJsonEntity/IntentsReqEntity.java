/**
 *  Zeninte - Connected Car Operation Platform (CCOP)
 *  Copyright(C) 2016, 2017, 2018, Zeninte Development Team
 *  All Right Reserved.
 */
package com.zcnet.voiceinteractionmodule.parseJsonEntity;

import java.util.List;

/**
 * Descriptions
 * 
 * @version Jul 17, 2018
 * @author Zeninte
 * @since JDK1.6
 * 
 */
public class IntentsReqEntity {

	private String name;
	private Integer actionCount;
	private List<Slots> slots;
	private List<SlotGroups> slotGroups;

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
	public List<Slots> getSlots() {
		return slots;
	}

	/**
	 * @param slots
	 *            the slots to set
	 */
	public void setSlots(List<Slots> slots) {
		this.slots = slots;
	}

	/**
	 * @return the slotGroups
	 */
	public List<SlotGroups> getSlotGroups() {
		return slotGroups;
	}

	/**
	 * @param slotGroups
	 *            the slotGroups to set
	 */
	public void setSlotGroups(List<SlotGroups> slotGroups) {
		this.slotGroups = slotGroups;
	}

}
