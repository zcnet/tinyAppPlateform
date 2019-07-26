package com.zcnet.voice_callback_lib.voice_data;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

public class VAErrorTextEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer errorHintNum;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<VAText> textList;

    public Integer getErrorHintNum() {return errorHintNum;}
    public void setErrorHintNum(Integer errorHintNum) {this.errorHintNum = errorHintNum;}

    public List<VAText> getTextList() {return textList;}
    public void setTextList(List<VAText> textList) {this.textList = textList;}
}
