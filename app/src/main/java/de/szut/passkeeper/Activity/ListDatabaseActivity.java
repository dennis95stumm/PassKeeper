package de.szut.passkeeper.Activity;

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

import java.util.Vector;

import de.szut.passkeeper.Interface.IActivity;
import de.szut.passkeeper.Interface.IUserProperty;
import de.szut.passkeeper.Model.DatabaseModel;
import de.szut.passkeeper.Model.Security;
import de.szut.passkeeper.Property.DatabaseProperty;
import de.szut.passkeeper.R;
import de.szut.passkeeper.Utility.AlertBuilderHelper;
import de.szut.passkeeper.Utility.ListViewAdapter;


public class ListDatabaseActivity extends Activity implements AdapterView.OnItemClickListener, IActivity {

    private DatabaseModel databaseModel;
    private Vector<IUserProperty> vectorUserDatabaseProperties;
    private ListView listView;

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
        getMenuInflater().inflate(R.menu.menu_database, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case R.id.addDatabase:
                startActivity(new Intent(this, CreateDatabaseActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        final AlertDialog.Builder alertDialog = new AlertBuilderHelper(this, R.string.dialog_title_open_database, R.string.dialog_message_open_database, true);
        final EditText editText = new EditText(this);
        editText.setHint(R.string.hint_database_pwd);
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editText.setTransformationMethod(new PasswordTransformationMethod());
        alertDialog.setView(editText);
        alertDialog.setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (Security.getInstance().checkPassword(editText.getText().toString(), ((DatabaseProperty) vectorUserDatabaseProperties.get(position)).getDatabasePwd())) {
                    Intent intent = new Intent(ListDatabaseActivity.this, ListCategoryActivity.class);
                    intent.putExtra("databaseName", ((DatabaseProperty) vectorUserDatabaseProperties.get(position)).getDatabaseName());
                    intent.putExtra("databaseId", ((DatabaseProperty) vectorUserDatabaseProperties.get(position)).getDatabaseId());
                    startActivity(intent);
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

    @Override
    public void setDefaults() {
        listView = (ListView) findViewById(R.id.listViewDefault);
        databaseModel = new DatabaseModel(getApplicationContext());
    }

    @Override
    public void populateView() {
        vectorUserDatabaseProperties = databaseModel.getUserDatabasePropertyVector();
        listView.setAdapter(new ListViewAdapter(vectorUserDatabaseProperties, this));
        listView.setOnItemClickListener(this);
        registerForContextMenu(listView);
    }
}
