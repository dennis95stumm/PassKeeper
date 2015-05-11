package de.szut.passkeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import de.szut.passkeeper.R;
import de.szut.passkeeper.interfaces.IActivity;
import de.szut.passkeeper.model.DatabaseModel;
import de.szut.passkeeper.model.Security;
import de.szut.passkeeper.property.CategoryProperty;
import de.szut.passkeeper.property.DatabaseProperty;
import de.szut.passkeeper.utility.ViewPwdTouchListener;


public class DatabaseActivity extends Activity implements IActivity {

    private EditText editTextDatabaseName;
    private EditText editTextDatabasePwd;
    private MenuItem saveDatabaseItem;
    private DatabaseModel databaseModel;
    private int databaseId;
    private DatabaseProperty databaseProperty;
    private EditText editTextDatabasePwdNew;
    private EditText editTextDatabasePwdNewRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaults();
        populateView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.database_menu, menu);
        saveDatabaseItem = menu.findItem(R.id.menuItemDatabaseSave);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editTextDatabaseName.getWindowToken(), 0);
        switch (item.getItemId()) {
            case R.id.menuItemDatabaseSave:
                if (databaseProperty != null && Security.getInstance().checkPassword(editTextDatabasePwd.getText().toString(), databaseProperty.getDatabasePwd())) {
                    databaseProperty.setDatabaseName(editTextDatabaseName.getText().toString());
                    if (!editTextDatabasePwdNew.getText.toString().isEmpty()) {
                        databaseProperty.setDatabasePwd(editTextDatabasePwdNew.getText().toString());
                    }
                    databaseModel.updateUserDatabase(databaseProperty);
                } else if (databaseProperty == null) {
                    databaseId = databaseModel.createUserDatabase(new DatabaseProperty(editTextDatabaseName.getText().toString(), editTextDatabasePwd.getText().toString(), R.drawable.ic_database));
                    for (String categoryName : getResources().getStringArray(R.array.array_default_category_name)) {
                        databaseModel.createUserCategory(new CategoryProperty(
                                databaseId,
                                categoryName,
                                R.drawable.ic_folder
                        ));
                    }
                }
                Intent intentListCategory = new Intent(DatabaseActivity.this, ListCategoryActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra("databaseId", databaseId)
                        .putExtra("databasePwd", editTextDatabasePwd.getText().toString());
                startActivity(intentListCategory);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void setDefaults() {
        databaseModel = new DatabaseModel(this);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            databaseId = extras.getInt("databaseId");
            databaseProperty = databaseModel.getUserDatabaseProperty(databaseId);
        }
    }

    @Override
    public void populateView() {
        setContentView(R.layout.activity_create_database_layout);
        editTextDatabaseName = (EditText) findViewById(R.id.editTextDatabaseName);
        editTextDatabasePwd = (EditText) findViewById(R.id.editTextDatabasePwd);
        editTextDatabasePwdNew = (EditText) findViewById(R.id.editTextNewDbPwd);
        editTextDatabasePwdNewRepeat = (EditText) findViewById(R.id.editTextNewPwdDbRepeat);
        if (databaseProperty != null) {
            editTextDatabaseName.setText(databaseProperty.getDatabaseName());
            editTextDatabasePwdNew.setVisibility(View.VISIBLE);
            editTextDatabasePwdNewRepeat.setVisibility(View.VISIBLE);
        }
        editTextDatabaseName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    editTextDatabaseName.setError(getText(R.string.bad_db_name));
                    saveDatabaseItem.setEnabled(false);
                } else {
                    editTextDatabaseName.setError(null);
                    saveDatabaseItem.setEnabled(editTextDatabasePwd.getError() == null && !editTextDatabasePwd.getText().toString().isEmpty()
                        && editTextDatabasePwdNew.getError() == null && editTextDatabasePwdNewRepeat.getError() == null
                    );
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        editTextDatabasePwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().matches("((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^0-9A-Za-z ]).{8,16})")) {
                    editTextDatabasePwd.setError(getText(R.string.bad_password));
                    saveDatabaseItem.setEnabled(false);
                } else {
                    editTextDatabasePwd.setError(null);
                    saveDatabaseItem.setEnabled(!editTextDatabaseName.getText().toString().isEmpty() && editTextDatabasePwdNew.getError() == null && editTextDatabasePwdNewRepeat.getError() == null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        editTextDatabasePwdNew.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().matches("((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^0-9A-Za-z ]).{8,16})")) {
                    editTextDatabasePwdNew.setError(getText(R.string.bad_password));
                    saveDatabaseItem.setEnabled(false);
                } else if (!s.toString().equals(editTextDatabasePwdNewRepeat.getText().toString())) {
                    editTextDatabasePwdNewRepeat.setError(getText(R.string.password_not_match));
                    saveDatabaseItem.setEnabled(false);
                } else {
                    editTextDatabasePwdNew.setError(null);
                    saveDatabaseItem.setEnabled(!editTextDatabaseName.getText().toString().isEmpty() && editTextDatabasePwd.getError() == null && !editTextDatabasePwd.getText().toString().isEmpty() && editTextDatabasePwdNewRepeat.getError() == null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        editTextDatabasePwdNewRepeat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(editTextDatabasePwdNew.getText().toString())) {
                    editTextDatabasePwdNewRepeat.setError(getText(R.string.password_not_match));
                    saveDatabaseItem.setEnabled(false);
                } else {
                    editTextDatabasePwdNewRepeat.setError(null);
                    saveDatabaseItem.setEnabled(!editTextDatabaseName.getText().toString().isEmpty() && editTextDatabasePwd.getError() == null && !editTextDatabasePwd.getText().toString().isEmpty() && editTextDatabasePwdNew.getError() == null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButtonDisplayPwd);
        imageButton.setOnTouchListener(new ViewPwdTouchListener(editTextDatabasePwd));
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
}
