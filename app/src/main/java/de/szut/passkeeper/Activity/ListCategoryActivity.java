package de.szut.passkeeper.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Vector;

import de.szut.passkeeper.Interface.IActivity;
import de.szut.passkeeper.Interface.IUserProperty;
import de.szut.passkeeper.Model.DatabaseModel;
import de.szut.passkeeper.Model.Security;
import de.szut.passkeeper.Property.CategoryProperty;
import de.szut.passkeeper.Property.DatabaseProperty;
import de.szut.passkeeper.R;
import de.szut.passkeeper.Utility.AlertBuilderHelper;
import de.szut.passkeeper.Utility.ListViewAdapter;

public class ListCategoryActivity extends Activity implements AdapterView.OnItemClickListener, IActivity, View.OnClickListener {

    private ListView listView;
    private Vector<IUserProperty> vectorCategoryProperty;
    private DatabaseModel databaseModel;
    private ListViewAdapter listViewAdapter;
    private int databaseId;
    private Vector<IUserProperty> vectorUserDatabaseProperties;
    private ImageButton imageButtonFab;
    private String databasePwd;
    private static final int CONTEXT_UPDATE_CATEGORY_NAME_ID = ContextMenu.FIRST;
    private static final int CONTEXT_DELETE_CATEGORY_ID = ContextMenu.FIRST + 1;
    private AdapterView.AdapterContextMenuInfo listItemInfo;


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

    /** TODO
     * Keine Logik hinter Update Category
     * Logik hinter Delete Category ist noch buggy. Nach dem zweiten mal Löschen eines Eintrages
     * wird scheinbar der Ursprungszustand wieder hergestellt. (Alle Einträge werden gelöscht
     * und die Default einträge werden wieder hergestellt
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, CONTEXT_UPDATE_CATEGORY_NAME_ID, Menu.NONE, R.string.contextmenu_item_update_category_name);
        menu.add(Menu.NONE, CONTEXT_DELETE_CATEGORY_ID, Menu.NONE, R.string.contextmenu_item_delete_category);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        listItemInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case 1:
                //TODO Umbenennen Logik hinterlegen
                break;
            case 2:
                final AlertBuilderHelper alertBuilderHelper = new AlertBuilderHelper(ListCategoryActivity.this, R.string.dialog_title_warning, R.string.dialog_message_delete_category_warning_message, true);
                alertBuilderHelper.setPositiveButton(R.string.dialog_positive_button_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                            databaseModel.deleteUserCategory(((CategoryProperty) vectorCategoryProperty.get(listItemInfo.position)).getCategoryId());
                            vectorCategoryProperty.remove(listItemInfo.position);
                            listViewAdapter.refresh(databaseModel.getUserCategoryPropertyVector(listItemInfo.position));
                    }
                });
                alertBuilderHelper.show();
                break;
        }
        return super.onContextItemSelected(item);
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
