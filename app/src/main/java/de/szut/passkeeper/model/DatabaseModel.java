package de.szut.passkeeper.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import de.szut.passkeeper.interfaces.IUserProperty;
import de.szut.passkeeper.property.CategoryProperty;
import de.szut.passkeeper.property.DatabaseProperty;
import de.szut.passkeeper.property.EntryProperty;

/**
 * This class provides all the necessary functions for creating, updating or deleting user-databases, categories and password-entries
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
            DatabaseOpenHelper.KEY_MODIFY_DATE_USER_ENTRY,
            DatabaseOpenHelper.KEY_IV_USER_ENTRY
    };

    /**
     * @param context the application context that is passed to the {{@link DatabaseOpenHelper}}
     */
    public DatabaseModel(Context context) {
        databaseOpenHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Get all user databases and return a single vector object
     * @return the vector that holds all the user-database data
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
     * This function get's all categories of an users database
     * @param databaseId the databaseid of which the categories should be selected
     * @return the vector that holds all the categories of an database
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

    /**
     * This function get's all user entries of an database & category
     * @param databaseId the database id of user-entries
     * @param categoryId the category id of user-entries
     * @return the vector that holds all user-entries of the selected category and user-database
     */
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

    /**
     *
     * @param databaseId
     * @return
     */
    public DatabaseProperty getUserDatabaseProperty(int databaseId) {
        sqLiteDatabase = databaseOpenHelper.getReadableDatabase();
        DatabaseProperty databaseProperty = null;
        Cursor cursor = sqLiteDatabase.query(DatabaseOpenHelper.TABLE_USER_DATABASE, passDatabaseColumns, DatabaseOpenHelper.KEY_ID_USER_DATABASE + " = ?", new String[]{String.valueOf(databaseId)}, null, null, null);
        if (cursor.moveToFirst()) {
            databaseProperty = new DatabaseProperty(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getString(4)
            );
            cursor.close();
        }
        sqLiteDatabase.close();
        return databaseProperty;
    }

    /**
     *
     * @param categoryId
     * @return
     */
    public CategoryProperty getUserCategoryProperty(int categoryId) {
        sqLiteDatabase = databaseOpenHelper.getReadableDatabase();
        CategoryProperty categoryProperty = null;
        Cursor cursor = sqLiteDatabase.query(DatabaseOpenHelper.TABLE_USER_CATEGORY, passCategoryColumns, DatabaseOpenHelper.KEY_ID_USER_CATEGORY + " = ?", new String[]{String.valueOf(categoryId)}, null, null, null);
        if (cursor.moveToFirst()) {
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

    /**
     *
     * @param entryId
     * @return
     */
    public EntryProperty getUserEntryProperty(int entryId) {
        sqLiteDatabase = databaseOpenHelper.getReadableDatabase();
        EntryProperty entryProperty = null;
        Cursor cursor = sqLiteDatabase.query(DatabaseOpenHelper.TABLE_USER_ENTRY, passEntryColumns, DatabaseOpenHelper.KEY_ID_USER_ENTRY + " = ? ", new String[]{String.valueOf(entryId)}, null, null, null);
        if (cursor.moveToFirst()) {
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
                    cursor.getString(9),
                    cursor.getString(10)
            );
            cursor.close();
        }
        sqLiteDatabase.close();
        return entryProperty;
    }

    /**
     *
     * @param databaseId
     * @return
     */
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

    /**
     *
     * @param categoryId
     * @return
     */
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
     *
     * @param databaseProperty
     * @return
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
     *
     * @param categoryProperty
     * @return
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
     *
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
        contentValues.put(DatabaseOpenHelper.KEY_IV_USER_ENTRY, entryProperty.getEntryIV());
        sqLiteDatabase.insert(DatabaseOpenHelper.TABLE_USER_ENTRY, null, contentValues);
        databaseOpenHelper.close();
    }

    /**
     *
     * @param databaseProperty
     */
    public void updateUserDatabase(DatabaseProperty databaseProperty) {
        sqLiteDatabase = databaseOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseOpenHelper.KEY_NAME_USER_DATABASE, databaseProperty.getDatabaseName());
        contentValues.put(DatabaseOpenHelper.KEY_PWD_USER_DATABASE, databaseProperty.getDatabasePwd());
        contentValues.put(DatabaseOpenHelper.KEY_MODIFY_DATE_USER_DATABASE, SimpleDateFormat.getDateTimeInstance().format(new Date()));
        sqLiteDatabase.update(DatabaseOpenHelper.TABLE_USER_DATABASE, contentValues, DatabaseOpenHelper.KEY_ID_USER_DATABASE + " = ?", new String[]{String.valueOf(databaseProperty.getDatabaseId())});
    }

    /**
     *
     * @param entryProperty
     */
    public void updateUserEntry(EntryProperty entryProperty) {
        sqLiteDatabase = databaseOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseOpenHelper.KEY_TITLE_USER_ENTRY, entryProperty.getEntryTitle());
        contentValues.put(DatabaseOpenHelper.KEY_USERNAME_USER_ENTRY, entryProperty.getEntryUsername());
        contentValues.put(DatabaseOpenHelper.KEY_USERPWD_USER_ENTRY, entryProperty.getEntryPwd());
        contentValues.put(DatabaseOpenHelper.KEY_HASH_USER_ENTRY, entryProperty.getEntryHash());
        contentValues.put(DatabaseOpenHelper.KEY_IV_USER_ENTRY, entryProperty.getEntryIV());
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

    /**
     *
     * @param databaseId
     */
    public void deleteUserDatabase(int databaseId) {
        sqLiteDatabase = databaseOpenHelper.getWritableDatabase();
        sqLiteDatabase.delete(DatabaseOpenHelper.TABLE_USER_DATABASE, DatabaseOpenHelper.KEY_ID_USER_DATABASE + " = ?", new String[]{String.valueOf(databaseId)});
        databaseOpenHelper.close();
    }

    /**
     *
     * @param categoryId
     */
    public void deleteUserCategory(int categoryId) {
        sqLiteDatabase = databaseOpenHelper.getWritableDatabase();
        sqLiteDatabase.delete(DatabaseOpenHelper.TABLE_USER_CATEGORY, DatabaseOpenHelper.KEY_ID_USER_CATEGORY + " = ?", new String[]{String.valueOf(categoryId)});
        databaseOpenHelper.close();
    }

    /**
     *
     * @param entryId
     */
    public void deleteUserEntry(int entryId) {
        sqLiteDatabase = databaseOpenHelper.getWritableDatabase();
        sqLiteDatabase.delete(DatabaseOpenHelper.TABLE_USER_ENTRY, DatabaseOpenHelper.KEY_ID_USER_ENTRY + " = ?", new String[]{String.valueOf(entryId)});
        databaseOpenHelper.close();
    }
}
