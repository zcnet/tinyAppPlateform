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
public enum ReEnterEnum {

	YES(1), NO(2);

	public int value = 1;

	private ReEnterEnum(int value) {
		this.value = value;
	}

	public static ReEnterEnum valueOf(int value) {
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
