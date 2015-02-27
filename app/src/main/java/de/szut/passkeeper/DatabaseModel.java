package de.szut.passkeeper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Sami.Al-Khatib on 10.02.2015.
 */
public class DatabaseModel {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    private String[] passDatabaseColumns = {
            DatabaseHelper.KEY_ID_USER_DATABASE + " as _id",
            DatabaseHelper.KEY_ID_USER_DATABASE,
            DatabaseHelper.KEY_USER_DATABASE_NAME,
            DatabaseHelper.KEY_USER_DATABASE_PWD,
            DatabaseHelper.KEY_USER_DATABASE_CDATE,
            DatabaseHelper.KEY_USER_DATABASE_MDATE
    };

    public DatabaseModel(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void createDatabase(UserDatabaseProperties userDatabaseProperties) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.KEY_USER_DATABASE_NAME, userDatabaseProperties.getDatabaseName());
        contentValues.put(DatabaseHelper.KEY_USER_DATABASE_PWD, Security.getInstance().encryptPassword(userDatabaseProperties.getDatabasePwd()));
        sqLiteDatabase.insert(DatabaseHelper.TABLE_USER_DATABASE, null, contentValues);
        sqLiteDatabase.close();
    }

    public Cursor getDatabasePropertiesCursor() {
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_USER_DATABASE, passDatabaseColumns, null, null, null, null, null);
        sqLiteDatabase.close();
        return cursor;
    }
}
