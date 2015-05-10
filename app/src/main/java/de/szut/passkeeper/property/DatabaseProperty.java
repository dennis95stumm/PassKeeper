package de.szut.passkeeper.property;

import de.szut.passkeeper.interfaces.IUserProperty;

public class DatabaseProperty implements IUserProperty {
    private int databaseId;
    private String databaseName;
    private String databasePwd;
    private int databaseIconId;
    private String databaseModifyDate;

    public DatabaseProperty(int databaseId, String databaseName, String databasePwd, int databaseIconId, String databaseModifyDate) {
        this.databaseId = databaseId;
        this.databaseName = databaseName;
        this.databasePwd = databasePwd;
        this.databaseIconId = databaseIconId;
        this.databaseModifyDate = databaseModifyDate;
    }

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

    public int getDatabaseId() {
        return databaseId;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getDatabasePwd() {
        return databasePwd;
    }

    public int getDatabaseIconId() {
        return databaseIconId;
    }

    public String getDatabaseModifyDate() {
        return databaseModifyDate;
    }
}
