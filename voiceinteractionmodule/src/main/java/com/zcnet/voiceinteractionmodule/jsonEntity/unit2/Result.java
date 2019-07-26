/**
 *  Zeninte - Connected Car Operation Platform (CCOP)
 *  Copyright(C) 2016, 2017, 2018, Zeninte Development Team
 *  All Right Reserved.
 */
package com.zcnet.voiceinteractionmodule.jsonEntity.unit2;

import java.io.Serializable;
import java.util.List;

/**
 * Descriptions
 * 
 * @version 2018.08.16
 * @author Zeninte
 * @since JDK1.6
 * 
 */
public class Result implements Serializable {

	/** */
	private static final long serialVersionUID = 1L;
	private String log_id;
	private List<Response> response_list;
	private Response response;
	private String bot_session;
	private String interaction_id;
	private String version;
	private String bot_id;
	private String timestamp;

	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}

	public String getLog_id() {
		return log_id;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public Response getResponse() {
		return response;
	}

	public List<Response> getResponse_list() {
		return response_list;
	}

	public void setResponse_list(List<Response> response_list) {
		this.response_list = response_list;
	}

	public void setBot_session(String bot_session) {
		this.bot_session = bot_session;
	}

	public String getBot_session() {
		return bot_session;
	}

	public void setInteraction_id(String interaction_id) {
		this.interaction_id = interaction_id;
	}

	public String getInteraction_id() {
		return interaction_id;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getVersion() {
		return version;
	}

	public void setBot_id(String bot_id) {
		this.bot_id = bot_id;
	}

	public String getBot_id() {
		return bot_id;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getTimestamp() {
		return timestamp;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */

}
