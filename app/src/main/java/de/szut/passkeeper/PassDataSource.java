package de.szut.passkeeper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Vector;

/**
 * Created by Sami.Al-Khatib on 10.02.2015.
 */
public class PassDataSource {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    private String[] passDatabaseColumns = {
            DatabaseHelper.KEY_ID_USER_DATABASE,
            DatabaseHelper.KEY_USER_DATABASE_NAME,
            DatabaseHelper.KEY_USER_DATABASE_PWD,
            DatabaseHelper.KEY_USER_DATABASE_CDATE,
            DatabaseHelper.KEY_USER_DATABASE_MDATE
    };

    public  PassDataSource(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    public void openDatabaseConnection() {
        sqLiteDatabase = databaseHelper.getWritableDatabase();
    }

    public  void closeDatabaseConnection() {
        databaseHelper.close();
    }

    public Vector<DatabaseProperties> getDatabasePropertiesVec(){
        Vector<DatabaseProperties> vectorDatabaseProperties = new Vector<DatabaseProperties>();

        return vectorDatabaseProperties;
    }
}
