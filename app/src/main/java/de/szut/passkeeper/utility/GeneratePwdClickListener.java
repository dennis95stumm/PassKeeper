package de.szut.passkeeper.utility;

import android.view.View;
import android.widget.EditText;

import de.szut.passkeeper.model.Security;

/**
 * Created by Sami.Al-Khatib on 17.03.2015.
 */
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
