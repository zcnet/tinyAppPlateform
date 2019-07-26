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
public class Sentiment_analysis implements Serializable {

	/** */
	private static final long serialVersionUID = 1L;
	private double pval;
	private String label;

	public void setPval(double pval) {
		this.pval = pval;
	}

	public double getPval() {
		return pval;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Sentiment_analysis [pval=" + pval + ", label=" + label + "]";
	}

}
