package de.szut.passkeeper.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.util.Vector;

import de.szut.passkeeper.R;
import de.szut.passkeeper.interfaces.IActivity;
import de.szut.passkeeper.interfaces.IRecyclerActivity;
import de.szut.passkeeper.interfaces.IUserProperty;
import de.szut.passkeeper.model.DatabaseModel;
import de.szut.passkeeper.property.EntryProperty;
import de.szut.passkeeper.utility.AlertBuilderHelper;
import de.szut.passkeeper.utility.RecyclerItemDividerDecoration;
import de.szut.passkeeper.utility.RecyclerViewAdapter;

public class ListEntryActivity extends IRecyclerActivity implements IActivity, View.OnClickListener {

    private Vector<IUserProperty> vectorEntryPropery;
    private DatabaseModel databaseModel;
    private RecyclerViewAdapter recyclerViewAdapter;
    private int databaseId;
    private int categoryId;
    private String databasePwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null)
            setDefaults();
        populateView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        vectorEntryPropery.clear();
        vectorEntryPropery.addAll(databaseModel.getUserEntryVector(databaseId, categoryId));
        recyclerViewAdapter.refresh(databaseModel.getUserEntryVector(databaseId, categoryId));
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
    public void onRecyclerItemClick(int position) {
        Intent intentUpdateEntryActivity = new Intent(ListEntryActivity.this, EntryActivity.class)
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
                Intent intentCreateEntryActivity = new Intent(ListEntryActivity.this, EntryActivity.class)
                        .putExtra("databaseId", databaseId)
                        .putExtra("categoryId", categoryId)
                        .putExtra("databasePwd", databasePwd);
                startActivity(intentCreateEntryActivity);
                break;
        }
    }

    @Override
    public void setDefaults() {
        databaseModel = new DatabaseModel(this);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        databaseId = getIntent().getExtras().getInt("databaseId");
        categoryId = getIntent().getExtras().getInt("categoryId");
        databasePwd = getIntent().getExtras().getString("databasePwd");
        vectorEntryPropery = databaseModel.getUserEntryVector(databaseId, categoryId);
        if (databaseModel.getUserEntryVector(databaseId, categoryId).isEmpty()) {
            AlertBuilderHelper alertBuilderHelper = new AlertBuilderHelper(ListEntryActivity.this, R.string.dialog_title_create_entry, R.string.dialog_message_create_entry, true);
            alertBuilderHelper.setPositiveButton(R.string.dialog_positive_button_default, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intentCreateEntryActivity = new Intent(ListEntryActivity.this, EntryActivity.class)
                            .putExtra("databaseId", databaseId)
                            .putExtra("categoryId", categoryId)
                            .putExtra("databasePwd", databasePwd);
                    startActivity(intentCreateEntryActivity);
                }
            });
            alertBuilderHelper.show();
        }
    }

    @Override
    public void populateView() {
        setTitle(databaseModel.getUserCategoryName(categoryId));
        setContentView(R.layout.activity_recyclerview_layout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewDefault);
        ImageButton imageButtonFab = (ImageButton) findViewById(R.id.imageButtonFab);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(this, vectorEntryPropery, recyclerView, this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.addItemDecoration(new RecyclerItemDividerDecoration(this));
        imageButtonFab.setOnClickListener(this);
        registerForContextMenu(recyclerView);
    }

    @Override
    public void removeItem(int position) {
        databaseModel.deleteUserEntry(((EntryProperty) vectorEntryPropery.get(position)).getEntryId());
        vectorEntryPropery.remove(position);
        recyclerViewAdapter.refresh(vectorEntryPropery);
    }
}
