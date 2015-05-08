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
import de.szut.passkeeper.Property.DatabaseProperty;
import de.szut.passkeeper.R;
import de.szut.passkeeper.Utility.AlertBuilderHelper;
import de.szut.passkeeper.Utility.ListViewAdapter;


public class ListDatabaseActivity extends Activity implements AdapterView.OnItemClickListener, IActivity, View.OnClickListener {

    private DatabaseModel databaseModel;
    private Vector<IUserProperty> vectorUserDatabaseProperties;
    private ListView listView;
    private ListViewAdapter listViewAdapter;
    private ImageButton imageButtonFab;
    private AdapterView.AdapterContextMenuInfo listItemInfo;
    private static final int CONTEXT_UPDATE_DATABASE_NAME_ID = ContextMenu.FIRST;
    private static final int CONTEXT_UPDATE_DATABASE_PWD_ID = ContextMenu.FIRST + 1;
    private static final int CONTEXT_DELETE_DATABASE_ID = ContextMenu.FIRST + 2;

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
        listViewAdapter.refresh(databaseModel.getUserDatabasePropertyVector());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, CONTEXT_UPDATE_DATABASE_NAME_ID, Menu.NONE, R.string.contextmenu_item_update_database_name);
        menu.add(Menu.NONE, CONTEXT_UPDATE_DATABASE_PWD_ID, Menu.NONE, R.string.contextmenu_item_update_database_password);
        menu.add(Menu.NONE, CONTEXT_DELETE_DATABASE_ID, Menu.NONE, R.string.contextmenu_item_delete_database);
    }


    /**TODO
     * Change Password -> Fuehrt zum Absturz des Programmes
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        listItemInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case 1:
                final AlertDialog.Builder alertChangeName = new AlertBuilderHelper(this, R.string.dialog_title_change_database_name, 0, true);
                //Edittext zur Benennung wird hinterlegt
                final EditText editTextRename = new EditText(this);
                editTextRename.setHint(R.string.hint_database_new_name);
                alertChangeName.setView(editTextRename);

                alertChangeName.setPositiveButton(R.string.dialog_positive_button_default, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Integer actualDbId = ((DatabaseProperty) vectorUserDatabaseProperties.get(listItemInfo.position)).getDatabaseId();
                        databaseModel.updateUserDatabaseName(actualDbId, editTextRename.getText().toString());
                        listViewAdapter.refresh(databaseModel.getUserDatabasePropertyVector());
                    }
                });
                alertChangeName.show();
                break;
            case 2:
                //final AlertBuilderHelper alertChangePwd = new AlertBuilderHelper(ListDatabaseActivity.this, R.string.dialog_title_change_database_pwd, 0, true);
                //AlertDialog.Builder builder = new AlertDialog.Builder(ListDatabaseActivity.this);

                final AlertDialog.Builder alertChangePwd = new AlertBuilderHelper(this, R.string.dialog_title_change_database_pwd, 0, true);

                //AlertDialog alertDialog = builder.create();
                //alertChangePwd.setView(R.layout.alert_dialog_change_pwd_layout);

                //final EditText editTextOldDbPwd = new EditText(this);
                //final EditText editTextNewDbPwd = new EditText(this);
                //final EditText editTextNewDbPwdRepeat = new EditText(this);

                //editTextOldDbPwd.setHint(R.string.hint_database_pwd_old);
                //editTextNewDbPwd.setHint(R.string.hint_database_pwd_new);
                //editTextNewDbPwdRepeat.setHint(R.string.hint_database_pwd_repeat);

                //alertChangePwd.setView(editTextOldDbPwd).setView(editTextNewDbPwd).setView(editTextNewDbPwdRepeat);
                //alertChangePwd.setView(editTextNewDbPwd);
                //alertChangePwd.setView(editTextNewDbPwdRepeat);


                final EditText editTextOldDbPwd = (EditText) findViewById(R.id.editTextOldPwdDb);
                final EditText editTextNewDbPwd = (EditText) findViewById(R.id.editTextNewPwdDb);
                final EditText editTextNewDbPwdRepeat = (EditText) findViewById(R.id.editTextNewPwdDbRepeat);

                //ImageButton imageButtonShowDbPwd = (ImageButton) findViewById(R.id.imageButtonViewPwd);
                //Toast.makeText(ListDatabaseActivity.this, String.valueOf(editTextOldDbPwd), Toast.LENGTH_SHORT).show();
                alertChangePwd.setPositiveButton(R.string.dialog_positive_button_default, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String oldPwd = editTextOldDbPwd.getText().toString();
                        String newPwd = editTextNewDbPwd.getText().toString();
                        String newPwdRepeat = editTextNewDbPwdRepeat.getText().toString();

                        String databasePwd = ((DatabaseProperty) vectorUserDatabaseProperties.get(listItemInfo.position)).getDatabasePwd();
                        Integer databaseId = ((DatabaseProperty) vectorUserDatabaseProperties.get(listItemInfo.position)).getDatabaseId();

                        String newEncryptPassword = Security.getInstance().encryptPassword(newPwd);
                        //if(Security.getInstance().checkPassword(oldPwd, databasePwd)
                                //&& newPwd.equals(newPwdRepeat) && newPwd.length() >= 8 ){

                            databaseModel.updateUserDatabasePwd(databaseId, newEncryptPassword);

                        // }else{
                        //    Toast.makeText(ListDatabaseActivity.this, R.string.toast_message_wrong_password, Toast.LENGTH_SHORT).show();
                        //}
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
                        if(Security.getInstance().checkPassword(editTextDatabasePwd.getText().toString(), ((DatabaseProperty) vectorUserDatabaseProperties.get(listItemInfo.position)).getDatabasePwd()) ){
                            databaseModel.deleteUserDatabase(((DatabaseProperty) vectorUserDatabaseProperties.get(listItemInfo.position)).getDatabaseId());
                            vectorUserDatabaseProperties.remove(listItemInfo.position);
                            listViewAdapter.refresh(databaseModel.getUserDatabasePropertyVector());
                        }else{
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
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        final AlertDialog.Builder alertDialog = new AlertBuilderHelper(this, R.string.dialog_title_open_database, R.string.dialog_message_open_database, true);
        final EditText editText = new EditText(this);
        editText.setHint(R.string.hint_database_pwd);
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editText.setTransformationMethod(new PasswordTransformationMethod());
        alertDialog.setView(editText);
        alertDialog.setPositiveButton(R.string.dialog_positive_button_default, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (Security.getInstance().checkPassword(editText.getText().toString(), ((DatabaseProperty) vectorUserDatabaseProperties.get(position)).getDatabasePwd())) {
                    Intent intentListCategoryActivity = new Intent(ListDatabaseActivity.this, ListCategoryActivity.class)
                            .putExtra("databaseId", ((DatabaseProperty) vectorUserDatabaseProperties.get(position)).getDatabaseId())
                            .putExtra("databasePwd", editText.getText().toString());
                    startActivity(intentListCategoryActivity);
                }else{
                    Toast.makeText(ListDatabaseActivity.this, R.string.toast_message_wrong_password, Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.show();
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
        vectorUserDatabaseProperties = new Vector<>();
        vectorUserDatabaseProperties.addAll(databaseModel.getUserDatabasePropertyVector());
    }

    @Override
    public void populateView() {
        setContentView(R.layout.activity_listview_layout);
        imageButtonFab = (ImageButton) findViewById(R.id.imageButtonFab);
        listView = (ListView) findViewById(R.id.listViewDefault);
        listViewAdapter = new ListViewAdapter(ListDatabaseActivity.this, databaseModel.getUserDatabasePropertyVector());
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(this);
        imageButtonFab.setOnClickListener(this);
        registerForContextMenu(listView);
    }
}
