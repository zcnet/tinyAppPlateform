/**
 *  Zeninte - Connected Car Operation Platform (CCOP)
 *  Copyright(C) 2016, 2017, 2018, Zeninte Development Team
 *  All Right Reserved.
 */
package com.zcnet.voiceinteractionmodule.Enum;

/**
 * Descriptions
 * 
 * @version 2018年7月18日
 * @author Zeninte
 * @since JDK1.6
 * 
 */
public enum TypeEnum {

	FAILURE_IDENTIFY_UNIQUE(1), FAILURE_IDENTIFY_3TIMES(2), FAILURE_NON_ACTION_PARASITE(3), FAILURE_NON_ACTION_INDETIFY(
			4);

	public int value;

	private TypeEnum(int value) {
		this.value = value;
	}

	public static TypeEnum valueOf(int value) {
		switch (value) {
		case 1:
			return FAILURE_IDENTIFY_UNIQUE;
		case 2:
			return FAILURE_IDENTIFY_3TIMES;
		case 3:
			return FAILURE_NON_ACTION_PARASITE;
		case 4:
			return FAILURE_NON_ACTION_INDETIFY;
		default:
			return null;
		}
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
