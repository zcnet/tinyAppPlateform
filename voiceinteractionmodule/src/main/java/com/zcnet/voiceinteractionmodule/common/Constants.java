/**
 *  Zeninte - Connected Car Operation Platform (CCOP)
 *  Copyright(C) 2016, 2017, 2018, Zeninte Development Team
 *  All Right Reserved.
 */
package com.zcnet.voiceinteractionmodule.common;


/**
 * 
 * @version 2016-2-13
 * @author Zeninte
 * @since JDK1.6
 * 
 */
public class Constants {

// public final static String APPVER = "1.0";

	public final static int RESULT_RETURN_MAX = 200;
	public final static int RESULT_TRACE_RETURN_MAX = 500;
	
	public final static int PAGE_OFFSET_MIN = 1;
	public final static int PAGE_LIMIT_MIN = 1;
	public final static int PAGE_LIMIT_MAX = 100;
	public final static int PARAM_SPLIT_LENGTH_1 = 1;
	public final static int PARAM_SPLIT_MAX_LENGTH = 10;
	public final static int PARAM_LIST_MAX_LENGTH = 10;
	public final static int PARAM_LIST_MAX_LENGTH_ONE = 1;

	public final static int FUNC_TYPE_UNBOOKMARK = 0;
	public final static int FUNC_TYPE_BOOKMARK = 1;
	public final static int FUNC_TYPE_COMMON = 2;
	public final static int FUNC_TYPE_HISTORY = 3;

	public final static int SLS_STATUS_NOTAPPLICABLE = 0;
	public final static int SLS_STATUS_NEWADDED = 1;
	public final static int SLS_STATUS_ACCESSED = 2;
	public final static int SLS_STATUS_USED = 3;

	public final static int ADDRESS_COMMONTYPE_NOTCOMMON = -1;
	public final static int ADDRESS_COMMONTYPE_HOME = 0;
	public final static int ADDRESS_COMMONTYPE_COMPANY = 1;
	public final static int ADDRESS_COMMONTYPE_OTHER = 2;

	public final static int VEHICLE_BLOCK_BINDING_LIST_MAX_LENGTH = 50000;
	public final static int VEHICLE_BLOCK_BINDING_LIST_MIN_LENGTH = 500;

	/** 60*60*256 */
	public final static double LONLAT256 = 921600.0;

	public final static long REMINDERRECORD_DOWNLOAD_TIME_INTERVAL = 600000;

	/** 用户设备类型 **/
	public final static int USER_DEVICETYPE_VEHICLE = 0;
	public final static int USER_DEVICETYPE_HANDHELDDEVICES = 1;
	/** 意图状态 **/
	public final static Long INTENT_STATUS_COMPLETE = 0l;
	public final static Long INTENT_STATUS_DECIDED = 1l;
	public final static Long INTENT_STATUS_PENDING = 2l;

	// unit AI access_token
	public static final String REDIS_UNIT_AI_ACCESS_TOKEN = "REDIS_UNIT_AI_ACCESS_TOKEN";
	// 对话次数
	public static final String REDIS_BOT_DIALOG_TIMES = "REDIS_BOT_DIALOG_TIMES";
	
	public static final int SECONDS_OF_24_HOURS = 24 * 3600;

	public final static int PUSHMESSAGE_EXPIRED = 72 * 3600;

	public final static int PUSHMESSAGE_NUMBEROFFAILEDCYCLES = 3;

	public final static int HTTP_STATUS_CODE_OK = 200;

	public final static int HTTP_SLEEP_TIME = 1000;

}
