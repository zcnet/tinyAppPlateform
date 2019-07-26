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
public enum IsQuestionEnum {

	UNKNOWN(0), YES(1), NO(2);

	public int value;

	private IsQuestionEnum(int value) {
		this.value = value;
	}

	public static IsQuestionEnum valueOf(int value) {
		switch (value) {
		case 0:
			return UNKNOWN;
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
