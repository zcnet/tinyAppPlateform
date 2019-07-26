package com.zcnet.voice_callback_lib.voice_data;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

public class VAText implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String text;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String id;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer interval;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer isShow;

    public String getText() {return text;}
    public void setText(String text) {this.text = text;}

    public Integer getInterval() {return interval;}

    public void setInterval(Integer interval) {this.interval = interval;}
    public Integer getIsShow() {return isShow;}

    public void setIsShow(Integer isShow) {this.isShow = isShow;}
    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    @Override
    public String toString() {
        return "Text [text=" + text + ", id=" + id + ", interval=" + interval
                + ", isShow=" + isShow + "]";
    }
}
