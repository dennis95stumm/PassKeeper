package de.szut.passkeeper.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import de.szut.passkeeper.R;

/**
 * this is a helper for creating simple alert dialogs
 */
public class AlertBuilderHelper extends AlertDialog.Builder {
    /**
     * @param context the application context
     * @param title the title of the dialog
     * @param message the message of the dialog
     * @param setNegative if a negative clicklistener should be set
     */
    public AlertBuilderHelper(Context context, int title, int message, boolean setNegative) {
        super(context);
        this.setTitle(title);
        if (message != 0)
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
