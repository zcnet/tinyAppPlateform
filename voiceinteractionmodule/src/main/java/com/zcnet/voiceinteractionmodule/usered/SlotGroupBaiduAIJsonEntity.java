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
public class SlotGroupBaiduAIJsonEntity implements Serializable {

	/** */
	private static final long serialVersionUID = 1L;
	private String name;
	private String must;
	private String leastFillSlot;
	private List<SlotForSlotGroupJsonEntity> slots;

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
	 * @return the must
	 */
	public String getMust() {
		return must;
	}

	/**
	 * @param must
	 *            the must to set
	 */
	public void setMust(String must) {
		this.must = must;
	}


	/**
	 * @return the leastFillSlot
	 */
	public String getLeastFillSlot() {
		return leastFillSlot;
	}

	/**
	 * @param leastFillSlot
	 *            the leastFillSlot to set
	 */
	public void setLeastFillSlot(String leastFillSlot) {
		this.leastFillSlot = leastFillSlot;
	}

	/**
	 * @return the slots
	 */
	public List<SlotForSlotGroupJsonEntity> getSlots() {
		return slots;
	}

	/**
	 * @param slots
	 *            the slots to set
	 */
	public void setSlots(List<SlotForSlotGroupJsonEntity> slots) {
		this.slots = slots;
	}


}
