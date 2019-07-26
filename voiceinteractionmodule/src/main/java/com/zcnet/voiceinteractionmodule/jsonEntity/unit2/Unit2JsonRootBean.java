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
public class Unit2JsonRootBean implements Serializable {

	/** */
	private static final long serialVersionUID = 1L;
	private Result result;
	private String error_msg;
	private int error_code;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	private String action;

	public void setResult(Result result) {
		this.result = result;
	}

	public Result getResult() {
		return result;
	}

	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}

	public String getError_msg() {
		return error_msg;
	}

	public void setError_code(int error_code) {
		this.error_code = error_code;
	}

	public int getError_code() {
		return error_code;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Unit2JsonRootBean [result=" + result + ", error_msg=" + error_msg + ", error_code=" + error_code + "]";
	}

}
