/**
  * Copyright 2018 bejson.com 
  */
package com.zcnet.voiceinteractionmodule.usered;

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
public class ScenarioJsonRootBean implements Serializable {
	/** */
	private static final long serialVersionUID = 1L;
	private String botId;
	private String scenarioName;
	private String scenarioVersion;
	private List<ScenarioBaiduAI> scenarios;


	/**
	 * @return the botId
	 */
	public String getBotId() {
		return botId;
	}

	/**
	 * @param botId
	 *            the botId to set
	 */
	public void setBotId(String botId) {
		this.botId = botId;
	}

	/**
	 * @return the scenarioName
	 */
	public String getScenarioName() {
		return scenarioName;
	}

	/**
	 * @param scenarioName
	 *            the scenarioName to set
	 */
	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}

	/**
	 * @return the scenarioVersion
	 */
	public String getScenarioVersion() {
		return scenarioVersion;
	}

	/**
	 * @param scenarioVersion
	 *            the scenarioVersion to set
	 */
	public void setScenarioVersion(String scenarioVersion) {
		this.scenarioVersion = scenarioVersion;
	}

	/**
	 * @return the scenarios
	 */
	public List<ScenarioBaiduAI> getScenarios() {
		return scenarios;
	}

	/**
	 * @param scenarios
	 *            the scenarios to set
	 */
	public void setScenarios(List<ScenarioBaiduAI> scenarios) {
		this.scenarios = scenarios;
	}





}