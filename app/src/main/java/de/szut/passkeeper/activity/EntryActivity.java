package de.szut.passkeeper.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import javax.crypto.SecretKey;

import de.szut.passkeeper.R;
import de.szut.passkeeper.interfaces.IActivity;
import de.szut.passkeeper.model.DatabaseModel;
import de.szut.passkeeper.model.Security;
import de.szut.passkeeper.property.EntryProperty;
import de.szut.passkeeper.utility.AlertBuilderHelper;
import de.szut.passkeeper.utility.GeneratePwdClickListener;
import de.szut.passkeeper.utility.ViewPwdTouchListener;

public class EntryActivity extends Activity implements IActivity {

    private static final int NOTIFICATION_ID = 0;
    private static final String EXTRA_PWD = "PWD";
    private static final String EXTRA_NAME = "NAME";
    public static final String USERNAME_CLICKED = "UsernameClicked";
    public static final String PASSWORD_CLICKED = "PasswordClicked";
    public static final String RETURN_TO_ACTIVITY = "ReturnToActivity";
    private EntryProperty entryProperty;
    private EditText editTextEntryTitle;
    private EditText editTextEntryUsername;
    private EditText editTextEntryPwd;
    private EditText editTextEntryNote;
    private ProgressDialog progressDialog;
    private DatabaseModel databaseModel;
    private int databaseId;
    private int categoryId;
    private int entryId;
    private String decryptedUsername;
    private String decryptedUserPwd;
    private String databasePwd;
    private boolean hasDecrypted;
    private NotificationManager notificationManager;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaults();
        new BackgroundTask().execute();
    }


    public void setNotification() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //Intent notificationIntent = new Intent(this, NotificationActivity.class);
        Intent userNameIntent = new Intent(USERNAME_CLICKED);
        Intent passwordIntent = new Intent(PASSWORD_CLICKED);
        this.getIntent().setAction(RETURN_TO_ACTIVITY);
                //notificationIntent.setData(Uri.parse(String.valueOf(decryptedUsername) + "||" +  String.valueOf(decryptedUserPwd)));
       // notificationIntent.putExtra(EXTRA_PWD, String.valueOf(decryptedUserPwd));
        //notificationIntent.putExtra(EXTRA_NAME, String.valueOf(decryptedUsername));
        //Bundle extras = notificationIntent.getExtras();
        PendingIntent userIntent = PendingIntent.getBroadcast(this,0,userNameIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent passIntent = PendingIntent.getBroadcast(this,0,passwordIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent thisIntent = PendingIntent.getBroadcast(this,0,this.getIntent(),PendingIntent.FLAG_UPDATE_CURRENT);
        //PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_NO_CREATE);//PendingIntent.FLAG_UPDATE_CURRENT);



        //Intent nextIntent = new Intent("test");
        //PendingIntent nextPendingIntent = PendingIntent.getBroadcast(this, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        IntentFilter filter = new IntentFilter();
        filter.addAction(USERNAME_CLICKED);
        filter.addAction(PASSWORD_CLICKED);
        //filter.addAction(RETURN_TO_ACTIVITY);


        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        //mBuilder.addExtras(extras);
        mBuilder.addAction(0, "Username?", userIntent);
        mBuilder.addAction(0, "Password?", passIntent);
        mBuilder.setSmallIcon(R.drawable.ic_launcher);
        mBuilder.setContentTitle("Passkeeper Service");
        mBuilder.setTicker("Password temporary saved");
        mBuilder.setContentText("See here Username and Password");
        mBuilder.setWhen(System.currentTimeMillis());
        mBuilder.setOnlyAlertOnce(false);
        mBuilder.setOngoing(false);
        mBuilder.setAutoCancel(false);
        mBuilder.setContentIntent(thisIntent);

        final Notification n = mBuilder.build();

        notificationManager.notify(NOTIFICATION_ID, n);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String clicked = intent.getAction();
                String toCopy = null;
                switch(clicked){
                    case PASSWORD_CLICKED:
                        toCopy = decryptedUserPwd;
                        break;
                    case USERNAME_CLICKED:
                        toCopy = decryptedUsername;
                        break;
                    default:
                        break;
                }
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", toCopy);
                Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
                context.sendBroadcast(it);
                //context.unregisterReceiver(this);
                //finish();
            }
        };
        registerReceiver(receiver, filter);
   }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.entry_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editTextEntryTitle.getWindowToken(), 0);
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.saveEntry:
                if (editTextEntryTitle.getText().length() != 0 && editTextEntryPwd.getText().length() != 0) {
                    hasDecrypted = !hasDecrypted;
                    new BackgroundTask().execute();
                } else {
                    AlertBuilderHelper alertBuilderHelper = new AlertBuilderHelper(this, R.string.dialog_title_missing_data, R.string.dialog_message_entry_required_data, false);
                    alertBuilderHelper.setPositiveButton(R.string.dialog_positive_button_default, null);
                    alertBuilderHelper.show();
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
        databaseModel = new DatabaseModel(this);
        hasDecrypted = false;
        entryId = getIntent().getExtras().getInt("entryId");
        entryProperty = databaseModel.getUserEntryProperty(entryId);
        if (entryProperty != null) {
            setTitle(R.string.title_activity_update_entry);
        }
    }

    @Override
    public void populateView() {
        setContentView(R.layout.activity_entry_layout);
        editTextEntryTitle = (EditText) findViewById(R.id.editTextEntryTitle);
        editTextEntryUsername = (EditText) findViewById(R.id.editTextEntryUsername);
        editTextEntryPwd = (EditText) findViewById(R.id.editTextEntryPwd);
        editTextEntryNote = (EditText) findViewById(R.id.editTextEntryNote);
        ImageButton imageButtonDisplayPwd = (ImageButton) findViewById(R.id.imageButtonDisplayPwd);
        ImageButton imageButtonGeneratePwd = (ImageButton) findViewById(R.id.imageButtonGeneratePwd);
        imageButtonDisplayPwd.setOnTouchListener(new ViewPwdTouchListener(editTextEntryPwd));
        imageButtonGeneratePwd.setOnClickListener(new GeneratePwdClickListener(editTextEntryPwd));
        if (entryProperty != null) {
            editTextEntryTitle.setText(entryProperty.getEntryTitle());
            editTextEntryUsername.setText(decryptedUsername);
            editTextEntryPwd.setText(decryptedUserPwd);
            editTextEntryNote.setText(entryProperty.getEntryNote());
            setNotification();
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void decryptData() {
        if (entryProperty != null) {
            String username = entryProperty.getEntryUsername();
            String password = entryProperty.getEntryPwd();
            byte[] salt = Base64.decode(entryProperty.getEntryHash(), Base64.NO_WRAP);
            SecretKey secret = Security.getInstance().getSecret(databasePwd, salt);
            byte[] iv = Base64.decode(entryProperty.getEntryIV(), Base64.NO_WRAP);
            decryptedUsername = Security.getInstance().decryptValue(username, secret, iv);
            decryptedUserPwd = Security.getInstance().decryptValue(password, secret, iv);
        }
    }

    private void encryptData() {
        String username = editTextEntryUsername.getText().toString();
        String password = editTextEntryPwd.getText().toString();
        byte[] salt = Security.getInstance().generateSalt();
        SecretKey secret = Security.getInstance().getSecret(databasePwd, salt);
        byte[] iv = Security.getInstance().generateIV();
        String encryptedUsername = Security.getInstance().encryptValue(username, secret, iv);
        String encryptedPassword = Security.getInstance().encryptValue(password, secret, iv);
        if (entryProperty != null) {
            databaseModel.updateUserEntry(new EntryProperty(
                    entryId,
                    editTextEntryTitle.getText().toString(),
                    encryptedUsername,
                    encryptedPassword,
                    Base64.encodeToString(salt, Base64.NO_WRAP),
                    editTextEntryNote.getText().toString(),
                    Base64.encodeToString(iv, Base64.NO_WRAP)
            ));
        } else {
            databaseModel.createUserEntry(new EntryProperty(
                            databaseId,
                            categoryId,
                            editTextEntryTitle.getText().toString(),
                            encryptedUsername,
                            encryptedPassword,
                            Base64.encodeToString(salt, Base64.NO_WRAP),
                            editTextEntryNote.getText().toString(),
                            R.drawable.ic_lock,
                            Base64.encodeToString(iv, Base64.NO_WRAP)
                    )
            );
        }
    }

    private class BackgroundTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(EntryActivity.this);
            progressDialog.setMessage(getResources().getString(!hasDecrypted ? R.string.dialog_loading_message_decrypting_data : R.string.dialog_loading_message_encrypting_data));
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
            if (!hasDecrypted) {
                populateView();
            } else {
                onBackPressed();
            }
        }
    }
}