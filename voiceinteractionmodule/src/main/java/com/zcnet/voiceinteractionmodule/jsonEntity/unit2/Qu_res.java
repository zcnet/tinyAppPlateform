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
public class Qu_res implements Serializable {

	/** */
	private static final long serialVersionUID = 1L;
	private List<Candidates> candidates;
	private String qu_res_chosen;
	private Sentiment_analysis sentiment_analysis;
	private List<Lexical_analysis> lexical_analysis;
	private String raw_query;
	private List<Nerl_result> nerl_result;
	private int status;
	private int timestamp;

	public void setCandidates(List<Candidates> candidates) {
		this.candidates = candidates;
	}

	public List<Candidates> getCandidates() {
		return candidates;
	}

	public void setQu_res_chosen(String qu_res_chosen) {
		this.qu_res_chosen = qu_res_chosen;
	}

	public String getQu_res_chosen() {
		return qu_res_chosen;
	}

	public void setSentiment_analysis(Sentiment_analysis sentiment_analysis) {
		this.sentiment_analysis = sentiment_analysis;
	}

	public Sentiment_analysis getSentiment_analysis() {
		return sentiment_analysis;
	}

	public void setLexical_analysis(List<Lexical_analysis> lexical_analysis) {
		this.lexical_analysis = lexical_analysis;
	}

	public List<Lexical_analysis> getLexical_analysis() {
		return lexical_analysis;
	}

	public void setRaw_query(String raw_query) {
		this.raw_query = raw_query;
	}

	public String getRaw_query() {
		return raw_query;
	}

	public void setNerl_result(List<Nerl_result> nerl_result) {
		this.nerl_result = nerl_result;
	}

	public List<Nerl_result> getNerl_result() {
		return nerl_result;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

	public int getTimestamp() {
		return timestamp;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Qu_res [candidates=" + candidates + ", qu_res_chosen=" + qu_res_chosen + ", sentiment_analysis="
				+ sentiment_analysis + ", lexical_analysis=" + lexical_analysis + ", raw_query=" + raw_query
				+ ", nerl_result=" + nerl_result + ", status=" + status + ", timestamp=" + timestamp + "]";
	}

}
