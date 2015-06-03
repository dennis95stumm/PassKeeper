package de.szut.passkeeper.interfaces;

/**
 * This is a generic interface used to create a single recyler-adapter
 * This interface is used by the classes {@link de.szut.passkeeper.property.DatabaseProperty} {@link de.szut.passkeeper.property.CategoryProperty} {@link de.szut.passkeeper.property.EntryProperty}
 */
public interface IUserProperty {
    /**
     * @return the item header for an recyler-item
     */
    String getItemHeader();

    /**
     * @return the item sub-header for an recyler-item
     */
    String getItemSubHeader();

    /**
     * @return the item image for an recyler-item
     */
    int getItemImage();
}
