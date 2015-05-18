package de.szut.passkeeper.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Vector;

import de.szut.passkeeper.R;
import de.szut.passkeeper.interfaces.IActivity;
import de.szut.passkeeper.interfaces.IRecyclerActivity;
import de.szut.passkeeper.interfaces.IUserProperty;
import de.szut.passkeeper.model.DatabaseModel;
import de.szut.passkeeper.model.Security;
import de.szut.passkeeper.property.DatabaseProperty;
import de.szut.passkeeper.utility.AlertBuilderHelper;
import de.szut.passkeeper.utility.RecyclerItemDividerDecoration;
import de.szut.passkeeper.utility.RecyclerViewAdapter;

/**
 *
 */
public class ListDatabaseActivity extends IRecyclerActivity implements IActivity, View.OnClickListener {
    private DatabaseModel databaseModel;
    private Vector<IUserProperty> vectorUserDatabaseProperties;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaults();
        populateView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        vectorUserDatabaseProperties.clear();
        vectorUserDatabaseProperties.addAll(databaseModel.getUserDatabasePropertyVector());
        recyclerViewAdapter.refresh(databaseModel.getUserDatabasePropertyVector());
    }

    @Override
    public boolean editItem(int position) {
        Intent intentDatabaseActivity = new Intent(this, DatabaseActivity.class)
                .putExtra("databaseId", ((DatabaseProperty) vectorUserDatabaseProperties.get(position)).getDatabaseId());
        startActivity(intentDatabaseActivity);

        return true;
    }


    public boolean longPressEnabled() {
        return true;
    }

    @Override
    public void onRecyclerItemClick(final int position) {
        final AlertDialog.Builder alertDialog = new AlertBuilderHelper(this, R.string.dialog_title_open_database, R.string.dialog_message_open_database, true);
        final EditText editText = new EditText(this);
        editText.setHint(R.string.hint_database_pwd);
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editText.setTransformationMethod(new PasswordTransformationMethod());
        alertDialog.setView(editText);
        alertDialog.setPositiveButton(R.string.dialog_positive_button_default, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                dialog.dismiss();
                if (Security.getInstance().checkPassword(editText.getText().toString(), ((DatabaseProperty) vectorUserDatabaseProperties.get(position)).getDatabasePwd())) {
                    Intent intentListCategoryActivity = new Intent(ListDatabaseActivity.this, ListCategoryActivity.class)
                            .putExtra("databaseId", ((DatabaseProperty) vectorUserDatabaseProperties.get(position)).getDatabaseId())
                            .putExtra("databasePwd", editText.getText().toString());
                    startActivity(intentListCategoryActivity);
                } else {
                    Toast.makeText(ListDatabaseActivity.this, R.string.toast_message_wrong_password, Toast.LENGTH_SHORT).show();
                }
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButtonFab:
                startActivity(new Intent(this, DatabaseActivity.class));
                break;
        }
    }


    @Override
    public void setDefaults() {
        databaseModel = new DatabaseModel(getApplicationContext());
        vectorUserDatabaseProperties = databaseModel.getUserDatabasePropertyVector();
    }

    @Override
    public void populateView() {
        setContentView(R.layout.activity_recyclerview_layout);
        ImageButton imageButtonFab = (ImageButton) findViewById(R.id.imageButtonFab);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewDefault);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAdapter = new RecyclerViewAdapter(this, databaseModel.getUserDatabasePropertyVector(), recyclerView, this, R.id.delitition_password);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.addItemDecoration(new RecyclerItemDividerDecoration(this));
        imageButtonFab.setOnClickListener(this);
        registerForContextMenu(recyclerView);
    }

    @Override
    public void removeItem(int position) {
        databaseModel.deleteUserDatabase(((DatabaseProperty) vectorUserDatabaseProperties.get(position)).getDatabaseId());
        vectorUserDatabaseProperties.remove(position);
        recyclerViewAdapter.refresh(vectorUserDatabaseProperties);
    }

    @Override
    public boolean confirmRemove(String password, int position) {
        return Security.getInstance().checkPassword(password, ((DatabaseProperty) vectorUserDatabaseProperties.get(position)).getDatabasePwd());
    }

    @Override
    public void onRemoveConfirmationFailed() {
        Toast.makeText(ListDatabaseActivity.this, R.string.toast_message_wrong_password, Toast.LENGTH_SHORT).show();
    }
}
