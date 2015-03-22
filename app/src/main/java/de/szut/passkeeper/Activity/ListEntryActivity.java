package de.szut.passkeeper.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Vector;

import de.szut.passkeeper.Interface.IActivity;
import de.szut.passkeeper.Interface.IUserProperty;
import de.szut.passkeeper.Model.DatabaseModel;
import de.szut.passkeeper.R;
import de.szut.passkeeper.Utility.ListViewAdapter;

/**
 * Created by Sami.Al-Khatib on 21.03.2015.
 */
public class ListEntryActivity extends Activity implements AdapterView.OnItemClickListener, IActivity {

    private ListView listView;
    private Vector<IUserProperty> vectorEntryPropery;
    private DatabaseModel databaseModel;
    private int databaseId;
    private int categoryId;
    private String password;

    //TODO implement context menu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_layout);
        setDefaults();
        populateView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.addEntry:
                Intent intent = new Intent(ListEntryActivity.this, CreateEntryActivity.class);
                intent.putExtra("databaseId", databaseId);
                intent.putExtra("categoryId", categoryId);
                intent.putExtra("password", password);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    @Override
    public void setDefaults() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getIntent().getExtras().getString("categoryName"));
        databaseId = getIntent().getExtras().getInt("databaseId");
        categoryId = getIntent().getExtras().getInt("categoryId");
        password = getIntent().getExtras().getString("password");
        listView = (ListView) findViewById(R.id.listViewDefault);
        databaseModel = new DatabaseModel(this);
    }

    @Override
    public void populateView() {
        vectorEntryPropery = databaseModel.getUserEntryVector(databaseId, categoryId);
        listView.setAdapter(new ListViewAdapter(vectorEntryPropery, this));
        listView.setOnItemClickListener(this);
        registerForContextMenu(listView);
    }
}
