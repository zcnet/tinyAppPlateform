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
public enum ActionEnum {

	EXIT(0), ENTER(1), REMAIN(2);
	public int value = 0;

	private ActionEnum(int value) {
		this.value = value;
	}

	public static ActionEnum valueOf(int value) {
		switch (value) {
		case 0:
			return EXIT;
		case 1:
			return ENTER;
		case 2:
			return REMAIN;
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
