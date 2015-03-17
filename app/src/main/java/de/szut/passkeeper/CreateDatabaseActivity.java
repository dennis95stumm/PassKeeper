package de.szut.passkeeper;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;


public class CreateDatabaseActivity extends Activity implements TextWatcher, View.OnClickListener{

    private EditText editTextDatabaseName;
    private EditText editTextDatabasePwd;
    private Button buttonCreateNewDatabase;
    private Button testButton;
    private DatabaseModel databaseModel;
    private boolean testClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_database_layout);
        databaseModel = new DatabaseModel(this);
        this.initializeView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createNewDatabaseBtn:
                buttonCreateNewDatabase.setEnabled(false);
                try {
                    databaseModel.createPassDatabase(new UserDatabaseProperties(editTextDatabaseName.getText().toString(), editTextDatabasePwd.getText().toString()));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.TestButton:
                //editTextDatabasePwd.setInputType(InputType.TYPE_CLASS_TEXT);
                //editTextDatabasePwd.setTransformationMethod(null);
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

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

    }

    private void initializeView() {
        editTextDatabaseName = (EditText) findViewById(R.id.newDatabaseName);
        editTextDatabasePwd = (EditText) findViewById(R.id.newDatabasePwd);
        buttonCreateNewDatabase = (Button) findViewById(R.id.createNewDatabaseBtn);
        testButton = (Button) findViewById(R.id.TestButton);

        editTextDatabaseName.addTextChangedListener(this);
        editTextDatabasePwd.addTextChangedListener(this);
        buttonCreateNewDatabase.setOnClickListener(this);
        testButton.setOnTouchListener(new CustomTouchListener(editTextDatabasePwd));
    }
}
