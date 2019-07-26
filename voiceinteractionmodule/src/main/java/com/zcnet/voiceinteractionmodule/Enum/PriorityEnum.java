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
public enum PriorityEnum {

	// 0:不延后(默认)、1:延后
	NO_DELAY(0), DELAY(1);
	public int value = 0;

	private PriorityEnum(int value) {
		this.value = value;
	}

	public static PriorityEnum valueOf(int value) {
		switch (value) {
		case 0:
			return NO_DELAY;
		case 1:
			return DELAY;
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
