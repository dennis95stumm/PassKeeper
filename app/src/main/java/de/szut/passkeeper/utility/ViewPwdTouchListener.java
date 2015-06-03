package de.szut.passkeeper.utility;

import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * This class implements a custom touchlistener to hide or show a textfields password
 */
public class ViewPwdTouchListener implements View.OnTouchListener {
    private EditText editText;

    /**
     * Constructor.
     * @param editText the textfield where the password should be shown or hidden
     */
    public ViewPwdTouchListener(EditText editText) {
        this.editText = editText;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            // When the clicked view has been released hide the password
            case MotionEvent.ACTION_UP:
                editText.setTransformationMethod(new PasswordTransformationMethod());
                editText.setSelection(editText.length());
                break;
            // When the clicked view has been released display the password
            case MotionEvent.ACTION_DOWN:
                editText.setTransformationMethod(null);
                editText.setSelection(editText.length());
                break;
        }
        return false;
    }
}
