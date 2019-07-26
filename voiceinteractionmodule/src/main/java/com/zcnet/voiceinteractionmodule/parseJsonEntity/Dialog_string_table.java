/**
 *  Zeninte - Connected Car Operation Platform (CCOP)
 *  Copyright(C) 2016, 2017, 2018, Zeninte Development Team
 *  All Right Reserved.
 */
package com.zcnet.voiceinteractionmodule.parseJsonEntity;

import java.io.Serializable;

/**
 * Descriptions
 * 
 * @version Jul 17, 2018
 * @author Zeninte
 * @since JDK1.6
 * 
 */
public class Dialog_string_table implements Serializable {

	/** */
	private static final long serialVersionUID = 1L;
	private Clarify_error clarify_error;
	private Clarify_backtrack clarify_backtrack;
	private Ask_for_locate ask_for_locate;
	private Ask_for_time ask_for_time;
	private Ask_for_name ask_for_name;
	private Ask_for_phone_num ask_for_phone_num;
	private Ask_for_carrier ask_for_carrier;
	private Ask_for_phone_type ask_for_phone_type;
	private Ask_for_use_onstar ask_for_use_onstar;
	private Ask_for_stop_call ask_for_stop_call;
	private Ask_for_confirm_yesno ask_for_confirm_yesno;
	private Ask_for_item_no ask_for_item_no;
	private Ask_for_item_unit ask_for_item_unit;
	private Ask_for_order ask_for_order;
	private Ask_for_append_num ask_for_append_num;
	private Ask_for_prev_fragment ask_for_prev_fragment;
	private Ask_for_curr_fragment ask_for_curr_fragment;
	private Ask_for_missing_call ask_for_missing_call;
	private Ask_for_redial_call ask_for_redial_call;
	private Ask_for_poi_name ask_for_poi_name;
	private Ask_for_poi_category ask_for_poi_category;

	public void setClarify_error(Clarify_error clarify_error) {
		this.clarify_error = clarify_error;
	}

	public Clarify_error getClarify_error() {
		return clarify_error;
	}

	public void setClarify_backtrack(Clarify_backtrack clarify_backtrack) {
		this.clarify_backtrack = clarify_backtrack;
	}

	public Clarify_backtrack getClarify_backtrack() {
		return clarify_backtrack;
	}

	public void setAsk_for_locate(Ask_for_locate ask_for_locate) {
		this.ask_for_locate = ask_for_locate;
	}

	public Ask_for_locate getAsk_for_locate() {
		return ask_for_locate;
	}

	public void setAsk_for_time(Ask_for_time ask_for_time) {
		this.ask_for_time = ask_for_time;
	}

	public Ask_for_time getAsk_for_time() {
		return ask_for_time;
	}

	public void setAsk_for_phone_num(Ask_for_phone_num ask_for_phone_num) {
		this.ask_for_phone_num = ask_for_phone_num;
	}

	public Ask_for_phone_num getAsk_for_phone_num() {
		return ask_for_phone_num;
	}

	public void setAsk_for_carrier(Ask_for_carrier ask_for_carrier) {
		this.ask_for_carrier = ask_for_carrier;
	}

	public Ask_for_carrier getAsk_for_carrier() {
		return ask_for_carrier;
	}

	public void setAsk_for_poi_name(Ask_for_poi_name ask_for_poi_name) {
		this.ask_for_poi_name = ask_for_poi_name;
	}

	public Ask_for_poi_name getAsk_for_poi_name() {
		return ask_for_poi_name;
	}

	public void setAsk_for_poi_category(Ask_for_poi_category ask_for_poi_category) {
		this.ask_for_poi_category = ask_for_poi_category;
	}

	public Ask_for_poi_category getAsk_for_poi_category() {
		return ask_for_poi_category;
	}

	/**
	 * @return the ask_for_phone_type
	 */
	public Ask_for_phone_type getAsk_for_phone_type() {
		return ask_for_phone_type;
	}

	/**
	 * @param ask_for_phone_type
	 *            the ask_for_phone_type to set
	 */
	public void setAsk_for_phone_type(Ask_for_phone_type ask_for_phone_type) {
		this.ask_for_phone_type = ask_for_phone_type;
	}

	/**
	 * @return the ask_for_use_onstar
	 */
	public Ask_for_use_onstar getAsk_for_use_onstar() {
		return ask_for_use_onstar;
	}

	/**
	 * @param ask_for_use_onstar
	 *            the ask_for_use_onstar to set
	 */
	public void setAsk_for_use_onstar(Ask_for_use_onstar ask_for_use_onstar) {
		this.ask_for_use_onstar = ask_for_use_onstar;
	}

