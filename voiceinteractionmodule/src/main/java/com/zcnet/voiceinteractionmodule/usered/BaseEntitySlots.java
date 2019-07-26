/**
 *  Zeninte - Connected Car Operation Platform (CCOP)
 *  Copyright(C) 2016, 2017, 2018, Zeninte Development Team
 *  All Right Reserved.
 */
package com.zcnet.voiceinteractionmodule.usered;

import java.io.Serializable;

/**
 * Descriptions
 *
 * @version 2018年7月19日
 * @author Zeninte
 * @since JDK1.6
 *
 */
public class BaseEntitySlots implements Serializable {
	/** */
	private static final long serialVersionUID = 1L;
	private String name;
	private String originalWord;
	private String normalizedWord;
	private String transformerWord;
	private String must;
	private String type;
	private String resetWhenIntentRecognized;
	private String clarify;

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
	 * @return the originalWord
	 */
	public String getOriginalWord() {
		return originalWord;
	}

	/**
	 * @param originalWord
	 *            the originalWord to set
	 */
	public void setOriginalWord(String originalWord) {
		this.originalWord = originalWord;
	}

	/**
	 * @return the normalizedWord
	 */
	public String getNormalizedWord() {
		return normalizedWord;
	}

	/**
	 * @param normalizedWord
	 *            the normalizedWord to set
	 */
	public void setNormalizedWord(String normalizedWord) {
		this.normalizedWord = normalizedWord;
	}

	/**
	 * @return the transformerWord
	 */
	public String getTransformerWord() {
		return transformerWord;
	}

	/**
	 * @param transformerWord
	 *            the transformerWord to set
	 */
	public void setTransformerWord(String transformerWord) {
		this.transformerWord = transformerWord;
	}

	/**
	 * @return the must
	 */
	public String getMust() {
		return must;
	}

	/**
	 * @param must
	 *            the must to set
	 */
	public void setMust(String must) {
		this.must = must;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the resetWhenIntentRecognized
	 */
	public String getResetWhenIntentRecognized() {
		return resetWhenIntentRecognized;
	}

	/**
	 * @param resetWhenIntentRecognized
	 *            the resetWhenIntentRecognized to set
	 */
	public void setResetWhenIntentRecognized(String resetWhenIntentRecognized) {
		this.resetWhenIntentRecognized = resetWhenIntentRecognized;
	}

	/**
	 * @return the clarify
	 */
	public String getClarify() {
		return clarify;
	}

	/**
	 * @param clarify
	 *            the clarify to set
	 */
	public void setClarify(String clarify) {
		this.clarify = clarify;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BaseEntitySlots [name=" + name + ", originalWord=" + originalWord + ", normalizedWord="
				+ normalizedWord + ", must=" + must + ", type=" + type + ", resetWhenIntentRecognized="
				+ resetWhenIntentRecognized + ", clarify=" + clarify + "]";
	}

}
