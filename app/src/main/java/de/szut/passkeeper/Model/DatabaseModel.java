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
import de.szut.passkeeper.R;

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
            DatabaseOpenHelper.KEY_ICON_USER_DATABASE,
            DatabaseOpenHelper.KEY_CDATE_USER_DATABASE,
            DatabaseOpenHelper.KEY_MDATE_USER_DATABASE
    };
    private String[] passCategoryColumns = {
            DatabaseOpenHelper.KEY_ID_USER_CATEGORY,
            DatabaseOpenHelper.KEY_ID_USER_DATABASE,
            DatabaseOpenHelper.KEY_NAME_USER_CATEGORY,
            DatabaseOpenHelper.KEY_ICON_USER_CATEGORY,
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
            DatabaseOpenHelper.KEY_ICON_USER_ENTRY,
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
                            cursor.getInt(3),
                            cursor.getString(4),
                            cursor.getString(5)
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
        while (cursor.moveToNext()) {
            Log.d(getClass().getSimpleName(), "Selcted Categoryid: " + cursor.getInt(0));
            vectorUserCategoryProperty.add(new CategoryProperty(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getString(4),
                    cursor.getString(5)
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
                    cursor.getInt(8),
                    cursor.getString(9),
                    cursor.getString(10)
            ));
        }
        cursor.close();
        databaseOpenHelper.close();
        return vectorUserEntryProperty;
    }

    public String getUserDatabaseName(int databaseId) {
        sqLiteDatabase = databaseOpenHelper.getReadableDatabase();
        String databaseName = null;
        Cursor cursor = sqLiteDatabase.query(DatabaseOpenHelper.TABLE_USER_DATABASE, passDatabaseColumns, DatabaseOpenHelper.KEY_ID_USER_DATABASE + " = ?", new String[]{String.valueOf(databaseId)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            databaseName = cursor.getString(1);
            Log.d(getClass().getSimpleName(), "ID: " + String.valueOf(databaseId));
            Log.d(getClass().getSimpleName(), "Databasename: " + databaseName);
            cursor.close();
        }
        databaseOpenHelper.close();

        return databaseName;
    }

    public String getUserCategoryName(int categoryId) {
        sqLiteDatabase = databaseOpenHelper.getReadableDatabase();
        String categoryName = null;
        Cursor cursor = sqLiteDatabase.query(DatabaseOpenHelper.TABLE_USER_CATEGORY, passCategoryColumns, DatabaseOpenHelper.KEY_ID_USER_CATEGORY + " = ?", new String[]{String.valueOf(categoryId)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            categoryName = cursor.getString(2);
            Log.d(getClass().getSimpleName(), "ID: " + String.valueOf(categoryId));
            Log.d(getClass().getSimpleName(), "Category: " + categoryName);
            cursor.close();
        }
        databaseOpenHelper.close();
        return categoryName;
    }

    /**
     * @param databaseProperty
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public int createUserDatabase(DatabaseProperty databaseProperty, String[] arrDefaultCategory) {
        sqLiteDatabase = databaseOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseOpenHelper.KEY_NAME_USER_DATABASE, databaseProperty.getDatabaseName());
        contentValues.put(DatabaseOpenHelper.KEY_PWD_USER_DATABASE, Security.getInstance().encryptPassword(databaseProperty.getDatabasePwd()));
        contentValues.put(DatabaseOpenHelper.KEY_ICON_USER_DATABASE, databaseProperty.getDatabaseIconId());
        long databaseId = sqLiteDatabase.insert(DatabaseOpenHelper.TABLE_USER_DATABASE, null, contentValues);
        databaseOpenHelper.close();
        Log.d(getClass().getSimpleName() + " Databaseid: ", String.valueOf(databaseId));
        for (String defaultCategory : arrDefaultCategory) {
            createUserCategory(new CategoryProperty(Integer.valueOf(String.valueOf(databaseId)), defaultCategory, R.drawable.ic_folder));
        }
        return Integer.valueOf(String.valueOf(databaseId));
    }

    /**
     * @param categoryProperty
     */
    public void createUserCategory(CategoryProperty categoryProperty) {
        sqLiteDatabase = databaseOpenHelper.getWritableDatabase();
        Log.d(getClass().getSimpleName() + " Databaseid: ", String.valueOf(categoryProperty.getDatabaseId()));
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseOpenHelper.KEY_ID_USER_DATABASE, categoryProperty.getDatabaseId());
        contentValues.put(DatabaseOpenHelper.KEY_NAME_USER_CATEGORY, categoryProperty.getCategoryName());
        contentValues.put(DatabaseOpenHelper.KEY_ICON_USER_CATEGORY, categoryProperty.getCategoryIconId());
        sqLiteDatabase.insert(DatabaseOpenHelper.TABLE_USER_CATEGORY, null, contentValues);
        databaseOpenHelper.close();
    }

    /**
     * @param entryProperty
     */
    public void createUserEntry(EntryProperty entryProperty) {
        sqLiteDatabase = databaseOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseOpenHelper.KEY_ID_USER_DATABASE, entryProperty.getDatabaseId());
        contentValues.put(DatabaseOpenHelper.KEY_ID_USER_CATEGORY, entryProperty.getCategoryId());
        contentValues.put(DatabaseOpenHelper.KEY_TITLE_USER_ENTRY, entryProperty.getEntryTitle());
        contentValues.put(DatabaseOpenHelper.KEY_USERNAME_USER_ENTRY, entryProperty.getEntryUserName());
        contentValues.put(DatabaseOpenHelper.KEY_USERPWD_USER_ENTRY, entryProperty.getEntryPwd());
        contentValues.put(DatabaseOpenHelper.KEY_HASH_USER_ENTRY, entryProperty.getEntryHash());
        contentValues.put(DatabaseOpenHelper.KEY_NOTE_USER_ENTRY, entryProperty.getEntryNote());
        contentValues.put(DatabaseOpenHelper.KEY_ICON_USER_ENTRY, entryProperty.getEntryIconId());
        sqLiteDatabase.insert(DatabaseOpenHelper.TABLE_USER_ENTRY, null, contentValues);
        databaseOpenHelper.close();
    }
}
