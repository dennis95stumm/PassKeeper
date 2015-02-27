package de.szut.passkeeper;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;


public class CreateDatabaseActivity extends Activity implements TextWatcher, View.OnClickListener{

    private EditText editTextDatabaseName;
    private EditText editTextDatabasePwd;
    private Button buttonCreateNewDatabase;
    private DatabaseModel databaseModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_database_layout);
        databaseModel = new DatabaseModel(this);
        this.initializeViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.createNewDatabaseBtn:
                buttonCreateNewDatabase.setEnabled(false);
                try {
                    databaseModel.createDatabase(new UserDatabaseProperties(editTextDatabaseName.getText().toString(), editTextDatabasePwd.getText().toString()));
                } catch (UnsupportedEncodingException e) {
                    // TODO Fehler müssen abgefangen und an die Oberfläche gebracht werden
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //TODO add Regex for Password!
        if(editTextDatabaseName.getText().length() != 0 && editTextDatabasePwd.getText().length() >= 8){
            buttonCreateNewDatabase.setEnabled(true);
        }else{
            buttonCreateNewDatabase.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void initializeViews(){
        editTextDatabaseName = (EditText) findViewById(R.id.newDatabaseName);
        editTextDatabasePwd = (EditText) findViewById(R.id.newDatabasePwd);
        buttonCreateNewDatabase = (Button) findViewById(R.id.createNewDatabaseBtn);

        editTextDatabaseName.addTextChangedListener(this);
        editTextDatabasePwd.addTextChangedListener(this);
        buttonCreateNewDatabase.setOnClickListener(this);
    }
}
