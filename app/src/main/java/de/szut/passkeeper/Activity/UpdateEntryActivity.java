package de.szut.passkeeper.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Calendar;

import de.szut.passkeeper.Interface.IActivity;
import de.szut.passkeeper.Model.DatabaseModel;
import de.szut.passkeeper.Model.Security;
import de.szut.passkeeper.Property.EntryProperty;
import de.szut.passkeeper.R;
import de.szut.passkeeper.Utility.TouchListener;

public class UpdateEntryActivity extends Activity implements IActivity {

    private EntryProperty entryProperty;
    private EditText editTextEntryTitle;
    private EditText editTextEntryUsername;
    private EditText editTextEntryPwd;
    private EditText editTextEntryNote;
    private ImageButton imageButtonDisplayPwd;
    private ProgressDialog progressDialog;
    private DatabaseModel databaseModel;
    private int databaseId;
    private int categoryId;
    private int entryId;
    private String decryptedUsername;
    private String decryptedUserPwd;
    private String databasePwd;
    private boolean hasDecrypted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaults();
        new BackgroundTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.update_entry_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.updateEntry:
                if (editTextEntryTitle.getText().length() != 0 && editTextEntryPwd.getText().length() != 0) {
                    hasDecrypted = !hasDecrypted;
                    new BackgroundTask().execute();
                } else {
                    //Toast.makeText(this, R.string.toast_entry_required_message, Toast.LENGTH_SHORT).show();
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
        databasePwd = getIntent().getExtras().getString("databasePwd");
        entryId = getIntent().getExtras().getInt("entryId");
        hasDecrypted = false;
        databaseModel = new DatabaseModel(this);
        entryProperty = databaseModel.getUserEntryProperty(entryId);
    }

    @Override
    public void populateView() {
        setContentView(R.layout.activity_update_entry_layout);
        editTextEntryTitle = (EditText) findViewById(R.id.editTextEntryTitle);
        editTextEntryUsername = (EditText) findViewById(R.id.editTextEntryUsername);
        editTextEntryPwd = (EditText) findViewById(R.id.editTextEntryPwd);
        editTextEntryNote = (EditText) findViewById(R.id.editTextEntryNote);
        imageButtonDisplayPwd = (ImageButton) findViewById(R.id.imageButtonDisplayPwd);
        editTextEntryTitle.setText(entryProperty.getEntryTitle());
        editTextEntryUsername.setText(decryptedUsername);
        editTextEntryPwd.setText(decryptedUserPwd);
        editTextEntryNote.setText(entryProperty.getEntryNote());
        imageButtonDisplayPwd.setOnTouchListener(new TouchListener(editTextEntryPwd));
    }

    private void decryptData() {
        String username = entryProperty.getEntryUsername();
        String password = entryProperty.getEntryPwd();
        byte[] salt;
        salt = Base64.decode(entryProperty.getEntryHash(), Base64.DEFAULT);
        decryptedUsername = Security.getInstance().decryptValue(databasePwd, username, salt);
        decryptedUserPwd = Security.getInstance().decryptValue(databasePwd, password, salt);
    }

    private void encryptData() {
        Toast.makeText(UpdateEntryActivity.this, editTextEntryUsername.getText().toString(), Toast.LENGTH_SHORT).show();
        String username = editTextEntryUsername.getText().toString();
        String password = editTextEntryPwd.getText().toString();
        byte[] salt;
        salt = Security.getInstance().generateSalt();
        String encryptedUsername = Security.getInstance().decryptValue(databasePwd, username, salt);
        String encryptedPassword = Security.getInstance().decryptValue(databasePwd, password, salt);
        entryProperty = new EntryProperty(
                entryId,
                editTextEntryTitle.getText().toString(),
                encryptedUsername,
                encryptedPassword,
                Base64.encodeToString(salt, Base64.DEFAULT),
                editTextEntryNote.getText().toString()
        );
    }

    private class BackgroundTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(UpdateEntryActivity.this);
            if (!hasDecrypted) {
                progressDialog.setMessage(getResources().getString(R.string.dialog_loading_message_decrypting_data));
            } else {
                progressDialog.setMessage(getResources().getString(R.string.dialog_loading_message_encrypting_data));
            }
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (!hasDecrypted) {
                decryptData();
            } else {
                encryptData();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aBoolean) {
            super.onPostExecute(aBoolean);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            //Toast.makeText(UpdateEntryActivity.this, "Finished", Toast.LENGTH_SHORT).show();
            if (!hasDecrypted) {
                populateView();
            } else {
                databaseModel.updateUserEntry(entryProperty);
                onBackPressed();
            }
        }
    }
}