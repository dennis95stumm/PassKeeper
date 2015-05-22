package de.szut.passkeeper.interfaces;

/**
 * This is a generic interface for all available activities
 * Every single activity sets defaults and populates a view when the activity is created
 * The functions for this cause will always be named the same way
 */
public interface IActivity {
    /**
     * Set the default values of an activity
     */
    void setDefaults();

    /**
     * populate view elements
     */
    void populateView();
}
