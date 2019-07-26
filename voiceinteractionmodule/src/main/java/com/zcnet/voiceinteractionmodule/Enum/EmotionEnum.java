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
public enum EmotionEnum {

	UNKNOWN(0), POSITIVE(1), NEUTAL(2), NEGATIVE(3);

	public int value = 0;

	private EmotionEnum(int value) {
		this.value = value;
	}

	public static EmotionEnum valueOf(int value) {
		switch (value) {
		case 0:
			return UNKNOWN;
		case 1:
			return POSITIVE;
		case 2:
			return NEUTAL;
		case 3:
			return NEGATIVE;
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
