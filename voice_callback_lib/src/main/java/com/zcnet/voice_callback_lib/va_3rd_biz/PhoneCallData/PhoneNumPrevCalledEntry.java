package com.zcnet.voice_callback_lib.va_3rd_biz.PhoneCallData;

import android.provider.BaseColumns;

public final class PhoneNumPrevCalledEntry {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private PhoneNumPrevCalledEntry() {}

    /* Inner class that defines the table contents */
    public static class PhoneNumEntry implements BaseColumns {
        public static final String TABLE_NAME = "PrevCalled";
        public static final String PHONE_NUM = "PhoneNum";
    }
}

