package de.szut.passkeeper.interfaces;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

import de.szut.passkeeper.R;

/**
 * Abstract class which contains common functionality
 * for the activities with a recycler view.
 */
public abstract class IRecyclerActivity extends Activity {
    private MenuItem editMenu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_edit_menu, menu);
        editMenu = menu.findItem(R.id.editItem);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * @return
     */
    public MenuItem getEditMenu() {
        return editMenu;
    }

    /**
     * @param password
     * @param position
     * @return
     */
    public boolean confirmRemove(String password, int position) {
        return true;
    }

    /**
     * Handles a failed remove confirmation.
     */
    public void onRemoveConfirmationFailed() {
    }

    /**
     * @param position
     * @return
     */
    public boolean editItem(int position) {
        return false;
    }

    /**
     * @return
     */
    public boolean longPressEnabled() {
        return false;
    }

    /**
     *
     * @param position
     */
    public abstract void removeItem(int position);

    /**
     * Handles a click on a item in the recycler view.
     * @param position The position of the recycler item in the recycler view
     */
    public abstract void onRecyclerItemClick(int position);
}
