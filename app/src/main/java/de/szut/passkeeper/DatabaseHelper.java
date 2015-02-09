package de.szut.passkeeper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sami.Al-Khatib on 09.02.2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 0;
    private static final String DB_NAME = "PassDatabase";
    private static final String TABLE_USER_DATABASE = "Pass_Database";
    private static final String TABLE_USER_CATEGORIE = "Pass_Categorie";
    private static final String TABLE_USER_ENTRY = "Pass_Entry";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createUserPassDatabaseTable = "CREATE TABLE " + TABLE_USER_DATABASE + "(\n"
                + "";
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
