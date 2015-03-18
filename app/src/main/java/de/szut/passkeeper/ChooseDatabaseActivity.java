package de.szut.passkeeper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


public class ChooseDatabaseActivity extends Activity implements AdapterView.OnItemClickListener {

    private DatabaseModel databaseModel;
    private ArrayList<UserDatabaseProperty> listUserDatabaseProperties;
    private ListView listViewDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_database_layout);
        this.initializeView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose_database_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.addDatabase) {
            startActivity(new Intent(this, CreateDatabaseActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id){
        AlertDialog.Builder alertDialog = new AlertBuilderHelper(this, R.string.dialog_title_open_database, R.string.dialog_message_open_database);
        final EditText editText = new EditText(this);
        editText.setHint(R.string.hint_database_pwd);
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editText.setTransformationMethod(new PasswordTransformationMethod());
        alertDialog.setView(editText);
        alertDialog.setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which){
                try {
                    if (Security.getInstance().checkPassword(editText.getText().toString(), listUserDatabaseProperties.get(position).getDatabasePwd()))
                        Toast.makeText(getApplicationContext(), "YO DAWG!", Toast.LENGTH_SHORT).show();
                } catch (UnsupportedEncodingException exception){
                    exception.printStackTrace();
                } catch(NoSuchAlgorithmException exception){
                    exception.printStackTrace();
                }
            }
        });
        alertDialog.show();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Toast.makeText(getApplicationContext(), "Context Menu", Toast.LENGTH_SHORT).show();
        return super.onContextItemSelected(item);
    }

    private void initializeView() {
        databaseModel = new DatabaseModel(getApplicationContext());
        listUserDatabaseProperties = databaseModel.getUserDatabasePropertyList();
        listViewDatabase = (ListView) findViewById(R.id.listViewDatabase);
        listViewDatabase.setAdapter(new CustomListViewAdapter(listUserDatabaseProperties, this));
        listViewDatabase.setOnItemClickListener(this);
        registerForContextMenu(listViewDatabase);
    }
}
