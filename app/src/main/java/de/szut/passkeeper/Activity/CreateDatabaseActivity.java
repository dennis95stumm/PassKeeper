package de.szut.passkeeper.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import de.szut.passkeeper.Interface.IActivity;
import de.szut.passkeeper.Model.DatabaseModel;
import de.szut.passkeeper.Property.CategoryProperty;
import de.szut.passkeeper.Property.DatabaseProperty;
import de.szut.passkeeper.R;
import de.szut.passkeeper.Utility.AlertBuilderHelper;
import de.szut.passkeeper.Utility.TouchListener;


public class CreateDatabaseActivity extends Activity implements IActivity {

    private EditText editTextDatabaseName;
    private EditText editTextDatabasePwd;
    private ImageButton imageButton;

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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.menuItemDatabaseSave:
                if(editTextDatabaseName.getText().length() != 0 && editTextDatabasePwd.getText().length() >= 8){
                    DatabaseModel databaseModel = new DatabaseModel(this);
                    int databaseId = databaseModel.createUserDatabase(new DatabaseProperty(editTextDatabaseName.getText().toString(), editTextDatabasePwd.getText().toString(), R.drawable.ic_database));
                    for(String categoryName : getResources().getStringArray(R.array.array_default_category_name)){
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
                }else{
                    AlertBuilderHelper alertBuilderHelper = new AlertBuilderHelper(CreateDatabaseActivity.this, R.string.dialog_title_missing_data, R.string.dialog_message_database_required_data, false);
                    alertBuilderHelper.setPositiveButton(R.string.dialog_positive_button, null);
                    alertBuilderHelper.show();
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
        imageButton = (ImageButton) findViewById(R.id.imageButtonDisplayPwd);
        imageButton.setOnTouchListener(new TouchListener(editTextDatabasePwd));
    }
}
