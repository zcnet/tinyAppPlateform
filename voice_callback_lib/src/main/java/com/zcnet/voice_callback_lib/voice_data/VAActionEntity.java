package com.zcnet.voice_callback_lib.voice_data;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

public class VAActionEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer priority;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer interval;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer retry;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer doActionNow;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<VAText> textList;

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public Integer getPriority() {return priority;}
    public void setPriority(Integer priority) {this.priority = priority;}

    public Integer getInterval() {return interval;}
    public void setInterval(Integer interval) {this.interval = interval;}

    public Integer getRetry() {return retry;}
    public void setRetry(Integer retry) {this.retry = retry;}

    public Integer getDoActionNow() {return doActionNow;}
    public void setDoActionNow(Integer doActionNow) {this.doActionNow = doActionNow;}

    public List<VAText> getTextList() {return textList;}
    public void setTextList(List<VAText> textList) {this.textList = textList;}
}
