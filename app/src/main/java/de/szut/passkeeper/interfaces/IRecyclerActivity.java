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
     * Getter for the menu item which should be shown on long press of an recycler item.
     * @return Menu item for long press gesture
     */
    public MenuItem getEditMenu() {
        return editMenu;
    }

    /**
     * Confirms an remove of a recycler item. Default is always true returned and the recycler
     * item is always deleted. Override this function if the item needs special confirmation.
     * @param password Password of the input field if one is available else null
     * @param position Position of the recycler that should be deleted
     * @return Boolean value that indicates if the recycler item can be removed
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
     * Handles an click of the menu item which is shown on long press gesture of an
     * recycler item. Override to add proper functionality to the menu item click.
     * @param position Position of the recycler item in the recycler view
     * @return Boolean value which indicates that the event was consumed
     */
    public boolean editItem(int position) {
        return false;
    }

    /**
     * Indicates if the long press gesture is enabled on the recycler view. Default
     * the long press is disabled by returning false. Override this function if
     * long press should be enabled on the recycler view.
     * @return Boolean value that indicates if long press is enabled on the recycler view
     */
    public boolean longPressEnabled() {
        return false;
    }

    /**
     * Handles an remove of a recycler item in the recycler view.
     * @param position Position of the recycler item in the recycler view
     */
    public abstract void removeItem(int position);

    /**
     * Handles an click on a item in the recycler view.
     * @param position Position of the recycler item in the recycler view
     */
    public abstract void onRecyclerItemClick(int position);
}
