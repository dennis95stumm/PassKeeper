package de.szut.passkeeper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by Sami.Al-Khatib on 10.02.2015.
 */
public class DatabaseModel {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    private String[] passDatabaseColumns = {
            DatabaseHelper.KEY_ID_USER_DATABASE,
            DatabaseHelper.KEY_NAME_USER_DATABASE,
            DatabaseHelper.KEY_PWD_USER_DATABASE,
            DatabaseHelper.KEY_CDATE_USER_DATABASE,
            DatabaseHelper.KEY_MDATE_USER_DATABASE
    };
    private String[] passCategoryColumns = {
            DatabaseHelper.KEY_ID_USER_CATEGORY,
            DatabaseHelper.KEY_NAME_USER_CATEGORY,
            DatabaseHelper.KEY_CDATE_USER_CATEGORY,
            DatabaseHelper.KEY_MDATE_USER_CATEGORY
    };

    private String[] passEntryColumns = {
            DatabaseHelper.KEY_ID_USER_ENTRY,
            DatabaseHelper.KEY_USERNAME_USER_ENTRY,
            DatabaseHelper.KEY_USERPWD_USER_ENTRY,
            DatabaseHelper.KEY_CDATE_USER_ENTRY,
            DatabaseHelper.KEY_MDATE_USER_ENTRY,
            DatabaseHelper. KEY_HASH_USER_ENTRY
    };

    public DatabaseModel(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public ArrayList<UserDatabaseProperties> getDatabasePropertiesList() {
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        ArrayList listUserDatabaseProperties = new ArrayList<UserDatabaseProperties>();
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_USER_DATABASE, passDatabaseColumns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            listUserDatabaseProperties.add(
                    new UserDatabaseProperties(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            String.valueOf(cursor.getString(3)),
                            String.valueOf(cursor.getString(4))
                    )
            );
        }
        databaseHelper.close();
        return listUserDatabaseProperties;
    }

    public void createPassDatabase(UserDatabaseProperties userDatabaseProperties) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.KEY_NAME_USER_DATABASE, userDatabaseProperties.getDatabaseName());
        contentValues.put(DatabaseHelper.KEY_PWD_USER_DATABASE, Security.getInstance().encryptPassword(userDatabaseProperties.getDatabasePwd()));
        long databaseId = sqLiteDatabase.insert(DatabaseHelper.TABLE_USER_DATABASE, null, contentValues);
        contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.KEY_ID_USER_DATABASE, databaseId);
        contentValues.put(DatabaseHelper.KEY_NAME_USER_CATEGORY, "Default Category");
        long categoryId = sqLiteDatabase.insert(DatabaseHelper.TABLE_USER_CATEGORY, null, contentValues);
        databaseHelper.close();
    }
}
