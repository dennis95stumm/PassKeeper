package de.szut.passkeeper.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.Vector;

import de.szut.passkeeper.Interface.IActivity;
import de.szut.passkeeper.Interface.IUserProperty;
import de.szut.passkeeper.Model.DatabaseModel;
import de.szut.passkeeper.Property.CategoryProperty;
import de.szut.passkeeper.R;
import de.szut.passkeeper.Utility.AlertBuilderHelper;
import de.szut.passkeeper.Utility.ListViewAdapter;

public class ListCategoryActivity extends Activity implements AdapterView.OnItemClickListener, IActivity, View.OnClickListener {
    //TODO implement floating image button

    private ListView listView;
    private Vector<IUserProperty> vectorCategoryProperty;
    private DatabaseModel databaseModel;
    private ListViewAdapter listViewAdapter;
    private int databaseId;
    private ImageButton imageButtonFab;

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
        getMenuInflater().inflate(R.menu.category_list_menu, menu);
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
        Intent intent = new Intent(ListCategoryActivity.this, ListEntryActivity.class)
                .putExtra("databaseId", ((CategoryProperty) vectorCategoryProperty.get(position)).getDatabaseId())
                .putExtra("categoryId", ((CategoryProperty) vectorCategoryProperty.get(position)).getCategoryId());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButtonFab:
                AlertBuilderHelper alertDialog = new AlertBuilderHelper(this, R.string.dialog_title_add_category, R.string.dialog_message_add_category, true);
                final EditText editText = new EditText(this);
                editText.setHint(R.string.hint_category_name);
                alertDialog.setView(editText);
                alertDialog.setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int categoryId = databaseModel.createUserCategory(new CategoryProperty(databaseId, editText.getText().toString(), R.drawable.ic_folder));
                        vectorCategoryProperty.add(databaseModel.getUserCategoryProperty(categoryId));
                        listViewAdapter.notifyDataSetChanged();
                    }
                });
                alertDialog.show();
                break;
        }
    }

    @Override
    public void setDefaults() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        databaseModel = new DatabaseModel(getApplicationContext());
        databaseId = getIntent().getExtras().getInt("databaseId");
        setTitle(databaseModel.getUserDatabaseName(databaseId));
        listView = (ListView) findViewById(R.id.listViewDefault);
        imageButtonFab = (ImageButton) findViewById(R.id.imageButtonFab);
    }

    @Override
    public void populateView() {
        vectorCategoryProperty = databaseModel.getUserCategoryPropertyVector(databaseId);
        listViewAdapter = new ListViewAdapter(vectorCategoryProperty, this);
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(this);
        imageButtonFab.setOnClickListener(this);
        registerForContextMenu(listView);
    }
}
