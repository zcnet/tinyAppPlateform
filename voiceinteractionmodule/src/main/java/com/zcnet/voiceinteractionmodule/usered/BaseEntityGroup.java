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
public class BaseEntityGroup implements Serializable {
	/** */
	private static final long serialVersionUID = 1L;
	private String name;
	private String must;
	private Integer leastFillSlot;
	private List<BaseEntityGroupEntity> slots;

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
	public Integer getLeastFillSlot() {
		return leastFillSlot;
	}

	/**
	 * @param leastFillSlot
	 *            the leastFillSlot to set
	 */
	public void setLeastFillSlot(Integer leastFillSlot) {
		this.leastFillSlot = leastFillSlot;
	}

	/**
	 * @return the slots
	 */
	public List<BaseEntityGroupEntity> getSlots() {
		return slots;
	}

	/**
	 * @param slots
	 *            the slots to set
	 */
	public void setSlots(List<BaseEntityGroupEntity> slots) {
		this.slots = slots;
	}

}
