package de.szut.passkeeper.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sami.Al-Khatib on 09.02.2015.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

    // Table
    public static final String TABLE_USER_DATABASE = "pass_database";
    public static final String TABLE_USER_CATEGORY = "pass_category";
    public static final String TABLE_USER_ENTRY = "pass_entry";
    // Columns of pass_database table
    public static final String KEY_ID_USER_DATABASE = "pass_database_id";
    public static final String KEY_NAME_USER_DATABASE = "pass_database_name";
    public static final String KEY_PWD_USER_DATABASE = "pass_database_pwd";
    public static final String KEY_ICON_USER_DATABASE = "pass_database_icon";
    public static final String KEY_MODIFY_DATE_USER_DATABASE = "pass_database_mdate";
    //
    private static final String CREATE_USER_DATABASE_TABLE_SQL =
            "CREATE TABLE " + TABLE_USER_DATABASE +
                    "(\n"
                    + KEY_ID_USER_DATABASE + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n"
                    + KEY_NAME_USER_DATABASE + " TEXT NOT NULL,\n"
                    + KEY_PWD_USER_DATABASE + " TEXT NOT NULL,\n"
                    + KEY_ICON_USER_DATABASE + " INTEGER,\n"
                    + KEY_MODIFY_DATE_USER_DATABASE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP\n" +
                    ")";
    // Columns of pass_category table
    public static final String KEY_ID_USER_CATEGORY = "pass_category_id";
    public static final String KEY_NAME_USER_CATEGORY = "pass_category_name";
    public static final String KEY_ICON_USER_CATEGORY = "pass_category_icon";
    public static final String KEY_MODIFY_DATE_USER_CATEGORY = "pass_category_mdate";
    //
    private static final String CREATE_USER_CATEGORY_TABLE_SQL =
            "CREATE TABLE " + TABLE_USER_CATEGORY +
                    "(\n"
                    + KEY_ID_USER_CATEGORY + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n"
                    + KEY_ID_USER_DATABASE + " INTEGER NOT NULL,\n"
                    + KEY_NAME_USER_CATEGORY + " TEXT NOT NULL,\n"
                    + KEY_ICON_USER_CATEGORY + " INTEGER,\n"
                    + KEY_MODIFY_DATE_USER_CATEGORY + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n"
                    + " FOREIGN KEY (" + KEY_ID_USER_DATABASE + ") REFERENCES " + TABLE_USER_DATABASE + "(" + KEY_ID_USER_DATABASE + ") ON DELETE CASCADE\n" +
                    ")";
    // Columns of pass_entry table
    public static final String KEY_ID_USER_ENTRY = "pass_entry_id";
    public static final String KEY_TITLE_USER_ENTRY = "pass_entry_title";
    public static final String KEY_USERNAME_USER_ENTRY = "pass_entry_username";
    public static final String KEY_USERPWD_USER_ENTRY = "pass_entry_pwd";
    public static final String KEY_HASH_USER_ENTRY = "pass_entry_hash";
    public static final String KEY_NOTE_USER_ENTRY = "pass_entry_note";
    public static final String KEY_ICON_USER_ENTRY = "pass_entry_icon";
    public static final String KEY_MODIFY_DATE_USER_ENTRY = "pass_entry_mdate";
    // CREATE STATEMENTS FOR TABLES
    private static final String CREATE_USER_ENTRY_TABLE_SQL =
            "CREATE TABLE " + TABLE_USER_ENTRY +
                    "(\n"
                    + KEY_ID_USER_ENTRY + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n"
                    + KEY_ID_USER_DATABASE + " INTEGER NOT NULL,\n"
                    + KEY_ID_USER_CATEGORY + " INTEGER NOT NULL,\n"
                    + KEY_TITLE_USER_ENTRY + " TEXT,\n"
                    + KEY_USERNAME_USER_ENTRY + " TEXT,\n"
                    + KEY_USERPWD_USER_ENTRY + " TEXT,\n"
                    + KEY_HASH_USER_ENTRY + " TEXT,\n"
                    + KEY_NOTE_USER_ENTRY + " TEXT,\n"
                    + KEY_ICON_USER_ENTRY + " INTEGER,\n"
                    + KEY_MODIFY_DATE_USER_ENTRY + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n"
                    + "FOREIGN KEY (" + KEY_ID_USER_DATABASE + ") REFERENCES " + TABLE_USER_DATABASE + "(" + KEY_ID_USER_DATABASE + ") ON DELETE CASCADE\n"
                    + "FOREIGN KEY (" + KEY_ID_USER_CATEGORY + ") REFERENCES " + TABLE_USER_ENTRY + "(" + KEY_ID_USER_CATEGORY + ") ON DELETE CASCADE\n" +
                    ")";
    // Database Version and Name
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "PassDatabase";


    /**
     * @param context
     */
    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_DATABASE_TABLE_SQL);
        sqLiteDatabase.execSQL(CREATE_USER_CATEGORY_TABLE_SQL);
        sqLiteDatabase.execSQL(CREATE_USER_ENTRY_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_DATABASE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_CATEGORY);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_ENTRY);
        onCreate(sqLiteDatabase);
    }
}
