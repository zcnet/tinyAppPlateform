package com.zcnet.voice_callback_lib.va_3rd_biz.PhoneCallData;

import com.fasterxml.jackson.annotation.JsonInclude;

public class PhoneNumber {
    String phoneNumber;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    String ISP;

    public String getPhoneNumber() {return phoneNumber;}
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}

    public String getISP() {return ISP;}
    public void setISP(String ISP) {this.ISP = ISP;}
}
