package de.szut.passkeeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class ChooseDatabaseActivity extends Activity {

    private DatabaseModel databaseModel;
    private ArrayList<UserDatabaseProperties> listUserDatabaseProperties;
    private ListView listViewDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_database_layout);
        this.initializeView();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.choose_database_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.addDatabase){
            startActivity(new Intent(this, CreateDatabaseActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    

    private void initializeView(){
        databaseModel = new DatabaseModel(getApplicationContext());
        listUserDatabaseProperties = databaseModel.getDatabasePropertiesList();
        for(UserDatabaseProperties userDatabaseProperties : listUserDatabaseProperties){
            Toast.makeText(getApplicationContext(), userDatabaseProperties.getDatabaseMdate(), Toast.LENGTH_SHORT).show();
        }
        listViewDatabase = (ListView) findViewById(R.id.listViewDatabase);
        listViewDatabase.setAdapter(new CustomListViewAdapter(listUserDatabaseProperties, this));
    }
}
