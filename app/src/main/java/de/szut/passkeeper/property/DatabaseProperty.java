package de.szut.passkeeper.property;

import de.szut.passkeeper.interfaces.IUserProperty;

/**
 *
 */
public class DatabaseProperty implements IUserProperty {
    private int databaseId;
    private String databaseName;
    private String databasePwd;
    private int databaseIconId;
    private String databaseModifyDate;

    /**
     * @param databaseId
     * @param databaseName
     * @param databasePwd
     * @param databaseIconId
     * @param databaseModifyDate
     */
    public DatabaseProperty(int databaseId, String databaseName, String databasePwd, int databaseIconId, String databaseModifyDate) {
        this.databaseId = databaseId;
        this.databaseName = databaseName;
        this.databasePwd = databasePwd;
        this.databaseIconId = databaseIconId;
        this.databaseModifyDate = databaseModifyDate;
    }

    /**
     *
     * @param databaseName
     * @param databasePwd
     * @param databaseIconId
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
     *
     * @return
     */
    public int getDatabaseId() {
        return databaseId;
    }

    /**
     *
     * @return
     */
    public String getDatabaseName() {
        return databaseName;
    }

    /**
     *
     * @param databaseName
     */
    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    /**
     *
     * @return
     */
    public String getDatabasePwd() {
        return databasePwd;
    }

    /**
     *
     * @param databasePwd
     */
    public void setDatabasePwd(String databasePwd) {
        this.databasePwd = databasePwd;
    }

    /**
     *
     * @return
     */
    public int getDatabaseIconId() {
        return databaseIconId;
    }

    /**
     *
     * @return
     */
    public String getDatabaseModifyDate() {
        return databaseModifyDate;
    }
}
