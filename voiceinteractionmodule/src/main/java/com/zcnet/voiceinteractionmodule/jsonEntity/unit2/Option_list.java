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
public class Option_list implements Serializable {

	/** */
	private static final long serialVersionUID = 1L;
	private String option;
	private Info info;


	/**
	 * @return the option
	 */
	public String getOption() {
		return option;
	}


	/**
	 * @param option
	 *            the option to set
	 */
	public void setOption(String option) {
		this.option = option;
	}


	/**
	 * @return the info
	 */
	public Info getInfo() {
		return info;
	}


	/**
	 * @param info
	 *            the info to set
	 */
	public void setInfo(Info info) {
		this.info = info;
	}


	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Option_list [option=" + option + ", info=" + info + "]";
	}

}
