package de.szut.passkeeper.Activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import de.szut.passkeeper.Interface.IActivity;
import de.szut.passkeeper.R;

public class CreateEntryActivity extends Activity implements IActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entry_layout);
        setDefaults();
        populateView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setDefaults() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void populateView() {

    }
}
