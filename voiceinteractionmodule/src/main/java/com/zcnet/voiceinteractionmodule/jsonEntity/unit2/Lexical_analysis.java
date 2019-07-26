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
public class Lexical_analysis implements Serializable {

	/** */
	private static final long serialVersionUID = 1L;
	private List<String> etypes;
	private List<String> basic_word;
	private double weight;
	private String term;
	private String type;

	public void setBasic_word(List<String> basic_word) {
		this.basic_word = basic_word;
	}

	public List<String> getBasic_word() {
		return basic_word;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getWeight() {
		return weight;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getTerm() {
		return term;
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
		return "Lexical_analysis [etypes=" + etypes + ", basic_word=" + basic_word + ", weight=" + weight + ", term="
				+ term + ", type=" + type + "]";
	}

	/**
	 * @return the etypes
	 */
	public List<String> getEtypes() {
		return etypes;
	}

	/**
	 * @param etypes the etypes to set
	 */
	public void setEtypes(List<String> etypes) {
		this.etypes = etypes;
	}

}
