package de.szut.passkeeper.Utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import de.szut.passkeeper.R;

/**
 * Created by Sami.Al-Khatib on 18.03.2015.
 */
public class AlertBuilderHelper extends AlertDialog.Builder {

    /**
     * @param context
     * @param title
     * @param message
     */
    public AlertBuilderHelper(Context context, int title, int message, boolean setNegative) {
        super(context);
        this.setTitle(title);
        this.setMessage(message);

        if (setNegative) {
            this.setNegativeButton(R.string.dialog_negative_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
    }
}
