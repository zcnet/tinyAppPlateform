package com.zcnet.voice_callback_lib.va_3rd_biz.PhoneCallData;

import java.util.List;

public class ContactInfo {
    String name;
    List<PhoneNumber> phoneNums;

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public List<PhoneNumber> getPhoneNums() {return phoneNums;}
    public void setPhoneNums(List<PhoneNumber> phoneNums) {this.phoneNums = phoneNums;}
}
