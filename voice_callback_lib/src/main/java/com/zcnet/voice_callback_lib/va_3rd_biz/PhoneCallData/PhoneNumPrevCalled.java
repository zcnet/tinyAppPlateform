package com.zcnet.voice_callback_lib.va_3rd_biz.PhoneCallData;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PhoneNumPrevCalled extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PhoneNumPrevCalledEntry.PhoneNumEntry.TABLE_NAME + " (" +
                    PhoneNumPrevCalledEntry.PhoneNumEntry._ID + " INTEGER PRIMARY KEY," +
                    PhoneNumPrevCalledEntry.PhoneNumEntry.PHONE_NUM + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PhoneNumPrevCalledEntry.PhoneNumEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PhoneNumPrevCalled.db";

    public PhoneNumPrevCalled(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
