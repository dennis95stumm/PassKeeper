package de.szut.passkeeper.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Vector;

import de.szut.passkeeper.interfaces.IUserProperty;
import de.szut.passkeeper.property.CategoryProperty;
import de.szut.passkeeper.property.DatabaseProperty;
import de.szut.passkeeper.property.EntryProperty;

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
            DatabaseOpenHelper.KEY_MODIFY_DATE_USER_DATABASE
    };
    private String[] passCategoryColumns = {
            DatabaseOpenHelper.KEY_ID_USER_CATEGORY,
            DatabaseOpenHelper.KEY_ID_USER_DATABASE,
            DatabaseOpenHelper.KEY_NAME_USER_CATEGORY,
            DatabaseOpenHelper.KEY_ICON_USER_CATEGORY,
            DatabaseOpenHelper.KEY_MODIFY_DATE_USER_CATEGORY
    };

    private String[] passEntryColumns = {
            DatabaseOpenHelper.KEY_ID_USER_ENTRY,
            DatabaseOpenHelper.KEY_ID_USER_CATEGORY,
            DatabaseOpenHelper.KEY_ID_USER_DATABASE,
            DatabaseOpenHelper.KEY_TITLE_USER_ENTRY,
            DatabaseOpenHelper.KEY_USERNAME_USER_ENTRY,
            DatabaseOpenHelper.KEY_USERPWD_USER_ENTRY,
            DatabaseOpenHelper.KEY_HASH_USER_ENTRY,
            DatabaseOpenHelper.KEY_NOTE_USER_ENTRY,
            DatabaseOpenHelper.KEY_ICON_USER_ENTRY,
            DatabaseOpenHelper.KEY_MODIFY_DATE_USER_ENTRY
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
                            cursor.getString(4)
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
            vectorUserCategoryProperty.add(new CategoryProperty(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getInt(3),
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
                    cursor.getInt(8),
                    cursor.getString(9)
            ));
        }
        cursor.close();
        databaseOpenHelper.close();
        return vectorUserEntryProperty;
    }

    public CategoryProperty getUserCategoryProperty(int categoryId) {
        sqLiteDatabase = databaseOpenHelper.getReadableDatabase();
        CategoryProperty categoryProperty = null;
        Cursor cursor = sqLiteDatabase.query(DatabaseOpenHelper.TABLE_USER_CATEGORY, passCategoryColumns, DatabaseOpenHelper.KEY_ID_USER_CATEGORY + " = ?", new String[]{String.valueOf(categoryId)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            categoryProperty = new CategoryProperty(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getString(4)
            );
            cursor.close();
        }
        sqLiteDatabase.close();
        return categoryProperty;
    }

    public EntryProperty getUserEntryProperty(int entryId) {
        sqLiteDatabase = databaseOpenHelper.getReadableDatabase();
        EntryProperty entryProperty = null;
        Cursor cursor = sqLiteDatabase.query(DatabaseOpenHelper.TABLE_USER_ENTRY, passEntryColumns, DatabaseOpenHelper.KEY_ID_USER_ENTRY + " = ? ", new String[]{String.valueOf(entryId)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            entryProperty = new EntryProperty(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getInt(8),
                    cursor.getString(9)
            );
            cursor.close();
        }
        sqLiteDatabase.close();
        return entryProperty;
    }

    public String getUserDatabaseName(int databaseId) {
        sqLiteDatabase = databaseOpenHelper.getReadableDatabase();
        String databaseName = null;
        Cursor cursor = sqLiteDatabase.query(DatabaseOpenHelper.TABLE_USER_DATABASE, passDatabaseColumns, DatabaseOpenHelper.KEY_ID_USER_DATABASE + " = ?", new String[]{String.valueOf(databaseId)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            databaseName = cursor.getString(1);
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
    public int createUserDatabase(DatabaseProperty databaseProperty) {
        sqLiteDatabase = databaseOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseOpenHelper.KEY_NAME_USER_DATABASE, databaseProperty.getDatabaseName());
        contentValues.put(DatabaseOpenHelper.KEY_PWD_USER_DATABASE, Security.getInstance().encryptPassword(databaseProperty.getDatabasePwd()));
        contentValues.put(DatabaseOpenHelper.KEY_ICON_USER_DATABASE, databaseProperty.getDatabaseIconId());
        long databaseId = sqLiteDatabase.insert(DatabaseOpenHelper.TABLE_USER_DATABASE, null, contentValues);
        databaseOpenHelper.close();
        return (int) databaseId;
    }

    /**
     * @param categoryProperty
     */
    public int createUserCategory(CategoryProperty categoryProperty) {
        sqLiteDatabase = databaseOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseOpenHelper.KEY_ID_USER_DATABASE, categoryProperty.getDatabaseId());
        contentValues.put(DatabaseOpenHelper.KEY_NAME_USER_CATEGORY, categoryProperty.getCategoryName());
        contentValues.put(DatabaseOpenHelper.KEY_ICON_USER_CATEGORY, categoryProperty.getCategoryIconId());
        long categoryId = sqLiteDatabase.insert(DatabaseOpenHelper.TABLE_USER_CATEGORY, null, contentValues);
        databaseOpenHelper.close();
        return (int) categoryId;
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
        contentValues.put(DatabaseOpenHelper.KEY_USERNAME_USER_ENTRY, entryProperty.getEntryUsername());
        contentValues.put(DatabaseOpenHelper.KEY_USERPWD_USER_ENTRY, entryProperty.getEntryPwd());
        contentValues.put(DatabaseOpenHelper.KEY_HASH_USER_ENTRY, entryProperty.getEntryHash());
        contentValues.put(DatabaseOpenHelper.KEY_NOTE_USER_ENTRY, entryProperty.getEntryNote());
        contentValues.put(DatabaseOpenHelper.KEY_ICON_USER_ENTRY, entryProperty.getEntryIconId());
        sqLiteDatabase.insert(DatabaseOpenHelper.TABLE_USER_ENTRY, null, contentValues);
        databaseOpenHelper.close();
    }

    public void updateUserEntry(EntryProperty entryProperty){
        sqLiteDatabase = databaseOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseOpenHelper.KEY_TITLE_USER_ENTRY, entryProperty.getEntryTitle());
        contentValues.put(DatabaseOpenHelper.KEY_USERNAME_USER_ENTRY, entryProperty.getEntryUsername());
        contentValues.put(DatabaseOpenHelper.KEY_USERPWD_USER_ENTRY, entryProperty.getEntryPwd());
        contentValues.put(DatabaseOpenHelper.KEY_HASH_USER_ENTRY, entryProperty.getEntryHash());
        sqLiteDatabase.update(DatabaseOpenHelper.TABLE_USER_ENTRY, contentValues, DatabaseOpenHelper.KEY_ID_USER_ENTRY + " = ?", new String[]{String.valueOf(entryProperty.getEntryId())});
        String rawSql = "UPDATE " +
                DatabaseOpenHelper.TABLE_USER_ENTRY
                + " SET " + DatabaseOpenHelper.KEY_MODIFY_DATE_USER_ENTRY
                + " = CURRENT_TIMESTAMP WHERE " + DatabaseOpenHelper.KEY_ID_USER_ENTRY + " = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(rawSql, new String[]{String.valueOf(entryProperty.getEntryId())});
        cursor.moveToFirst();
        cursor.close();
        databaseOpenHelper.close();
    }

    public void deleteUserDatabase(int databaseId){
        sqLiteDatabase = databaseOpenHelper.getWritableDatabase();
        sqLiteDatabase.delete(DatabaseOpenHelper.TABLE_USER_DATABASE, DatabaseOpenHelper.KEY_ID_USER_DATABASE + " = ?", new String[]{String.valueOf(databaseId)});
        databaseOpenHelper.close();
    }
}
