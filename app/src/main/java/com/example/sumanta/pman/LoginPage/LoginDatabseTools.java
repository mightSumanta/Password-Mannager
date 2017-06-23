package com.example.sumanta.pman.LoginPage;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sumanta on 17-06-2017.
 */

public class LoginDatabseTools {
    public static SQLiteDatabase dBase = null;
    public static SQLiteOpenHelper dBaseHelper = null;

    public static SQLiteOpenHelper getdBaseHelper() {
        return dBaseHelper;
    }
}
