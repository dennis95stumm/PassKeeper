package de.szut.passkeeper.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
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


public class ListDatabaseActivity extends IRecyclerActivity implements IActivity, View.OnClickListener {

    private static final int CONTEXT_UPDATE_DATABASE_NAME_ID = ContextMenu.FIRST;
    private static final int CONTEXT_UPDATE_DATABASE_PWD_ID = ContextMenu.FIRST + 1;
    private static final int CONTEXT_DELETE_DATABASE_ID = ContextMenu.FIRST + 2;
    private DatabaseModel databaseModel;
    private Vector<IUserProperty> vectorUserDatabaseProperties;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ImageButton imageButtonFab;
    private AdapterView.AdapterContextMenuInfo listItemInfo;
    private LinearLayoutManager layoutManager;

    //TODO implement context menu

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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, CONTEXT_UPDATE_DATABASE_NAME_ID, Menu.NONE, R.string.contextmenu_item_update_database_name);
        menu.add(Menu.NONE, CONTEXT_UPDATE_DATABASE_PWD_ID, Menu.NONE, R.string.contextmenu_item_update_database_password);
        menu.add(Menu.NONE, CONTEXT_DELETE_DATABASE_ID, Menu.NONE, R.string.contextmenu_item_delete_database);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        listItemInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case 1:
                break;
            case 2:
                final AlertBuilderHelper alertChangePwd = new AlertBuilderHelper(ListDatabaseActivity.this, R.string.dialog_title_change_database_pwd, 0, true);
                AlertDialog.Builder builder = new AlertDialog.Builder(ListDatabaseActivity.this);
                AlertDialog alertDialog = builder.create();

                alertChangePwd.setView(R.layout.alert_dialog_change_pwd_layout);
                final EditText editTextOldDbPwd = (EditText) findViewById(R.id.editTextOldDbPwd);
                final EditText editTextNewDbPwd = (EditText) findViewById(R.id.editTextNewDbPwd);
                final EditText editTextNewDbPwdRepeat = (EditText) findViewById(R.id.editTextNewPwdDbRepeat);
                ImageButton imageButtonShowDbPwd = (ImageButton) findViewById(R.id.imageButtonViewPwd);
                Toast.makeText(ListDatabaseActivity.this, String.valueOf(editTextOldDbPwd), Toast.LENGTH_SHORT).show();
                alertChangePwd.setPositiveButton(R.string.dialog_positive_button_default, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Security.getInstance().checkPassword(editTextOldDbPwd.getText().toString(), ((DatabaseProperty) vectorUserDatabaseProperties.get(listItemInfo.position)).getDatabasePwd())
                                && editTextNewDbPwd.getText().toString().equals(editTextNewDbPwdRepeat.getText().toString()) && editTextNewDbPwd.getText().length() == 8) {
                            databaseModel.updateUserDatabasePwd((((DatabaseProperty) vectorUserDatabaseProperties.get(listItemInfo.position)).getDatabaseId()), Security.getInstance().encryptPassword(editTextNewDbPwd.getText().toString()));
                        } else {
                            Toast.makeText(ListDatabaseActivity.this, R.string.toast_message_wrong_password, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alertChangePwd.show();
                break;
            case 3:
                final AlertBuilderHelper alertBuilderHelper = new AlertBuilderHelper(ListDatabaseActivity.this, R.string.dialog_title_warning, R.string.dialog_message_delete_database_warning_message, true);
                final EditText editTextDatabasePwd = new EditText(ListDatabaseActivity.this);
                editTextDatabasePwd.setHint(R.string.hint_database_pwd);
                editTextDatabasePwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editTextDatabasePwd.setTransformationMethod(new PasswordTransformationMethod());
                alertBuilderHelper.setView(editTextDatabasePwd);
                alertBuilderHelper.setPositiveButton(R.string.dialog_positive_button_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (Security.getInstance().checkPassword(editTextDatabasePwd.getText().toString(), ((DatabaseProperty) vectorUserDatabaseProperties.get(listItemInfo.position)).getDatabasePwd())) {
                            removeItem(listItemInfo.position);
                        } else {
                            Toast.makeText(ListDatabaseActivity.this, R.string.toast_message_wrong_password, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alertBuilderHelper.show();
                //TODO SET ICON
                //TODO DISABLE POSITIVE BUTTON
                break;
        }
        return super.onContextItemSelected(item);
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
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
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
        alertDialog.show();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButtonFab:
                startActivity(new Intent(ListDatabaseActivity.this, CreateDatabaseActivity.class));
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
        imageButtonFab = (ImageButton) findViewById(R.id.imageButtonFab);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewDefault);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
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
