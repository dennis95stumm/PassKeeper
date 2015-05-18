package de.szut.passkeeper.interfaces;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

import de.szut.passkeeper.R;

/**
 *
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
     *
     * @param position
     */
    public abstract void removeItem(int position);

    /**
     *
     * @param position
     */
    public abstract void onRecyclerItemClick(int position);

    /**
     *
     * @param password
     * @param position
     * @return
     */
    public boolean confirmRemove(String password, int position) {
        return true;
    }

    public void onRemoveConfirmationFailed() {
    }

    /**
     *
     * @param position
     * @return
     */
    public boolean editItem(int position) {
        return false;
    }

    /**
     *
     * @return
     */
    public boolean longPressEnabled() {
        return false;
    }
}
