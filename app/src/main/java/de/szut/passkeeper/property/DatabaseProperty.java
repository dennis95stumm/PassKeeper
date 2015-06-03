package de.szut.passkeeper.property;

import de.szut.passkeeper.interfaces.IUserProperty;

/**
 * This class holds the properties of an logical user-database
 */
public class DatabaseProperty implements IUserProperty {
    private int databaseId;
    private String databaseName;
    private String databasePwd;
    private int databaseIconId;
    private String databaseModifyDate;

    /**
     * Constructor.
     * @param databaseId the id of the database
     * @param databaseName the database name of the database
     * @param databasePwd the password of the database
     * @param databaseIconId the icon of the database
     * @param databaseModifyDate the modification date of the databse
     */
    public DatabaseProperty(int databaseId, String databaseName, String databasePwd, int databaseIconId, String databaseModifyDate) {
        this.databaseId = databaseId;
        this.databaseName = databaseName;
        this.databasePwd = databasePwd;
        this.databaseIconId = databaseIconId;
        this.databaseModifyDate = databaseModifyDate;
    }

    /**
     * Constructor.
     * @param databaseName the database name of the database
     * @param databasePwd the password of the database
     * @param databaseIconId the icon of the database
     */
    public DatabaseProperty(String databaseName, String databasePwd, int databaseIconId) {
        this.databaseName = databaseName;
        this.databasePwd = databasePwd;
        this.databaseIconId = databaseIconId;
    }

    @Override
    public String getItemHeader() {
        return getDatabaseName();
    }

    @Override
    public String getItemSubHeader() {
        return getDatabaseModifyDate();
    }

    @Override
    public int getItemImage() {
        return getDatabaseIconId();
    }

    /**
     * @return the database id
     */
    public int getDatabaseId() {
        return databaseId;
    }

    /**
     * @return the database name
     */
    public String getDatabaseName() {
        return databaseName;
    }

    /**
     * @param databaseName set the databasename
     */
    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    /**
     *
     * @return get the database password
     */
    public String getDatabasePwd() {
        return databasePwd;
    }

    /**
     * @param databasePwd set the database password
     */
    public void setDatabasePwd(String databasePwd) {
        this.databasePwd = databasePwd;
    }

    /***
     * @return get the database icon id
     */
    public int getDatabaseIconId() {
        return databaseIconId;
    }

    /**
     * @return get the database modification date
     */
    public String getDatabaseModifyDate() {
        return databaseModifyDate;
    }
}
