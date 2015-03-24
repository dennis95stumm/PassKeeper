package de.szut.passkeeper.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import de.szut.passkeeper.Interface.IActivity;
import de.szut.passkeeper.Model.DatabaseModel;
import de.szut.passkeeper.Property.DatabaseProperty;
import de.szut.passkeeper.R;
import de.szut.passkeeper.Utility.TouchListener;


public class CreateDatabaseActivity extends Activity implements TextWatcher, View.OnClickListener, IActivity {

    private EditText editTextDatabaseName;
    private EditText editTextDatabasePwd;
    private Button buttonCreateNewDatabase;
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_database_layout);
        setDefaults();
        populateView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonCreateDatabase:
                buttonCreateNewDatabase.setEnabled(false);
                DatabaseModel databaseModel = new DatabaseModel(this);
                int databaseId = databaseModel.createUserDatabase(new DatabaseProperty(editTextDatabaseName.getText().toString(), editTextDatabasePwd.getText().toString(), R.drawable.ic_database), getResources().getStringArray(R.array.array_default_category_name));
                Intent intent = new Intent(CreateDatabaseActivity.this, ListCategoryActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .putExtra("databaseId", databaseId)
                        .putExtra("databaseName", databaseModel.getUserDatabaseName(databaseId));
                startActivity(intent);
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // NOT IN USE
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //TODO add Regex for Password!
        if (editTextDatabaseName.getText().length() != 0 && editTextDatabasePwd.getText().length() >= 8) {
            buttonCreateNewDatabase.setEnabled(true);
        } else {
            buttonCreateNewDatabase.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        // NOT IN USE
    }

    @Override
    public void populateView() {

    }

    @Override
    public void setDefaults() {
        editTextDatabaseName = (EditText) findViewById(R.id.editTextDatabaseName);
        editTextDatabasePwd = (EditText) findViewById(R.id.editTextDatabasePwd);
        imageButton = (ImageButton) findViewById(R.id.imageButtonDisplayPwd);
        buttonCreateNewDatabase = (Button) findViewById(R.id.buttonCreateDatabase);

        editTextDatabaseName.addTextChangedListener(this);
        editTextDatabasePwd.addTextChangedListener(this);
        imageButton.setOnTouchListener(new TouchListener(editTextDatabasePwd));
        buttonCreateNewDatabase.setOnClickListener(this);
    }
}
