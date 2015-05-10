package de.szut.passkeeper.utility;

import android.view.View;
import android.widget.EditText;

import de.szut.passkeeper.model.Security;

public class GeneratePwdClickListener implements View.OnClickListener {

    private EditText editText;

    public GeneratePwdClickListener(EditText editText) {
        this.editText = editText;
    }


    @Override
    public void onClick(View v) {
        editText.setText(Security.getInstance().generatePassword());
    }
}
