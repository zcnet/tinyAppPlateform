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
public class BotJsonRootBean implements Serializable {

	/** */
	private static final long serialVersionUID = 1L;
	private String bot_name;
	private String bot_version;
	private Ai_platform ai_platform;
	private String error_action;
	private String default_error_dialog_string;
	private Dialog_string_table dialog_string_table;

	public void setBot_name(String bot_name) {
		this.bot_name = bot_name;
	}

	public String getBot_name() {
		return bot_name;
	}

	public void setBot_version(String bot_version) {
		this.bot_version = bot_version;
	}

	public String getBot_version() {
		return bot_version;
	}

	public void setAi_platform(Ai_platform ai_platform) {
		this.ai_platform = ai_platform;
	}

	public Ai_platform getAi_platform() {
		return ai_platform;
	}

	public void setError_action(String error_action) {
		this.error_action = error_action;
	}

	public String getError_action() {
		return error_action;
	}

	public void setDefault_error_dialog_string(String default_error_dialog_string) {
		this.default_error_dialog_string = default_error_dialog_string;
	}

	public String getDefault_error_dialog_string() {
		return default_error_dialog_string;
	}

	public void setDialog_string_table(Dialog_string_table dialog_string_table) {
		this.dialog_string_table = dialog_string_table;
	}

	public Dialog_string_table getDialog_string_table() {
		return dialog_string_table;
	}

}
