package de.szut.passkeeper.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import de.szut.passkeeper.Model.DatabaseModel;
import de.szut.passkeeper.R;
import de.szut.passkeeper.Property.UserDatabaseProperty;


public class CreateDatabaseActivity extends Activity implements TextWatcher, View.OnClickListener{

    private EditText editTextDatabaseName;
    private EditText editTextDatabasePwd;
    private Button buttonCreateNewDatabase;
    private DatabaseModel databaseModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_database_layout);
        databaseModel = new DatabaseModel(this);
        this.initializeView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createNewDatabaseBtn:
                buttonCreateNewDatabase.setEnabled(false);
                try {
                    int databaseId = databaseModel.createPassDatabaseAndDefaultCategory(new UserDatabaseProperty(editTextDatabaseName.getText().toString(), editTextDatabasePwd.getText().toString()));
                    Intent intent = new Intent(CreateDatabaseActivity.this, CategoryActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("databaseId", databaseId);
                    startActivity(intent);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
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

    /**
     *
     */
    private void initializeView() {
        editTextDatabaseName = (EditText) findViewById(R.id.newDatabaseName);
        editTextDatabasePwd = (EditText) findViewById(R.id.newDatabasePwd);
        buttonCreateNewDatabase = (Button) findViewById(R.id.createNewDatabaseBtn);

        editTextDatabaseName.addTextChangedListener(this);
        editTextDatabasePwd.addTextChangedListener(this);
        buttonCreateNewDatabase.setOnClickListener(this);

    }
}
