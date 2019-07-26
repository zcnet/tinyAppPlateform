/**
 *  Zeninte - Connected Car Operation Platform (CCOP)
 *  Copyright(C) 2016, 2017, 2018, Zeninte Development Team
 *  All Right Reserved.
 */
package com.zcnet.voiceinteractionmodule.parseJsonEntity;

import java.io.Serializable;

/**
 * Descriptions
 *
 * @version 2018年7月17日
 * @author Zeninte
 * @since JDK1.6
 *
 */
public class SlotForSlotGroupJsonEntity implements Serializable {
	/** */
	private static final long serialVersionUID = 1L;
	private String name;
	private String must;

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

}
