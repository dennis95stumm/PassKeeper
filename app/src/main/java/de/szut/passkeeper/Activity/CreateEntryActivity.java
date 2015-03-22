package de.szut.passkeeper.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Vector;

import de.szut.passkeeper.Interface.IActivity;
import de.szut.passkeeper.Model.DatabaseModel;
import de.szut.passkeeper.Model.Security;
import de.szut.passkeeper.Property.EntryProperty;
import de.szut.passkeeper.R;
import de.szut.passkeeper.Utility.TouchListener;

public class CreateEntryActivity extends Activity implements IActivity{

    private EditText editTextEntryTitle;
    private EditText editTextEntryUsername;
    private EditText editTextEntryPwd;
    private EditText editTextEntryNote;
    private ImageButton imageButtonDisplayPwd;
    private DatabaseModel databaseModel;
    private int databaseId;
    private int categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entry_layout);
        setDefaults();
        populateView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menuItemEntrySave:
                if(editTextEntryTitle.getText().length() != 0 && editTextEntryUsername.getText().length() != 0 & editTextEntryPwd.getText().length() != 0){
                    String username = editTextEntryUsername.getText().toString();
                    String password = editTextEntryPwd.getText().toString();
                    byte[] salt;
                    salt = Security.getInstance().generateSalt();
                    String hashedUsername = Security.getInstance().encryptValue(password, username, salt);
                    String hashedPassword = Security.getInstance().encryptValue(password, username, salt);
                    databaseModel.createEntry(
                            databaseId,
                            categoryId,
                            editTextEntryTitle.getText().toString(),
                            hashedUsername,
                            hashedPassword,
                            Base64.encodeToString(salt, Base64.DEFAULT),
                            editTextEntryNote.getText().toString()
                    );
                }else{
                    Toast.makeText(this, "Titel, Username and Password must be given!", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void setDefaults() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        databaseId = getIntent().getExtras().getInt("databaseId");
        categoryId = getIntent().getExtras().getInt("categoryId");
        databaseModel = new DatabaseModel(this);

        editTextEntryTitle = (EditText) findViewById(R.id.editTextEntryTitle);
        editTextEntryUsername = (EditText) findViewById(R.id.editTextEntryUsername);
        editTextEntryPwd = (EditText) findViewById(R.id.editTextEntryPwd);
        editTextEntryNote = (EditText) findViewById(R.id.editTextEntryNote);
        imageButtonDisplayPwd = (ImageButton) findViewById(R.id.imageButtonDisplayPwd);

        imageButtonDisplayPwd.setOnTouchListener(new TouchListener(editTextEntryPwd));
    }

    @Override
    public void populateView() {

    }
}
