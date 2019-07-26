/**
 *  vvap - Connected Car Operation Platform (CCOP)
 *  Copyright(C) 2016, 2017, 2018, vvap Development Team
 *  All Right Reserved.
 */
package com.zcnet.voiceinteractionmodule.usered;

import java.io.Serializable;
import java.util.List;

import com.zcnet.voiceinteractionmodule.common.BaseEntity;
/**
 * Descriptions
 *
 * @version Sep 14, 2018
 * @author vvap
 * @since JDK1.6
 *
 */
public class BaseOnResultReqPostData extends BaseEntity implements Serializable {
	/** */
	private static final long serialVersionUID = 1L;
	private List<BaseIntentEntity> intent;
	private Integer actionType;
	private List<VAActionEntity> vaActionList;
	private List<ErrorTextEntity> errorTextList;
	private List<BuActionEntity> buActionList;
	private Integer resetAiSession;
	private String frontSessionId;
	private String frontSessionRoundId;

	/**
	 * @return the intent
	 */
	public List<BaseIntentEntity> getIntent() {
		return intent;
	}
	
	/**
	 * @param intent
	 *            the intent to set
	 */
	public void setIntent(List<BaseIntentEntity> intent) {
		this.intent = intent;
	}
	/**
	 * @return the actionType
	 */
	public Integer getActionType() {
		return actionType;
	}
	
	/**
	 * @param actionType
	 *            the actionType to set
	 */
	public void setActionType(Integer actionType) {
		this.actionType = actionType;
	}
	
	/**
	 * @return the resetAiSession
	 */
	public Integer getResetAiSession() {
		return resetAiSession;
	}
	
	/**
	 * @param resetAiSession
	 *            the resetAiSession to set
	 */
	public void setResetAiSession(Integer resetAiSession) {
		this.resetAiSession = resetAiSession;
	}
	/**
	 * @return the frontSessionId
	 */
	public String getFrontSessionId() {
		return frontSessionId;
	}
	
	/**
	 * @param frontSessionId
	 *            the frontSessionId to set
	 */
	public void setFrontSessionId(String frontSessionId) {
		this.frontSessionId = frontSessionId;
	}
	/**
	 * @return the frontSessionRoundId
	 */
	public String getFrontSessionRoundId() {
		return frontSessionRoundId;
	}
	
	/**
	 * @param frontSessionRoundId
	 *            the frontSessionRoundId to set
	 */
	public void setFrontSessionRoundId(String frontSessionRoundId) {
		this.frontSessionRoundId = frontSessionRoundId;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BaseOnResultReqPostData [intent=" + intent + ", actionType=" + actionType + ", vaActionList="
				+ vaActionList + ", errorTextList" + errorTextList + ", buActionList" + buActionList
				+ ", resetAiSession=" + resetAiSession + ", frontSessionId=" + frontSessionId
				+ ", frontSessionRoundId=" + frontSessionRoundId + "]";
	}

	/**
	 * @return the errorTextList
	 */
	public List<ErrorTextEntity> getErrorTextList() {
		return errorTextList;
	}

	/**
	 * @param errorTextList the errorTextList to set
	 */
	public void setErrorTextList(List<ErrorTextEntity> errorTextList) {
		this.errorTextList = errorTextList;
	}

	/**
	 * @return the vaActionList
	 */
	public List<VAActionEntity> getVaActionList() {
		return vaActionList;
	}

	/**
	 * @param vaActionList the vaActionList to set
	 */
	public void setVaActionList(List<VAActionEntity> vaActionList) {
		this.vaActionList = vaActionList;
	}

	/**
	 * @return the buActionList
	 */
	public List<BuActionEntity> getBuActionList() {
		return buActionList;
	}

	/**
	 * @param buActionList the buActionList to set
	 */
	public void setBuActionList(List<BuActionEntity> buActionList) {
		this.buActionList = buActionList;
	}

}
