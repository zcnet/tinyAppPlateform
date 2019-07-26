/**
 *  Zeninte - Connected Car Operation Platform (CCOP)
 *  Copyright(C) 2016, 2017, 2018, Zeninte Development Team
 *  All Right Reserved.
 */
package com.zcnet.voiceinteractionmodule.util;

import java.util.UUID;

/**
 * Descriptions
 *
 * @version Apr 10, 2017
 * @author Zeninte
 * @since JDK1.6
 *
 */
public class GuidUtil {
	/**
	 * 得到一个长度为36的uuid字符串
	 * 
	 * @return
	 */
	public static String generateGuid(){
		return UUID.randomUUID().toString();
	}
}
