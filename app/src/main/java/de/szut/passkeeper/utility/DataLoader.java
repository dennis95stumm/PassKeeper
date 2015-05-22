package de.szut.passkeeper.utility;

import android.content.Context;

import java.util.Vector;

import de.szut.passkeeper.interfaces.IUserProperty;
import de.szut.passkeeper.model.DatabaseModel;

/**
 * Runnable class that loads the information of the password databases for the list.
 */
public class DataLoader implements Runnable {
    public static final int LOAD_STATE_COMPLETED = 1;
    private DatabaseModel databaseModel;
    private DataLoaderTask task;

    /**
     * Constructor. Initializes the class attributes.
     * @param context Context for which the data will be loaded from the database
     * @param dataLoaderTask Data loader task that should handle data load state
     */
    public DataLoader(Context context, DataLoaderTask dataLoaderTask) {
        databaseModel = new DatabaseModel(context);
        task = dataLoaderTask;
    }

    @Override
    public void run() {
        try {
            /* Sleep the thread so that the loading animation will
             * be displayed even when data is loaded fast.
             */
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Vector<IUserProperty> vectorUserDatabaseProperty = databaseModel.getUserDatabasePropertyVector();
        task.setPropertyVector(vectorUserDatabaseProperty);
        task.handleLoadState(LOAD_STATE_COMPLETED);
    }
}
