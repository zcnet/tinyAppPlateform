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
public class SchemaSlots implements Serializable {

	/** */
	private static final long serialVersionUID = 1L;
	private String word_type;
	private double confidence;
	private int length;
	private String name;
	private int session_offset;
	private String merge_method;
	private String original_word;
	private int begin;
	private String normalized_word;
	private List<SchemaSlots> sub_slots;

	public void setWord_type(String word_type) {
		this.word_type = word_type;
	}

	public String getWord_type() {
		return word_type;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getLength() {
		return length;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	/**
	 * @return the session_offset
	 */
	public int getSession_offset() {
		return session_offset;
	}

	/**
	 * @param session_offset
	 *            the session_offset to set
	 */
	public void setSession_offset(int session_offset) {
		this.session_offset = session_offset;
	}

	/**
	 * @return the merge_method
	 */
	public String getMerge_method() {
		return merge_method;
	}

	/**
	 * @param merge_method
	 *            the merge_method to set
	 */
	public void setMerge_method(String merge_method) {
		this.merge_method = merge_method;
	}

	public void setOriginal_word(String original_word) {
		this.original_word = original_word;
	}

	public String getOriginal_word() {
		return original_word;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getBegin() {
		return begin;
	}

	public void setNormalized_word(String normalized_word) {
		this.normalized_word = normalized_word;
	}

	public String getNormalized_word() {
		return normalized_word;
	}

	/**
	 * @return the sub_slots
	 */
	public List<SchemaSlots> getSub_slots() {
		return sub_slots;
	}

	/**
	 * @param sub_slots
	 *            the sub_slots to set
	 */
	public void setSub_slots(List<SchemaSlots> sub_slots) {
		this.sub_slots = sub_slots;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SchemaSlots [word_type=" + word_type + ", confidence=" + confidence + ", length=" + length + ", name="
				+ name + ", session_offset=" + session_offset + ", merge_method=" + merge_method + ", original_word="
				+ original_word + ", begin=" + begin + ", normalized_word=" + normalized_word + ", sub_slots="
				+ sub_slots + "]";
	}

}
