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
public class CandidatesSlots extends SchemaSlots implements Serializable {

	/** */
	private static final long serialVersionUID = 1L;
	private int father_idx;
	private boolean need_clarify;

	public void setNeed_clarify(boolean need_clarify) {
		this.need_clarify = need_clarify;
	}

	public boolean getNeed_clarify() {
		return need_clarify;
	}

	public void setFather_idx(int father_idx) {
		this.father_idx = father_idx;
	}

	public int getFather_idx() {
		return father_idx;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CandidatesSlots [father_idx=" + father_idx + ", need_clarify=" + need_clarify + "]";
	}

}
