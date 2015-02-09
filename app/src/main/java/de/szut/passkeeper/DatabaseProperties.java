package de.szut.passkeeper;

import java.util.Vector;

/**
 * Created by Sami.Al-Khatib on 09.02.2015.
 */
public class DatabaseProperties {
    private int databaseId;
    private String databaseName;
    private String databasePwd;

    public DatabaseProperties(int databaseId, String databaseName, String databasePwd){
        this.databaseId = databaseId;
        this.databaseName = databaseName;
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
}
