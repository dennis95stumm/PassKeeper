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
        this.setTitle("");
        this.setStartActivity();
    }

    private void setStartActivity(){
        databaseModel = new DatabaseModel(getApplicationContext());
        listUserDatabaseProperties = databaseModel.getDatabasePropertiesList();
        if(listUserDatabaseProperties.size() == 0){
            Intent intent = new Intent(this, CreateDatabaseActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            //intent.addCategory(Intent.CATEGORY_HOME);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, ChooseDatabaseActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            //intent.addCategory(Intent.CATEGORY_HOME);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
