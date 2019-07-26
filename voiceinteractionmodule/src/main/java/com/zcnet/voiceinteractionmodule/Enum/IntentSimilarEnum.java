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
public enum IntentSimilarEnum {

	// 0:栈空、1:相同、2:相似、3:不相似
	STACK_EMPTY(0), SAME(1), SIMILAR(2), NOT_SIMILAR(3);
	public int value = 0;

	private IntentSimilarEnum(int value) {
		this.value = value;
	}

	public static IntentSimilarEnum valueOf(int value) {
		switch (value) {
		case 0:
			return STACK_EMPTY;
		case 1:
			return SAME;
		case 2:
			return SIMILAR;
		case 3:
			return NOT_SIMILAR;
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
