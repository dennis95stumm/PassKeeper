package de.szut.passkeeper.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Vector;

import de.szut.passkeeper.R;
import de.szut.passkeeper.interfaces.IActivity;
import de.szut.passkeeper.interfaces.IRecyclerActivity;
import de.szut.passkeeper.interfaces.IUserProperty;
import de.szut.passkeeper.model.DatabaseModel;
import de.szut.passkeeper.property.CategoryProperty;
import de.szut.passkeeper.utility.AlertBuilderHelper;
import de.szut.passkeeper.utility.RecyclerItemDividerDecoration;
import de.szut.passkeeper.utility.RecyclerViewAdapter;

/**
 *
 */
public class ListCategoryActivity extends IRecyclerActivity implements IActivity, View.OnClickListener {
    private Vector<IUserProperty> vectorCategoryProperty;
    private DatabaseModel databaseModel;
    private RecyclerViewAdapter recyclerViewAdapter;
    private int databaseId;
    private String databasePwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaults();
        populateView();
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

    /**
     * @param position
     */
    public void onRecyclerItemClick(int position) {
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
                        recyclerViewAdapter.refresh(databaseModel.getUserCategoryPropertyVector(databaseId));
                    }
                });
                alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                    }
                });
                alertDialog.show();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                break;
        }
    }

    @Override
    public void setDefaults() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        databaseId = getIntent().getExtras().getInt("databaseId");
        databasePwd = getIntent().getExtras().getString("databasePwd");
        databaseModel = new DatabaseModel(ListCategoryActivity.this);
        vectorCategoryProperty = databaseModel.getUserCategoryPropertyVector(databaseId);
    }

    @Override
    public void populateView() {
        setTitle(databaseModel.getUserDatabaseName(databaseId));
        setContentView(R.layout.activity_recyclerview_layout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewDefault);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ImageButton imageButtonFab = (ImageButton) findViewById(R.id.imageButtonFab);
        recyclerViewAdapter = new RecyclerViewAdapter(this, databaseModel.getUserCategoryPropertyVector(databaseId), recyclerView, this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.addItemDecoration(new RecyclerItemDividerDecoration(this));
        imageButtonFab.setOnClickListener(this);
        registerForContextMenu(recyclerView);
    }

    /**
     *
     * @param position
     */
    public void removeItem(int position) {
        databaseModel.deleteUserCategory(((CategoryProperty) vectorCategoryProperty.get(position)).getCategoryId());
        vectorCategoryProperty.remove(position);
        recyclerViewAdapter.refresh(vectorCategoryProperty);
    }
}
