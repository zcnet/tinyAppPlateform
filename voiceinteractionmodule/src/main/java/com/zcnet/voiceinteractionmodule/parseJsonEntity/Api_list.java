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
public class Api_list implements Serializable {

	/** */
	private static final long serialVersionUID = 1L;
	private String auth_api;
	private String dialog_api;

	public void setAuth_api(String auth_api) {
		this.auth_api = auth_api;
	}

	public String getAuth_api() {
		return auth_api;
	}

	public void setDialog_api(String dialog_api) {
		this.dialog_api = dialog_api;
	}

	public String getDialog_api() {
		return dialog_api;
	}

}
