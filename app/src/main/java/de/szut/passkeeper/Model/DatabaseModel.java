package de.szut.passkeeper.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Vector;

import de.szut.passkeeper.Interface.IUserProperty;
import de.szut.passkeeper.Property.CategoryProperty;
import de.szut.passkeeper.Property.DatabaseProperty;
import de.szut.passkeeper.Property.EntryProperty;

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
            DatabaseOpenHelper.KEY_TITLE_USER_ENTRY,
            DatabaseOpenHelper.KEY_USERNAME_USER_ENTRY,
            DatabaseOpenHelper.KEY_USERPWD_USER_ENTRY,
            DatabaseOpenHelper.KEY_HASH_USER_ENTRY,
            DatabaseOpenHelper.KEY_NOTE_USER_ENTRY,
            DatabaseOpenHelper.KEY_CDATE_USER_ENTRY,
            DatabaseOpenHelper.KEY_MDATE_USER_ENTRY
    };

    public DatabaseModel(Context context) {
        databaseOpenHelper = new DatabaseOpenHelper(context);
    }

    /**
     * @return
     */
    public Vector<IUserProperty> getUserDatabasePropertyVector() {
        sqLiteDatabase = databaseOpenHelper.getReadableDatabase();
        Vector<IUserProperty> vectorUserDatabaseProperty = new Vector<>();
        Cursor cursor = sqLiteDatabase.query(DatabaseOpenHelper.TABLE_USER_DATABASE, passDatabaseColumns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            vectorUserDatabaseProperty.add(
                    new DatabaseProperty(
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
    public Vector<IUserProperty> getUserCategoryPropertyVector(int databaseId) {
        sqLiteDatabase = databaseOpenHelper.getReadableDatabase();
        Vector<IUserProperty> vectorUserCategoryProperty = new Vector<>();
        Cursor cursor = sqLiteDatabase.query(DatabaseOpenHelper.TABLE_USER_CATEGORY, passCategoryColumns, DatabaseOpenHelper.KEY_ID_USER_DATABASE + " = ?", new String[]{String.valueOf(databaseId)}, null, null, null);
        //Log.d(this.getClass().getSimpleName(), String.valueOf(cursor.getCount()));
        while (cursor.moveToNext()) {
            vectorUserCategoryProperty.add(new CategoryProperty(
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

    public Vector<IUserProperty> getUserEntryVector(int databaseId, int categoryId) {
        sqLiteDatabase = databaseOpenHelper.getReadableDatabase();
        Vector<IUserProperty> vectorUserEntryProperty = new Vector<>();
        Cursor cursor = sqLiteDatabase.query(DatabaseOpenHelper.TABLE_USER_ENTRY, passEntryColumns, DatabaseOpenHelper.KEY_ID_USER_DATABASE + " = ? AND " + DatabaseOpenHelper.KEY_ID_USER_CATEGORY + " = ?", new String[]{String.valueOf(databaseId), String.valueOf(categoryId)}, null, null, null);
        while (cursor.moveToNext()) {
            vectorUserEntryProperty.add(new EntryProperty(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9)
            ));
        }
        cursor.close();
        databaseOpenHelper.close();
        return vectorUserEntryProperty;
    }

    /**
     * @param databaseProperty
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public int createPassDatabaseAndDefaultCategory(DatabaseProperty databaseProperty, String[] arrDefaultCategory) {
        sqLiteDatabase = databaseOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseOpenHelper.KEY_NAME_USER_DATABASE, databaseProperty.getDatabaseName());
        contentValues.put(DatabaseOpenHelper.KEY_PWD_USER_DATABASE, Security.getInstance().encryptPassword(databaseProperty.getDatabasePwd()));
        long databaseId = sqLiteDatabase.insert(DatabaseOpenHelper.TABLE_USER_DATABASE, null, contentValues);
        for(String defaultCategory : arrDefaultCategory){
            contentValues = new ContentValues();
            contentValues.put(DatabaseOpenHelper.KEY_ID_USER_DATABASE, databaseId);
            contentValues.put(DatabaseOpenHelper.KEY_NAME_USER_CATEGORY, defaultCategory);
            sqLiteDatabase.insert(DatabaseOpenHelper.TABLE_USER_CATEGORY, null, contentValues);
        }
        databaseOpenHelper.close();
        return Integer.valueOf(String.valueOf(databaseId));
    }

    /**
     *
     * @param databaseId
     * @param categoryName
     */
    public void createCategory(int databaseId, String categoryName) {
        sqLiteDatabase = databaseOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseOpenHelper.KEY_ID_USER_DATABASE, databaseId);
        contentValues.put(DatabaseOpenHelper.KEY_NAME_USER_CATEGORY, categoryName);
        sqLiteDatabase.insert(DatabaseOpenHelper.TABLE_USER_CATEGORY, null, contentValues);
        databaseOpenHelper.close();
    }

    /**
     *
     * @param entryProperty
     */
    public void createEntry(EntryProperty entryProperty){
        sqLiteDatabase = databaseOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseOpenHelper.KEY_ID_USER_DATABASE, entryProperty.getDatabaseId());
        contentValues.put(DatabaseOpenHelper.KEY_ID_USER_CATEGORY, entryProperty.getCategoryId());
        contentValues.put(DatabaseOpenHelper.KEY_TITLE_USER_ENTRY, entryProperty.getEntryTitle());
        contentValues.put(DatabaseOpenHelper.KEY_USERNAME_USER_ENTRY, entryProperty.getEntryUserName());
        contentValues.put(DatabaseOpenHelper.KEY_USERPWD_USER_ENTRY, entryProperty.getEntryPwd());
        contentValues.put(DatabaseOpenHelper.KEY_HASH_USER_ENTRY, entryProperty.getEntryHash());
        contentValues.put(DatabaseOpenHelper.KEY_NOTE_USER_ENTRY, entryProperty.getEntryNote());
        sqLiteDatabase.insert(DatabaseOpenHelper.TABLE_USER_ENTRY, null, contentValues);
        databaseOpenHelper.close();
    }
}
