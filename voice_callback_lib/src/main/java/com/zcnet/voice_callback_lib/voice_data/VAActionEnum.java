package com.zcnet.voice_callback_lib.voice_data;

public enum VAActionEnum {
    EXIT("EXIT"), LEAVE("LEAVE"), RETURN("RETURN"), CUSTOMIZED("CUSTOMIZED");

    public String vaAction;
    private VAActionEnum(String vaAction) {
        this.vaAction = vaAction;
    }
    public String getVaAction() {
        return vaAction;
    }
    public void setVaAction(String vaAction) {
        this.vaAction = vaAction;
    }
}
