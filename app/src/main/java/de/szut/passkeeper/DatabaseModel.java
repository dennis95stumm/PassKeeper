package de.szut.passkeeper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.widget.CursorAdapter;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Vector;

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
            DatabaseHelper.KEY_ID_USER_DATABASE,
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

    public Vector<UserDatabaseProperty> getUserDatabasePropertyVector() {
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Vector<UserDatabaseProperty> vectorUserDatabaseProperty = new Vector<UserDatabaseProperty>();
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_USER_DATABASE, passDatabaseColumns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            vectorUserDatabaseProperty.add(
                    new UserDatabaseProperty(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            String.valueOf(cursor.getString(3)),
                            String.valueOf(cursor.getString(4))
                    )
            );
        }
        cursor.close();
        databaseHelper.close();
        return vectorUserDatabaseProperty;
    }

    public Vector<UserCategoryProperty> getUserCategoryPropertyList(int databaseId) {
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Vector<UserCategoryProperty> vectorUserCategoryProperty = new Vector<>();
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_USER_CATEGORY, passCategoryColumns, DatabaseHelper.KEY_ID_USER_DATABASE + " = ?", new String[]{String.valueOf(databaseId)}, null, null, null);
        while(cursor.moveToNext()){
            vectorUserCategoryProperty.add(new UserCategoryProperty(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            ));
        }
        cursor.close();
        databaseHelper.close();
        return vectorUserCategoryProperty;
    }

    public void createPassDatabase(UserDatabaseProperty userDatabaseProperty) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.KEY_NAME_USER_DATABASE, userDatabaseProperty.getDatabaseName());
        contentValues.put(DatabaseHelper.KEY_PWD_USER_DATABASE, Security.getInstance().encryptPassword(userDatabaseProperty.getDatabasePwd()));
        long databaseId = sqLiteDatabase.insert(DatabaseHelper.TABLE_USER_DATABASE, null, contentValues);
        contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.KEY_ID_USER_DATABASE, databaseId);
        contentValues.put(DatabaseHelper.KEY_NAME_USER_CATEGORY, "Default Category");
        sqLiteDatabase.insert(DatabaseHelper.TABLE_USER_CATEGORY, null, contentValues);
        databaseHelper.close();
    }
}
