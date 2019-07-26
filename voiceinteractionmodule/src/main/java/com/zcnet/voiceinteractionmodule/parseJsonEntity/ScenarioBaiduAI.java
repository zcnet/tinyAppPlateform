/**
 * Copyright 2018 bejson.com 
 */
package com.zcnet.voiceinteractionmodule.parseJsonEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Descriptions
 * 
 * @version Jul 3, 2018
 * @author Zeninte
 * @since JDK1.6
 * 
 */
public class ScenarioBaiduAI implements Serializable {

	/** */
	private static final long serialVersionUID = 1L;
	private String name;
	private Long id;
	private List<IntentBaiduAI> intents;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the intents
	 */
	public List<IntentBaiduAI> getIntents() {
		return intents;
	}

	/**
	 * @param intents
	 *            the intents to set
	 */
	public void setIntents(List<IntentBaiduAI> intents) {
		this.intents = intents;
	}


}
