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
public class Nerl_result implements Serializable {

	/** */
	private static final long serialVersionUID = 1L;
	private List<String> etypes;
	private String name;
	private String etype;

	public void setEtypes(List<String> etypes) {
		this.etypes = etypes;
	}

	public List<String> getEtypes() {
		return etypes;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setEtype(String etype) {
		this.etype = etype;
	}

	public String getEtype() {
		return etype;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Nerl_result [etypes=" + etypes + ", name=" + name + ", etype=" + etype + "]";
	}

}
