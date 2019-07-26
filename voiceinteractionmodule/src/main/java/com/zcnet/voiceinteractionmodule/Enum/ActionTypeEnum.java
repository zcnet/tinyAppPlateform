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
public enum ActionTypeEnum {

	FILLSLOT(1), RECOGNITION_COMPLETE(2), RECOGNITION_ERROR(3);

	public int value = 1;

	private ActionTypeEnum(int value) {
		this.value = value;
	}

	public static ActionTypeEnum valueOf(int value) {
		switch (value) {
		case 1:
			return FILLSLOT;
		case 2:
			return RECOGNITION_COMPLETE;
		case 3:
			return RECOGNITION_ERROR;
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
