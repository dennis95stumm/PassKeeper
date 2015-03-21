package de.szut.passkeeper.Activity;

import android.app.Activity;
import android.os.Bundle;
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

    //TODO implement context menu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_layout);
        setDefaults();
        populateView();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    @Override
    public void setDefaults() {
        setTitle(getIntent().getExtras().getString(getResources().getString(R.string.intent_extra_category_name)));
        databaseId = getIntent().getExtras().getInt(getResources().getString(R.string.intent_extra_database_id));
        categoryId = getIntent().getExtras().getInt(getResources().getString(R.string.intent_extra_category_id));
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