package de.szut.passkeeper;

import java.util.Date;
import java.util.Vector;

/**
 * Created by Sami.Al-Khatib on 09.02.2015.
 */
public class DatabaseProperties {
    private int databaseId;
    private String databaseName;
    private String databasePwd;
    private Date databaseCdate;
    private Date databaseMdate;

    public DatabaseProperties(int databaseId, String databaseName, String databasePwd, Date databaseCdate, Date databaseMdate){
        this.databaseId = databaseId;
        this.databaseName = databaseName;
        this.databasePwd = databasePwd;
        this.databaseCdate = databaseCdate;
        this.databaseMdate = databaseMdate;
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
