package de.szut.passkeeper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

/**
 * Created by Sami.Al-Khatib on 18.03.2015.
 */
public class AlertBuilderHelper extends AlertDialog.Builder {

    /**
     *
     * @param context
     * @param title
     * @param message
     */
    public AlertBuilderHelper(Context context, int title, int message){
        super(context);
        this.setTitle(title);
        this.setMessage(message);
    }
}
