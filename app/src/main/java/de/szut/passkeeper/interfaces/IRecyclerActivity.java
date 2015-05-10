package de.szut.passkeeper.interfaces;

import android.app.Activity;

/**
 * Created by redtiger on 02.05.15.
 */
public abstract class IRecyclerActivity extends Activity {
    public abstract void removeItem(int position);

    public abstract void onRecyclerItemClick(int position);

    public boolean confirmRemove(String password, int position) {
        return true;
    }

    public void onRemoveConfirmationFailed() {
    }
}
