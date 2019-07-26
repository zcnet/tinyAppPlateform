package com.zcnet.voice_callback_lib.voice_data;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

public class VABuActionEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String appName;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String moduleName;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer displayType;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer isReuse;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String content;

    public static long getSerialversionuid() {return serialVersionUID;}

    public String getAppName() {return appName;}
    public void setAppName(String appName) {this.appName = appName;}

    public String getModuleName() {return moduleName;}
    public void setModuleName(String moduleName) {this.moduleName = moduleName;}

    public Integer getDisplayType() {return displayType;}
    public void setDisplayType(Integer displayType) {this.displayType = displayType;}

    public Integer getIsReuse() {return isReuse;}
    public void setIsReuse(Integer isReuse) {this.isReuse = isReuse;}

    public String getContent() {return content;}
    public void setContent(String content) {this.content = content;}
}
