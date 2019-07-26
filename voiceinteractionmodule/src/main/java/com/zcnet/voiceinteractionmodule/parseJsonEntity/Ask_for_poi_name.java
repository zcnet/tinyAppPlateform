/**
 *  Zeninte - Connected Car Operation Platform (CCOP)
 *  Copyright(C) 2016, 2017, 2018, Zeninte Development Team
 *  All Right Reserved.
 */
package com.zcnet.voiceinteractionmodule.parseJsonEntity;

import java.io.Serializable;

/**
 * Descriptions
 * 
 * @version Jul 17, 2018
 * @author Zeninte
 * @since JDK1.6
 * 
 */
public class Ask_for_poi_name implements Serializable {

	private String lively;
	private String normal;

	public void setLively(String lively) {
		this.lively = lively;
	}

	public String getLively() {
		return lively;
	}

	public void setNormal(String normal) {
		this.normal = normal;
	}

	public String getNormal() {
		return normal;
	}

}
