package de.szut.passkeeper.utility;

import android.content.Context;

import java.util.Vector;

import de.szut.passkeeper.interfaces.IUserProperty;
import de.szut.passkeeper.model.DatabaseModel;

public class DataLoader implements Runnable {
    public static final int LOAD_STATE_COMPLETED = 1;
    private DatabaseModel databaseModel;
    private DataLoaderTask task;

    public DataLoader(Context context, DataLoaderTask dataLoaderTask) {
        databaseModel = new DatabaseModel(context);
        task = dataLoaderTask;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Vector<IUserProperty> vectorUserDatabaseProperty = databaseModel.getUserDatabasePropertyVector();
        task.setPropertyVector(vectorUserDatabaseProperty);
        task.handleLoadState(LOAD_STATE_COMPLETED);
    }
}
