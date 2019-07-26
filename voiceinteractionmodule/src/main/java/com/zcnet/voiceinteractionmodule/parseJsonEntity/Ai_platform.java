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
public class Ai_platform implements Serializable {

	/** */
	private static final long serialVersionUID = 1L;
	private String platform_name;
	private Platform_data platform_data;
	private Api_list api_list;

	public void setPlatform_name(String platform_name) {
		this.platform_name = platform_name;
	}

	public String getPlatform_name() {
		return platform_name;
	}

	public void setPlatform_data(Platform_data platform_data) {
		this.platform_data = platform_data;
	}

	public Platform_data getPlatform_data() {
		return platform_data;
	}

	public void setApi_list(Api_list api_list) {
		this.api_list = api_list;
	}

	public Api_list getApi_list() {
		return api_list;
	}

}
