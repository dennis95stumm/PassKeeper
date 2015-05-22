package de.szut.passkeeper.utility;

import java.util.Vector;

import de.szut.passkeeper.activity.StartActivity;
import de.szut.passkeeper.interfaces.IUserProperty;

/**
 * This class is supposed load the data while the start-up screen is shown.
 */
public class DataLoaderTask {
    private StartActivity startActivity;
    private Vector<IUserProperty> propertyVector;

    /**
     * Constructor. Initializes the values.
     * @param startActivity Activity for which the task was defined.
     */
    public DataLoaderTask(StartActivity startActivity) {
        this.startActivity = startActivity;
    }

    /**
     * Starts the async task.
     */
    public void startTask() {
        new Thread(new DataLoader(startActivity, this)).start();
    }

    /**
     * Handles the state of the data loader.
     * @param state State that should be handled
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
     * Getter for the vector with the information of the password databases.
     * @return Vector that contains the data of the password databases
     */
    public Vector<IUserProperty> getPropertyVector() {
        return propertyVector;
    }

    /**
     * Setter for the vector with the information of the password databases.
     * @param propertyVector  Vector that contains the data of the password databases
     */
    public void setPropertyVector(Vector<IUserProperty> propertyVector) {
        this.propertyVector = propertyVector;
    }
}
