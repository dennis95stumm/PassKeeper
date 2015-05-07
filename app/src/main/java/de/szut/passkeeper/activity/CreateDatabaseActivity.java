package de.szut.passkeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;

import de.szut.passkeeper.R;
import de.szut.passkeeper.interfaces.IActivity;
import de.szut.passkeeper.model.DatabaseModel;
import de.szut.passkeeper.property.CategoryProperty;
import de.szut.passkeeper.property.DatabaseProperty;
import de.szut.passkeeper.utility.AlertBuilderHelper;
import de.szut.passkeeper.utility.ViewPwdTouchListener;


public class CreateDatabaseActivity extends Activity implements IActivity {

    private EditText editTextDatabaseName;
    private EditText editTextDatabasePwd;
    private ImageButton imageButton;
    private MenuItem saveDatabaseItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaults();
        populateView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_database_menu, menu);
        saveDatabaseItem = menu.findItem(R.id.menuItemDatabaseSave);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.menuItemDatabaseSave:
                if (editTextDatabaseName.getText().length() != 0 && editTextDatabasePwd.getText().length() >= 8) {
                    DatabaseModel databaseModel = new DatabaseModel(this);
                    int databaseId = databaseModel.createUserDatabase(new DatabaseProperty(editTextDatabaseName.getText().toString(), editTextDatabasePwd.getText().toString(), R.drawable.ic_database));
                    for (String categoryName : getResources().getStringArray(R.array.array_default_category_name)) {
                        databaseModel.createUserCategory(new CategoryProperty(
                                databaseId,
                                categoryName,
                                R.drawable.ic_folder
                        ));
                    }
                    Intent intentListCategory = new Intent(CreateDatabaseActivity.this, ListCategoryActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .putExtra("databaseId", databaseId)
                            .putExtra("databasePwd", editTextDatabasePwd.getText().toString());
                    startActivity(intentListCategory);
                    finish();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void setDefaults() {
    }

    @Override
    public void populateView() {
        setContentView(R.layout.activity_create_database_layout);
        editTextDatabaseName = (EditText) findViewById(R.id.editTextDatabaseName);
        editTextDatabasePwd = (EditText) findViewById(R.id.editTextDatabasePwd);
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
                    saveDatabaseItem.setEnabled(editTextDatabasePwd.getError() == null && !editTextDatabasePwd.getText().toString().isEmpty());
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
                if (!s.toString().matches("((?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!\"#$%&'\\(\\)\\*\\+,\\-\\./:;<=>\\?@\\[\\\\\\]\\^_`\\{\\|\\}~]).{8,16})")) {
                    editTextDatabasePwd.setError(getText(R.string.bad_password));
                    saveDatabaseItem.setEnabled(false);
                } else {
                    editTextDatabasePwd.setError(null);
                    saveDatabaseItem.setEnabled(!editTextDatabaseName.getText().toString().isEmpty());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        imageButton = (ImageButton) findViewById(R.id.imageButtonDisplayPwd);
        imageButton.setOnTouchListener(new ViewPwdTouchListener(editTextDatabasePwd));
    }
}
