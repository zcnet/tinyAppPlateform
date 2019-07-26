package com.zcnet.voice_callback_lib.voice_data;

import java.io.Serializable;

public class VAExpectedIntent implements Serializable {
    private String intentName;
    private VAExpectEnum vaExpect;
    private VAActionEnum vaAction;

    public String getIntentName() {return intentName;}
    public void setIntentName(String intentName) {this.intentName = intentName;}

    public VAExpectEnum getVaExpect() {return vaExpect;}
    public void setVaExpect(VAExpectEnum vaExpect) {this.vaExpect = vaExpect;}

    public VAActionEnum getVaAction() {return vaAction;}
    public void setVaAction(VAActionEnum vaAction) {this.vaAction = vaAction;}

    @Override
    public String toString() {
        return "VAExpectedIntent [userId=" + intentName
                + ", vaExpect=" + vaExpect
                + ", vaAction=" + vaAction + "]";
    }
}
