package de.szut.passkeeper.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Vector;

import de.szut.passkeeper.Interface.IUserProperty;
import de.szut.passkeeper.Model.DatabaseModel;
import de.szut.passkeeper.Model.DatabaseOpenHelper;


public class StartActivity extends Activity {

    private DatabaseOpenHelper databaseOpenHelper;
    private Vector<IUserProperty> vectorUserDatabaseProperty;
    private DatabaseModel databaseModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStartActivity();
    }

    private void setStartActivity() {
        databaseModel = new DatabaseModel(getApplicationContext());
        vectorUserDatabaseProperty = databaseModel.getUserDatabasePropertyVector();
        if (vectorUserDatabaseProperty.size() == 0) {
            Intent intent = new Intent(this, CreateDatabaseActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, ListDatabaseActivity.class);
            //Intent intent = new Intent(StartActivity.this, CardViewDatabaseActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
