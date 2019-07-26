/**
 *  vvap - Connected Car Operation Platform (CCOP)
 *  Copyright(C) 2016, 2017, 2018, vvap Development Team
 *  All Right Reserved.
 */
package com.zcnet.voiceinteractionmodule.usered;

import java.io.Serializable;


/**
 * Descriptions
 *
 * @version Sep 19, 2018
 * @author vvap
 * @since JDK1.6
 *
 */
public class BuActionEntity implements Serializable {

	/** */
	private static final long serialVersionUID = 1L;

	private String appName;
	private String moduleName;
	private Integer displayType;
	private Integer isReuse;
	private String content;
	
	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}
	
	/**
	 * @param appName
	 *            the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	/**
	 * @return the moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}
	
	/**
	 * @param moduleName
	 *            the moduleName to set
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	/**
	 * @return the displayType
	 */
	public Integer getDisplayType() {
		return displayType;
	}
	
	/**
	 * @param displayType
	 *            the displayType to set
	 */
	public void setDisplayType(Integer displayType) {
		this.displayType = displayType;
	}
	
	/**
	 * @return the isReuse
	 */
	public Integer getIsReuse() {
		return isReuse;
	}
	
	/**
	 * @param isReuse
	 *            the isReuse to set
	 */
	public void setIsReuse(Integer isReuse) {
		this.isReuse = isReuse;
	}
	
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
