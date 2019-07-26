/**
 *  Zeninte - Connected Car Operation Platform (CCOP)
 *  Copyright(C) 2016, 2017, 2018, Zeninte Development Team
 *  All Right Reserved.
 */
package com.zcnet.voiceinteractionmodule.jsonEntity.unit2;

import java.io.Serializable;

/**
 * Descriptions
 * 
 * @version 2018.08.16
 * @author Zeninte
 * @since JDK1.6
 * 
 */
public class Extra_info implements Serializable {

	/** */
	private static final long serialVersionUID = 1L;
	private String group_id;
	private String real_threshold;
	private String threshold;

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setReal_threshold(String real_threshold) {
		this.real_threshold = real_threshold;
	}

	public String getReal_threshold() {
		return real_threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}

	public String getThreshold() {
		return threshold;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Extra_info [group_id=" + group_id + ", real_threshold=" + real_threshold + ", threshold=" + threshold
				+ "]";
	}

}
