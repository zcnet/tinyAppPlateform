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
public class SlotGroups {

	private String name;
	private String must;
	private int leastFillSlot;
	private List<Slots> slots;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setMust(String must) {
		this.must = must;
	}

	public String getMust() {
		return must;
	}

	/**
	 * @return the leastFillSlot
	 */
	public int getLeastFillSlot() {
		return leastFillSlot;
	}

	/**
	 * @param leastFillSlot
	 *            the leastFillSlot to set
	 */
	public void setLeastFillSlot(int leastFillSlot) {
		this.leastFillSlot = leastFillSlot;
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


}
