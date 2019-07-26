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
public class Platform_data implements Serializable {

	/** */
	private static final long serialVersionUID = 1L;
	private String grant_type;
	private String api_key;
	private String api_secret_key;

	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}

	public String getGrant_type() {
		return grant_type;
	}

	public void setApi_key(String api_key) {
		this.api_key = api_key;
	}

	public String getApi_key() {
		return api_key;
	}

	public void setApi_secret_key(String api_secret_key) {
		this.api_secret_key = api_secret_key;
	}

	public String getApi_secret_key() {
		return api_secret_key;
	}

}
