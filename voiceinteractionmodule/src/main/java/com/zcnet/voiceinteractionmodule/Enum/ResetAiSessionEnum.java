/**
 *  Zeninte - Connected Car Operation Platform (CCOP)
 *  Copyright(C) 2016, 2017, 2018, Zeninte Development Team
 *  All Right Reserved.
 */
package com.zcnet.voiceinteractionmodule.Enum;

/**
 * Descriptions
 * 
 * @version 2018年8月15日
 * @author Zeninte
 * @since JDK1.6
 * 
 */
public enum ResetAiSessionEnum {

	YES(1), NO(2);

	public int value;

	private ResetAiSessionEnum(int value) {
		this.value = value;
	}

	public static ResetAiSessionEnum valueOf(int value) {
		switch (value) {
		case 1:
			return YES;
		case 2:
			return NO;
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
