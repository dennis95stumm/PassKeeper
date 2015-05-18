package de.szut.passkeeper.utility;

import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 *
 */
public class ViewPwdTouchListener implements View.OnTouchListener {
    private EditText editText;

    /**
     * @param editText
     */
    public ViewPwdTouchListener(EditText editText) {
        this.editText = editText;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                editText.setTransformationMethod(new PasswordTransformationMethod());
                editText.setSelection(editText.length());
                break;
            case MotionEvent.ACTION_DOWN:
                editText.setTransformationMethod(null);
                editText.setSelection(editText.length());
                break;
        }
        return false;
    }
}
