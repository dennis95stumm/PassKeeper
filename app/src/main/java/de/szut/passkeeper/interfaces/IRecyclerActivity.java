package de.szut.passkeeper.interfaces;

/**
 * Created by redtiger on 02.05.15.
 */
public interface IRecyclerActivity {
    void removeItem(int position);
    void onRecyclerItemClick(int position);
    boolean confirmRemove(String password, int position);
    void onRemoveConfirmationFailed();
}
