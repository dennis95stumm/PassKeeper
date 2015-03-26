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

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
    private String decryptedUserName;
    private String decryptedUserPwd;
    private String databasePwd;
    private BackgroundTask backgroundTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaults();
        backgroundTask = new BackgroundTask();
        backgroundTask.execute(true);
        setContentView(R.layout.activity_update_entry_layout);
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
            case R.id.menuItemEntrySave:
                if (editTextEntryTitle.getText().length() != 0 && editTextEntryUsername.getText().length() != 0 & editTextEntryPwd.getText().length() != 0) {
                    new BackgroundTask().execute();
                } else {
                    Toast.makeText(this, "Titel, Username and Password must be given!", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(UpdateEntryActivity.this, ListEntryActivity.class)
                .putExtra("databaseId", databaseId)
                .putExtra("categoryId", categoryId);
        startActivity(intent);

    }

    @Override
    public void setDefaults() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        databaseId = getIntent().getExtras().getInt("databaseId");
        categoryId = getIntent().getExtras().getInt("categoryId");
        databasePwd = getIntent().getExtras().getString("databasePwd");
        entryId = getIntent().getExtras().getInt("entryId");
        databaseModel = new DatabaseModel(this);
        entryProperty = databaseModel.getUserEntryProperty(entryId);
    }

    @Override
    public void populateView() {
        editTextEntryTitle = (EditText) findViewById(R.id.editTextEntryTitle);
        editTextEntryUsername = (EditText) findViewById(R.id.editTextEntryUsername);
        editTextEntryPwd = (EditText) findViewById(R.id.editTextEntryPwd);
        editTextEntryNote = (EditText) findViewById(R.id.editTextEntryNote);
        imageButtonDisplayPwd = (ImageButton) findViewById(R.id.imageButtonDisplayPwd);
        Toast.makeText(this, entryProperty.getEntryHash(), Toast.LENGTH_SHORT).show();
        editTextEntryTitle.setText(entryProperty.getEntryTitle());
        editTextEntryUsername.setText(decryptedUserName);
        editTextEntryPwd.setText(decryptedUserPwd);
        editTextEntryNote.setText(entryProperty.getEntryNote());
        imageButtonDisplayPwd.setOnTouchListener(new TouchListener(editTextEntryPwd));
    }

    public void decryptData() {
        String username = entryProperty.getEntryUserName();
        String password = entryProperty.getEntryPwd();
        byte[] salt;
        salt = Base64.decode(entryProperty.getEntryHash(), Base64.DEFAULT);
        decryptedUserName = Security.getInstance().decryptValue(databasePwd, username, salt);
        decryptedUserPwd = Security.getInstance().decryptValue(databasePwd, password, salt);
        progressDialog.dismiss();
    }

    private class BackgroundTask extends AsyncTask<Boolean, Boolean, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(UpdateEntryActivity.this);
            progressDialog.setMessage(getResources().getString(R.string.dialog_loading_message_decrypting_data));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(Boolean... params) {
            if (params[0]) {
                decryptData();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
           populateView();
        }
    }
}