package de.szut.passkeeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Vector;


public class StartActivity extends Activity {

    private DatabaseHelper databaseHelper;
    private Vector vecUserDatabaseProperties;
    private DatabaseModel databaseModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStartActivity();
    }

    private void setStartActivity(){
        databaseModel = new DatabaseModel(this);
        databaseModel.openDatabaseConnection();
        vecUserDatabaseProperties = databaseModel.getDatabasePropertiesVec();
        databaseModel.closeDatabaseConnection();
        if(vecUserDatabaseProperties.size() == 0){
            Intent intent = new Intent(this, CreateDatabaseActivity.class);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
