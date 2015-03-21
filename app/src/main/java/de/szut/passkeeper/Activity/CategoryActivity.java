package de.szut.passkeeper.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Vector;

import de.szut.passkeeper.Interface.IListViewType;
import de.szut.passkeeper.Model.DatabaseModel;
import de.szut.passkeeper.R;
import de.szut.passkeeper.Utility.CustomListViewAdapter;

public class CategoryActivity extends Activity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private Vector<IListViewType> vectorUserCategoryProperty;
    private DatabaseModel databaseModel;
    private int databaseId;
    private String databaseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_layout);
        initializeView();
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private void initializeView() {
        databaseModel = new DatabaseModel(getApplicationContext());
        databaseId = getIntent().getExtras().getInt(getResources().getString(R.string.intent_extra_database_id));
        vectorUserCategoryProperty = databaseModel.getUserCategoryPropertyList(databaseId);
        listView = (ListView) findViewById(R.id.listViewDefault);
        listView.setAdapter(new CustomListViewAdapter(vectorUserCategoryProperty, this));
        listView.setOnItemClickListener(this);
        registerForContextMenu(listView);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
