package de.szut.passkeeper.activity;

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

import de.szut.passkeeper.R;
import de.szut.passkeeper.interfaces.IActivity;
import de.szut.passkeeper.interfaces.IUserProperty;
import de.szut.passkeeper.model.DatabaseModel;
import de.szut.passkeeper.property.CategoryProperty;
import de.szut.passkeeper.utility.AlertBuilderHelper;
import de.szut.passkeeper.utility.ListViewAdapter;

public class ListCategoryActivity extends Activity implements AdapterView.OnItemClickListener, IActivity, View.OnClickListener {

    private ListView listView;
    private Vector<IUserProperty> vectorCategoryProperty;
    private DatabaseModel databaseModel;
    private ListViewAdapter listViewAdapter;
    private int databaseId;
    private ImageButton imageButtonFab;
    private String databasePwd;

    //TODO implement context menu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intentListEntryActivity = new Intent(ListCategoryActivity.this, ListEntryActivity.class)
                .putExtra("databaseId", ((CategoryProperty) vectorCategoryProperty.get(position)).getDatabaseId())
                .putExtra("categoryId", ((CategoryProperty) vectorCategoryProperty.get(position)).getCategoryId())
                .putExtra("databasePwd", databasePwd);
        startActivity(intentListEntryActivity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButtonFab:
                AlertBuilderHelper alertDialog = new AlertBuilderHelper(this, R.string.dialog_title_add_category, R.string.dialog_message_add_category, true);
                final EditText editTextCategoryName = new EditText(this);
                editTextCategoryName.setHint(R.string.hint_category_name);
                alertDialog.setView(editTextCategoryName);
                alertDialog.setPositiveButton(R.string.dialog_positive_button_default, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int categoryId = databaseModel.createUserCategory(new CategoryProperty(databaseId, editTextCategoryName.getText().toString(), R.drawable.ic_folder));
                        vectorCategoryProperty.add(databaseModel.getUserCategoryProperty(categoryId));
                        listViewAdapter.refresh(databaseModel.getUserCategoryPropertyVector(databaseId));
                    }
                });
                alertDialog.show();
                break;
        }
    }

    @Override
    public void setDefaults() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        databaseId = getIntent().getExtras().getInt("databaseId");
        databasePwd = getIntent().getExtras().getString("databasePwd");
        databaseModel = new DatabaseModel(ListCategoryActivity.this);
        vectorCategoryProperty = new Vector<>();
        vectorCategoryProperty.addAll(databaseModel.getUserCategoryPropertyVector(databaseId));
    }

    @Override
    public void populateView() {
        setTitle(databaseModel.getUserDatabaseName(databaseId));
        setContentView(R.layout.activity_listview_layout);
        listView = (ListView) findViewById(R.id.listViewDefault);
        imageButtonFab = (ImageButton) findViewById(R.id.imageButtonFab);
        listViewAdapter = new ListViewAdapter(ListCategoryActivity.this, databaseModel.getUserCategoryPropertyVector(databaseId));
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(this);
        imageButtonFab.setOnClickListener(this);
        registerForContextMenu(listView);
    }
}