	/**
	 * @return the ask_for_stop_call
	 */
	public Ask_for_stop_call getAsk_for_stop_call() {
		return ask_for_stop_call;
	}

	/**
	 * @param ask_for_stop_call
	 *            the ask_for_stop_call to set
	 */
	public void setAsk_for_stop_call(Ask_for_stop_call ask_for_stop_call) {
		this.ask_for_stop_call = ask_for_stop_call;
	}

	/**
	 * @return the ask_for_confirm_yesno
	 */
	public Ask_for_confirm_yesno getAsk_for_confirm_yesno() {
		return ask_for_confirm_yesno;
	}

	/**
	 * @param ask_for_confirm_yesno
	 *            the ask_for_confirm_yesno to set
	 */
	public void setAsk_for_confirm_yesno(Ask_for_confirm_yesno ask_for_confirm_yesno) {
		this.ask_for_confirm_yesno = ask_for_confirm_yesno;
	}

	/**
	 * @return the ask_for_item_no
	 */
	public Ask_for_item_no getAsk_for_item_no() {
		return ask_for_item_no;
	}

	/**
	 * @param ask_for_item_no
	 *            the ask_for_item_no to set
	 */
	public void setAsk_for_item_no(Ask_for_item_no ask_for_item_no) {
		this.ask_for_item_no = ask_for_item_no;
	}

	/**
	 * @return the ask_for_item_unit
	 */
	public Ask_for_item_unit getAsk_for_item_unit() {
		return ask_for_item_unit;
	}

	/**
	 * @param ask_for_item_unit
	 *            the ask_for_item_unit to set
	 */
	public void setAsk_for_item_unit(Ask_for_item_unit ask_for_item_unit) {
		this.ask_for_item_unit = ask_for_item_unit;
	}

	/**
	 * @return the ask_for_order
	 */
	public Ask_for_order getAsk_for_order() {
		return ask_for_order;
	}

	/**
	 * @param ask_for_order
	 *            the ask_for_order to set
	 */
	public void setAsk_for_order(Ask_for_order ask_for_order) {
		this.ask_for_order = ask_for_order;
	}

	/**
	 * @return the ask_for_append_num
	 */
	public Ask_for_append_num getAsk_for_append_num() {
		return ask_for_append_num;
	}

	/**
	 * @param ask_for_append_num
	 *            the ask_for_append_num to set
	 */
	public void setAsk_for_append_num(Ask_for_append_num ask_for_append_num) {
		this.ask_for_append_num = ask_for_append_num;
	}

	/**
	 * @return the ask_for_prev_fragment
	 */
	public Ask_for_prev_fragment getAsk_for_prev_fragment() {
		return ask_for_prev_fragment;
	}

	/**
	 * @param ask_for_prev_fragment
	 *            the ask_for_prev_fragment to set
	 */
	public void setAsk_for_prev_fragment(Ask_for_prev_fragment ask_for_prev_fragment) {
		this.ask_for_prev_fragment = ask_for_prev_fragment;
	}

	/**
	 * @return the ask_for_curr_fragment
	 */
	public Ask_for_curr_fragment getAsk_for_curr_fragment() {
		return ask_for_curr_fragment;
	}

	/**
	 * @param ask_for_curr_fragment
	 *            the ask_for_curr_fragment to set
	 */
	public void setAsk_for_curr_fragment(Ask_for_curr_fragment ask_for_curr_fragment) {
		this.ask_for_curr_fragment = ask_for_curr_fragment;
	}

	/**
	 * @return the ask_for_missing_call
	 */
	public Ask_for_missing_call getAsk_for_missing_call() {
		return ask_for_missing_call;
	}

	/**
	 * @param ask_for_missing_call
	 *            the ask_for_missing_call to set
	 */
	public void setAsk_for_missing_call(Ask_for_missing_call ask_for_missing_call) {
		this.ask_for_missing_call = ask_for_missing_call;
	}

	/**
	 * @return the ask_for_redial_call
	 */
	public Ask_for_redial_call getAsk_for_redial_call() {
		return ask_for_redial_call;
	}

	/**
	 * @param ask_for_redial_call
	 *            the ask_for_redial_call to set
	 */
	public void setAsk_for_redial_call(Ask_for_redial_call ask_for_redial_call) {
		this.ask_for_redial_call = ask_for_redial_call;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the ask_for_name
	 */
	public Ask_for_name getAsk_for_name() {
		return ask_for_name;
	}

	/**
	 * @param ask_for_name the ask_for_name to set
	 */
	public void setAsk_for_name(Ask_for_name ask_for_name) {
		this.ask_for_name = ask_for_name;
	}

}
