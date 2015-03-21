package de.szut.passkeeper.Property;

import de.szut.passkeeper.Interface.IListViewType;

/**
 * Created by Sami.Al-Khatib on 09.02.2015.
 */
public class UserDatabaseProperty implements IListViewType {
    private int databaseId;
    private String databaseName;
    private String databasePwd;
    private String databaseCdate;
    private String databaseMdate;

    public UserDatabaseProperty(int databaseId, String databaseName, String databasePwd, String databaseCdate, String databaseMdate) {
        this.databaseId = databaseId;
        this.databaseName = databaseName;
        this.databasePwd = databasePwd;
        this.databaseCdate = databaseCdate;
        this.databaseMdate = databaseMdate;
    }

    public UserDatabaseProperty(String databaseName, String databasePwd) {
        this.databaseName = databaseName;
        this.databasePwd = databasePwd;
    }

    @Override
    public String getItemHeader() {
        return getDatabaseName();
    }

    @Override
    public String getItemSubHeader() {
        return getDatabaseMdate();
    }

    public int getDatabaseId() {
        return databaseId;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getDatabasePwd() {
        return databasePwd;
    }

    public String getDatabaseCdate() {
        return databaseCdate;
    }

    public String getDatabaseMdate() {
        return databaseMdate;
    }
}
