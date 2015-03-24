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
    private ListViewAdapter listViewAdapter;
    private int databaseId;
    private int categoryId;

    //TODO implement context menu
    //TODO implement floating image button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_layout);
        setDefaults();
        populateView();
    }

    /*
    @Override
    protected void onResume() {
        super.onResume();
        vectorEntryPropery.clear();
        vectorEntryPropery = databaseModel.getUserEntryVector(databaseId, categoryId);
        listViewAdapter.notifyDataSetChanged();
    }
    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.entry_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.addEntry:
                Intent intentCreateEntryActivity = new Intent(ListEntryActivity.this, CreateEntryActivity.class)
                        .putExtra("databaseId", databaseId)
                        .putExtra("categoryId", categoryId);
                startActivity(intentCreateEntryActivity);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    @Override
    public void setDefaults() {
        databaseModel = new DatabaseModel(this);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        databaseId = getIntent().getExtras().getInt("databaseId");
        categoryId = getIntent().getExtras().getInt("categoryId");
        setTitle(databaseModel.getUserCategoryName(categoryId));
        listView = (ListView) findViewById(R.id.listViewDefault);
    }

    @Override
    public void populateView() {
        vectorEntryPropery = databaseModel.getUserEntryVector(databaseId, categoryId);
        listViewAdapter = new ListViewAdapter(vectorEntryPropery, this);
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(this);
        registerForContextMenu(listView);
    }
}
