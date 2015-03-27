package de.szut.passkeeper.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Vector;

import de.szut.passkeeper.Interface.IActivity;
import de.szut.passkeeper.Interface.IUserProperty;
import de.szut.passkeeper.Model.DatabaseModel;
import de.szut.passkeeper.Property.EntryProperty;
import de.szut.passkeeper.R;
import de.szut.passkeeper.Utility.ListViewAdapter;

/**
 * Created by Sami.Al-Khatib on 21.03.2015.
 */
public class ListEntryActivity extends Activity implements AdapterView.OnItemClickListener, IActivity, View.OnClickListener {

    private ListView listView;
    private Vector<IUserProperty> vectorEntryPropery;
    private DatabaseModel databaseModel;
    private ListViewAdapter listViewAdapter;
    private int databaseId;
    private int categoryId;
    private String databasePwd;
    private ImageButton imageButtonFab;

    //TODO implement context menu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null)
            setDefaults();
            populateView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(ListEntryActivity.this, "Restarted...", Toast.LENGTH_SHORT).show();
        vectorEntryPropery = databaseModel.getUserEntryVector(databaseId, categoryId);
        listViewAdapter.notifyDataSetChanged();
    }

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
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intentUpdateEntryActivity = new Intent(ListEntryActivity.this, UpdateEntryActivity.class)
                .putExtra("databaseId", databaseId)
                .putExtra("categoryId", categoryId)
                .putExtra("entryId", ((EntryProperty) vectorEntryPropery.get(position)).getEntryId())
                .putExtra("databasePwd", databasePwd);
        startActivity(intentUpdateEntryActivity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButtonFab:
                Intent intentCreateEntryActivity = new Intent(ListEntryActivity.this, CreateEntryActivity.class)
                        .putExtra("databaseId", databaseId)
                        .putExtra("categoryId", categoryId)
                        .putExtra("databasePwd", databasePwd);
                startActivity(intentCreateEntryActivity);
                break;
        }
    }

    @Override
    public void setDefaults() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        databaseId = getIntent().getExtras().getInt("databaseId");
        categoryId = getIntent().getExtras().getInt("categoryId");
        databasePwd = getIntent().getExtras().getString("databasePwd");
        databaseModel = new DatabaseModel(this);
        vectorEntryPropery = databaseModel.getUserEntryVector(databaseId, categoryId);
    }

    @Override
    public void populateView() {
        setTitle(databaseModel.getUserCategoryName(categoryId));
        setContentView(R.layout.activity_listview_layout);
        listView = (ListView) findViewById(R.id.listViewDefault);
        imageButtonFab = (ImageButton) findViewById(R.id.imageButtonFab);
        listViewAdapter = new ListViewAdapter(ListEntryActivity.this, vectorEntryPropery);
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(this);
        imageButtonFab.setOnClickListener(this);
        registerForContextMenu(listView);
    }
}
