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
public class Refine_detail implements Serializable {

	/** */
	private static final long serialVersionUID = 1L;
	private List<Option_list> option_list;
	private String interact;
	private String clarify_reason;

	public void setOption_list(List<Option_list> option_list) {
		this.option_list = option_list;
	}

	public List<Option_list> getOption_list() {
		return option_list;
	}

	public void setInteract(String interact) {
		this.interact = interact;
	}

	public String getInteract() {
		return interact;
	}

	public void setClarify_reason(String clarify_reason) {
		this.clarify_reason = clarify_reason;
	}

	public String getClarify_reason() {
		return clarify_reason;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Refine_detail [option_list=" + option_list + ", interact=" + interact + ", clarify_reason="
				+ clarify_reason + "]";
	}

}
