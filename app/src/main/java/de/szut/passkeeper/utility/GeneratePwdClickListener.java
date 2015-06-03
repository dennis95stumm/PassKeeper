package de.szut.passkeeper.utility;

import android.view.View;
import android.widget.EditText;

import de.szut.passkeeper.model.Security;

/**
 * Listener for the generate password button.
 */
public class GeneratePwdClickListener implements View.OnClickListener {
    private EditText editText;

    /**
     * Constructor.
     * @param editText the textfield where the generated password is set
     */
    public GeneratePwdClickListener(EditText editText) {
        this.editText = editText;
    }


    @Override
    public void onClick(View v) {
        // set the generated password to the view;
        editText.setText(Security.getInstance().generatePassword());
    }
}
