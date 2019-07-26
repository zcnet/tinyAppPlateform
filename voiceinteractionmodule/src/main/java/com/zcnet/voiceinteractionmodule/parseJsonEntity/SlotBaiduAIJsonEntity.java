/**
 * Copyright 2018 bejson.com 
 */
package com.zcnet.voiceinteractionmodule.parseJsonEntity;

import java.io.Serializable;

/**
 * Descriptions
 * 
 * @version Jul 3, 2018
 * @author Zeninte
 * @since JDK1.6
 * 
 */
public class SlotBaiduAIJsonEntity implements Serializable {

	/** */
	private static final long serialVersionUID = 1L;
	private String name;
	private String must;
	private String type;
	private String resetWhenIntentRecognized;
	private String transformer;
	private String clarify;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
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

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
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

	public void setTransformer(String transformer) {
		this.transformer = transformer;
	}

	public String getTransformer() {
		return transformer;
	}

	public void setClarify(String clarify) {
		this.clarify = clarify;
	}

	public String getClarify() {
		return clarify;
	}

}
