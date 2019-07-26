/**
 *  vvap - Connected Car Operation Platform (CCOP)
 *  Copyright(C) 2016, 2017, 2018, vvap Development Team
 *  All Right Reserved.
 */
package com.zcnet.voiceinteractionmodule.usered;

import com.zcnet.voiceinteractionmodule.common.Text;

import java.io.Serializable;
import java.util.List;



/**
 * Descriptions
 *
 * @version Sep 19, 2018
 * @author vvap
 * @since JDK1.6
 *
 */
public class VAActionEntity implements Serializable {

	/** */
	private static final long serialVersionUID = 1L;

	private String name;
	private Integer priority;
	private Integer interval;
	private Integer retry;
	private Integer doActionNow;
	private List<Text> textList;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the priority
	 */
	public Integer getPriority() {
		return priority;
	}

	/**
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	/**
	 * @return the interval
	 */
	public Integer getInterval() {
		return interval;
	}

	/**
	 * @param interval
	 *            the interval to set
	 */
	public void setInterval(Integer interval) {
		this.interval = interval;
	}

	/**
	 * @return the retry
	 */
	public Integer getRetry() {
		return retry;
	}
	
	/**
	 * @param retry
	 *            the retry to set
	 */
	public void setRetry(Integer retry) {
		this.retry = retry;
	}
	/**
	 * @return the doActionNow
	 */
	public Integer getDoActionNow() {
		return doActionNow;
	}
	
	/**
	 * @param doActionNow
	 *            the doActionNow to set
	 */
	public void setDoActionNow(Integer doActionNow) {
		this.doActionNow = doActionNow;
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
	
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
