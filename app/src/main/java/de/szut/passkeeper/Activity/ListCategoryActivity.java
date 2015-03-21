package de.szut.passkeeper.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.Vector;

import de.szut.passkeeper.Interface.IActivity;
import de.szut.passkeeper.Interface.IUserProperty;
import de.szut.passkeeper.Model.DatabaseModel;
import de.szut.passkeeper.R;
import de.szut.passkeeper.Utility.AlertBuilderHelper;
import de.szut.passkeeper.Utility.ListViewAdapter;

public class ListCategoryActivity extends Activity implements AdapterView.OnItemClickListener, IActivity {

    private ListView listView;
    private Vector<IUserProperty> vectorCategoryProperty;
    private DatabaseModel databaseModel;
    private int databaseId;

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
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.addCategory) {
            AlertBuilderHelper alertDialog = new AlertBuilderHelper(this, R.string.dialog_title_add_category, R.string.dialog_message_add_category, true);
            final EditText editText = new EditText(this);
            editText.setHint(R.string.hint_category_name);
            alertDialog.setView(editText);
            alertDialog.setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    databaseModel.createCategory(databaseId, editText.getText().toString());
                    populateView();
                }
            });
            alertDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void setDefaults() {
        setTitle(getIntent().getExtras().getString(getResources().getString(R.string.intent_extra_database_name)));
        databaseId = getIntent().getExtras().getInt(getResources().getString(R.string.intent_extra_database_id));
        databaseModel = new DatabaseModel(getApplicationContext());
        listView = (ListView) findViewById(R.id.listViewDefault);

    }

    @Override
    public void populateView() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        vectorCategoryProperty = databaseModel.getUserCategoryPropertyVector(databaseId);
        listView.setAdapter(new ListViewAdapter(vectorCategoryProperty, this));
        listView.setOnItemClickListener(this);
        registerForContextMenu(listView);
    }
}
