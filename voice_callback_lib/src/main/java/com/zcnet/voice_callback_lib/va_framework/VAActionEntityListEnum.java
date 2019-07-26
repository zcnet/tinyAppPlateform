package com.zcnet.voice_callback_lib.va_framework;

public enum VAActionEntityListEnum {
    PLAY("play"), DISPLAY("display"), REC("rec"), EXECUTION("execution");

    public String value;

    private VAActionEntityListEnum(String value) {this.value = value;}

    public String getValue() {return value;}

    public void setValue(String value) {this.value = value;}
}
