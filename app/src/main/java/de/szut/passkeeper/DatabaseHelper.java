package de.szut.passkeeper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sami.Al-Khatib on 09.02.2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_USER_DATABASE = "pass_database";
    public static final String TABLE_USER_CATEGORIE = "pass_categorie";
    public static final String TABLE_USER_ENTRY = "pass_entry";
    public static final String KEY_ID_USER_DATABASE = "pass_database_id";
    public static final String KEY_USER_DATABASE_NAME = "pass_database_name";
    public static final String KEY_USER_DATABASE_PWD = "pass_database_pwd";
    public static final String KEY_USER_DATABASE_CDATE = "pass_database_cdate";
    public static final String KEY_USER_DATABASE_MDATE = "pass_database_mdate";

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "PassDatabase";
    private static final String CREATE_USER_DATABASE_SQL =
            "CREATE TABLE " + TABLE_USER_DATABASE +
                "(\n"
                    + KEY_ID_USER_DATABASE + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n"
                    + KEY_USER_DATABASE_NAME + " varchar(16) NOT NULL,\n"
                    + KEY_USER_DATABASE_PWD + " varchar(4096) NOT NULL,\n"
                    + KEY_USER_DATABASE_CDATE + " timestamp DEFAULT CURRENT_TIMESTAMP,\n"
                    + KEY_USER_DATABASE_MDATE + " timestamp DEFAULT CURRENT_TIMESTAMP\n" +
                ")";
   /*
    private static final String CREATE_CATEGORIE_DATABASE_SQL =
            "CREATE TABLE " + TABLE_USER_CATEGORIE +
                    "(\n"
                        +
    */

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_DATABASE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
