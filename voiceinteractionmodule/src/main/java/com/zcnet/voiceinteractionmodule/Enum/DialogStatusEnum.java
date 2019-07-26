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
public enum DialogStatusEnum {

	// 0:确认(默认)、1:合并、2:重入、3:仅填槽、4:填槽完成
	DECIDED(0), MERGE(1), RE_ENTER(2), FILLING(3), DONE(4);
	public int value = 0;

	private DialogStatusEnum(int value) {
		this.value = value;
	}

	public static DialogStatusEnum valueOf(int value) {
		switch (value) {
		case 0:
			return DECIDED;
		case 1:
			return MERGE;
		case 2:
			return RE_ENTER;
		case 3:
			return FILLING;
		case 4:
			return DONE;
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
