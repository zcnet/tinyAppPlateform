/**
 *  vvap - Connected Car Operation Platform (CCOP)
 *  Copyright(C) 2016, 2017, 2018, vvap Development Team
 *  All Right Reserved.
 */
package com.zcnet.voiceinteractionmodule.usered;

import java.io.Serializable;
import java.util.List;


import org.w3c.dom.Text;

/**
 * Descriptions
 *
 * @version Sep 19, 2018
 * @author vvap
 * @since JDK1.6
 *
 */
public class ErrorTextEntity implements Serializable {

	/** */
	private static final long serialVersionUID = 1L;

	private Integer errorHintNum;
	private List<Text> textList;
	
	/**
	 * @return the errorHintNum
	 */
	public Integer getErrorHintNum() {
		return errorHintNum;
	}
	
	/**
	 * @param errorHintNum
	 *            the errorHintNum to set
	 */
	public void setErrorHintNum(Integer errorHintNum) {
		this.errorHintNum = errorHintNum;
	}

	/**
	 * @return the textList
	 */
	public List<Text> getTextList() {
		return textList;
	}
	
	/**
	 * @param textList
	 *            the textList to set
	 */
	public void setTextList(List<Text> textList) {
		this.textList = textList;
	}

}
