package de.szut.passkeeper.Utility;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by Sami.Al-Khatib on 18.03.2015.
 */
public class AlertBuilderHelper extends AlertDialog.Builder {

    /**
     * @param context
     * @param title
     * @param message
     */
    public AlertBuilderHelper(Context context, int title, int message) {
        super(context);
        this.setTitle(title);
        this.setMessage(message);
    }
}
