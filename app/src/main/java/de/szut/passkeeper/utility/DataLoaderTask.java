package de.szut.passkeeper.utility;

import java.util.Vector;

import de.szut.passkeeper.activity.StartActivity;
import de.szut.passkeeper.interfaces.IUserProperty;

/**
 *
 */
public class DataLoaderTask {
    private StartActivity startActivity;
    private Vector<IUserProperty> propertyVector;

    /**
     * @param startActivity
     */
    public DataLoaderTask(StartActivity startActivity) {
        this.startActivity = startActivity;
    }

    /**
     *
     */
    public void startTask() {
        new Thread(new DataLoader(startActivity, this)).start();
    }

    /**
     *
     * @param state
     */
    public void handleLoadState(int state) {
        int outState = 0;
        switch (state) {
            case DataLoader.LOAD_STATE_COMPLETED:
                outState = StartActivity.TASK_COMPLETE;
                break;
        }
        startActivity.handleState(this, outState);
    }

    /**
     *
     * @return
     */
    public Vector<IUserProperty> getPropertyVector() {
        return propertyVector;
    }

    /**
     *
     * @param propertyVector
     */
    public void setPropertyVector(Vector<IUserProperty> propertyVector) {
        this.propertyVector = propertyVector;
    }
}
