package de.szut.passkeeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;


public class StartActivity extends Activity {

    private DatabaseHelper databaseHelper;
    private List listUserDatabaseProperties;
    private DatabaseModel databaseModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_list_layout);
        databaseModel = new DatabaseModel(this);
        listUserDatabaseProperties = databaseModel.getDatabasePropertiesList();
        if (listUserDatabaseProperties.size() == 0) {
            Intent intent = new Intent(this, CreateDatabaseActivity.class);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
