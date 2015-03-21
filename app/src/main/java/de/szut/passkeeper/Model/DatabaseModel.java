package de.szut.passkeeper.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Vector;

import de.szut.passkeeper.Interface.IListViewType;
import de.szut.passkeeper.Property.UserCategoryProperty;
import de.szut.passkeeper.Property.UserDatabaseProperty;

/**
 * Created by Sami.Al-Khatib on 10.02.2015.
 */
public class DatabaseModel {
    private DatabaseOpenHelper databaseOpenHelper;
    private SQLiteDatabase sqLiteDatabase;
    private String[] passDatabaseColumns = {
            DatabaseOpenHelper.KEY_ID_USER_DATABASE,
            DatabaseOpenHelper.KEY_NAME_USER_DATABASE,
            DatabaseOpenHelper.KEY_PWD_USER_DATABASE,
            DatabaseOpenHelper.KEY_CDATE_USER_DATABASE,
            DatabaseOpenHelper.KEY_MDATE_USER_DATABASE
    };
    private String[] passCategoryColumns = {
            DatabaseOpenHelper.KEY_ID_USER_CATEGORY,
            DatabaseOpenHelper.KEY_ID_USER_DATABASE,
            DatabaseOpenHelper.KEY_NAME_USER_CATEGORY,
            DatabaseOpenHelper.KEY_CDATE_USER_CATEGORY,
            DatabaseOpenHelper.KEY_MDATE_USER_CATEGORY
    };

    private String[] passEntryColumns = {
            DatabaseOpenHelper.KEY_ID_USER_ENTRY,
            DatabaseOpenHelper.KEY_ID_USER_DATABASE,
            DatabaseOpenHelper.KEY_ID_USER_CATEGORY,
            DatabaseOpenHelper.KEY_USERNAME_USER_ENTRY,
            DatabaseOpenHelper.KEY_USERPWD_USER_ENTRY,
            DatabaseOpenHelper.KEY_CDATE_USER_ENTRY,
            DatabaseOpenHelper.KEY_MDATE_USER_ENTRY,
            DatabaseOpenHelper.KEY_HASH_USER_ENTRY
    };

    public DatabaseModel(Context context) {
        databaseOpenHelper = new DatabaseOpenHelper(context);
    }

    /**
     * @return
     */
    public Vector<IListViewType> getUserDatabasePropertyVector() {
        sqLiteDatabase = databaseOpenHelper.getReadableDatabase();
        Vector<IListViewType> vectorUserDatabaseProperty = new Vector<>();
        Cursor cursor = sqLiteDatabase.query(DatabaseOpenHelper.TABLE_USER_DATABASE, passDatabaseColumns, null, null, null, null, null);
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
        databaseOpenHelper.close();
        return vectorUserDatabaseProperty;
    }

    /**
     * @param databaseId
     * @return
     */
    public Vector<IListViewType> getUserCategoryPropertyList(int databaseId) {
        sqLiteDatabase = databaseOpenHelper.getReadableDatabase();
        Vector<IListViewType> vectorUserCategoryProperty = new Vector<>();
        Cursor cursor = sqLiteDatabase.query(DatabaseOpenHelper.TABLE_USER_CATEGORY, passCategoryColumns, DatabaseOpenHelper.KEY_ID_USER_DATABASE + " = ?", new String[]{String.valueOf(databaseId)}, null, null, null);
        Log.d(this.getClass().getSimpleName(), String.valueOf(cursor.getCount()));
        while (cursor.moveToNext()) {
            vectorUserCategoryProperty.add(new UserCategoryProperty(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            ));
        }
        cursor.close();
        databaseOpenHelper.close();
        return vectorUserCategoryProperty;
    }

    /**
     * @param userDatabaseProperty
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public int createPassDatabaseAndDefaultCategory(UserDatabaseProperty userDatabaseProperty) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        sqLiteDatabase = databaseOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseOpenHelper.KEY_NAME_USER_DATABASE, userDatabaseProperty.getDatabaseName());
        contentValues.put(DatabaseOpenHelper.KEY_PWD_USER_DATABASE, Security.getInstance().encryptPassword(userDatabaseProperty.getDatabasePwd()));
        long databaseId = sqLiteDatabase.insert(DatabaseOpenHelper.TABLE_USER_DATABASE, null, contentValues);
        contentValues = new ContentValues();
        contentValues.put(DatabaseOpenHelper.KEY_ID_USER_DATABASE, databaseId);
        //TODO find out why it is not possible to use android String resources.
        contentValues.put(DatabaseOpenHelper.KEY_NAME_USER_CATEGORY, "Default Category");
        sqLiteDatabase.insert(DatabaseOpenHelper.TABLE_USER_CATEGORY, null, contentValues);
        databaseOpenHelper.close();
        return Integer.valueOf(String.valueOf(databaseId));
    }
}
