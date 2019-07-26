/**
 *  Zeninte - Connected Car Operation Platform (CCOP)
 *  Copyright(C) 2016, 2017, 2018, Zeninte Development Team
 *  All Right Reserved.
 */
package com.zcnet.voiceinteractionmodule.usered;

import java.io.Serializable;
import java.util.List;


/**
 * Descriptions
 * 
 * @version Dec 6, 2017
 * @author Zeninte
 * @since JDK1.6
 * 
 */
public class BotDialogResultGetPhase6APIResult extends APIResult implements Serializable {
	/** */
	private static final long serialVersionUID = 1L;
	private Long botId;
	private String sessionId;
	private List<BaseIntentEntity> intent;
	private List<VAActionEntity> vaActionList;
	private List<ErrorTextEntity> errorTextList;
	private List<BuActionEntity> buActionList;
	private String message;

	/**
	 * @return the botId
	 */
	public Long getBotId() {
		return botId;
	}
	
	/**
	 * @param botId
	 *            the botId to set
	 */
	public void setBotId(Long botId) {
		this.botId = botId;
	}
	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}
	
	/**
	 * @param sessionId
	 *            the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	/**
	 * @return the vaActionList
	 */
	public List<VAActionEntity> getVaActionList() {
		return vaActionList;
	}

	/**
	 * @param vaActionList
	 *            the vaActionList to set
	 */
	public void setVaActionList(List<VAActionEntity> vaActionList) {
		this.vaActionList = vaActionList;
	}

	/**
	 * @return the errorTextList
	 */
	public List<ErrorTextEntity> getErrorTextList() {
		return errorTextList;
	}

	/**
	 * @param errorTextList
	 *            the errorTextList to set
	 */
	public void setErrorTextList(List<ErrorTextEntity> errorTextList) {
		this.errorTextList = errorTextList;
	}

	/**
	 * @return the buActionList
	 */
	public List<BuActionEntity> getBuActionList() {
		return buActionList;
	}

	/**
	 * @param buActionList
	 *            the buActionList to set
	 */
	public void setBuActionList(List<BuActionEntity> buActionList) {
		this.buActionList = buActionList;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BotDialogResultGetPhase6APIResult [botId=" + botId + ", sessionId=" + sessionId + ", vaActionList="
				+ vaActionList + ", errorTextList=" + errorTextList + ", buActionList=" + buActionList + ", message="
				+ message + "]";
	}

	/**
	 * @return the intent
	 */
	public List<BaseIntentEntity> getIntent() {
		return intent;
	}

	/**
	 * @param intent the intent to set
	 */
	public void setIntent(List<BaseIntentEntity> intent) {
		this.intent = intent;
	}


}
