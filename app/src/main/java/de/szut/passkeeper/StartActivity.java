package de.szut.passkeeper;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.CursorAdapter;


public class StartActivity extends ListActivity {

    private DatabaseHelper databaseHelper;
    private Cursor cursorUserDatabaseProperties;
    private DatabaseModel databaseModel;
    private DatabaseCursorAdapter databaseCursorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_database_layout);
        databaseModel = new DatabaseModel(this);
        cursorUserDatabaseProperties = databaseModel.getDatabasePropertiesCursor();
        if (cursorUserDatabaseProperties.getCount() == 0) {
            Intent intent = new Intent(this, CreateDatabaseActivity.class);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        databaseCursorAdapter = new DatabaseCursorAdapter(this, cursorUserDatabaseProperties, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        setListAdapter(databaseCursorAdapter);
    }
}
