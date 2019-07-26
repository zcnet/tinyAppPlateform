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
public class Candidates implements Serializable {

	/** */
	private static final long serialVersionUID = 1L;
	private double intent_confidence;
	private String match_info;
	private List<CandidatesSlots> slots;
	private Extra_info extra_info;
	private double confidence;
	private int domain_confidence;
	private String from_who;
	private String intent;
	private boolean intent_need_clarify;

	public void setIntent_confidence(double intent_confidence) {
		this.intent_confidence = intent_confidence;
	}

	public double getIntent_confidence() {
		return intent_confidence;
	}

	public void setMatch_info(String match_info) {
		this.match_info = match_info;
	}

	public String getMatch_info() {
		return match_info;
	}

	public void setSlots(List<CandidatesSlots> slots) {
		this.slots = slots;
	}

	public List<CandidatesSlots> getSlots() {
		return slots;
	}

	public void setExtra_info(Extra_info extra_info) {
		this.extra_info = extra_info;
	}

	public Extra_info getExtra_info() {
		return extra_info;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setDomain_confidence(int domain_confidence) {
		this.domain_confidence = domain_confidence;
	}

	public int getDomain_confidence() {
		return domain_confidence;
	}

	public void setFrom_who(String from_who) {
		this.from_who = from_who;
	}

	public String getFrom_who() {
		return from_who;
	}

	public void setIntent(String intent) {
		this.intent = intent;
	}

	public String getIntent() {
		return intent;
	}

	public void setIntent_need_clarify(boolean intent_need_clarify) {
		this.intent_need_clarify = intent_need_clarify;
	}

	public boolean getIntent_need_clarify() {
		return intent_need_clarify;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Candidates [intent_confidence=" + intent_confidence + ", match_info=" + match_info + ", slots=" + slots
				+ ", extra_info=" + extra_info + ", confidence=" + confidence + ", domain_confidence="
				+ domain_confidence + ", from_who=" + from_who + ", intent=" + intent + ", intent_need_clarify="
				+ intent_need_clarify + "]";
	}

}
