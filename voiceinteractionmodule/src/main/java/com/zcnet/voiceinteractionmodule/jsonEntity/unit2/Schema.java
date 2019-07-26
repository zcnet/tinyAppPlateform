/**
 *  Zeninte - Connected Car Operation Platform (CCOP)
 *  Copyright(C) 2016, 2017, 2018, Zeninte Development Team
 *  All Right Reserved.
 */
package com.zcnet.voiceinteractionmodule.jsonEntity.unit2;

import java.io.Serializable;
import java.util.List;

/**
 * Descriptions
 * 
 * @version 2018.08.16
 * @author Zeninte
 * @since JDK1.6
 * 
 */
public class Schema implements Serializable {

	/** */
	private static final long serialVersionUID = 1L;
	private int intent_confidence;
	private List<SchemaSlots> slots;
	private int domain_confidence;
	private String intent;

	public void setIntent_confidence(int intent_confidence) {
		this.intent_confidence = intent_confidence;
	}

	public int getIntent_confidence() {
		return intent_confidence;
	}

	public void setSlots(List<SchemaSlots> slots) {
		this.slots = slots;
	}

	public List<SchemaSlots> getSlots() {
		return slots;
	}

	public void setDomain_confidence(int domain_confidence) {
		this.domain_confidence = domain_confidence;
	}

	public int getDomain_confidence() {
		return domain_confidence;
	}

	public void setIntent(String intent) {
		this.intent = intent;
	}

	public String getIntent() {
		return intent;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Schema [intent_confidence=" + intent_confidence + ", slots=" + slots + ", domain_confidence="
				+ domain_confidence + ", intent=" + intent + "]";
	}

}
