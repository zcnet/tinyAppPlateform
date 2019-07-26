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
public class Action_list implements Serializable {

	/** */
	private static final long serialVersionUID = 1L;
	private String action_id;
	private Refine_detail refine_detail;
	private int confidence;
	private String custom_reply;
	private String say;
	private String type;

	public void setAction_id(String action_id) {
		this.action_id = action_id;
	}

	public String getAction_id() {
		return action_id;
	}

	public void setRefine_detail(Refine_detail refine_detail) {
		this.refine_detail = refine_detail;
	}

	public Refine_detail getRefine_detail() {
		return refine_detail;
	}

	public void setConfidence(int confidence) {
		this.confidence = confidence;
	}

	public int getConfidence() {
		return confidence;
	}

	public void setCustom_reply(String custom_reply) {
		this.custom_reply = custom_reply;
	}

	public String getCustom_reply() {
		return custom_reply;
	}

	public void setSay(String say) {
		this.say = say;
	}

	public String getSay() {
		return say;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Action_list [action_id=" + action_id + ", refine_detail=" + refine_detail + ", confidence="
				+ confidence + ", custom_reply=" + custom_reply + ", say=" + say + ", type=" + type + "]";
	}

}
