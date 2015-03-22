package de.szut.passkeeper.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import de.szut.passkeeper.Interface.IActivity;
import de.szut.passkeeper.Model.DatabaseModel;
import de.szut.passkeeper.Property.DatabaseProperty;
import de.szut.passkeeper.R;


public class CreateDatabaseActivity extends Activity implements TextWatcher, View.OnClickListener, IActivity {

    private EditText editTextDatabaseName;
    private EditText editTextDatabasePwd;
    private Button buttonCreateNewDatabase;

    //TODO implement context menu

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
            case R.id.createNewDatabaseBtn:
                buttonCreateNewDatabase.setEnabled(false);
                int databaseId = new DatabaseModel(this).createPassDatabaseAndDefaultCategory(new DatabaseProperty(editTextDatabaseName.getText().toString(), editTextDatabasePwd.getText().toString()));
                Intent intent = new Intent(CreateDatabaseActivity.this, ListCategoryActivity.class);
                intent.putExtra(getResources().getString(R.string.intent_extra_database_id), databaseId);
                intent.putExtra(getResources().getString(R.string.intent_extra_database_name), editTextDatabaseName.getText().toString());
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
        editTextDatabaseName = (EditText) findViewById(R.id.newDatabaseName);
        editTextDatabasePwd = (EditText) findViewById(R.id.newDatabasePwd);
        buttonCreateNewDatabase = (Button) findViewById(R.id.createNewDatabaseBtn);

        editTextDatabaseName.addTextChangedListener(this);
        editTextDatabasePwd.addTextChangedListener(this);
        buttonCreateNewDatabase.setOnClickListener(this);
    }
}
