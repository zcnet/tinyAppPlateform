/**
 *  Zeninte - Connected Car Operation Platform (CCOP)
 *  Copyright(C) 2016, 2017, 2018, Zeninte Development Team
 *  All Right Reserved.
 */
package com.zcnet.voiceinteractionmodule.parseJsonEntity;

/**
 * Descriptions
 * 
 * @version Jul 17, 2018
 * @author Zeninte
 * @since JDK1.6
 * 
 */
public class NLPInfoEx {

	private Integer isQuestion;
	private Integer isPolite;
	private Integer emotion;

	/**
	 * @return the isQuestion
	 */
	public Integer getIsQuestion() {
		return isQuestion;
	}

	/**
	 * @param isQuestion
	 *            the isQuestion to set
	 */
	public void setIsQuestion(Integer isQuestion) {
		this.isQuestion = isQuestion;
	}

	/**
	 * @return the isPolite
	 */
	public Integer getIsPolite() {
		return isPolite;
	}

	/**
	 * @param isPolite
	 *            the isPolite to set
	 */
	public void setIsPolite(Integer isPolite) {
		this.isPolite = isPolite;
	}

	/**
	 * @return the emotion
	 */
	public Integer getEmotion() {
		return emotion;
	}

	/**
	 * @param emotion
	 *            the emotion to set
	 */
	public void setEmotion(Integer emotion) {
		this.emotion = emotion;
	}

}
